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
 * Parse a XML file to extract data to build a list of Playlist definition and
 * their children definition.
 *
 * @author Grégory Van den Borre
 */
final class XmlMusicParser implements MusicParser {

    /**
     * Simple constructor.
     */
    XmlMusicParser() {
        super();
    }

    /**
     * Build a list of PlayList and Music definitions from a XML file.
     *
     * @param xmlFile File to parse.
     * @return A list of PlayList definitions.
     */
    @Override
    public List<PlayListDefinition> parse(final File xmlFile) {
        final List<PlayListDefinition> playListDefinitionList = new ArrayList<>();
        final Document doc = XMLParser.getDocument(xmlFile);

        final NodeList playListList = doc.getElementsByTagName("playlist");

        for (int i = 0; i < playListList.getLength(); i++) {
            final Node playList = playListList.item(i);
            final NodeList itemList = playList.getChildNodes();
            final PlayListDefinition playListDefinition = new PlayListDefinition();
            for (int j = 0; j < itemList.getLength(); j++) {
                final Node item = itemList.item(j);
                final String itemName = item.getNodeName();
                if ("name".equals(itemName)) {
                    playListDefinition.setName(item.getTextContent());
                } else if ("music".equals(itemName)) {
                    final MusicDefinition musicDef = new MusicDefinition();
                    final NodeList musicItemList = item.getChildNodes();
                    for (int k = 0; k < musicItemList.getLength(); k++) {
                        final Node musicItem = musicItemList.item(k);
                        final String musicItemName = musicItem.getNodeName();
                        if ("name".equals(musicItemName)) {
                            musicDef.setName(musicItem.getTextContent());
                        } else if ("file".equals(musicItemName)) {
                            musicDef.setFile(musicItem.getTextContent());
                        }
                    }
                    playListDefinition.addMusic(musicDef);
                }
            }
            playListDefinitionList.add(playListDefinition);
        }
        return playListDefinitionList;
    }
}
