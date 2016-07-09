//        This file is part of the Yildiz-Online project, licenced under the MIT License
//        (MIT)
//
//        Copyright (c) 2016 Grégory Van den Borre
//
//        More infos available: http://yildiz.bitbucket.org
//
//        Permission is hereby granted, free of charge, to any person obtaining a copy
//        of this software and associated documentation files (the "Software"), to deal
//        in the Software without restriction, including without limitation the rights
//        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//        copies of the Software, and to permit persons to whom the Software is
//        furnished to do so, subject to the following conditions:
//
//        The above copyright notice and this permission notice shall be included in all
//        copies or substantial portions of the Software.
//
//        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//        SOFTWARE.

package be.yildiz.client.game.engine;

import be.yildiz.client.entity.ClientEntity;
import be.yildiz.client.game.engine.gui.TranslatedGuiBuilder;
import be.yildiz.common.Color;
import be.yildiz.common.Size;
import be.yildiz.common.Version;
import be.yildiz.common.authentication.Credentials;
import be.yildiz.common.client.debug.DebugListener;
import be.yildiz.common.collections.Lists;
import be.yildiz.common.config.Configuration;
import be.yildiz.common.exeption.ResourceMissingException;
import be.yildiz.common.log.Logger;
import be.yildiz.common.resource.FileResource.FileType;
import be.yildiz.common.translation.Translation;
import be.yildiz.common.util.StringUtil;
import be.yildiz.module.graphic.*;
import be.yildiz.module.graphic.gui.EventBubblingDispatcher;
import be.yildiz.module.graphic.gui.GuiEventManager;
import be.yildiz.module.graphic.gui.View;
import be.yildiz.module.network.client.AbstractNetworkEngineClient;
import be.yildiz.module.network.client.NetworkListener;
import be.yildiz.module.network.protocol.AuthenticationRequest;
import be.yildiz.module.network.protocol.MessageWrapper;
import be.yildiz.module.network.protocol.ServerRequest;
import be.yildiz.module.sound.Playlist;
import be.yildiz.module.sound.SoundEngine;
import be.yildiz.module.sound.SoundSource;
import be.yildiz.module.window.Cursor;
import be.yildiz.module.window.WindowEngine;
import be.yildiz.shared.game.engine.AbstractGameEngine;
import be.yildiz.shared.player.Player;
import be.yildiz.shared.protocol.request.CloseSession;
import lombok.Getter;
import lombok.NonNull;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.List;

/**
 * Controller for all game logic, all other engines are called from here.
 *
 * @author Grégory Van den Borre
 */
public final class GameEngine extends AbstractGameEngine implements MessageSender {


    /**
     * Maximum frame per seconds.
     */
    private static final int FPS = 60;
    /**
     * Graphic logic.
     */
    private final GraphicEngine graphicEngine;
    /**
     * Sound logic.
     */
    private final SoundEngine soundEngine;
    /**
     * Network logic.
     */
    private final AbstractNetworkEngineClient networkEngine;
    /**
     * Renderer to notify when the graphic engine is not active.
     */
    private final List<NotRenderingListener> notRenderingListenerList = Lists.newList();
    /**
     * Manager for materials.
     */
    @Getter
    private final MaterialManager materialManager;
    /**
     * Event dispatcher.
     */
    private final GuiEventManager eventDispatcher = new EventBubblingDispatcher();
    /**
     * Current configuration.
     */
    @Getter
    private final Configuration configuration;
    private final WindowEngine windowEngine;
    private final int authenticationPort;
    @Getter
    private final TranslatedGuiBuilder guiManager;
    private final String authenticationServer;
    /**
     * True if the loop is currently running.
     */
    private boolean running;
    /**
     * Flag to check if the graphic engine must render the current frame.
     */
    private boolean rendering = true;
    /**
     * Currently active world.
     */
    @Getter
    private ClientWorld activeWorld;
    /**
     * Flag to check if the engines must run in debug mode or not.
     */
    private boolean debug;
    /**
     * Current player.
     */
    @Getter
    @NonNull
    private Player player;
    /**
     * Flag to check if engine is closed.
     */
    @Getter
    private boolean closed = false;
    private Cursor defaultCursor;

    /**
     * Full constructor, create a default world.
     *
     * @param config      Configuration.
     * @param gameVersion Version of the game.
     * @param engines     Engines to load.
     */
    public GameEngine(final Configuration config, final Version gameVersion,
                      final Engines engines, final Translation translation) {
        super(gameVersion);
        this.configuration = config;
        this.authenticationServer = config.getAuthenticationHost();
        this.authenticationPort = config.getAuthenticationPort();
        Logger.info("Initializing client game engine...");
        this.graphicEngine = engines.getGraphic();
        this.windowEngine = engines.getWindow();
        Cursor empty = new Cursor("empty", "empty.gif");
        //this.windowEngine.createCursor(empty);
        //this.windowEngine.setCursor(empty);
        this.networkEngine = engines.getNetwork();
        this.soundEngine = engines.getAudio();
        this.createWorld();
        this.materialManager = new MaterialManager(this.graphicEngine);
        // this.addResourcePath("media/brs.yzk", "engine", FileType.ZIP);
        this.guiManager = new TranslatedGuiBuilder(this.graphicEngine.getGuiBuilder(), translation);

        this.windowEngine.registerInput(eventDispatcher);
        Logger.info("Client game engine initialized.");
    }

    /**
     * Pause the rendering.
     *
     * @post rendering = false
     */
    public void pauseRender() {
        this.rendering = false;
    }

    /**
     * Restart the rendering.
     *
     * @post rendering = true
     */
    public void startRender() {
        this.rendering = true;
    }

    /**
     * Start the game loop.
     */
    @Override
    public void start() {
        Logger.info("Game engine started.");
        if (!this.running) {
            this.running = true;
            this.networkEngine.connect(this.authenticationServer, this.authenticationPort);
            this.setFrameLimiter(GameEngine.FPS);
            while (this.running) {
                this.runOneFrame();
            }
            this.close();
        }
    }

    /**
     * Run just graphic frame.
     */
    @Override
    public void runOneFrameImpl() {
        this.networkEngine.update();
        this.windowEngine.updateWindow();
        this.soundEngine.update();
        if (this.rendering) {
            this.graphicEngine.update();
        } else {
            for (int i = 0; i < this.notRenderingListenerList.size(); i++) {
                if (!this.notRenderingListenerList.get(i).renderingStopped()) {
                    this.notRenderingListenerList.remove(i);
                    i--;
                }
            }
        }
    }

    /**
     * Register a view to receive inputs.
     *
     * @param v View to register.
     */
    public void registerView(final View v) {
        this.eventDispatcher.addView(v);
    }

    public void registerMainView(final View v) {
        this.eventDispatcher.setDefaultView(v);
    }

    /**
     * Unregister a view to receive inputs.
     *
     * @param v View to unregister.
     */
    public void unregisterView(final View v) {
        this.eventDispatcher.removeView(v);
    }

    /**
     * Stop the game loop and close the engine.
     */
    public void stopGameLoop() {
        this.sendMessage(new CloseSession());
        this.running = false;
    }

    /**
     * Add a resource path. This method also update the view to ensure the
     * application stays awake when multiple calls are made.
     *
     * @param name Resource group name.
     * @param path Path to the resource.
     * @param type Resource type.
     */
    public void addResourcePath(final String name, final String path, final FileType type) {
        File f = new File(path);
        if (!f.exists()) {
            throw new ResourceMissingException(f.getAbsolutePath());
        }
        this.windowEngine.updateWindow();
        Logger.info("Registering resource group " + name);
        this.soundEngine.addResourcePath(path);
        this.graphicEngine.addResourcePath(name, path, type);
        if (type == FileType.FILE) {
            new FileParser(this.guiManager, this.materialManager, this.graphicEngine, this.soundEngine)
                    .addResourcePath(name, path);
        }
        Logger.info("Resource group " + name + " registered.");
    }

    /**
     * Create a sky box.
     *
     * @param name Unique name.
     * @param path Path to the textures to use.
     * @return The newly built sky box.
     */
    public Skybox createSkybox(final String name, final String path) {
        Logger.info("Create skybox.");
        return this.graphicEngine.createSkybox(name, path);
    }

    /**
     * Check if an entity belongs to the current player.
     *
     * @param e Entity to check.
     * @return <code>true</code> if the entity belongs to the current player.
     */
    public boolean isMine(final ClientEntity e) {
        return e.getOwner().equals(this.player.id);
    }

    /**
     * Set a debug listener, it will receive the current framerate.
     *
     * @param listener Listener to set.
     */
    public void setDebugListener(final DebugListener listener) {
        this.addFrameListener(new FrameRateDisplayer(listener, this.graphicEngine));
        this.eventDispatcher.setDebugListener(listener);
    }

    /**
     * Set the focus on a given View.
     *
     * @param view View gaining the focus.
     */
    public void setFocus(final View view) {
        this.eventDispatcher.setFocus(view);
    }

    /**
     * Create a new font.
     *
     * @param name Font name, must be unique.
     * @param path Path to the font file.
     * @param size Size of the font.
     * @return The newly created font.
     */
    public Font createFont(final String name, final String path, final int size) {
        return this.graphicEngine.createFont(name, path, size);
    }

    /**
     * Create a new font with a given color.
     *
     * @param name  Font name, must be unique.
     * @param path  Path to the font file.
     * @param size  Size of the font.
     * @param color Font color.
     * @return The newly created font.
     */
    public Font createFont(final String name, final String path, final int size, final Color color) {
        return this.graphicEngine.createFont(name, path, size, color);
    }

    /**
     * Create a new font with a given color and a random name.
     *
     * @param path  Path to the font file.
     * @param size  Size of the font.
     * @param color Font color.
     * @return The newly created font.
     */
    public Font createFont(final String path, final int size, final Color color) {
        return this.graphicEngine.createFont(StringUtil.buildRandomString("font"), path, size, color);
    }

    /**
     * Add a listener to notify of the network events.
     *
     * @param listener Listener to add.
     */
    public void addNetworkListener(final NetworkListener listener) {
        this.networkEngine.addNetworkListener(listener);
    }

    /**
     * Try to authenticate to the server, if succeed, the NetworkListener will
     * be notified.
     *
     * @param credential User credentials.
     */
    public void login(final Credentials credential) {
        this.networkEngine
                .sendMessage(new AuthenticationRequest(credential.getLogin(), credential.getHashedPassword()));
    }

    /**
     * Create a new mouse cursor.
     *
     * @param cursor Data to build the cursor.
     */
    public Cursor createCursor(Cursor cursor) {
        this.windowEngine.createCursor(cursor);
        return cursor;
    }

    /**
     * Set the mouse cursor.
     *
     * @param cursor Name of the cursor to use.
     */
    public void setCursor(final Cursor cursor) {
        this.windowEngine.setCursor(cursor);
    }

    /**
     * Set the mouse cursor visible.
     */
    public void showCursor() {
        this.windowEngine.showCursor();
    }

    /**
     * Set the mouse cursor invisible.
     */
    public void hideCursor() {
        this.windowEngine.hideCursor();
    }

    /**
     * Set the game window title.
     *
     * @param title Title to print in OS task bar.
     */
    public void setWindowTitle(final String title) {
        this.windowEngine.setWindowTitle(title);
    }

    /**
     * Set the game window icon.
     *
     * @param file Image file to use to display in the OS task bar.
     */
    public void setWindowIcon(final String file) {
        this.windowEngine.setWindowIcon(file);
    }

    /**
     * Create a new music play list.
     *
     * @param name PlayList name, must be unique.
     * @return The created PlayList.
     */
    public Playlist createPlaylist(final String name) {
        return this.soundEngine.createPlaylist(name);
    }

    /**
     * Render a graphic frame.
     */
    public void render() {
        this.graphicEngine.update();
    }

    /**
     * If a network message cannot be processed(i.e waiting for an other message
     * that should come before this one), it can be delayed to the next frame.
     *
     * @param message Message to delay.
     */
    public void delayMessageToNextFrame(final MessageWrapper message) {
        this.networkEngine.delayMessageToNextFrame(message);
    }

    /**
     * Take a print screen and save it in a file.
     */
    public void printScreen() {
        this.graphicEngine.printScreen();
    }

    /**
     * Send a network message.
     *
     * @param message Message to send to the server.
     */
    public void sendMessage(final ServerRequest message) {
        this.networkEngine.sendMessage(message);
    }

    /**
     * Send a network message.
     *
     * @param message Message to send to the server.
     */
    public void sendMessage(final String message) {
        this.networkEngine.sendMessage(message);
    }

    /**
     * Create a new World to build graphic and physic object, a default Camera
     * and view port is also built.
     *
     * @return The newly built world.
     */
    public ClientWorld createWorld() {
        final ClientWorld world = this.graphicEngine.createWorld();
        if (this.debug) {
            world.setDebugMode();
        }
        this.activeWorld = world;
        return world;
    }

    /**
     * Set the currently active camera focusing on an Entity.
     *
     * @param entity Entity to look at.
     */
    public void focusCameraOnEntity(final ClientEntity entity) {
        this.activeWorld.getDefaultCamera().setRelativePosition(entity.getPosition());
    }

    /**
     * Close all engines.
     */
    @Override
    public void close() {
        if (!this.closed) {
            Logger.info("Closing engines...");
            this.closed = true;
            this.graphicEngine.close();
            this.soundEngine.close();
            this.networkEngine.close();
            Logger.info("Engines closed.");
        }
    }

    /**
     * Add a new NotRenderingListener to be executed when the rendering is
     * paused.
     *
     * @param listener Listener to add.
     */
    public void addNotRenderingListener(final NotRenderingListener listener) {
        this.notRenderingListenerList.add(listener);
    }

    /**
     * @return The game screen size.
     */
    public Size getScreenSize() {
        return this.windowEngine.getScreenSize();
    }

    /**
     * Create a new source source from a file.
     *
     * @param file File name.
     * @return The created sound.
     */
    public SoundSource createSound(final String file) {
        return this.soundEngine.createSound(file);
    }

    /**
     * Set the current player.
     *
     * @param player Player to set.
     */
    public void setPlayer(final Player player) {
        if (this.player != null) {
            throw new InvalidParameterException("Already existing player");
        }
        this.player = player;
    }

    /**
     * Load the additional resources group(needed for compositors...).
     */
    public void addAdditionalResource() {
        this.addResourcePath("additional", "media/opr.yzk", FileType.ZIP);
    }

    /**
     * Create a selection rectangle, only one can be used.
     *
     * @param material  Material to set on rectangle border.
     * @param material2 Material to set on rectangle background.
     * @return The created rectangle.
     */
    public SelectionRectangle createSelectionRectangle(final Material material, final Material material2) {
        return this.graphicEngine.createSelectionRectangle(material, material2);
    }

    /**
     * Disconnect from the server.
     */
    public void disconnect() {
        this.networkEngine.disconnect();
    }

    public void connect(final String url, final int port) {
        this.networkEngine.connect(url, port);
    }

    public Cursor getDefaultCursor() {
        return this.defaultCursor;
    }

    public void createAndSetDefaultCursor(Cursor cursor) {
        this.defaultCursor = this.createCursor(cursor);
        this.setCursor(defaultCursor);
    }
}
