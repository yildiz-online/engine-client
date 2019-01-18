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
package be.yildizgames.engine.client.internal;

import be.yildizgames.common.client.config.Configuration;
import be.yildizgames.common.file.ResourcePath;
import be.yildizgames.common.file.exception.FileMissingException;
import be.yildizgames.common.model.Version;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Grégory Van den Borre
 */
class SimpleGameEngineTest {

    @Nested
    class Constructor {

        @Test
        void happyFlow() {
            SimpleGameEngine engine = new SimpleGameEngine();
            Assertions.assertNotNull(engine.getAudioEngine());
            Assertions.assertNotNull(engine.getGraphicEngine());
            Assertions.assertNotNull(engine.getScriptingEngine());
            Assertions.assertNotNull(engine.getNetworkEngine());
            Assertions.assertNotNull(engine.getConfiguration());
            //TODO uncomment when cursor is initialized
            //Assertions.assertNotNull(engine.getDefaultCursor());
            Assertions.assertEquals(Version.release(1,0,0,0), engine.getGameVersion());
        }

        @Test
        void versionHappyFlow() {
            SimpleGameEngine engine = new SimpleGameEngine(Version.release(1,1,1,1));
            Assertions.assertEquals(Version.release(1,1,1,1), engine.getGameVersion());
        }

        @Test
        void versionConfigHappyFlow() {
            SimpleGameEngine engine = new SimpleGameEngine(Configuration.getInstance(), Version.release(1,1,1,1));
            Assertions.assertEquals(Version.release(1,1,1,1), engine.getGameVersion());
            Assertions.assertEquals(Configuration.getInstance(), engine.getConfiguration());
        }

    }

    @Nested
    class AddResourcePath {

        @Test
        void happyFlow() throws IOException {
            Path temp = Files.createTempDirectory("resource");
            SimpleGameEngine engine = new SimpleGameEngine();
            engine.addResourcePath(ResourcePath.directory("temp", temp.toString()));
        }

        @Test
        void fileNotExists() {
            SimpleGameEngine engine = new SimpleGameEngine();
            Assertions.assertThrows(FileMissingException.class, () -> engine.addResourcePath(ResourcePath.directory("azerty", "azerty")));
        }

    }
}
