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

import be.yildiz.common.Rectangle;
import be.yildiz.common.client.gui.listener.*;
import be.yildiz.common.vector.Point2D;
import be.yildiz.module.graphic.gui.*;

import java.util.Optional;

/**
 * @author Grégory Van den Borre
 */
class TranslatedContainerElementWrapper<T extends ContainerElement> extends GuiWrapper<T> implements ContainerElement {

    private final T wrapped;

    protected TranslatedContainerElementWrapper(T wrapped) {
        super(wrapped);
        this.wrapped = wrapped;
    }

    @Override
    public final void detachFromParent() {
        this.wrapped.detachFromParent();
    }

    @Override
    public final ContainerElement setLeftFromParent(BaseElement.PositionRelativeLeft p) {
        this.wrapped.setLeftFromParent(p);
        return this;
    }

    @Override
    public final ContainerElement setLeftFromParent(PositionRelativeLeft relative, int diff) {
        this.wrapped.setLeftFromParent(relative, diff);
        return this;
    }

    @Override
    public final ContainerElement setTopFromParent(PositionRelativeTop relative) {
        this.wrapped.setTopFromParent(relative);
        return this;
    }

    @Override
    public final ContainerElement setTopFromParent(PositionRelativeTop relative, int diff) {
        this.wrapped.setTopFromParent(relative, diff);
        return this;
    }

    @Override
    public final Optional<GuiContainer> getParent() {
        return this.wrapped.getParent();
    }

    @Override
    public void addMouseLeftClickListener(MouseLeftClickListener listener) {
        this.wrapped.addMouseLeftClickListener(listener);
    }

    @Override
    public void addMouseDoubleLeftClickListener(MouseDoubleLeftClickListener listener) {
        this.wrapped.addMouseDoubleLeftClickListener(listener);
    }

    @Override
    public void addMouseReleaseListener(MouseReleaseListener listener) {
        this.wrapped.addMouseReleaseListener(listener);
    }

    @Override
    public void removeAllClickListeners() {
        this.wrapped.removeAllClickListeners();
    }

    @Override
    public void addKeyboardListener(KeyboardListener listener) {
        this.wrapped.addKeyboardListener(listener);
    }

    @Override
    public void addSpecialKeyListener(SpecialKeyPressedListener listener) {
        this.wrapped.addSpecialKeyListener(listener);
    }

    @Override
    public void addMouseMoveListener(MouseMoveListener listener) {
        this.wrapped.addMouseMoveListener(listener);
    }

    @Override
    public void addMouseWheelListener(MouseWheelListener listener) {
        this.wrapped.addMouseWheelListener(listener);
    }

    @Override
    public void addMouseDragListener(MouseDragListener listener) {
        this.wrapped.addMouseDragListener(listener);
    }

    @Override
    public void addOnMouseOverListener(OnMouseOverListener listener) {
        this.wrapped.addOnMouseOverListener(listener);
    }

    @Override
    public boolean contains(Point2D position) {
        return this.wrapped.contains(position);
    }

    @Override
    public boolean contains(int x, int y) {
        return this.wrapped.contains(x, y);
    }

    @Override
    public void addEmptyZone(Rectangle zone) {
        this.wrapped.addEmptyZone(zone);
    }

    @Override
    public void align(Alignment alignement) {
        this.wrapped.align(alignement);
    }

    @Override
    public int getAbsoluteLeft() {
        return this.wrapped.getAbsoluteLeft();
    }

    @Override
    public int getAbsoluteTop() {
        return this.wrapped.getAbsoluteTop();
    }

    @Override
    public void registerAnimation(GuiAnimation anim) {
        this.wrapped.registerAnimation(anim);
    }

    @Override
    public void playAnimation(String animation) {
        this.wrapped.playAnimation(animation);
    }
}
