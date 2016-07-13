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
import be.yildiz.module.graphic.gui.ButtonMaterial;
import be.yildiz.module.graphic.gui.GuiContainer;
import be.yildiz.module.graphic.gui.button.ButtonBuilder;

/**
 * @author Grégory Van den Borre
 */
public class TranslatedButtonBuilder {

    private final TranslatedGuiBuilder builder;

    private final ButtonBuilder buttonBuilder;

    public TranslatedButtonBuilder(TranslatedGuiBuilder builder) {
        super();
        this.builder = builder;
        this.buttonBuilder = new ButtonBuilder(builder.getGuiBuilder());
    }

    public TranslatedButtonBuilder withName(final String name) {
        this.buttonBuilder.withName(name);
        return this;
    }

    public TranslatedButtonBuilder withMaterials(final ButtonMaterial m) {
        this.buttonBuilder.withButtonMaterial(m);
        return this;
    }

    public TranslatedButtonBuilder withSize(final Size s) {
        this.buttonBuilder.withSize(s);
        return this;
    }

    public TranslatedButtonBuilder atPosition(final Position p) {
        this.buttonBuilder.atPosition(p);
        return this;
    }

    public TranslatedButton build(final GuiContainer c) {
        return this.builder.buildButton(this.buttonBuilder.build(c));
    }
}
