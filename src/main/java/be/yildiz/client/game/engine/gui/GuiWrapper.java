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

import be.yildiz.common.BaseCoordinate;
import be.yildiz.common.Coordinates;
import be.yildiz.common.Relative;
import be.yildiz.common.Size;
import be.yildiz.common.vector.Point2D;
import be.yildiz.module.graphic.gui.Element;

/**
 * @author Grégory Van den Borre
 */
class GuiWrapper<T extends Element> implements Element {

    private final T wrapped;

    protected GuiWrapper(final T wrapped) {
        super();
        this.wrapped = wrapped;
    }

    @Override
    public final Element hide() {
        wrapped.hide();
        return this;
    }

    @Override
    public final Element show() {
        wrapped.show();
        return this;
    }

    public final Element setVisible(boolean visible) {
        this.wrapped.setVisible(visible);
        return this;
    }

    @Override
    public final int getLeft() {
        return wrapped.getLeft();
    }

    @Override
    public final Element setLeft(int left) {
        wrapped.setLeft(left);
        return this;
    }

    @Override
    public final int getTop() {
        return wrapped.getTop();
    }

    @Override
    public final Element setTop(int top) {
        return wrapped.setTop(top);
    }

    @Override
    public final Element setPosition(int left, int top) {
        this.wrapped.setPosition(left, top);
        return this;
    }

    @Override
    public final int getWidth() {
        return wrapped.getWidth();
    }

    @Override
    public final void setWidth(int width) {
        this.wrapped.setWidth(width);
    }

    @Override
    public final int getHeight() {
        return wrapped.getHeight();
    }

    @Override
    public final void setHeight(int height) {
        this.wrapped.setHeight(height);
    }

    @Override
    public final BaseCoordinate getCoordinates() {
        return wrapped.getCoordinates();
    }

    @Override
    public void setCoordinates(Coordinates coordinates) {
        this.wrapped.setCoordinates(coordinates);
    }

    @Override
    public final boolean isVisible() {
        return this.wrapped.isVisible();
    }

    @Override
    public final Element setPosition(Point2D position) {
        this.wrapped.setPosition(position);
        return this;
    }

    @Override
    public final void resetVirtualHeight() {
        this.wrapped.resetVirtualHeight();
    }

    @Override
    public final void setVirtualHeight(int height) {
        this.wrapped.setVirtualHeight(height);
    }

    @Override
    public final String getName() {
        return wrapped.getName();
    }

    @Override
    public Element setPosition(Element other) {
        return this.wrapped.setPosition(other);
    }

    @Override
    public Element setTop(Element other, PositionRelativeTop relative, int diff) {
        return this.wrapped.setTop(other, relative, diff);
    }

    @Override
    public Element setTop(Element other, PositionRelativeTop top, Relative relativeDiff) {
        return this.wrapped.setTop(other, top, relativeDiff);
    }

    @Override
    public Element setTop(Element other, PositionRelativeTop relative) {
        return this.wrapped.setTop(other, relative);
    }

    @Override
    public Element setLeft(Element other, PositionRelativeLeft relative) {
        return this.wrapped.setLeft(other, relative);
    }

    @Override
    public Element setLeft(Element other, PositionRelativeLeft relative, int diff) {
        return this.wrapped.setLeft(other, relative, diff);
    }

    @Override
    public Element setLeft(Element other, PositionRelativeLeft left, Relative relativeDiff) {
        return this.wrapped.setLeft(other, left, relativeDiff);
    }

    @Override
    public void addToPosition(int xValue, int yValue) {
        this.wrapped.addToPosition(xValue, yValue);
    }

    @Override
    public void addToLeft(int value) {
        this.wrapped.addToLeft(value);
    }

    @Override
    public void addToTop(int value) {
        this.wrapped.addToTop(value);
    }

    @Override
    public int getBottom() {
        return this.wrapped.getBottom();
    }

    @Override
    public void setSize(Size size) {
        this.wrapped.setSize(size);
    }

    @Override
    public void setSize(int newWidth, int newHeight) {
        this.wrapped.setSize(newWidth, newHeight);
    }

    @Override
    public int getRight() {
        return this.wrapped.getRight();
    }
}
