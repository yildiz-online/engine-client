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

package be.yildizgames.engine.client.world.internal;

import be.yildizgames.common.geometry.Point3D;
import be.yildizgames.engine.client.world.ClientGameObject;
import be.yildizgames.module.graphic.GraphicObject;
import be.yildizgames.module.graphic.material.Material;

/**
 * @author Grégory Van den Borre
 */
abstract class BaseClientGameObject implements ClientGameObject {

    private final GraphicObject graphicObject;

    protected BaseClientGameObject(GraphicObject graphicObject) {
        super();
        this.graphicObject = graphicObject;
    }

    @Override
    public final BaseClientGameObject show() {
        this.graphicObject.show();
        return this;
    }

    @Override
    public final BaseClientGameObject hide() {
        this.graphicObject.hide();
        return this;
    }

    @Override
    public final boolean isVisible() {
        return this.graphicObject.isVisible();
    }

    @Override
    public final BaseClientGameObject setCastShadow(boolean cast) {
        this.graphicObject.setCastShadow(cast);
        return this;
    }

    @Override
    public final BaseClientGameObject setMaterial(Material material) {
        this.graphicObject.setMaterial(material);
        return this;
    }

    @Override
    public final BaseClientGameObject setUnpickable() {
        this.graphicObject.setUnpickable();
        return this;
    }

    @Override
    public final boolean isCastingShadow() {
        return this.graphicObject.isCastingShadow();
    }

    @Override
    public final BaseClientGameObject setParameter(int index, float v1, float v2, float v3, float v4) {
        this.graphicObject.setParameter(index, v1, v2, v3, v4);
        return this;
    }

    @Override
    public final BaseClientGameObject setRenderingDistance(int distance) {
        this.graphicObject.setRenderingDistance(distance);
        return this;
    }

    @Override
    public final BaseClientGameObject setRenderBehind() {
        this.graphicObject.setRenderBehind();
        return this;
    }

    @Override
    public final Point3D getDirection() {
        return this.graphicObject.getDirection();
    }

    @Override
    public final Point3D getAbsoluteDirection() {
        return this.graphicObject.getAbsoluteDirection();
    }

    @Override
    public final Point3D getScaleSize() {
        return this.graphicObject.getScaleSize();
    }

    @Override
    public final Point3D getPosition() {
        return this.graphicObject.getPosition();
    }

    @Override
    public final Point3D getAbsolutePosition() {
        return this.graphicObject.getAbsolutePosition();
    }
}
