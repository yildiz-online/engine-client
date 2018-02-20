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
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * XML parser to build LightDefinition from the XML document.
 *
 * @author Grégory Van den Borre
 */
final class XmlLightParser {

    /**
     * Simple constructor.
     */
    XmlLightParser() {
        super();
    }

    /**
     * Build a list of LightDefinition from the XML document, the XML must be
     * formed following the given schema.
     *
     * @param xmlFile XML file.
     * @return The list of light definition.
     */
    public List<LightDefinition> parse(final File xmlFile) {
        final List<LightDefinition> result = new ArrayList<>();
        final Document doc = XMLParser.getDocument(xmlFile);
        final NodeList lightList = doc.getElementsByTagName("light");
        for (int i = 0; i < lightList.getLength(); i++) {
            final Node light = lightList.item(i);
            final NodeList itemList = light.getChildNodes();
            final LightDefinition def = new LightDefinition();
            for (int j = 0; j < itemList.getLength(); j++) {
                final Node item = itemList.item(j);
                final String value = item.getTextContent();
                switch (item.getNodeName()) {
                    case "name":
                        def.setName(value);
                        break;
                    case "type":
                        def.setType(value);
                        break;
                    case "x":
                        def.setX(value);
                        break;
                    case "y":
                        def.setY(value);
                        break;
                    case "z":
                        def.setZ(value);
                        break;
                    case "lightMaterial":
                        def.setLightMaterial(value);
                        break;
                    case "haloMaterial":
                        def.setHaloMaterial(value);
                        break;
                    case "burstMaterial":
                        def.setBurstMaterial(value);
                        break;
                    default:
                        break;
                }
            }
            result.add(def);
        }
        return result;
    }
}
