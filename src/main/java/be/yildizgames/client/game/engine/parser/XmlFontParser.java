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
 * XML parser to build FontDefinition from the XML document.
 *
 * @author Grégory Van den Borre
 */
final class XmlFontParser implements FontParser {

    /**
     * Simple constructor.
     */
    XmlFontParser() {
        super();
    }


    /**
     * Build a list of FontDefinition from the XML document, the XML must be
     * formed following the given schema.
     *
     * @param xmlFile XML file.
     * @return The list of font definition.
     */
    @Override
    public List<FontDefinition> parse(final File xmlFile) {
        final List<FontDefinition> result = new ArrayList<>();
        final Document doc = XMLParser.getDocument(xmlFile);
        final NodeList fontList = doc.getElementsByTagName("font");
        for (int i = 0; i < fontList.getLength(); i++) {
            final Node font = fontList.item(i);
            final NodeList itemList = font.getChildNodes();
            final FontDefinition def = new FontDefinition();
            for (int j = 0; j < itemList.getLength(); j++) {
                final Node item = itemList.item(j);
                final String itemName = item.getNodeName();
                if ("name".equals(itemName)) {
                    def.setName(item.getTextContent());
                } else if ("file".equals(itemName)) {
                    def.setPath(item.getTextContent());
                } else if ("size".equals(itemName)) {
                    def.setSize(item.getTextContent());
                }
            }
            result.add(def);
        }
        return result;
    }
}
