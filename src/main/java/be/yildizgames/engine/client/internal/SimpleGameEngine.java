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

package be.yildizgames.engine.client.internal;

import be.yildizgames.common.client.config.Configuration;
import be.yildizgames.common.client.debug.DebugListener;
import be.yildizgames.common.exception.implementation.ImplementationException;
import be.yildizgames.common.file.ResourcePath;
import be.yildizgames.common.file.exception.FileMissingException;
import be.yildizgames.common.logging.LogFactory;
import be.yildizgames.common.model.Version;
import be.yildizgames.engine.client.GameEngine;
import be.yildizgames.engine.client.exception.InvalidClientVersionException;
import be.yildizgames.engine.client.world.ClientWorld;
import be.yildizgames.engine.client.world.internal.GraphicPhysicWorld;
import be.yildizgames.module.audio.AudioEngine;
import be.yildizgames.module.audio.BaseAudioEngine;
import be.yildizgames.module.graphic.BaseGraphicEngine;
import be.yildizgames.module.graphic.GraphicWorld;
import be.yildizgames.module.graphic.NotRenderingListener;
import be.yildizgames.module.network.client.Client;
import be.yildizgames.module.physics.BasePhysicEngine;
import be.yildizgames.module.physics.PhysicWorld;
import be.yildizgames.module.script.ScriptInterpreter;
import be.yildizgames.module.window.BaseWindowEngine;
import be.yildizgames.module.window.Cursor;
import be.yildizgames.shared.game.engine.AbstractGameEngine;
import be.yildizgames.shared.protocol.EngineMessageFactory;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for all game logic, all other engines are called from here.
 *
 * @author Grégory Van den Borre
 */
public class SimpleGameEngine extends AbstractGameEngine implements GameEngine {

    private static final Logger LOGGER = LogFactory.getInstance().getLogger(SimpleGameEngine.class);

    /**
     * Maximum frame per seconds.
     */
    private static final int FPS = 60;

    private final ScriptInterpreter scriptInterpreter;

    private final BaseWindowEngine windowEngine;

    /**
     * Graphic logic.
     */
    private final BaseGraphicEngine graphicEngine;

    private final BasePhysicEngine physicEngine;

    /**
     * Sound logic.
     */
    private final BaseAudioEngine soundEngine;

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

    /**
     * True if the loop is currently running.
     */
    private boolean running;

    /**
     * Flag to check if the graphic engine must render the current frame.
     */
    private boolean rendering = true;

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
    public SimpleGameEngine(final Configuration config, final Version gameVersion) {
        super(gameVersion);
        ImplementationException.throwForNull(config);
        this.configuration = config;
        LOGGER.info("Initializing client game engine...");
        this.windowEngine = BaseWindowEngine.getEngine();
        this.graphicEngine = BaseGraphicEngine.getEngine(this.windowEngine);
        this.soundEngine = BaseAudioEngine.getEngine();
        this.physicEngine = BasePhysicEngine.getEngine();
        this.networkEngine = Client.getEngine();
        this.scriptInterpreter = ScriptInterpreter.getEngine();
        Cursor empty = new Cursor("empty", "empty.gif");
        //this.windowEngine.createCursor(empty);
        //this.windowEngine.setCursor(empty);
        // this.addResourcePath("media/brs.yzk", "engine", FileType.ZIP);
        this.addFrameListener(this.graphicEngine.getGuiFactory().getAnimationManager());
        this.windowEngine.registerInput(this.graphicEngine.getEventManager());
        LOGGER.info("Client game engine initialized.");
    }

    public SimpleGameEngine(final Version gameVersion) {
        this(Configuration.getInstance(), gameVersion);
    }

    public SimpleGameEngine() {
        this(Configuration.getInstance(), new Version(Version.VersionType.RELEASE, 1,0,0,0));
    }

    @Override
    public ScriptInterpreter getScriptingEngine() {
        return this.scriptInterpreter;
    }

    @Override
    public AudioEngine getAudioEngine() {
        return this.soundEngine;
    }

    @Override
    public Client getNetworkEngine() {
        return this.networkEngine;
    }

    @Override
    public BaseGraphicEngine getGraphicEngine() {
        return this.graphicEngine;
    }

    @Override
    public final void start() {
        LOGGER.info("Game engine started.");
        if (!this.running) {
            this.running = true;
            this.setFrameLimiter(SimpleGameEngine.FPS);
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
        //TODO rename into update()
        this.windowEngine.updateWindow();
        this.soundEngine.update();
        this.physicEngine.update();
        //TODO move this login in graphicengine.update()
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

    @Override
    public final void stop() {
        this.networkEngine.sendMessage(messageFactory.closeSession());
        this.running = false;
    }

    @Override
    public final void addResourcePath(final ResourcePath resource) {
        ImplementationException.throwForNull(resource);
        if (!resource.exists("")) {
            throw new FileMissingException(resource.getPath());
        }
        this.windowEngine.updateWindow();
        LOGGER.info("Registering resource group " + resource.getName());
        this.soundEngine.addResourcePath(resource);
        this.graphicEngine.addResourcePath(resource);
        LOGGER.info("Resource group {} registered.", resource.getName());
    }

    @Override
    public final ClientWorld createWorld() {
        GraphicWorld graphic = this.graphicEngine.createWorld();
        PhysicWorld physic = this.physicEngine.createWorld();

        ClientWorld world = new GraphicPhysicWorld(graphic, physic);
        if (this.debug) {
            world.setDebugMode();
        }
        return world;
    }

    @Override
    public final Configuration getConfiguration() {
        return this.configuration;
    }

    @Override
    public final void checkVersion(final Version version) {
        //TODO just make a call to the server and retrieve the expected version from there.
        if (!this.getGameVersion().equals(version)) {
            throw new InvalidClientVersionException(version, this.getGameVersion());
        }
    }

    @Override
    public final BaseWindowEngine getWindowEngine() {
        return this.graphicEngine.getWindowEngine();
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
     * Add a new NotRenderingListener to be executed when the rendering is
     * paused.
     *
     * @param listener Listener to add.
     */
    public final void addNotRenderingListener(final NotRenderingListener listener) {
        ImplementationException.throwForNull(listener);
        this.notRenderingListenerList.add(listener);
    }

    /**
     * Close all engines, ensure that the game loop is no longer running before using this.
     */
    private void close() {
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

    @Override
    public final boolean isClosed() {
        return this.closed;
    }
}
