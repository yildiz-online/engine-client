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
import be.yildiz.common.translation.Translation;
import be.yildiz.module.graphic.Font;
import be.yildiz.module.graphic.Material;
import be.yildiz.module.graphic.gui.GuiBuilder;
import be.yildiz.module.graphic.gui.GuiContainer;
import be.yildiz.module.graphic.gui.textarea.TextAreaBuilder;

/**
 * @author Grégory Van den Borre
 */
public class TranslatedTextAreaBuilder {

    /**
     * Gui builder to create the effective widget.
     */
    private final GuiBuilder builder;

    /**
     * Decorated builder to create the wrapped text area.
     */
    private final TextAreaBuilder textAreaBuilder;

    private final Translation translation;

    /**
     * Create a new TranslatedButtonBuilder to build a TranslatedButton.
     * @param builder Factory creating the effective widget.
     * @throws NullPointerException if builder is null.
     */
    TranslatedTextAreaBuilder(GuiBuilder builder, Translation translation) {
        super();
        this.builder = builder;
        this.textAreaBuilder = new TextAreaBuilder(builder);
        this.translation = translation;
    }

    public TranslatedTextAreaBuilder withName(final String name) {
        this.textAreaBuilder.withName(name);
        return this;
    }

    public TranslatedTextAreaBuilder withCoordinates(final Coordinates coordinates) {
        this.textAreaBuilder.withCoordinates(coordinates);
        return this;
    }

    public TranslatedTextAreaBuilder withFont(final Font font) {
        this.textAreaBuilder.withFont(font);
        return this;
    }

    public TranslatedTextAreaBuilder withBackground(final Material background) {
        this.textAreaBuilder.withBackground(background);
        return this;
    }

    public TranslatedTextAreaBuilder withPadding(final int padding) {
        this.textAreaBuilder.withPadding(padding);
        return this;
    }

    public TranslatedTextArea build(final GuiContainer container) {
        return new TranslatedTextArea(this.textAreaBuilder.build(container), this.translation);
    }
}
