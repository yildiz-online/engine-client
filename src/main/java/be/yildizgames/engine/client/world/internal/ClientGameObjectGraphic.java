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

import be.yildizgames.common.gameobject.Movable;
import be.yildizgames.common.geometry.Point3D;
import be.yildizgames.common.model.EntityId;
import be.yildizgames.engine.client.world.ClientGameObject;
import be.yildizgames.module.graphic.GraphicObject;

/**
 * @author Grégory Van den Borre
 */
class ClientGameObjectGraphic extends BaseClientGameObject {

    private final GraphicObject graphicObject;

    ClientGameObjectGraphic(GraphicObject graphicObject) {
        super(graphicObject);
        this.graphicObject = graphicObject;
    }


    @Override
    public final ClientGameObject rotate(float yaw, float pitch) {
        this.graphicObject.rotate(yaw, pitch);
        return this;
    }

    @Override
    public final ClientGameObject lookAt(Point3D target) {
        this.graphicObject.lookAt(target);
        return this;
    }


    @Override
    public final EntityId getId() {
        return EntityId.WORLD;
    }

    @Override
    public final void rotate(float x, float y, float z, float w) {
        this.graphicObject.rotate(x, y , z ,w);
    }

    @Override
    public final void scale(float x, float y, float z) {
        this.graphicObject.scale(x, y, z);
    }

    @Override
    public final void delete() {
        this.graphicObject.delete();
    }

    @Override
    public final void sleep(boolean b) {
        //does nothing.
    }

    @Override
    public final void attachTo(Movable other) {
        this.graphicObject.attachTo(other);
    }

    @Override
    public final void addChild(Movable other) {
        this.graphicObject.addChild(other);
    }

    @Override
    public final void removeChild(Movable child) {
        this.graphicObject.removeChild(child);
    }

    @Override
    public final void attachToOptional(Movable other) {
        this.graphicObject.attachToOptional(other);
    }

    @Override
    public final void detachFromParent() {
        this.graphicObject.detachFromParent();
    }

    @Override
    public final void setPosition(Point3D newPosition) {
        this.graphicObject.setPosition(newPosition);
    }

    @Override
    public final void setDirection(Point3D newDirection) {
        this.graphicObject.setDirection(newDirection);
    }

    @Override
    public final void setPosition(float posX, float posY, float posZ) {
        this.graphicObject.setPosition(posX, posY, posZ);
    }

    @Override
    public final void setDirection(float dirX, float dirY, float dirZ) {
        this.graphicObject.setDirection(dirX, dirY, dirZ);
    }

    @Override
    public final void addOptionalChild(Movable child) {
        this.graphicObject.addOptionalChild(child);
    }

    @Override
    public final Movable getInternal() {
        return this.graphicObject.getInternal();
    }
}
