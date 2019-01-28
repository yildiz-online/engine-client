/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2019 Grégory Van den Borre
 *
 * More infos available: https://engine.yildiz-games.be
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

import be.yildizgames.common.gameobject.CollisionListener;
import be.yildizgames.common.geometry.Point3D;
import be.yildizgames.common.model.EntityId;
import be.yildizgames.common.shape.Box;
import be.yildizgames.common.shape.Plane;
import be.yildizgames.common.shape.Sphere;
import be.yildizgames.module.color.Color;
import be.yildizgames.module.graphic.Font;
import be.yildizgames.module.graphic.GraphicMesh;
import be.yildizgames.module.graphic.RayProvider;
import be.yildizgames.module.graphic.billboard.BillboardSet;
import be.yildizgames.module.graphic.camera.BehavioredCamera;
import be.yildizgames.module.graphic.light.DirectionalLight;
import be.yildizgames.module.graphic.light.LensFlare;
import be.yildizgames.module.graphic.light.Light;
import be.yildizgames.module.graphic.light.PointLight;
import be.yildizgames.module.graphic.light.SpotLight;
import be.yildizgames.module.graphic.material.Material;
import be.yildizgames.module.graphic.misc.ElectricArc;
import be.yildizgames.module.graphic.misc.Explosion;
import be.yildizgames.module.graphic.misc.Line;
import be.yildizgames.module.graphic.misc.MovableText;
import be.yildizgames.module.graphic.misc.Ocean;
import be.yildizgames.module.graphic.misc.Sky;
import be.yildizgames.module.graphic.misc.Skybox;
import be.yildizgames.module.graphic.particle.ParticleSystem;
import be.yildizgames.module.graphic.query.GroundQuery;
import be.yildizgames.module.graphic.query.Query;
import be.yildizgames.module.physics.GhostObject;
import be.yildizgames.module.physics.Gravity;

/**
 * @author Grégory Van den Borre
 */
public interface ClientWorld {

    void setGravity(float x, float y, float z);

    void setGravity(Gravity g);

    void addCollisionListener(CollisionListener l);

    void addGhostCollisionListener(CollisionListener l);

    ClientGameObjectBuilder createObjectBuilder();

    /**
     * @deprecated Use createObject instead
     */
    @Deprecated(since = "2.1.1", forRemoval = true)
    GhostObject createGhost(EntityId id, Sphere sphere);

    /**
     * @deprecated Use createObject instead
     */
    @Deprecated(since = "2.1.1", forRemoval = true)
    ClientGameObject createMovableDoodad(GraphicMesh mesh);

    /**
     * @deprecated Use createObject instead
     */
    @Deprecated(since = "2.1.1", forRemoval = true)
    ClientGameObject createMovableDoodad(Box box, Material material);

    /**
     * @deprecated Use createObject instead
     */
    @Deprecated(since = "2.1.1", forRemoval = true)
    ClientGameObject createMovableDoodad(Sphere sphere, Material material);

    /**
     * @deprecated Use createObject instead
     */
    @Deprecated(since = "2.1.1", forRemoval = true)
    ClientGameObject createMovableDoodad(Plane plane, Material material);

    /**
     * @deprecated Use createObject instead
     */
    @Deprecated(since = "2.1.1", forRemoval = true)
    default ClientGameObject createStaticDoodad(GraphicMesh mesh, Point3D position) {
        return createStaticDoodad(mesh, position, Point3D.BASE_DIRECTION);
    }

    /**
     * @deprecated Use createObject instead
     */
    @Deprecated(since = "2.1.1", forRemoval = true)
    ClientGameObject createStaticDoodad(GraphicMesh mesh, Point3D position, Point3D direction);

    /**
     * @deprecated Use createObject instead
     */
    @Deprecated(since = "2.1.1", forRemoval = true)
    default ClientGameObject createStaticDoodad(Box box, Material material, Point3D position) {
        return createStaticDoodad(box, material, position, Point3D.BASE_DIRECTION);
    }

    /**
     * @deprecated Use createObject instead
     */
    @Deprecated(since = "2.1.1", forRemoval = true)
    ClientGameObject createStaticDoodad(Box box, Material material, Point3D position, Point3D direction);

    /**
     * @deprecated Use createObject instead
     */
    @Deprecated(since = "2.1.1", forRemoval = true)
    default ClientGameObject createStaticDoodad(Sphere sphere, Material material, Point3D position) {
        return createStaticDoodad(sphere, material, position, Point3D.BASE_DIRECTION);
    }

    /**
     * @deprecated Use createObject instead
     */
    @Deprecated(since = "2.1.1", forRemoval = true)
    ClientGameObject createStaticDoodad(Sphere sphere, Material material, Point3D position, Point3D direction);

    /**
     * @deprecated Use createObject instead
     */
    @Deprecated(since = "2.1.1", forRemoval = true)
    default ClientGameObject createStaticDoodad(Plane plane, Material material, Point3D position) {
        return createStaticDoodad(plane, material, position, Point3D.BASE_DIRECTION);
    }

    /**
     * @deprecated Use createObject instead
     */
    @Deprecated(since = "2.1.1", forRemoval = true)
    ClientGameObject createStaticDoodad(Plane plane, Material material, Point3D position, Point3D direction);

    /**
     * @deprecated Use createObject instead
     */
    @Deprecated(since = "2.1.1", forRemoval = true)
    default ClientGameObject createStaticObject(EntityId id, GraphicMesh shape, Point3D position) {
        return createStaticObject(id, shape, position, Point3D.BASE_DIRECTION);
    }

    /**
     * @deprecated Use createObject instead
     */
    @Deprecated(since = "2.1.1", forRemoval = true)
    ClientGameObject createStaticObject(EntityId id, GraphicMesh shape, Point3D position, Point3D direction);

    /**
     * @deprecated Use createObject instead
     */
    @Deprecated(since = "2.1.1", forRemoval = true)
    default ClientGameObject createStaticObject(EntityId id, Box box, Material material, Point3D position) {
        return createStaticObject(id, box, material, position, Point3D.BASE_DIRECTION);
    }

    /**
     * @deprecated Use createObject instead
     */
    @Deprecated(since = "2.1.1", forRemoval = true)
    ClientGameObject createStaticObject(EntityId id, Box box, Material material, Point3D position, Point3D direction);

    /**
     * @deprecated Use createObject instead
     */
    @Deprecated(since = "2.1.1", forRemoval = true)
    default ClientGameObject createStaticObject(EntityId id, Sphere sphere, Material material, Point3D position) {
        return createStaticObject(id, sphere, material, position, Point3D.BASE_DIRECTION);
    }

    /**
     * @deprecated Use createObject instead
     */
    @Deprecated(since = "2.1.1", forRemoval = true)
    ClientGameObject createStaticObject(EntityId id, Sphere sphere, Material material, Point3D position, Point3D direction);

    /**
     * @deprecated Use createObject instead
     */
    @Deprecated(since = "2.1.1", forRemoval = true)
    default ClientGameObject createStaticObject(EntityId id, Plane plane, Material material, Point3D position) {
        return createStaticObject(id, plane, material, position);
    }

    /**
     * @deprecated Use createObject instead
     */
    @Deprecated(since = "2.1.1", forRemoval = true)
    ClientGameObject createStaticObject(EntityId id, Plane plane, Material material, Point3D position, Point3D direction);

    /**
     * @deprecated Use createObject instead
     */
    @Deprecated(since = "2.1.1", forRemoval = true)
    ClientGameObject createMovableObject(EntityId id, GraphicMesh shape, Point3D position);

    /**
     * @deprecated Use createObject instead
     */
    @Deprecated(since = "2.1.1", forRemoval = true)
    ClientGameObject createMovableObject(EntityId id, Box box, Material material, Point3D position);

    /**
     * @deprecated Use createObject instead
     */
    @Deprecated(since = "2.1.1", forRemoval = true)
    ClientGameObject createMovableObject(EntityId id, Sphere sphere, Material material, Point3D position);

    /**
     * @deprecated Use createObject instead
     */
    @Deprecated(since = "2.1.1", forRemoval = true)
    ClientGameObject createMovableObject(EntityId id, Plane plane, Material material, Point3D position);

    /**
     * @deprecated Use createObject instead
     */
    @Deprecated(since = "2.1.1", forRemoval = true)
    ClientGameObject createDynamicObject(EntityId id, GraphicMesh shape, float mass, Point3D position);

    /**
     * @deprecated Use createObject instead
     */
    @Deprecated(since = "2.1.1", forRemoval = true)
    ClientGameObject createDynamicObject(EntityId id, Box box, float mass, Material material, Point3D position);

    /**
     * @deprecated Use createObject instead
     */
    @Deprecated(since = "2.1.1", forRemoval = true)
    ClientGameObject createDynamicObject(EntityId id, Sphere sphere, float mass, Material material, Point3D position);

    /**
     * @deprecated Use createObject instead
     */
    @Deprecated(since = "2.1.1", forRemoval = true)
    ClientGameObject createDynamicObject(EntityId id, Plane plane, float mass, Material material, Point3D position);

    ClientGameObjectBuilder createObject();

    BehavioredCamera createCamera(String name);

    void setSkybox(Skybox sky);

    void setDebugMode();

    void setAmbientLight(Color color);

    PointLight createPointLight(String name, Point3D position);

    ElectricArc createElectricArc(Point3D origin, Point3D end, float width);

    Explosion createExplosion();

    ParticleSystem createParticleSystem();

    Sky createSky();

    Ocean createOcean();

    BehavioredCamera getDefaultCamera();

    Line create3DLine();

    LensFlare createLensFlare(LensFlare.LensFlareMaterial mat, Point3D position);

    SpotLight createSpotLight(String name, Point3D position, Point3D direction);

    DirectionalLight createDirectionalLight(String name, Point3D position, Point3D direction);

    void serializeShapeFromMesh(String mesh, String file, String name);

    boolean isDebug();

    void deleteLight(Light light);

    BehavioredCamera getCamera(String name);

    Light getLight(String name);

    void deleteLight(String name);

    MovableText createMovableText(String name, String text, Font font);

    BillboardSet createBillboardSet(Material material);

    Query createQuery(RayProvider provider);

    GroundQuery createGroundQuery(RayProvider provider);
}
