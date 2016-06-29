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

import be.yildiz.common.translation.Key;
import be.yildiz.common.translation.Translation;
import be.yildiz.module.graphic.Font;
import be.yildiz.module.graphic.Material;
import be.yildiz.module.graphic.gui.InputBox;

/**
 * @author Grégory Van den Borre
 */
public class TranslatedInputBox extends TranslatedContainerElementWrapper<InputBox> implements InputBox {

    private final InputBox inputBox;
    private final Translation translation;

    TranslatedInputBox(InputBox inputBox, Translation translation) {
        super(inputBox);
        this.inputBox = inputBox;
        this.translation = translation;
    }

    public TranslatedInputBox setDefaultMessage(final Key text) {
        this.inputBox.setDefaultMessage(this.translation.translate(text));
        return this;
    }

    @Override
    public TranslatedInputBox setDefaultMessage(String message) {
        this.inputBox.setDefaultMessage(message);
        return this;
    }

    @Override
    public TranslatedInputBox setPassword() {
        this.inputBox.setPassword();
        return this;
    }

    @Override
    public TranslatedInputBox setMaterial(Material material) {
        this.inputBox.setMaterial(material);
        return this;
    }

    @Override
    public TranslatedInputBox setTextAlignment(PositionRelativeLeft left, PositionRelativeTop top) {
        this.inputBox.setTextAlignment(left, top);
        return this;
    }

    @Override
    public void setFont(Font font) {
        this.inputBox.setFont(font);
    }

    @Override
    public void setPasswordReplacement(char replacement) {
        this.inputBox.setPasswordReplacement(replacement);
    }

    @Override
    public String getText() {
        return this.inputBox.getText();
    }

    @Override
    public TranslatedInputBox setText(String text) {
        this.inputBox.setText(text);
        return this;
    }

    @Override
    public void setCaptionText(String caption) {
        this.inputBox.setCaptionText(caption);
    }

    @Override
    public void deleteText() {
        this.inputBox.deleteText();
    }

    @Override
    public TranslatedInputBox setTextCenter() {
        this.inputBox.setTextCenter();
        return this;
    }

    @Override
    public void removeChar() {
        this.inputBox.removeChar();
    }
}
