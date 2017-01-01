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

import java.security.InvalidParameterException;

/**
 * Create the different parser.
 *
 * @author Grégory Van den Borre
 */
public final class ParserFactory {

    public static final String UNKNOWN_TYPE = "Unkonwn type.";

    /**
     * Parser type to use.
     */
    private final ParserType parserType;

    public ParserFactory(ParserType parserType) {
        this.parserType = parserType;
    }


    /**
     * Create a new music parser.
     *
     * @return A new MusicParser.
     */
    public MusicParser createMusicParser() {
        switch (this.parserType) {
            case XML:
                return new XmlMusicParser();
            default:
                throw new InvalidParameterException(UNKNOWN_TYPE);
        }
    }

    /**
     * Create a material parser.
     *
     * @param screen Screen size data.
     * @return A new MaterialParser.
     */
    public MaterialParser createMaterialParser(final Size screen) {
        switch (this.parserType) {
            case XML:
                return new XmlMaterialParser(screen);
            default:
                throw new InvalidParameterException(UNKNOWN_TYPE);
        }
    }

    /**
     * Create a font parser.
     *
     * @return A new FontParser.
     */
    public FontParser createFontParser() {
        switch (this.parserType) {
            case XML:
                return new XmlFontParser();
            default:
                throw new InvalidParameterException(UNKNOWN_TYPE);
        }
    }

    /**
     * Create a GUI parser.
     *
     * @param screen Screen size data.
     * @return A new GuiParser.
     */
    public GuiParser createGuiParser(final Size screen) {
        switch (this.parserType) {
            case XML:
                return new XmlGuiParser(screen);
            default:
                throw new InvalidParameterException(UNKNOWN_TYPE);
        }
    }

    /**
     * Different kind of possible parsers.
     *
     * @author Van den Borre Grégory
     */
    public enum ParserType {

        /**
         * File to parse are in XML.
         */
        XML
    }
}
