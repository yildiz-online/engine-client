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
 * Build a translated button.
 * @author Grégory Van den Borre
 */
public class TranslatedButtonBuilder {

    /**
     * Gui builder to create the effective widget.
     */
    private final TranslatedGuiBuilder builder;

    /**
     * Decorated builder to create the wrapped button.
     */
    private final ButtonBuilder buttonBuilder;

    /**
     * Create a new TranslatedButtonBuilder to build a TranslatedButton.
     * @param builder Factory creating the effective widget.
     * @throws NullPointerException if builder is null.
     */
    public TranslatedButtonBuilder(TranslatedGuiBuilder builder) {
        super();
        this.builder = builder;
        this.buttonBuilder = new ButtonBuilder(builder.getGuiBuilder());
    }

    /**
     * Set a name to the button to build.
     * @param name Name to set, must be unique among the existing buttons.
     * @return This object for chaining.
     * @throws NullPointerException if name is null.
     */
    public TranslatedButtonBuilder withName(final String name) {
        this.buttonBuilder.withName(name);
        return this;
    }

    /**
     * Set the different materials to the button to build.
     * @param materials Materials to set as background, highlight and inactive states.
     * @return This object for chaining.
     * @throws NullPointerException if materials is null.
     */
    public TranslatedButtonBuilder withMaterials(final ButtonMaterial materials) {
        this.buttonBuilder.withButtonMaterial(materials);
        return this;
    }

    /**
     * Set the size of the button to build.
     * @param size Size values.
     * @return This object for chaining.
     * @throws NullPointerException if size is null.
     */
    public TranslatedButtonBuilder withSize(final Size size) {
        this.buttonBuilder.withSize(size);
        return this;
    }

    /**
     * Set the position of the button to build.
     * @param position Position values.
     * @return This object for chaining.
     * @throws NullPointerException if position is null.
     */
    public TranslatedButtonBuilder atPosition(final Position position) {
        this.buttonBuilder.atPosition(position);
        return this;
    }

    /**
     * Create the effective TranslatedButton from the provided informations.
     * @param container Container to hold the created button.
     * @return The created TranslatedButton.
     * @throws NullPointerException if container is null.
     */
    public TranslatedButton build(final GuiContainer container) {
        return this.builder.buildButton(this.buttonBuilder.build(container));
    }
}
