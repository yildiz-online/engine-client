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

package be.yildiz.client.entity;

import be.yildiz.common.gameobject.GameMaterialization;
import be.yildiz.common.id.EntityId;
import be.yildiz.common.vector.Point3D;
import be.yildiz.module.graphic.ClientWorld;

/**
 * Provide a materialization of an entity for the game view.
 *
 * @author Grégory Van den Borre
 * @immutable
 */
@FunctionalInterface
public interface ClientEntityMaterialization {

    /**
     * Generate a new materialization.
     *
     * @param world    World to use to build the materialization.
     * @param id       Entity unique id.
     * @param position Position to set the built materialization.
     * @return The created materialization.
     * @Requires world != null
     * @Requires id != null
     * @Requires position != null
     * @Ensures result != null
     * @Ensures result.id == id
     * @Ensures result.position == position
     */
    GameMaterialization generate(ClientWorld world, EntityId id, Point3D position);

}
