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
import be.yildiz.common.resource.xml.XMLParser;
import be.yildiz.module.graphic.Font;
import be.yildiz.module.graphic.Material;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.List;

/**
 * Parse a XML file to extract data to build a list of GuiContainer definition
 * and their children definition.
 *
 * @author Grégory Van den Borre
 */
final class XmlGuiParser implements GuiParser {

    /**
     * Tag name for a GuiContainer.
     */
    private static final String CONTAINER = "container";

    /**
     * Node name for the container Z attribute.
     */
    private static final String NODE_Z = "z";

    /**
     * Node name for a material attribute.
     */
    private static final String MATERIAL = "material";

    /**
     * Node name for a material highlight attribute.
     */
    private static final String MATERIAL_H = "materialH";

    /**
     * Node name for a font attribute.
     */
    private static final String FONT = "font";

    /**
     * Node name for a container list of children.
     */
    private static final String CHILDREN = "children";

    /**
     * Node name for a container image child.
     */
    private static final String IMAGE = "image";

    /**
     * Node name for a container input box child.
     */
    private static final String INPUT_BOX = "input";

    /**
     * Node name for a container text line child.
     */
    private static final String TEXT_LINE = "textline";

    /**
     * Node name for a container text area child.
     */
    private static final String TEXT_AREA = "textarea";

    /**
     * Node name for a container button child.
     */
    private static final String BUTTON = "button";

    /**
     * Contains the screen size data.
     */
    private Size screen;

    /**
     * Simple constructor.
     *
     * @param screen Screen size data.
     */
    XmlGuiParser(final Size screen) {
        super();
        this.screen = screen;
    }

    /**
     * Parse the common data for all GUI elements like the position, the size
     * and the name.
     *
     * @param list List of node to parse.
     * @param def  Definition to fill the data.
     * @throws ParserException If an error occurs during the parsing.
     */
    private static void retrieveCommonData(final NodeList list, final GuiCommonDefinition def) throws ParserException {
        for (int i = 0; i < list.getLength(); i++) {
            final Node item = list.item(i);
            final String value = item.getTextContent();
            switch (item.getNodeName()) {
                case "name":
                    def.setName(value);
                    break;
                case "x":
                    def.setLeft(value);
                    break;
                case "y":
                    def.setTop(value);
                    break;
                case "width":
                    def.setWidth(value);
                    break;
                case "height":
                    def.setHeight(value);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Build a list of GuiContainer and widgets definitions from a XML file.
     *
     * @param xmlFile File to parse.
     * @return A list of container definitions.
     * @throws ParserException If an error occurs during the parsing.
     */
    @Override
    public List<ContainerDefinition> parse(final File xmlFile) throws ParserException {
        final List<ContainerDefinition> resultList = Lists.newList();
        final Document doc = XMLParser.getDocument(xmlFile);
        final NodeList viewList = doc.getElementsByTagName(XmlGuiParser.CONTAINER);
        for (int i = 0; i < viewList.getLength(); i++) {
            final Node view = viewList.item(i);
            final NodeList itemList = view.getChildNodes();
            final ContainerDefinition def = new ContainerDefinition(this.screen);
            XmlGuiParser.retrieveCommonData(itemList, def);
            for (int j = 0; j < itemList.getLength(); j++) {
                final Node item = itemList.item(j);
                final String itemName = item.getNodeName();
                if (itemName.equals(XmlGuiParser.NODE_Z)) {
                    def.setZ(item.getTextContent());
                } else if (itemName.equals(XmlGuiParser.MATERIAL)) {
                    def.setMaterial(item.getTextContent());
                } else if (itemName.equals(XmlGuiParser.CHILDREN)) {
                    final NodeList childrenList = item.getChildNodes();
                    for (int k = 0; k < childrenList.getLength(); k++) {
                        final Node child = childrenList.item(k);
                        final String childName = child.getNodeName();
                        if (childName.equals(XmlGuiParser.IMAGE)) {
                            final ImageDefinition imageDef = new ImageDefinition(screen);
                            final NodeList childItemList = child.getChildNodes();
                            XmlGuiParser.retrieveCommonData(childItemList, imageDef);
                            int length = childItemList.getLength();
                            for (int l = 0; l < length; l++) {
                                final Node childItem = childItemList.item(l);
                                final String childItemName = childItem.getNodeName();
                                if (childItemName.equals(XmlGuiParser.MATERIAL)) {
                                    imageDef.setMaterial(childItem.getTextContent());
                                }
                            }
                            def.addImage(imageDef);
                        } else if (childName.equals(XmlGuiParser.TEXT_LINE)) {
                            final TextLineDefinition textLineDef = new TextLineDefinition(this.screen);
                            final NodeList childItemList = child.getChildNodes();
                            XmlGuiParser.retrieveCommonData(childItemList, textLineDef);
                            int length = childItemList.getLength();
                            for (int l = 0; l < length; l++) {
                                final Node childItem = childItemList.item(l);
                                final String childItemName = childItem.getNodeName();
                                if (childItemName.equals(XmlGuiParser.FONT)) {
                                    textLineDef.setFont(childItem.getTextContent());
                                }
                            }
                            def.addTextLine(textLineDef);
                        } else if (childName.equals(XmlGuiParser.BUTTON)) {
                            String material = "";
                            String highlight = "";
                            String font = "";
                            final NodeList childItemList = child.getChildNodes();

                            int length = childItemList.getLength();
                            for (int l = 0; l < length; l++) {
                                final Node childItem = childItemList.item(l);
                                switch (childItem.getNodeName()) {
                                    case XmlGuiParser.MATERIAL:
                                        material = childItem.getTextContent();
                                        break;
                                    case XmlGuiParser.MATERIAL_H:
                                        highlight = childItem.getTextContent();
                                        break;
                                    case XmlGuiParser.FONT:
                                        font = childItem.getTextContent();
                                        break;
                                    default:
                                        throw new ParserException("Invalid node: " + childItem.getNodeName());
                                }
                            }
                            final ButtonDefinition buttonDef = new ButtonDefinition(material, highlight, font, this.screen);
                            XmlGuiParser.retrieveCommonData(childItemList, buttonDef);
                            def.addButton(buttonDef);
                        } else if (childName.equals(XmlGuiParser.INPUT_BOX)) {
                            final NodeList childItemList = child.getChildNodes();
                            String material = "";
                            String highlight = "";
                            String font = "";

                            int length = childItemList.getLength();
                            for (int l = 0; l < length; l++) {
                                final Node childItem = childItemList.item(l);
                                switch (childItem.getNodeName()) {
                                    case XmlGuiParser.MATERIAL:
                                        material = childItem.getTextContent();
                                        break;
                                    case XmlGuiParser.MATERIAL_H:
                                        highlight = childItem.getTextContent();
                                        break;
                                    case XmlGuiParser.FONT:
                                        font = childItem.getTextContent();
                                        break;
                                    default:
                                        break;
                                }
                            }
                            final InputBoxDefinition inputDef = new InputBoxDefinition(Material.get(material), Material.get(highlight),
                                    Font.get(font), this.screen);
                            XmlGuiParser.retrieveCommonData(childItemList, inputDef);
                            def.addInputBox(inputDef);
                        } else if (childName.equals(XmlGuiParser.TEXT_AREA)) {
                            final TextAreaDefinition textAreaDef = new TextAreaDefinition(screen);
                            final NodeList childItemList = child.getChildNodes();
                            XmlGuiParser.retrieveCommonData(childItemList, textAreaDef);
                            int length = childItemList.getLength();
                            for (int l = 0; l < length; l++) {
                                final Node childItem = childItemList.item(l);
                                final String childItemName = childItem.getNodeName();
                                if (childItemName.equals(XmlGuiParser.MATERIAL)) {
                                    textAreaDef.setMaterial(childItem.getTextContent());
                                } else if (childItemName.equals(XmlGuiParser.FONT)) {
                                    textAreaDef.setFont(childItem.getTextContent());
                                }
                            }
                            def.addTextArea(textAreaDef);
                        }
                    }
                }
            }
            resultList.add(def);
        }
        return resultList;
    }
}
