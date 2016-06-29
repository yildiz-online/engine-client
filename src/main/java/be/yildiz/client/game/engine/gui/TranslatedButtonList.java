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

import be.yildiz.common.client.gui.listener.MouseLeftClickListener;
import be.yildiz.common.translation.Key;
import be.yildiz.common.translation.Translation;
import be.yildiz.module.graphic.gui.ButtonList;
import be.yildiz.module.graphic.gui.ButtonListGui;

/**
 * @author Grégory Van den Borre
 */
public class TranslatedButtonList extends TranslatedContainerElementWrapper<ButtonList> implements ButtonList {

    private final ButtonListGui buttonList;

    private final Translation translation;

    public TranslatedButtonList(ButtonListGui buttonList, Translation translation) {
        super(buttonList);
        this.buttonList = buttonList;
        this.translation = translation;
    }

    public void addElement(Key caption, MouseLeftClickListener listener) {
        this.buttonList.addElement(this.translation.translate(caption), listener);
    }

    @Override
    public void addElement(String caption, MouseLeftClickListener listener) {
        this.buttonList.addElement(caption, listener);
    }

    @Override
    public boolean isEmpty() {
        return this.buttonList.isEmpty();
    }
}
