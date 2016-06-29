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
import be.yildiz.module.graphic.gui.CheckBox;

/**
 * @author Grégory Van den Borre
 */
public class TranslatedCheckBox extends TranslatedContainerElementWrapper<CheckBox> implements CheckBox {

    private final CheckBox checkBox;
    private final Translation translation;

    public TranslatedCheckBox(CheckBox checkBox, Translation translation) {
        super(checkBox);
        this.checkBox = checkBox;
        this.translation = translation;
    }

    /**
     * Set the translation key for the box caption.
     *
     * @param key Key to use.
     * @return This object.
     */
    public TranslatedCheckBox setCaptionText(final Key key) {
        this.checkBox.setCaptionText(this.translation.translate(key));
        return this;
    }

    @Override
    public TranslatedCheckBox setCaptionText(String captionText) {
        this.checkBox.setCaptionText(captionText);
        return this;
    }

    @Override
    public void check(boolean checked) {
        this.checkBox.check(checked);
    }

    @Override
    public boolean isChecked() {
        return this.checkBox.isChecked();
    }
}
