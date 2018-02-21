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

package be.yildizgames.client.game.engine;

import be.yildizgames.client.entity.ClientGameEntity;
import be.yildizgames.common.gameobject.CollisionListener;
import be.yildizgames.common.geometry.Point3D;
import be.yildizgames.common.model.EntityId;
import be.yildizgames.common.shape.Box;
import be.yildizgames.common.shape.Plane;
import be.yildizgames.common.shape.Sphere;
import be.yildizgames.module.color.Color;
import be.yildizgames.module.graphic.Font;
import be.yildizgames.module.graphic.GraphicMesh;
import be.yildizgames.module.graphic.billboard.BillboardSet;
import be.yildizgames.module.graphic.camera.Camera;
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

    GhostObject createGhost(EntityId id, Sphere sphere);

    ClientGameEntity createMovableDoodad(GraphicMesh mesh);

    ClientGameEntity createMovableDoodad(Box box, Material material);

    ClientGameEntity createMovableDoodad(Sphere sphere, Material material);

    ClientGameEntity createMovableDoodad(Plane plane, Material material);

    default ClientGameEntity createStaticDoodad(GraphicMesh mesh, Point3D position) {
        return createStaticDoodad(mesh, position, Point3D.BASE_DIRECTION);
    }

    ClientGameEntity createStaticDoodad(GraphicMesh mesh, Point3D position, Point3D direction);

    default ClientGameEntity createStaticDoodad(Box box, Material material, Point3D position) {
        return createStaticDoodad(box, material, position, Point3D.BASE_DIRECTION);
    }

    ClientGameEntity createStaticDoodad(Box box, Material material, Point3D position, Point3D direction);

    default ClientGameEntity createStaticDoodad(Sphere sphere, Material material, Point3D position) {
        return createStaticDoodad(sphere, material, position, Point3D.BASE_DIRECTION);
    }

    ClientGameEntity createStaticDoodad(Sphere sphere, Material material, Point3D position, Point3D direction);

    default ClientGameEntity createStaticDoodad(Plane plane, Material material, Point3D position) {
        return createStaticDoodad(plane, material, position, Point3D.BASE_DIRECTION);
    }

    ClientGameEntity createStaticDoodad(Plane plane, Material material, Point3D position, Point3D direction);

    default ClientGameEntity createStaticObject(EntityId id, GraphicMesh shape, Point3D position) {
        return createStaticObject(id, shape, position, Point3D.BASE_DIRECTION);
    }

    ClientGameEntity createStaticObject(EntityId id, GraphicMesh shape, Point3D position, Point3D direction);

    default ClientGameEntity createStaticObject(EntityId id, Box box, Material material, Point3D position) {
        return createStaticObject(id, box, material, position, Point3D.BASE_DIRECTION);
    }

    ClientGameEntity createStaticObject(EntityId id, Box box, Material material, Point3D position, Point3D direction);

    default ClientGameEntity createStaticObject(EntityId id, Sphere sphere, Material material, Point3D position) {
        return createStaticObject(id, sphere, material, position, Point3D.BASE_DIRECTION);
    }

    ClientGameEntity createStaticObject(EntityId id, Sphere sphere, Material material, Point3D position, Point3D direction);

    default ClientGameEntity createStaticObject(EntityId id, Plane plane, Material material, Point3D position) {
        return createStaticObject(id, plane, material, position);
    }

    ClientGameEntity createStaticObject(EntityId id, Plane plane, Material material, Point3D position, Point3D direction);

    ClientGameEntity createMovableObject(EntityId id, GraphicMesh shape, Point3D position);

    ClientGameEntity createMovableObject(EntityId id, Box box, Material material, Point3D position);

    ClientGameEntity createMovableObject(EntityId id, Sphere sphere, Material material, Point3D position);

    ClientGameEntity createMovableObject(EntityId id, Plane plane, Material material, Point3D position);

    ClientGameEntity createDynamicObject(EntityId id, GraphicMesh shape, float mass, Point3D position);

    ClientGameEntity createDynamicObject(EntityId id, Box box, float mass, Material material, Point3D position);

    ClientGameEntity createDynamicObject(EntityId id, Sphere sphere, float mass, Material material, Point3D position);

    ClientGameEntity createDynamicObject(EntityId id, Plane plane, float mass, Material material, Point3D position);

    Camera createCamera(String name);

    void setSkybox(Skybox sky);

    void setDebugMode();

    void setAmbientLight(Color color);

    PointLight createPointLight(String name, Point3D position);

    ElectricArc createElectricArc(Point3D origin, Point3D end, float width);

    Explosion createExplosion();

    ParticleSystem createParticleSystem();

    Sky createSky();

    Ocean createOcean();

    Camera getDefaultCamera();

    Line create3DLine();

    LensFlare createLensFlare(LensFlare.LensFlareMaterial mat, Point3D position);

    SpotLight createSpotLight(String name, Point3D position, Point3D direction);

    DirectionalLight createDirectionalLight(String name, Point3D position, Point3D direction);

    void serializeShapeFromMesh(String mesh, String file, String name);

    boolean isDebug();

    void deleteLight(Light light);

    Camera getCamera(String name);

    Light getLight(String name);

    void deleteLight(String name);

    MovableText createMovableText(String name, String text, Font font);

    BillboardSet createBillboardSet(Material material);
}