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

package be.yildizgames.engine.client.world;

import be.yildizgames.common.gameobject.Deletable;
import be.yildizgames.common.gameobject.GameMaterialization;
import be.yildizgames.common.geometry.Point3D;
import be.yildizgames.module.graphic.material.Material;

/**
 * Behavior for all game entities in client. Different implementations exists to
 * provide movable or static objects, physic support or not... This class is not
 * meant to be used directly, it is always better to wrap it in an Entity to
 * avoid memory leaks and access to null native pointers(I.E. moving it through
 * a FrameListener to move it instead of the move manager witch will manage its
 * live cycle too).
 *
 * @author Grégory Van den Borre
 */
public interface ClientGameObject extends GameMaterialization, Deletable {

    /**
     * Set the object visible on screen.
     */
    ClientGameObject show();

    /**
     * Set the object not visible on screen.
     */
    ClientGameObject hide();

    /**
     * @return <code>true</code> if the object will be displayed,
     * <code>false</code> otherwise.
     */
    boolean isVisible();

    /**
     * Show or hide the object.
     *
     * @param visible <code>true</code> to show, <code>false</code> to hide.
     */
    default ClientGameObject setVisible(boolean visible) {
        if (visible) {
            this.show();
        } else {
            this.hide();
        }
        return this;
    }

    /**
     * Set the object casting shadows or not.
     *
     * @param cast <code>true</code> to cast shadows, <code>false</code> to stop
     *             casting shadows.
     * @return This object.
     */
    ClientGameObject setCastShadow(boolean cast);

    /**
     * Set the material to this 3d object.
     *
     * @param newMaterial New material to use.
     * @return This object.
     */
    ClientGameObject setMaterial(Material newMaterial);

    /**
     * The gameobject cannot be picked by the mouse.
     */
    ClientGameObject setUnpickable();

    /**
     * @return <code>true</code> if the object cast shadows, <code>false</code>
     * otherwise.
     */
    boolean isCastingShadow();

    /**
     * Rotate on X and Y axis.
     *
     * @param yaw   X axis rotation value.
     * @param pitch Y axis rotation value.
     */
    ClientGameObject rotate(float yaw, float pitch);

    /**
     * Set the object direction looking at a point.
     *
     * @param target Target coordinate.
     */
    ClientGameObject lookAt(Point3D target);

    /**
     * Set a GPU program parameter.
     *
     * @param index Parameter index.
     * @param v1 Vector4 first value.
     * @param v2 Vector4 second value.
     * @param v3 Vector4 third value.
     * @param v4 Vector4 fourth value.
     * @return This object for chaining.
     */
    ClientGameObject setParameter(int index, float v1, float v2, float v3, float v4);

    /**
     * Set the distance from where the gameobject will no longer be visible.
     *
     * @param distance Distance to set.
     * @return This object for chaining.
     */
    ClientGameObject setRenderingDistance(int distance);

    /**
     * Set the object to be rendered behind others.
     * @return This object for chaining.
     */
    ClientGameObject setRenderBehind();

}
