/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2018 Grégory Van den Borre
 *
 * More infos available: https://www.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify, merge,
 * publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons
 * to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE  SOFTWARE.
 */

package be.yildizgames.client.game.engine;

import be.yildizgames.common.client.config.Configuration;
import be.yildizgames.common.client.debug.DebugListener;
import be.yildizgames.common.exception.technical.ResourceMissingException;
import be.yildizgames.common.file.FileResource;
import be.yildizgames.common.file.ResourcePath;
import be.yildizgames.common.geometry.Point3D;
import be.yildizgames.common.logging.LogFactory;
import be.yildizgames.common.model.Version;
import be.yildizgames.common.util.StringUtil;
import be.yildizgames.module.audio.AudioEngine;
import be.yildizgames.module.audio.Playlist;
import be.yildizgames.module.audio.SoundSource;
import be.yildizgames.module.color.Color;
import be.yildizgames.module.coordinate.Relative;
import be.yildizgames.module.graphic.Font;
import be.yildizgames.module.graphic.GraphicEngine;
import be.yildizgames.module.graphic.GraphicWorld;
import be.yildizgames.module.graphic.NotRenderingListener;
import be.yildizgames.module.graphic.gui.GuiEventManager;
import be.yildizgames.module.graphic.gui.GuiFactory;
import be.yildizgames.module.graphic.gui.View;
import be.yildizgames.module.graphic.material.Material;
import be.yildizgames.module.graphic.material.MaterialManager;
import be.yildizgames.module.graphic.misc.SelectionRectangle;
import be.yildizgames.module.graphic.misc.Skybox;
import be.yildizgames.module.network.client.Client;
import be.yildizgames.module.network.client.NetworkListener;
import be.yildizgames.module.network.protocol.MessageWrapper;
import be.yildizgames.module.network.protocol.NetworkMessage;
import be.yildizgames.module.physics.PhysicEngine;
import be.yildizgames.module.physics.PhysicWorld;
import be.yildizgames.module.script.ScriptInterpreter;
import be.yildizgames.module.window.Cursor;
import be.yildizgames.module.window.ScreenSize;
import be.yildizgames.module.window.WindowEngine;
import be.yildizgames.shared.game.engine.AbstractGameEngine;
import be.yildizgames.shared.protocol.EngineMessageFactory;
import org.slf4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for all game logic, all other engines are called from here.
 *
 * @author Grégory Van den Borre
 */
public class GameEngine extends AbstractGameEngine implements MessageSender {

    private static final Logger LOGGER = LogFactory.getInstance().getLogger(GameEngine.class);

    /**
     * Maximum frame per seconds.
     */
    private static final int FPS = 60;

    private final ScriptInterpreter scriptInterpreter;

    private final WindowEngine windowEngine;

    /**
     * Graphic logic.
     */
    private final GraphicEngine graphicEngine;

    private final PhysicEngine physicEngine;

    /**
     * Sound logic.
     */
    private final AudioEngine soundEngine;

    /**
     * Network logic.
     */
    private final Client networkEngine;

    /**
     * Renderer to notify when the graphic engine is not active.
     */
    private final List<NotRenderingListener> notRenderingListenerList = new ArrayList<>();

    /**
     * Current configuration.
     */
    private final Configuration configuration;

    private final GuiFactory guiManager;

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
    private ClientWorld activeWorld;

    /**
     * Flag to check if the engines must run in debug mode or not.
     */
    private boolean debug;

    /**
     * Flag to check if engine is closed.
     */
    private boolean closed = false;

    private Cursor defaultCursor;

    private final EngineMessageFactory messageFactory = new EngineMessageFactory();

    /**
     * Full constructor, create a default world.
     *
     * @param config      Configuration.
     * @param gameVersion Version of the game.
     */
    public GameEngine(final Configuration config, final Version gameVersion) {
        super(gameVersion);
        this.configuration = config;
        LOGGER.info("Initializing client game engine...");
        this.windowEngine = WindowEngine.getEngine();
        this.graphicEngine = GraphicEngine.getEngine(this.windowEngine);
        this.soundEngine = AudioEngine.getEngine();
        this.physicEngine = PhysicEngine.getEngine();
        this.networkEngine = Client.getEngine();
        this.scriptInterpreter = ScriptInterpreter.getEngine();
        Cursor empty = new Cursor("empty", "empty.gif");
        //this.windowEngine.createCursor(empty);
        //this.windowEngine.setCursor(empty);
        this.createWorld();
        // this.addResourcePath("media/brs.yzk", "engine", FileType.ZIP);
        this.guiManager = this.graphicEngine.getGuiBuilder();
        this.addFrameListener(this.graphicEngine.getGuiBuilder().getAnimationManager());
        this.windowEngine.registerInput(this.graphicEngine.getEventManager());
        LOGGER.info("Client game engine initialized.");
    }

    public GameEngine(final Version gameVersion) {
        this(Configuration.getInstance(), gameVersion);
    }

    /**
     * Pause the rendering.
     */
    //@ensures rendering = false
    public final void pauseRender() {
        this.rendering = false;
    }

    /**
     * Restart the rendering.
     */
    //@ensures rendering = true
    public final void startRender() {
        this.rendering = true;
    }

    /**
     * Start the game loop.
     */
    @Override
    public final void start() {
        LOGGER.info("Game engine started.");
        if (!this.running) {
            this.running = true;
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
    public final void runOneFrameImpl() {
        this.networkEngine.update();
        this.windowEngine.updateWindow();
        this.soundEngine.update();
        this.physicEngine.update();
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

    public final void registerMainView(final View v) {
        this.graphicEngine.getEventManager().setDefaultView(v);
    }

    /**
     * Unregister a view to receive inputs.
     *
     * @param v View to unregister.
     */
    public final void unregisterView(final View v) {
        this.graphicEngine.getEventManager().removeView(v);
    }

    /**
     * Stop the game loop and close the engine.
     */
    public final void stopGameLoop() {
        this.sendMessage(messageFactory.closeSession());
        this.running = false;
    }

    /**
     * Add a resource path. This method also update the view to ensure the
     * application stays awake when multiple calls are made.
     *
     * @param resource Resource group data.
     */
    public final void addResourcePath(ResourcePath resource) {
        File f = new File(resource.getPath());
        if (!f.exists()) {
            throw new ResourceMissingException(f.getAbsolutePath());
        }
        this.windowEngine.updateWindow();
        LOGGER.info("Registering resource group " + resource.getName());
        this.soundEngine.addResourcePath(resource);
        this.graphicEngine.addResourcePath(resource);
        if (resource.getType() == FileResource.FileType.FILE) {
            new FileParser(this.graphicEngine, this.soundEngine)
                    .addResourcePath(resource);
        }
        LOGGER.info("Resource group " + resource.getName() + " registered.");
    }

    /**
     * Create a sky box.
     *
     * @param name Unique name.
     * @param path Path to the textures to use.
     * @return The newly built sky box.
     */
    public final Skybox createSkybox(final String name, final String path) {
        LOGGER.info("Create skybox.");
        return this.graphicEngine.createSkybox(name, path);
    }

    /**
     * Set a debug listener, it will receive the current framerate, as well as debug information.
     * The debug listener will only be registered if debug mode is true.
     *
     * @param listener Listener to set.
     */
    public final void setDebugListener(final DebugListener listener) {
        assert listener != null;
        if(this.debug) {
            this.addFrameListener(new FrameRateDisplayer(listener, this.graphicEngine));
            this.graphicEngine.getEventManager().setDebugListener(listener);
        }
    }

    /**
     * Create a new font.
     *
     * @param name Font name, must be unique.
     * @param path Path to the font file.
     * @param size Size of the font.
     * @return The newly created font.
     */
    public final Font createFont(final String name, final String path, final int size) {
        return this.graphicEngine.createFont(name, path, size);
    }

    /**
     * Create a new font.
     *
     * @param name Font name, must be unique.
     * @param path Path to the font file.
     * @param size Size of the font.
     * @return The newly created font.
     */
    public final Font createFont(final String name, final String path, final Relative size) {
        return this.graphicEngine.createFont(name, path, (int)(size.of(this.getScreenSize().height)));
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
    public final Font createFont(final String name, final String path, final Relative size, final Color color) {
        return this.graphicEngine.createFont(name, path, (int)(size.of(this.getScreenSize().height)), color);
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
    public final Font createFont(final String name, final String path, final int size, final Color color) {
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
    public final Font createFont(final String path, final int size, final Color color) {
        return this.graphicEngine.createFont(StringUtil.buildRandomString("font"), path, size, color);
    }

    /**
     * Create a new font with a given color and a random name.
     *
     * @param path  Path to the font file.
     * @param size  Size of the font.
     * @param color Font color.
     * @return The newly created font.
     */
    public final Font createFont(final String path, final Relative size, final Color color) {
        return this.graphicEngine.createFont(StringUtil.buildRandomString("font"), path, (int)(size.of(this.getScreenSize().height)), color);
    }

    /**
     * Add a listener to notify of the network events.
     *
     * @param listener Listener to add.
     */
    public final void addNetworkListener(final NetworkListener listener) {
        this.networkEngine.addNetworkListener(listener);
    }

    /**
     * Create a new mouse cursor.
     *
     * @param cursor Data to build the cursor.
     * @return The created cursor.
     */
    public final Cursor createCursor(Cursor cursor) {
        this.windowEngine.createCursor(cursor);
        return cursor;
    }

    /**
     * Set the mouse cursor.
     *
     * @param cursor Name of the cursor to use.
     */
    public final void setCursor(final Cursor cursor) {
        this.windowEngine.setCursor(cursor);
    }

    /**
     * Set the mouse cursor visible.
     */
    public final void showCursor() {
        this.windowEngine.showCursor();
    }

    /**
     * Set the mouse cursor invisible.
     */
    public final void hideCursor() {
        this.windowEngine.hideCursor();
    }

    /**
     * Set the game window title.
     *
     * @param title Title to print in OS task bar.
     */
    public final void setWindowTitle(final String title) {
        this.windowEngine.setWindowTitle(title);
    }

    /**
     * Set the game window icon.
     *
     * @param file Image file to use to display in the OS task bar.
     */
    public final void setWindowIcon(final String file) {
        this.windowEngine.setWindowIcon(file);
    }

    /**
     * Create a new music play list.
     *
     * @param name PlayList name, must be unique.
     * @return The created PlayList.
     */
    public final Playlist createPlaylist(final String name) {
        return this.soundEngine.createPlaylist(name);
    }

    /**
     * Render a graphic frame.
     */
    public final void render() {
        this.graphicEngine.update();
    }

    /**
     * If a network message cannot be processed(i.e waiting for an other message
     * that should come before this one), it can be delayed to the playNext frame.
     *
     * @param message Message to delay.
     */
    public final void delayMessageToNextFrame(final MessageWrapper message) {
        this.networkEngine.delayMessageToNextFrame(message);
    }

    /**
     * Take a print screen and save it in a file.
     */
    public final void printScreen() {
        this.graphicEngine.printScreen();
    }

    /**
     * Send a network message.
     *
     * @param message Message to send to the server.
     */
    @Override
    public final void sendMessage(final NetworkMessage message) {
        this.networkEngine.sendMessage(message);
    }

    /**
     * Send a network message.
     *
     * @param message Message to send to the server.
     */
    public final void sendMessage(final String message) {
        this.networkEngine.sendMessage(message);
    }

    /**
     * Create a new World to build graphic and physic object, a default Camera
     * and view port is also built.
     *
     * @return The newly built world.
     */
    public final ClientWorld createWorld() {
        GraphicWorld graphic = this.graphicEngine.createWorld();
        PhysicWorld physic = this.physicEngine.createWorld();

        ClientWorld world = new GraphicPhysicWorld(graphic, physic);
        if (this.debug) {
            world.setDebugMode();
        }
        this.activeWorld = world;
        return world;
    }

    /**
     * Set the currently active camera focusing on an Entity.
     *
     * @param position Where to look at.
     */
    public final void focusCamera(final Point3D position) {
        this.activeWorld.getDefaultCamera().setRelativePosition(position);
    }

    /**
     * Close all engines.
     */
    public final void close() {
        if (!this.closed) {
            LOGGER.info("Closing engines...");
            this.closed = true;
            this.graphicEngine.close();
            this.physicEngine.close();
            this.soundEngine.close();
            this.networkEngine.close();
            LOGGER.info("Engines closed.");
        }
    }

    /**
     * Add a new NotRenderingListener to be executed when the rendering is
     * paused.
     *
     * @param listener Listener to add.
     */
    public final void addNotRenderingListener(final NotRenderingListener listener) {
        this.notRenderingListenerList.add(listener);
    }

    /**
     * @return The game screen size.
     */
    public final ScreenSize getScreenSize() {
        return this.windowEngine.getScreenSize();
    }

    /**
     * Create a new source source from a file.
     *
     * @param file File name.
     * @return The created sound.
     */
    public final SoundSource createSound(final String file) {
        return this.soundEngine.createSound(file);
    }

    /**
     * Create a selection rectangle, only one can be used.
     *
     * @param material  Material to set on rectangle border.
     * @param material2 Material to set on rectangle background.
     * @return The created rectangle.
     */
    public final SelectionRectangle createSelectionRectangle(final Material material, final Material material2) {
        return this.graphicEngine.createSelectionRectangle(material, material2);
    }

    /**
     * Disconnect from the server.
     */
    public final void disconnect() {
        this.networkEngine.disconnect();
    }

    public final void connect(final String url, final int port) {
        this.networkEngine.connect(url, port);
    }

    public final Cursor getDefaultCursor() {
        return this.defaultCursor;
    }

    public final void createAndSetDefaultCursor(Cursor cursor) {
        this.defaultCursor = this.createCursor(cursor);
        this.setCursor(defaultCursor);
    }

    public final MaterialManager getMaterialManager() {
        return this.graphicEngine.getMaterialManager();
    }

    public final GuiEventManager getEventDispatcher() {
        return this.graphicEngine.getEventManager();
    }

    public final Configuration getConfiguration() {
        return configuration;
    }

    public final GuiFactory getGuiManager() {
        return guiManager;
    }

    public final ClientWorld getActiveWorld() {
        return activeWorld;
    }

    public final boolean isClosed() {
        return closed;
    }
}
