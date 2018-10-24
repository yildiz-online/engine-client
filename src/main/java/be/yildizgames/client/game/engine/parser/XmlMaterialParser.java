/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2018 Grégory Van den Borre
 *
 * More infos available: https://www.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify, merge,
 * publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons
 * to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE  SOFTWARE.
 */

package be.yildizgames.client.game.engine.parser;

import be.yildizgames.common.file.xml.XMLParser;
import be.yildizgames.module.coordinate.Size;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * XML parser to build SimpleMaterialDefinition from the XML document.
 *
 * @author Grégory Van den Borre
 */
final class XmlMaterialParser implements MaterialParser {

    /**
     * Screen size value.
     */
    private final Size screenSize;

    /**
     * Full constructor.
     *
     * @param screenSize Screen size.
     */
    XmlMaterialParser(final Size screenSize) {
        super();
        this.screenSize = screenSize;
    }

    /**
     * Build a list of SimpleMaterialDefinition from the XML document, the XML
     * must be formed following the given schema.
     *
     * @param xmlFile XML file.
     * @return The list of material definition.
     */
    @Override
    public List<SimpleMaterialDefinition> parse(final Path xmlFile) {
        final List<SimpleMaterialDefinition> resultList = new ArrayList<>();
        final Document doc = XMLParser.getDocument(xmlFile);

        final NodeList materialList = doc.getElementsByTagName("material");
        for (int i = 0; i < materialList.getLength(); i++) {
            final Node material = materialList.item(i);
            final NodeList itemList = material.getChildNodes();
            final SimpleMaterialDefinition matDefinition = new SimpleMaterialDefinition();
            for (int j = 0; j < itemList.getLength(); j++) {
                final Node item = itemList.item(j);
                final String value = item.getTextContent();
                switch (item.getNodeName()) {
                    case "name":
                        matDefinition.setName(value);
                        break;
                    case "file":
                        matDefinition.setPath(value.replace("$screenWidth", String.valueOf(this.screenSize.width)).replace("$screenHeight",
                                String.valueOf(this.screenSize.height)));
                        break;
                    case "file2":
                        matDefinition.setPath2(value.replace("$screenWidth", String.valueOf(this.screenSize.width)).replace("$screenHeight",
                                String.valueOf(this.screenSize.height)));
                        break;
                    case "transparency":
                        matDefinition.setTransparency(value);
                        break;
                    case "glowFile":
                        matDefinition.setGlowFile(value);
                        break;
                    case "light":
                        matDefinition.setAffectedByLight(value);
                        break;
                    case "blend":
                        matDefinition.setBlend(value);
                        break;
                    case "sceneBlend":
                        matDefinition.setSceneBlend(value);
                        break;
                    default:
                        break;
                }
            }
            resultList.add(matDefinition);
        }
        return resultList;
    }
}
