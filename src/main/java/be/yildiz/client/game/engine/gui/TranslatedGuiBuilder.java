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
import be.yildiz.common.Coordinates;
import be.yildiz.common.Position;
import be.yildiz.common.Size;
import be.yildiz.common.translation.Translation;
import be.yildiz.module.graphic.Font;
import be.yildiz.module.graphic.Material;
import be.yildiz.module.graphic.gui.*;
import be.yildiz.module.graphic.gui.button.ButtonBuilder;
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

    /**
     * Build a new widget button.
     *
     * @param name             Unique button name.
     * @param coordinates      GuiButton coordinates.
     * @param font             Font to use for the button label.
     * @param background       GuiButton background material.
     * @param backgroundHlight GuiButton background material when focused.
     * @param container        Container holding the button.
     * @return The built button.
     */
    @Deprecated
    public final TranslatedButton buildButton(final String name, final BaseCoordinate coordinates, final Font font, final Material background, final Material backgroundHlight, final GuiContainer container) {
        return new TranslatedButton(this.guiBuilder.buildButton(name, coordinates, font, background, backgroundHlight, container), this.translation);
    }

    /**
     * Build a new widget button with a random name.
     *
     * @param coordinates      GuiButton coordinates.
     * @param font             Font to use for the button label.
     * @param background       GuiButton background material.
     * @param backgroundHlight GuiButton background material when focused.
     * @param container        Container holding the button.
     * @return The built button.
     */
    @Deprecated
    public final TranslatedButton buildButton(final BaseCoordinate coordinates, final Font font, final Material background, final Material backgroundHlight, final GuiContainer container) {
        return new TranslatedButton(this.guiBuilder.buildButton(coordinates, font, background, backgroundHlight, container), this.translation);
    }

    /**
     * Build a new widget button.
     *
     * @param name        Unique button name.
     * @param coordinates GuiButton coordinates.
     * @param container   Container holding the button.
     * @return The built button.
     */
    @Deprecated
    public TranslatedButton buildButton(final String name, final Coordinates coordinates, final GuiContainer container) {
        return new TranslatedButton(this.guiBuilder.buildButton(name, coordinates, container), this.translation);
    }

    /**
     * Build a new widget button.
     *
     * @param name        Unique button name.
     * @param coordinates GuiButton coordinates.
     * @param material    GuiButton materials.
     * @param container   Container holding the button.
     * @return The built button.
     */
    @Deprecated
    public final TranslatedButton buildButton(final String name, final BaseCoordinate coordinates, final ButtonMaterial material, final GuiContainer container) {
        return new TranslatedButton(this.guiBuilder.buildButton(name, coordinates, material, container), this.translation);
    }

    /**
     * Build a new widget button with a random name, empty materials, no coordinates.
     *
     * @param container Container holding the button.
     * @return The built button.
     */
    @Deprecated
    public TranslatedButton buildButton(final GuiContainer container) {
        return new TranslatedButton(this.guiBuilder.buildButton(container), this.translation);
    }

    /**
     * Build a new widget button with a random name.
     *
     * @param coordinates GuiButton coordinates.
     * @param material    GuiButton materials.
     * @param container   Container holding the button.
     * @return The built button.
     */
    @Deprecated
    public final TranslatedButton buildButton(final BaseCoordinate coordinates, final ButtonMaterial material, final GuiContainer container) {
        return new TranslatedButton(this.guiBuilder.buildButton(coordinates, material, container), this.translation);
    }

    /**
     * Build a new widget button with a random generated unique name, and an empty material and font.
     *
     * @param coordinates GuiButton coordinates.
     * @param container   Container holding the button.
     * @return The built button.
     */
    @Deprecated
    public final TranslatedButton buildButton(final BaseCoordinate coordinates, final GuiContainer container) {
        return new TranslatedButton(this.guiBuilder.buildButton(coordinates, container), this.translation);
    }

    /**
     * Build a new widget button, all parameters are set to empty.
     *
     * @param name      Unique button name.
     * @param container Container holding the button.
     * @return The built button.
     */
    @Deprecated
    public final TranslatedButton buildButton(final String name, final GuiContainer container) {
        return new TranslatedButton(this.guiBuilder.buildButton(name, container), this.translation);
    }

    /**
     * Build a new widget button, parameters are retrieved from another button.
     *
     * @param name      Unique button name.
     * @param model     Other button used to retrieve parameters.
     * @param container Container holding the button.
     * @return The built button.
     */
    @Deprecated
    public final TranslatedButton buildButton(final String name, final Button model, final GuiContainer container) {
        return new TranslatedButton(this.guiBuilder.buildButton(name, model, container), this.translation);
    }

    public TranslatedButton buildButton(final Button button) {
        return new TranslatedButton(button, this.translation);
    }

    public ButtonBuilder createButton() {
        return new ButtonBuilder(this.guiBuilder);
    }

    /**
     * Delete a button.
     *
     * @param button GuiButton to delete.
     */
    public final void delete(final TranslatedButton button) {
        this.guiBuilder.delete(button);
    }

    /**
     * Build a new image widget.
     *
     * @param name        Unique image name.
     * @param coordinates Image coordinates.
     * @param background  Image background material.
     * @param container   Container holding the image.
     * @return The new image widget.
     */
    public final Image buildImage(final String name, final BaseCoordinate coordinates, final Material background, final GuiContainer container) {
        return this.guiBuilder.buildImage(name, coordinates, background, container);
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
     * Build a new image widget.
     *
     * @param name        Unique image name.
     * @param coordinates Image coordinates.
     * @param container   Container holding the image.
     * @return The new image widget.
     */
    public final Image buildImage(final String name, final BaseCoordinate coordinates, final GuiContainer container) {
        return this.guiBuilder.buildImage(name, coordinates, container);
    }

    /**
     * Build a new image widget.
     *
     * @param coordinates Image coordinates.
     * @param background  Image background material.
     * @param container   Container holding the image.
     * @return The new image widget.
     */
    public final Image buildImage(final BaseCoordinate coordinates, final Material background, final GuiContainer container) {
        return this.guiBuilder.buildImage(coordinates, background, container);
    }

    /**
     * Build a new image widget.
     *
     * @param coordinates Image coordinates.
     * @param container   Container holding the image.
     * @return The new image widget.
     */
    public final Image buildImage(final BaseCoordinate coordinates, final GuiContainer container) {
        return this.guiBuilder.buildImage(coordinates, container);
    }

    /**
     * Build a new image widget without background.
     *
     * @param name      Unique image name.
     * @param container Container holding the image.
     * @return The new image widget.
     */
    public final Image buildImage(final String name, final GuiContainer container) {
        return this.guiBuilder.buildImage(name, container);
    }

    /**
     * Delete an image.
     *
     * @param image Image to delete.
     */
    public final void delete(final Image image) {
        this.guiBuilder.delete(image);
    }

    /**
     * Build a new line of text widget.
     *
     * @param name      Text line unique name.
     * @param pos       Text line position.
     * @param font      Font used for the text.
     * @param container Container holding the text line.
     * @return The new text line.
     */
    public final TranslatedTextLine buildTextLine(final String name, final Position pos, final Font font, final GuiContainer container) {
        return new TranslatedTextLine(this.guiBuilder.buildTextLine(name, pos, font, container), this.translation);
    }

    /**
     * Build a new line of text widget with a random name.
     *
     * @param pos       Text line position.
     * @param font      Font used for the text.
     * @param container Container holding the text line.
     * @return The new text line.
     */
    public final TranslatedTextLine buildTextLine(final Position pos, final Font font, final GuiContainer container) {
        return new TranslatedTextLine(this.guiBuilder.buildTextLine(pos, font, container), this.translation);
    }

    /**
     * Build a new line of text widget, all parameters are set to empty.
     *
     * @param name      Text line unique name.
     * @param container Container holding the text line.
     * @return The new text line.
     */
    public final TranslatedTextLine buildTextLine(final String name, final GuiContainer container) {
        return new TranslatedTextLine(this.guiBuilder.buildTextLine(name, container), this.translation);
    }

    /**
     * Build a new line of text widget.
     *
     * @param font      Font to use.
     * @param container Container holding the text line.
     * @return The new text line.
     */
    public final TranslatedTextLine buildTextLine(final Font font, final GuiContainer container) {
        return new TranslatedTextLine(this.guiBuilder.buildTextLine(font, container), this.translation);
    }

    /**
     * Build a new line of text widget, all parameters are set to empty, a unique random name is generated.
     *
     * @param container Container holding the text line.
     * @return The new text line.
     */
    public final TranslatedTextLine buildTextLine(final GuiContainer container) {
        return new TranslatedTextLine(this.guiBuilder.buildTextLine(container), this.translation);
    }

    public TranslatedTextLine buildTextLineTitle(TextLineTitleTemplate template, GuiContainer background) {
        return new TranslatedTextLine(this.guiBuilder.buildTextLineTitle(template, background), this.translation);
    }

    public TranslatedTextLine buildTextLine(TextLineTemplate template, GuiContainer background) {
        return new TranslatedTextLine(this.guiBuilder.buildTextLine(template, background), this.translation);
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
     * Build a new multiple line text widget.
     *
     * @param name        Unique text area name.
     * @param coordinates Text area coordinates.
     * @param font        Font used for the text.
     * @param background  Background image.
     * @param textPadding Padding value for the text.
     * @param container   Container holding the text area.
     * @return The new text area widget.
     */
    public final TranslatedTextArea buildTextArea(final String name, final BaseCoordinate coordinates, final Font font, final Material background, final int textPadding, final GuiContainer container) {
        return new TranslatedTextArea(this.guiBuilder.buildTextArea(name, coordinates, font, background, textPadding, container), this.translation);
    }

    /**
     * Build a new multiple line text widget with a random name.
     *
     * @param coordinates Text area coordinates.
     * @param font        Font used for the text.
     * @param background  Background image.
     * @param textPadding Padding value for the text.
     * @param container   Container holding the text area.
     * @return The new text area widget.
     */
    public final TranslatedTextArea buildTextArea(final BaseCoordinate coordinates, final Font font, final Material background, final int textPadding, final GuiContainer container) {
        return new TranslatedTextArea(this.guiBuilder.buildTextArea(coordinates, font, background, textPadding, container), this.translation);
    }

    /**
     * Build a new multiple line text widget, all parameters are set to empty.
     *
     * @param name      Unique text area name.
     * @param container Container holding the text area.
     * @return The new text area widget.
     */
    public final TranslatedTextArea buildTextArea(final String name, final GuiContainer container) {
        return new TranslatedTextArea(this.guiBuilder.buildTextArea(name, container), this.translation);
    }

    /**
     * Build a new multiple line text widget with a default name, no background, and a padding of 0.
     *
     * @param coordinates Text area coordinates.
     * @param font        Font used for the text.
     * @param container   Container holding the text area.
     * @return The new text area widget.
     */
    public final TranslatedTextArea buildTextArea(final BaseCoordinate coordinates, final Font font, final GuiContainer container) {
        return new TranslatedTextArea(this.guiBuilder.buildTextArea(coordinates, font, container), this.translation);
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

    public final TranslatedCheckBox buildCheckBox(final String name, final Position pos, final CheckboxDefinition template, final GuiContainer container) {
        return new TranslatedCheckBox(this.guiBuilder.buildCheckBox(name, pos, template, container), this.translation);
    }

    public final TranslatedCheckBox buildCheckBox(final String name, final CheckboxDefinition template, final GuiContainer container) {
        return new TranslatedCheckBox(this.guiBuilder.buildCheckBox(name, template, container), this.translation);
    }

    /**
     * Build a new check box widget, all parameters are set to empty.
     *
     * @param name      Unique check box name.
     * @param container Container holding the check box widget.
     * @return The new check box widget.
     */
    public final TranslatedCheckBox buildCheckBox(final String name, final GuiContainer container) {
        return new TranslatedCheckBox(this.guiBuilder.buildCheckBox(name, container), this.translation);
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
     * Build a new GUI container, a GUI container is not meant to be used alone, it must be element of a view.
     *
     * @param name        Unique container name.
     * @param background  Material to use as background.
     * @param coordinates Container size and position.
     * @return The newly built container.
     */
    public final GuiContainer buildOverlayContainer(final String name, final Material background, final BaseCoordinate coordinates) {
        return this.guiBuilder.buildOverlayContainer(name, background, coordinates);
    }

    /**
     * Build a new GUI container, a GUI container is not meant to be used alone, it must be element of a view, a unique random name is generated.
     *
     * @param background  Material to use as background.
     * @param coordinates Container size and position.
     * @return The newly built container.
     */
    public final GuiContainer buildOverlayContainer(final Material background, final BaseCoordinate coordinates) {
        return this.guiBuilder.buildOverlayContainer(background, coordinates);
    }

    /**
     * Build a new GUI container, all parameters are set to empty, a GUI container is not meant to be used alone, it must be element of a view.
     *
     * @param name        Unique container name.
     * @param coordinates Container size and position.
     * @return The newly built container.
     */
    public final GuiContainer buildOverlayContainer(final String name, final BaseCoordinate coordinates) {
        return this.guiBuilder.buildOverlayContainer(name, coordinates);
    }

    /**
     * Build a new GUI container wrapped in a parent container.
     *
     * @param name        Unique container name.
     * @param background  Material to use as background.
     * @param coordinates Container size and position.
     * @param parent      Parent container.
     * @return The newly built container.
     */
    public final GuiContainer buildOverlayContainer(final String name, final BaseCoordinate coordinates, final Material background, final GuiContainer parent) {
        return this.guiBuilder.buildOverlayContainer(name, coordinates, background, parent);
    }

    /**
     * Build a new GUI container wrapped in a parent container with a random name.
     *
     * @param background  Material to use as background.
     * @param coordinates Container size and position.
     * @param parent      Parent container.
     * @return The newly built container.
     */
    public final GuiContainer buildOverlayContainer(final Material background, final BaseCoordinate coordinates, final GuiContainer parent) {
        return this.guiBuilder.buildOverlayContainer(background, coordinates, parent);
    }

    /**
     * Build a new GUI container wrapped in a parent container, position and size are same as parent, no background, z value is same as parent + 1.
     *
     * @param name   Unique container name.
     * @param parent Parent container.
     * @return The newly built container.
     */
    public final GuiContainer buildOverlayContainer(final String name, final GuiContainer parent) {
        return this.guiBuilder.buildOverlayContainer(name, parent);
    }

    /**
     * Build a new GUI container wrapped in a parent container, position and size are same as parent, no background, z value is same as parent + 1, a unique random name is generated.
     *
     * @param parent Parent container.
     * @return The newly built container.
     */
    public final GuiContainer buildOverlayContainer(final GuiContainer parent) {
        return this.guiBuilder.buildOverlayContainer(parent);
    }

    /**
     * Build a new GUI container wrapped in a parent container, no background, z value is same as parent + 1, a unique random name is generated.
     *
     * @param coordinates Coordinates.
     * @param parent      Parent container.
     * @return The newly built container.
     */
    public final GuiContainer buildOverlayContainer(final BaseCoordinate coordinates, final GuiContainer parent) {
        return this.guiBuilder.buildOverlayContainer(coordinates, parent);
    }

    /**
     * Build a new GUI container wrapped in a parent container, z value is same as parent + 1.
     *
     * @param coordinates Coordinates.
     * @param background  Container background material.
     * @param parent      Parent container.
     * @return The newly built container.
     */
    public GuiContainer buildOverlayContainer(final BaseCoordinate coordinates, final Material background, final GuiContainer parent) {
        return this.guiBuilder.buildOverlayContainer(coordinates, background, parent);
    }

    /**
     * Build a new GUI container wrapped in a parent container, no background, z value is same as parent + 1.
     *
     * @param name        Container name.
     * @param coordinates Coordinates.
     * @param parent      Parent container.
     * @return The newly built container.
     */
    public final GuiContainer buildOverlayContainer(final String name, final BaseCoordinate coordinates, final GuiContainer parent) {
        return this.guiBuilder.buildOverlayContainer(name, coordinates, parent);
    }

    /**
     * Build a new full screen GUI container, the position is set to (0, 0) and the size is the screen size, a GUI container is not meant to be used alone, it must be element of a view.
     *
     * @param name       Unique container name.
     * @param background Container background material.
     * @return The newly built container.
     */
    public final GuiContainer buildFullScreenOverlayContainer(final String name, final Material background) {
        return this.guiBuilder.buildFullScreenOverlayContainer(name, background);
    }

    /**
     * Build a new full screen GUI container, the position is set to (0, 0), there is no material and the size is the screen size, a GUI container is not meant to be used alone, it must be element of
     * a view.
     *
     * @param name Unique container name.
     * @return The newly built container.
     */
    public final GuiContainer buildFullScreenOverlayContainer(final String name) {
        return this.guiBuilder.buildFullScreenOverlayContainer(name);
    }

    /**
     * Build a new full screen GUI container with a random name, the position is set to (0, 0), there is no material and the size is the screen size, a GUI container is not meant to be used alone, it
     * must be element of a view.
     *
     * @return The newly built container.
     */
    public final GuiContainer buildFullScreenOverlayContainer() {
        return this.guiBuilder.buildFullScreenOverlayContainer();
    }

    /**
     * Build a new full screen GUI container with a random name, the position is set to (0, 0), there is no material and the size is the screen size, a GUI container is not meant to be used alone, it
     * must be element of a view.
     *
     * @param background Container background material.
     * @return The newly built container.
     */
    public final GuiContainer buildFullScreenOverlayContainer(final Material background) {
        return this.guiBuilder.buildFullScreenOverlayContainer(background);
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

    public GuiContainer buildOverlayContainer(Coordinates coordinates) {
        return this.guiBuilder.buildOverlayContainer(coordinates);
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

    public TranslatedTextLine buildTextLine(TextLine textLine) {
        return new TranslatedTextLine(textLine, this.translation);
    }
}
