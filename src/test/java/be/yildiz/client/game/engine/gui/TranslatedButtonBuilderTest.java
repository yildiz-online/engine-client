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

package be.yildiz.client.game.engine.gui;

import be.yildiz.common.Position;
import be.yildiz.common.Size;
import be.yildiz.common.translation.Translation;
import be.yildiz.module.graphic.gui.DummyGuiBuilder;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author Grégory Van den Borre
 */
@RunWith(Enclosed.class)
public class TranslatedButtonBuilderTest {

    private static TranslatedButtonBuilder givenATranslatedButtonBuilder() {
        TranslatedGuiBuilder guiBuilder = new TranslatedGuiBuilder(new DummyGuiBuilder(), new Translation());
        return guiBuilder.buildButton();
    }

    public static class Constructor {

        @Test
        public void happyFlow() {
            givenATranslatedButtonBuilder();
        }

        @Test(expected = NullPointerException.class)
        public void withNullGuiBuilder() {
            new TranslatedButtonBuilder(null, new Translation());
        }

    }

    public static class WithName {

        @Test
        public void correct() {
            TranslatedButtonBuilder builder = givenATranslatedButtonBuilder();
            builder.withName("test");
        }

        @Test(expected = AssertionError.class)
        public void thatIsNull() {
            TranslatedButtonBuilder builder = givenATranslatedButtonBuilder();
            builder.withName(null);
        }

    }

    public static class AtPosition {

        @Test
        public void correct() {
            TranslatedButtonBuilder builder = givenATranslatedButtonBuilder();
            builder.atPosition(new Position(5, 6));
        }

        @Test(expected = AssertionError.class)
        public void thatIsNull() {
            TranslatedButtonBuilder builder = givenATranslatedButtonBuilder();
            builder.atPosition(null);
        }

    }

    public static class WithSize {

        @Test
        public void correct() {
            TranslatedButtonBuilder builder = givenATranslatedButtonBuilder();
            builder.withSize(new Size(5, 6));
        }

        @Test(expected = AssertionError.class)
        public void thatIsNull() {
            TranslatedButtonBuilder builder = givenATranslatedButtonBuilder();
            builder.withSize(null);
        }

    }

}
