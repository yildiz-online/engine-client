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

package be.yildiz.client.game.engine.gui;

import be.yildiz.common.Color;
import be.yildiz.common.translation.Key;
import be.yildiz.common.translation.Translation;
import be.yildiz.module.graphic.Font;
import be.yildiz.module.graphic.Material;
import be.yildiz.module.graphic.gui.TextArea;

/**
 * @author Grégory Van den Borre
 */
public class TranslatedTextArea extends GuiWrapper<TextArea> implements TextArea {

    private final TextArea textArea;
    private final Translation translation;

    public TranslatedTextArea(TextArea textArea, Translation translation) {
        super(textArea);
        this.textArea = textArea;
        this.translation = translation;
    }


    public void addLine(final Key key) {
        this.textArea.addLine(this.translation.translate(key));
    }

    @Override
    public void addLine(final String line) {
        this.textArea.addLine(line);
    }

    /**
     * @param key Replace
     *            former text by a new one.
     */
    public void replaceText(final Key key) {
        this.textArea.replaceText(this.translation.translate(key));
    }

    @Override
    public void detachFromParent() {
        this.textArea.detachFromParent();
    }

    @Override
    public String getContent() {
        return this.textArea.getContent();
    }

    @Override
    public void deleteText() {
        this.textArea.deleteText();
    }

    @Override
    public void setFont(Font font) {
        this.textArea.setFont(font);
    }

    @Override
    public void setMaterial(Material newMaterial) {
        this.textArea.setMaterial(newMaterial);
    }

    @Override
    public TranslatedTextArea setColor(Color color) {
        this.textArea.setColor(color);
        return this;
    }

    @Override
    public void replaceText(String text) {
        this.textArea.replaceText(text);
    }
}
