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

import be.yildiz.common.Size;
import be.yildiz.common.collections.Lists;
import be.yildiz.module.graphic.Material;
import lombok.Getter;

import java.util.List;

/**
 * Data definition to create a GuiContainer widget from an external resource(i.e
 * parsing a script file).
 *
 * @author Grégory Van den Borre
 */
public final class ContainerDefinition extends GuiCommonDefinition {

    /**
     * List of children image widget.
     */
    @Getter
    private final List<ImageDefinition> imageList = Lists.newList();
    /**
     * List of children text line widget.
     */
    @Getter
    private final List<TextLineDefinition> textLineList = Lists.newList();
    /**
     * List of children button widget.
     */
    @Getter
    private final List<ButtonDefinition> buttonList = Lists.newList();
    /**
     * List of children input box widget.
     */
    @Getter
    private final List<InputBoxDefinition> inputBoxList = Lists.newList();
    /**
     * List of children text area widget.
     */
    @Getter
    private final List<TextAreaDefinition> textAreaList = Lists.newList();
    /**
     * Container background material.
     */
    @Getter
    private Material material = Material.empty();
    /**
     * Container depth value.
     */
    @Getter
    private int z;

    /**
     * Simple constructor, initialize with empty values.
     *
     * @param screen Screen size data.
     */
    ContainerDefinition(final Size screen) {
        super(screen);
    }

    /**
     * @param materialName Name of the material for the container background.
     */
    void setMaterial(final String materialName) {
        this.material = Material.get(materialName);
    }

    /**
     * @param zValue New container depth position value.
     */
    void setZ(final String zValue) {
        this.z = Integer.parseInt(zValue);
    }

    /**
     * @param imageDef Image definition to be used as child by the container.
     */
    void addImage(final ImageDefinition imageDef) {
        this.imageList.add(imageDef);
    }

    /**
     * @param textLineDef GuiTextLine definition to be used as child by the container.
     */
    void addTextLine(final TextLineDefinition textLineDef) {
        this.textLineList.add(textLineDef);
    }

    /**
     * @param buttonDef GuiButton definition to be used as child by the container.
     */
    void addButton(final ButtonDefinition buttonDef) {
        this.buttonList.add(buttonDef);
    }

    /**
     * @param inputDef InputBoxGui definition to be used as child by the container.
     */
    void addInputBox(final InputBoxDefinition inputDef) {
        this.inputBoxList.add(inputDef);
    }

    /**
     * @param textAreaDef TextAreaGui definition to be used as child by the container.
     */
    void addTextArea(final TextAreaDefinition textAreaDef) {
        this.textAreaList.add(textAreaDef);
    }
}
