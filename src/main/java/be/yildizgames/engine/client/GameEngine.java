/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2019 Grégory Van den Borre
 *
 * More infos available: https://engine.yildiz-games.be
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
package be.yildizgames.engine.client;

import be.yildizgames.common.client.config.Configuration;
import be.yildizgames.common.file.ResourcePath;
import be.yildizgames.common.frame.FrameManager;
import be.yildizgames.common.model.Version;
import be.yildizgames.engine.client.world.ClientWorld;
import be.yildizgames.module.audio.AudioEngine;
import be.yildizgames.module.graphic.GraphicEngine;
import be.yildizgames.module.network.client.NetworkClient;
import be.yildizgames.module.script.ScriptInterpreter;
import be.yildizgames.module.window.WindowEngine;
import be.yildizgames.shared.game.engine.Initializable;

/**
 * Game engine API.
 * @author Grégory Van den Borre
 */
public interface GameEngine extends FrameManager {

    void start();

    void stop();

    /**
     * Add logic to be initialized before the engine starts.
     *
     * @param init Object to initialize before the engine starts.
     */
    void addInitializable(Initializable init);

    ScriptInterpreter getScriptingEngine();

    /**
     * Provide the audio engine implementation.
     *
     * @return The audio engine implementation [ensure not null].
     */
    AudioEngine getAudioEngine();

    /**
     * Provide the network client implementation.
     *
     * @return The network client implementation [ensure not null].
     */
    NetworkClient getNetworkEngine();

    /**
     * Provide the graphic engine implementation.
     *
     * @return The graphic engine implementation [ensure not null].
     */
    GraphicEngine getGraphicEngine();

    /**
     * Provide the window engine implementation.
     *
     * @return The window engine implementation [ensure not null].
     */
    WindowEngine getWindowEngine();

    /**
     * Create a new World to build graphic and physic object, a default Camera
     * and view port is also built.
     *
     * @return The newly built world [ensure not null].
     */
    ClientWorld createWorld();

    /**
     * Add a resource path. This method also update the view to ensure the
     * application stays awake when multiple calls are made.
     *
     * @param resource Resource group data.
     * @throws ImplementationException If resource is null.
     * @throws IllegalStateException If the path does not exists.
     */
    void addResourcePath(ResourcePath resource);

    /**
     * Provide the configuration.
     * @return The configuration data [ensure not null].
     */
    Configuration getConfiguration();

    /**
     * Check if the version is the latest one.
     * @param version Expected application version.
     */
    void checkVersion(Version version);

    /**
     * Flag if the engine is closed or not.
     * @return True if the engine is closed, false if it is running.
     */
    boolean isClosed();

    /**
     * Provide the current application version.
     * @return The version.
     */
    Version getGameVersion();

    void close();
}
