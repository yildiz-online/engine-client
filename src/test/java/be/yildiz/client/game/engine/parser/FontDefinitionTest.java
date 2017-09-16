/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2017 Grégory Van den Borre
 *
 * More infos available: https://www.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 */

package be.yildiz.client.game.engine.parser;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Grégory Van den Borre
 */
final class FontDefinitionTest {

    @Nested
    class Constructor {

        @Test
        void happyFlow() {
            FontDefinition def = new FontDefinition();
            assertEquals("", def.getName());
            assertEquals("", def.getPath());
            assertEquals( 0, def.getSize());
        }
    }

    @Nested
    class SetSize {

        @Test
        void happyFlow() {
            FontDefinition def = new FontDefinition();
            def.setSize("3");
            assertEquals(3, def.getSize());
        }

        @Test
        void fromNull() {
            FontDefinition def = new FontDefinition();
            assertThrows(IllegalArgumentException.class, () -> def.setSize(null));
        }

        @Test
        void fromInvalid() {
            FontDefinition def = new FontDefinition();
            assertThrows(IllegalArgumentException.class, () -> def.setSize("3.1"));
        }
    }

    @Nested
    class SetPath {

        @Test
        void happyFlow() {
            FontDefinition def = new FontDefinition();
            def.setPath("abc");
            assertEquals("abc", def.getPath());
        }

        @Test
        void fromNull() {
            FontDefinition def = new FontDefinition();
            assertThrows(IllegalArgumentException.class, () -> def.setPath(null));
        }
    }

    @Nested
    class SetName {

        @Test
        void happyFlow() {
            FontDefinition def = new FontDefinition();
            def.setName("abc");
            assertEquals("abc", def.getName());
        }

        @Test
        void fromNull() {
            FontDefinition def = new FontDefinition();
            assertThrows(IllegalArgumentException.class, () -> def.setName(null));
        }
    }

}
