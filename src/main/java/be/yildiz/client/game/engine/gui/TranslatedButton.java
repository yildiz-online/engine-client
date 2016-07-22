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
import be.yildiz.common.client.gui.listener.MouseMoveListener;
import be.yildiz.common.translation.Key;
import be.yildiz.common.translation.Translation;
import be.yildiz.common.vector.Point2D;
import be.yildiz.module.graphic.Font;
import be.yildiz.module.graphic.Material;
import be.yildiz.module.graphic.gui.*;

/**
 * Decorate a button with the ability to translate the text.
 * @author Grégory Van den Borre
 */
public class TranslatedButton extends TranslatedContainerElementWrapper<Button> implements Button {

    /**
     * Associated button.
     */
    private final Button button;

    /**
     * Component to translate the button text.
     */
    private final Translation translation;

    TranslatedButton(final Button button, final Translation translation) {
        super(button);
        this.button = button;
        this.translation = translation;
    }

    /**
     * Set the text to print on the button.
     *
     * @param text Text to print.
     * @return This object.
     */
    public TranslatedButton setCaptionText(final Key text) {
        this.button.setCaptionText(translation.translate(text));
        return this;
    }

    @Override
    public TranslatedButton setCaptionText(String text) {
        this.button.setCaptionText(text);
        return this;
    }

    @Override
    public Font getCaptionFont() {
        return button.getCaptionFont();
    }

    @Override
    public void setCaptionFont(final Font font) {
        this.button.setCaptionFont(font);
    }

    @Override
    public void setMaterial(final ButtonMaterial material) {
        this.button.setMaterial(material);
    }

    @Override
    public Material getMaterial() {
        return this.button.getMaterial();
    }

    @Override
    public void setMaterial(Material m) {
        this.button.setMaterial(m);
    }

    @Override
    public Material getHighlightMaterial() {
        return this.button.getHighlightMaterial();
    }

    @Override
    public void addOnMouseOverListener(OnMouseOverListener l) {
        this.button.addOnMouseOverListener(l);
    }

    @Override
    public void addMouseMoveListener(MouseMoveListener l) {
        this.button.addMouseMoveListener(l);
    }

    @Override
    public void addMouseLeftClickListener(MouseLeftClickListener l) {
        this.button.addMouseLeftClickListener(l);
    }

    @Override
    public void delete() {
        this.button.delete();
    }

    @Override
    public void mouseLeftClick(int x, int y) {
        this.button.mouseLeftClick(x, y);
    }

    @Override
    public boolean contains(Point2D position) {
        return this.button.contains(position);
    }

    @Override
    public void highlight(boolean contains) {
        this.button.highlight(contains);
    }

    @Override
    public Element setMouseOver(boolean b, Point2D pos) {
        this.button.setMouseOver(b, pos);
        return this;
    }

    @Override
    public void setData(ButtonData buttonData) {
        this.button.setData(buttonData);
    }

    @Override
    public void desactivate() {
        this.button.desactivate();
    }

    @Override
    public void reactivate() {
        this.button.reactivate();
    }

    @Override
    public Element setFocusable(boolean b) {
        this.button.setFocusable(b);
        return this;
    }

    @Override
    public void setCaptionTextLeftAlignement(PositionRelativeLeft alignment, int diff) {
        this.button.setCaptionTextLeftAlignement(alignment, diff);
    }

    @Override
    public void setCaptionTextLeftAlignement(PositionRelativeLeft alignment) {
        this.button.setCaptionTextLeftAlignement(alignment);
    }

    @Override
    public void setCaptionTextTopAlignement(PositionRelativeTop alignement) {
        this.button.setCaptionTextTopAlignement(alignement);
    }

    @Override
    public void setCaptionTextTopAlignement(PositionRelativeTop alignment, int diff) {
        this.button.setCaptionTextTopAlignement(alignment, diff);
    }

    @Override
    public Material getInactiveMaterial() {
        return this.button.getInactiveMaterial();
    }

    @Override
    public void setInactiveMaterial(Material material) {
        this.button.setInactiveMaterial(material);
    }

    @Override
    public PositionRelativeLeft getCaptionHorizontalAlignment() {
        return this.button.getCaptionHorizontalAlignment();
    }

    @Override
    public int getCaptionHorizontalPadding() {
        return this.button.getCaptionHorizontalPadding();
    }

    @Override
    public PositionRelativeTop getCaptionVerticalAlignment() {
        return this.button.getCaptionVerticalAlignment();
    }

    @Override
    public int getCaptionVerticalPadding() {
        return this.button.getCaptionVerticalPadding();
    }
}
