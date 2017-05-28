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

import be.yildiz.common.Coordinates;
import be.yildiz.common.Position;
import be.yildiz.common.Size;
import be.yildiz.common.translation.Translation;
import be.yildiz.module.graphic.Font;
import be.yildiz.module.graphic.Material;
import be.yildiz.module.graphic.gui.GuiBuilder;
import be.yildiz.module.graphic.gui.GuiContainer;
import be.yildiz.module.graphic.gui.checkbox.CheckBoxAnimation;
import be.yildiz.module.graphic.gui.checkbox.CheckBoxBuilder;

/**
 * @author Grégory Van den Borre
 */
public class TranslatedCheckBoxBuilder {

    /**
     * Decorated builder to create the wrapped check box.
     */
    private final CheckBoxBuilder checkBoxBuilder;

    private final Translation translation;


    /**
     * Create a new TranslatedCheckboxBuilder to build a TranslatedButton.
     * @param builder Factory creating the effective widget.
     * @throws NullPointerException if builder is null.
     */
    TranslatedCheckBoxBuilder(GuiBuilder builder, Translation translation) {
        super();
        this.checkBoxBuilder = new CheckBoxBuilder(builder);
        this.translation = translation;
    }

    public TranslatedCheckBoxBuilder withName(final String name) {
        this.checkBoxBuilder.withName(name);
        return this;
    }


    public TranslatedCheckBoxBuilder atPosition(final Position position) {
        this.checkBoxBuilder.atPosition(position);
        return this;
    }

    public TranslatedCheckBoxBuilder atPosition(final int x, final int y) {
        this.checkBoxBuilder.atPosition(x, y);
        return this;
    }

    public TranslatedCheckBoxBuilder withSize(final int width, final int length) {
        this.checkBoxBuilder.withSize(width, length);
        return this;
    }

    public TranslatedCheckBoxBuilder withBackground(final Material background) {
        this.checkBoxBuilder.withBackground(background);
        return this;
    }

    public TranslatedCheckBoxBuilder withHover(final Material hover) {
        this.checkBoxBuilder.withHover(hover);
        return this;
    }

    public TranslatedCheckBoxBuilder withChecked(final Material checked) {
        this.checkBoxBuilder.withChecked(checked);
        return this;
    }

    public TranslatedCheckBoxBuilder withFont(final Font font) {
        this.checkBoxBuilder.withFont(font);
        return this;
    }

    public TranslatedCheckBoxBuilder withCoordinates(final Coordinates coordinates) {
        this.checkBoxBuilder.withCoordinates(coordinates);
        return this;
    }

    public TranslatedCheckBoxBuilder withSize(final Size size) {
        this.checkBoxBuilder.withSize(size);
        return this;
    }

    public TranslatedCheckBox build(GuiContainer container) {
        return new TranslatedCheckBox(this.checkBoxBuilder.build(container), this.translation);
    }

    public TranslatedCheckBoxBuilder animate(CheckBoxAnimation animation) {
        this.checkBoxBuilder.animate(animation);
        return this;
    }
}
