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

import be.yildiz.common.BaseCoordinate;
import be.yildiz.common.Position;
import be.yildiz.common.Size;
import be.yildiz.common.translation.Translation;
import be.yildiz.module.graphic.Font;
import be.yildiz.module.graphic.Material;
import be.yildiz.module.graphic.gui.*;
import be.yildiz.module.graphic.gui.container.ContainerBuilder;
import be.yildiz.module.graphic.gui.image.ImageBuilder;
import lombok.NonNull;

import java.util.Optional;

/**
 * @author Grégory Van den Borre
 */
public class TranslatedGuiBuilder {

    private final GuiBuilder guiBuilder;

    private final Translation translation;

    public TranslatedGuiBuilder(GuiBuilder guiBuilder, Translation translation) {
        super();
        this.guiBuilder = guiBuilder;
        this.translation = translation;
    }

    public TranslatedButtonBuilder buildButton() {
        return new TranslatedButtonBuilder(this.guiBuilder, this.translation);
    }

    /**
     * Delete a button.
     *
     * @param button GuiButton to delete.
     */
    public final void delete(final TranslatedButton button) {
        this.guiBuilder.delete(button);
    }

    public final ImageBuilder buildImage() {
        return new ImageBuilder(this.guiBuilder);
    }

    /**
     * Build a new image widget.
     *
     * @param name        Unique image name.
     * @param coordinates Image coordinates.
     * @param background  Image background material.
     * @param border      Size of the border, in pixels.
     * @param container   Container holding the image.
     * @return The new image widget.
     */
    public final Image buildEmptyRectangleImage(final String name, final BaseCoordinate coordinates, final Material background, final int border, final GuiContainer container) {
        return this.guiBuilder.buildEmptyRectangleImage(name, coordinates, background, border, container);
    }

    public final Image buildEmptyUnderlineImage(final String name, final BaseCoordinate coordinates, final Material background, final int border, final GuiContainer container) {
        return this.guiBuilder.buildEmptyUnderlineImage(name, coordinates, background, border, container);
    }

    /**
     * Delete an image.
     *
     * @param image Image to delete.
     */
    public final void delete(final Image image) {
        this.guiBuilder.delete(image);
    }

    public TranslatedTextLineBuilder buildTextLine() {
        return new TranslatedTextLineBuilder(this.guiBuilder, this.translation);
    }

    /**
     * Delete a text line.
     *
     * @param textLine Text line to delete.
     */
    public final void delete(final TranslatedTextLine textLine) {
        this.guiBuilder.delete(textLine);
    }

    /**
     * Delete a text area.
     *
     * @param textArea Text area to delete.
     */
    public final void delete(final TranslatedTextArea textArea) {
        this.guiBuilder.delete(textArea);
    }

    /**
     * Build a new progress bar widget.
     *
     * @param name           Unique progress bar name.
     * @param coordinates    Progress bar coordinates.
     * @param leftMat        Material for the left corner.
     * @param middleMat      Material for extensible part(will be stretched when the progress bar progress increase).
     * @param rightMat       Material for the right corner.
     * @param emptyMiddleMat Material for the middle part to fill when still empty.
     * @param emptyRightMat  Material for the right border part to fill when still empty.
     * @param borderWidth    Width of the border element.
     * @param container      Container holding the progress bar.
     * @return The new progress bar widget.
     */
    public final ProgressBar buildProgressBar(final String name, final BaseCoordinate coordinates, final Material leftMat, final Material middleMat, final Material rightMat,
                                              final Material emptyMiddleMat, final Material emptyRightMat, final int borderWidth, final GuiContainer container) {
        return this.guiBuilder.buildProgressBar(name, coordinates, leftMat, middleMat, rightMat, emptyMiddleMat, emptyRightMat, borderWidth, container);
    }

    /**
     * Build a new progress bar widget.
     *
     * @param name       Unique progress bar name.
     * @param background Image to use as empty background.
     * @param filled     Image to use as filled background.
     * @param container  Container holding the progress bar.
     * @return The new progress bar widget.
     */
    public final ProgressBar buildProgressBar(final String name, final Image background, final Image filled, final GuiContainer container) {
        return this.guiBuilder.buildProgressBar(name, background, filled, container);
    }

    /**
     * Build a new progress bar widget, all parameter are set to empty.
     *
     * @param name      Unique progress bar name.
     * @param container Container holding the progress bar.
     * @return The new progress bar widget.
     */
    public final ProgressBar buildProgressBar(final String name, final GuiContainer container) {
        return this.guiBuilder.buildProgressBar(name, container);
    }

    /**
     * Build a new simple progress bar widget.
     *
     * @param name        Unique progress bar name.
     * @param coordinates Progress bar coordinates.
     * @param empty       Material to use on empty part.
     * @param filled      Material to use on filled part.
     * @param container   Container holding the progress bar.
     * @return The new progress bar widget.
     */
    public final ProgressBar buildProgressBar(final String name, final BaseCoordinate coordinates, final Material empty, final Material filled, final GuiContainer container) {
        return this.guiBuilder.buildProgressBar(name, coordinates, empty, filled, container);
    }

    /**
     * Build a new simple progress bar widget with a random unique name.
     *
     * @param coordinates Progress bar coordinates.
     * @param empty       Material to use on empty part.
     * @param filled      Material to use on filled part.
     * @param container   Container holding the progress bar.
     * @return The new progress bar widget.
     */
    public final ProgressBar buildProgressBar(final BaseCoordinate coordinates, final Material empty, final Material filled, final GuiContainer container) {
        return this.guiBuilder.buildProgressBar(coordinates, empty, filled, container);
    }

    /**
     * Delete a progress bar.
     *
     * @param progressBar Progress bar to delete.
     */
    public final void delete(final ProgressBar progressBar) {
        this.guiBuilder.delete(progressBar);
    }

    /**
     * Build a new input box widget.
     *
     * @param name             Unique input box name.
     * @param coordinates      Input box coordinates.
     * @param font             Font used for the caption.
     * @param background       Background material.
     * @param backgroundHlight Background material when the widget is focused.
     * @param cursorMaterial   Material to use for the cursor.
     * @param container        Container holding the input box.
     * @return The new input box widget.
     */
    public final TranslatedInputBox buildInputBox(final String name, final BaseCoordinate coordinates, final Font font, final Material background, final Material backgroundHlight, final Material cursorMaterial,
                                                  final GuiContainer container) {
        return new TranslatedInputBox(this.guiBuilder.buildInputBox(name, coordinates, font, background, backgroundHlight, cursorMaterial, container), this.translation);
    }

    /**
     * Build a new input box widget, all parameters are set to empty.
     *
     * @param name      Unique input box name.
     * @param container Container holding the input box.
     * @return The new input box widget.
     */
    public final TranslatedInputBox buildInputBox(final String name, final GuiContainer container) {
        return new TranslatedInputBox(this.guiBuilder.buildInputBox(name, container), this.translation);
    }

    /**
     * Build a new input box widget.
     *
     * @param name           Unique input box name.
     * @param coordinates    Input box coordinates.
     * @param material       Background materials.
     * @param cursorMaterial Material to use for the cursor.
     * @param container      Container holding the input box.
     * @return The new input box widget.
     * @pre material.font !empty
     */
    public final TranslatedInputBox buildInputBox(final String name, final BaseCoordinate coordinates, final ButtonMaterial material, final Material cursorMaterial, final GuiContainer container) {
        return new TranslatedInputBox(this.guiBuilder.buildInputBox(name, coordinates, material, cursorMaterial, container), this.translation);
    }

    /**
     * Build a new input box widget.
     *
     * @param position  Input box position.
     * @param def       Input box data.
     * @param container Container holding the input box.
     * @return The new input box widget.
     */
    public final TranslatedInputBox buildInputBox(final Position position, final InputBoxGui.InputBoxDefinition def, final GuiContainer container) {
        return new TranslatedInputBox(this.guiBuilder.buildInputBox(position, def, container), this.translation);
    }

    /**
     * Delete an input box.
     *
     * @param inputBox Input box to delete.
     */
    public final void delete(final TranslatedInputBox inputBox) {
        this.guiBuilder.delete(inputBox);
    }

    /**
     * Build a new check box widget.
     *
     * @param name        Unique check box name.
     * @param coordinates Check box coordinates.
     * @param background  Background material.
     * @param check       Material when checked.
     * @param font        Font to use for caption.
     * @param container   Container holding the check box widget.
     * @return The new check box widget.
     */
    public final TranslatedCheckBox buildCheckBox(@NonNull final String name, @NonNull final BaseCoordinate coordinates, @NonNull final Material background, @NonNull final Material hover,
                                                  @NonNull final Material check, @NonNull final Font font, @NonNull final GuiContainer container) {
        return new TranslatedCheckBox(this.guiBuilder.buildCheckBox(name, coordinates, background, hover, check, font, container), this.translation);
    }

    /**
     * Delete a check box.
     *
     * @param checkBox Check box to delete.
     */
    public final void delete(final CheckBox checkBox) {
        this.guiBuilder.delete(checkBox);
    }


    /**
     * Retrieve a GuiContainer from its name, if no name matches, an {@link IllegalArgumentException} is thrown.
     *
     * @param name Name of the GuiContainer to retrieve.
     * @return The GuiContainer associated with the name.
     */
    public final GuiContainer getContainer(final String name) {
        return this.guiBuilder.getContainer(name);
    }

    /**
     * Delete a container.
     *
     * @param container Gui container to delete.
     */
    public final void delete(final GuiContainer container) {
        this.guiBuilder.delete(container);
    }

    /**
     * Build a new tab container.
     *
     * @param name        Container name, must be unique.
     * @param titles      Tab titles.
     * @param coordinates Container position and size.
     * @param tabWidth    Tab width size.
     * @param tabHeight   Tab height size.
     * @param background  Container background material.
     * @param tabMaterial Material on inactive tab.
     * @param highlight   Material on tab with mouse over.
     * @param pushed      Material on active tab.
     * @param font        Font to use on the tabs.
     * @param container   Container holding the element.
     * @return The newly created tab container.
     */
    public final TabContainer buildTabcontainer(final String name, final String[] titles, final BaseCoordinate coordinates, final int tabWidth, final int tabHeight, final Material background,
                                                final Material tabMaterial, final Material highlight, final Material pushed, final Font font, final GuiContainer container) {
        return this.guiBuilder.buildTabcontainer(name, titles, coordinates, tabWidth, tabHeight, background, tabMaterial, highlight, pushed, font, container);
    }

    public Size getScreenSize() {
        return this.guiBuilder.getScreenSize();
    }

    public Translation getTranslation() {
        return this.translation;
    }

    public GuiBuilder getGuiBuilder() {
        return this.guiBuilder;
    }

    public Optional<Image> findImage(String name) {
        return this.guiBuilder.findImage(name);
    }

    public TranslatedTextAreaBuilder buildTextArea() {
        return new TranslatedTextAreaBuilder(this.guiBuilder, this.translation);
    }

    public ContainerBuilder buildContainer() {
        return new ContainerBuilder(this.guiBuilder);
    }
}
