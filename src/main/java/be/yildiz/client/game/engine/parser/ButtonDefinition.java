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

import be.yildiz.module.coordinate.Size;

/**
 * Data definition to create a GuiButton from an external resource(i.e parsing a
 * script file).
 *
 * @author Grégory Van den Borre
 */
public final class ButtonDefinition extends GuiCommonDefinition {

    //FIXME MEDIUM this package should move in graphic module

    /**
     * The button background material name.
     */
    private final String material;

    /**
     * The button background material name when focused.
     */
    private final String materialHighlight;

    /**
     * The font name for the button caption text.
     */
    private final String font;

    /**
     * Simple constructor, initialize with empty values.
     *
     * @param background  GuiButton background material name.
     * @param highlight   GuiButton background material name when focused.
     * @param captionFont Font name to use for the caption.
     * @param screen      Screen size data.
     */
    ButtonDefinition(final String background, final String highlight, final String captionFont, final Size screen) {
        super(screen);
        this.material = background;
        this.materialHighlight = highlight;
        this.font = captionFont;
    }

    public String getMaterial() {
        return material;
    }

    public String getMaterialHighlight() {
        return materialHighlight;
    }

    public String getFont() {
        return font;
    }
}
