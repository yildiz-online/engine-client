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

package be.yildiz.client.game.engine.gui;

import be.yildiz.common.Position;
import be.yildiz.common.Size;
import be.yildiz.module.graphic.Font;
import be.yildiz.module.graphic.gui.GuiContainer;
import be.yildiz.module.graphic.gui.textline.TextLineBuilder;

/**
 * Build a TranslatedTextLine
 * @author Grégory Van den Borre
 */
public class TranslatedTextLineBuilder {

    private final TranslatedGuiBuilder builder;

    private final TextLineBuilder textLineBuilder;

    public TranslatedTextLineBuilder(TranslatedGuiBuilder guiBuilder) {
        super();
        this.builder = guiBuilder;
        this.textLineBuilder = new TextLineBuilder(builder.getGuiBuilder());
    }

    public TranslatedTextLineBuilder withName(final String name) {
        this.textLineBuilder.withName(name);
        return this;
    }

    public TranslatedTextLineBuilder atPosition(final Position position) {
        this.textLineBuilder.atPosition(position);
        return this;
    }

    public TranslatedTextLineBuilder atPosition(final int x, final int y) {
        this.textLineBuilder.atPosition(x, y);
        return this;
    }

    public TranslatedTextLineBuilder withSize(final Size size) {
        this.textLineBuilder.withSize(size);
        return this;
    }

    public TranslatedTextLineBuilder withFont(final Font font) {
        this.textLineBuilder.withFont(font);
        return this;
    }

    public TranslatedTextLine build(final GuiContainer container) {
        return this.builder.buildTextLine(this.textLineBuilder.build(container));
    }
}
