/*
 *
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2018 Grégory Van den Borre
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
 *  portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 *
 */

package be.yildizgames.engine.client.world;

import be.yildizgames.common.geometry.Point3D;
import be.yildizgames.common.model.EntityId;
import be.yildizgames.common.shape.Box;
import be.yildizgames.common.shape.Plane;
import be.yildizgames.common.shape.Sphere;
import be.yildizgames.module.graphic.GraphicMesh;
import be.yildizgames.module.graphic.material.Material;
import be.yildizgames.module.physics.GhostObject;
import be.yildizgames.module.physics.PhysicMesh;

/**
 * Create new game objects.
 * @author Grégory Van den Borre
 */
public interface ClientGameObjectBuilder {

    /**
     * Specify the object unique id.
     * @param id Unique id.
     * @return This object for chaining.
     */
    ClientGameObjectBuilder withId(EntityId id);

    /**
     * Specify the shape of the graphic part.
     * @param box Shape to use.
     * @return This object for chaining.
     */
    ClientGameObjectBuilder withGraphicShape(Box box);

    /**
     * Specify the shape of the graphic part.
     * @param sphere Shape to use.
     * @return This object for chaining.
     */
    ClientGameObjectBuilder withGraphicShape(Sphere sphere);

    /**
     * Specify the shape of the graphic part.
     * @param plane Shape to use.
     * @return This object for chaining.
     */
    ClientGameObjectBuilder withGraphicShape(Plane plane);

    /**
     * Specify the shape of the graphic part.
     * @param mesh Shape to use.
     * @return This object for chaining.
     */
    ClientGameObjectBuilder withGraphicShape(GraphicMesh mesh);

    /**
     * Specify the shape of the physic part.
     * @param box Shape to use.
     * @return This object for chaining.
     */
    ClientGameObjectBuilder withPhysicShape(Box box);

    /**
     * Specify the shape of the physic part.
     * @param sphere Shape to use.
     * @return This object for chaining.
     */
    ClientGameObjectBuilder withPhysicShape(Sphere sphere);

    /**
     * Specify the shape of the physic part.
     * @param plane Shape to use.
     * @return This object for chaining.
     */
    ClientGameObjectBuilder withPhysicShape(Plane plane);

    /**
     * Specify the shape of the physic part.
     * @param mesh Shape to use.
     * @return This object for chaining.
     */
    ClientGameObjectBuilder withPhysicShape(PhysicMesh mesh);

    /**
     * Specify the shape of the graphic and the physic part.
     * @param box Shape to use.
     * @return This object for chaining.
     */
    ClientGameObjectBuilder withShape(Box box);

    /**
     * Specify the shape of the graphic and the physic part.
     * @param sphere Shape to use.
     * @return This object for chaining.
     */
    ClientGameObjectBuilder withShape(Sphere sphere);

    /**
     * Specify the shape of the graphic and the physic part.
     * @param plane Shape to use.
     * @return This object for chaining.
     */
    ClientGameObjectBuilder withShape(Plane plane);

    /**
     * Specify the shape of the graphic and the physic part.
     * @param gMesh Shape to use for graphic.
     * @param pMesh Shape to use for physic.
     * @return This object for chaining.
     */
    ClientGameObjectBuilder withShape(GraphicMesh gMesh, PhysicMesh pMesh);

    /**
     * Specify the material to apply on the model.
     * @param material Material to set.
     * @return This object for chaining.
     */
    ClientGameObjectBuilder withMaterial(Material material);

    /**
     * Specify the object initial position.
     * @param position Initial position.
     * @return This object for chaining.
     */
    ClientGameObjectBuilder atPosition(Point3D position);

    /**
     * Specify the object initial direction.
     * @param direction Initial direction.
     * @return This object for chaining.
     */
    ClientGameObjectBuilder withDirection(Point3D direction);

    /**
     * Specify the object mass, only used by dynamic objects.
     * @param mass Object mass.
     * @return This object for chaining.
     */
    ClientGameObjectBuilder withMass(float mass);

    /**
     * Create an object of type ghost, no visible and used to trigger collisions.
     * @return The created ghost object.
     */
    GhostObject buildGhost();

    /**
     * Create an object of type movable.
     * @return The created movable object.
     */
    ClientGameObject buildMovableObject();

    /**
     * Create an object of type static.
     * @return The created static object.
     */
    ClientGameObject buildStaticObject();

    /**
     * Create an object of type dynamic.
     * @return The created dynamic object.
     */
    ClientGameObject buildDynamicObject();
}
