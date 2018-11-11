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
package be.yildizgames.engine.client;

import be.yildizgames.common.client.config.Configuration;
import be.yildizgames.common.file.ResourcePath;
import be.yildizgames.common.frame.FrameManager;
import be.yildizgames.common.model.Version;
import be.yildizgames.common.util.Engine;
import be.yildizgames.engine.client.world.ClientWorld;
import be.yildizgames.module.audio.AudioEngine;
import be.yildizgames.module.graphic.GraphicEngine;
import be.yildizgames.module.network.client.NetworkClient;
import be.yildizgames.module.script.ScriptInterpreter;
import be.yildizgames.module.window.Cursor;
import be.yildizgames.module.window.WindowEngine;

public interface GameEngine extends Engine, FrameManager {

    ScriptInterpreter getScriptingEngine();

    AudioEngine getAudioEngine();

    NetworkClient getNetworkEngine();

    GraphicEngine getGraphicEngine();

    WindowEngine getWindowEngine();

    /**
     * Create a new World to build graphic and physic object, a default Camera
     * and view port is also built.
     *
     * @return The newly built world.
     */
    ClientWorld createWorld();

    /**
     * Add a resource path. This method also update the view to ensure the
     * application stays awake when multiple calls are made.
     *
     * @param resource Resource group data.
     */
    void addResourcePath(ResourcePath resource);

    Configuration getConfiguration();

    void checkVersion(Version version);

    void createAndSetDefaultCursor(Cursor center);
}
