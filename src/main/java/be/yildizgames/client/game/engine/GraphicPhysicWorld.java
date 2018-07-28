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

import be.yildizgames.client.entity.ClientGameObject;
import be.yildizgames.client.entity.ClientGameObjectGraphic;
import be.yildizgames.client.entity.ClientGameObjectGraphicPhysic;
import be.yildizgames.common.gameobject.CollisionListener;
import be.yildizgames.common.geometry.Point3D;
import be.yildizgames.common.model.EntityId;
import be.yildizgames.common.shape.Box;
import be.yildizgames.common.shape.Plane;
import be.yildizgames.common.shape.Sphere;
import be.yildizgames.module.color.Color;
import be.yildizgames.module.graphic.Font;
import be.yildizgames.module.graphic.GraphicMesh;
import be.yildizgames.module.graphic.GraphicObject;
import be.yildizgames.module.graphic.GraphicWorld;
import be.yildizgames.module.graphic.billboard.BillboardSet;
import be.yildizgames.module.graphic.camera.BehavioredCamera;
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
import be.yildizgames.module.physics.DynamicBody;
import be.yildizgames.module.physics.GhostObject;
import be.yildizgames.module.physics.Gravity;
import be.yildizgames.module.physics.KinematicBody;
import be.yildizgames.module.physics.PhysicWorld;
import be.yildizgames.module.physics.StaticBody;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Grégory Van den Borre
 */
public class GraphicPhysicWorld implements ClientWorld {

    private final Map<Camera, BehavioredCamera> cameras = new HashMap<>();

    private final GraphicWorld graphicWorld;

    private final PhysicWorld physicWorld;

    GraphicPhysicWorld(GraphicWorld graphicWorld, PhysicWorld physicWorld) {
        this.graphicWorld = graphicWorld;
        this.physicWorld = physicWorld;
        this.cameras.put(this.graphicWorld.getDefaultCamera(), new BehavioredCamera(this.graphicWorld.getDefaultCamera()));

    }

    @Override
    public void setGravity(float x, float y, float z) {
        this.physicWorld.setGravity(x, y, z);
    }

    @Override
    public void setGravity(Gravity g) {
        this.physicWorld.setGravity(g);
    }

    @Override
    public void addCollisionListener(CollisionListener l) {
        this.physicWorld.addCollisionListener(l);
    }

    @Override
    public void addGhostCollisionListener(CollisionListener l) {
        this.physicWorld.addGhostCollisionListener(l);
    }

    @Override
    public GhostObject createGhost(EntityId id, Sphere sphere) {
        return this.physicWorld.createObject()
                .withId(id)
                .withShape(sphere)
                .buildGhost();
    }

    @Override
    public ClientGameObject createMovableDoodad(GraphicMesh mesh) {
        return new ClientGameObjectGraphic(graphicWorld.createMovableDoodad(mesh));
    }

    @Override
    public ClientGameObject createMovableDoodad(Box box, Material material) {
        return new ClientGameObjectGraphic(graphicWorld.createMovableDoodad(box, material));
    }

    @Override
    public ClientGameObject createMovableDoodad(Sphere sphere, Material material) {
        return new ClientGameObjectGraphic(graphicWorld.createMovableDoodad(sphere, material));
    }

    @Override
    public ClientGameObject createMovableDoodad(Plane plane, Material material) {
        return new ClientGameObjectGraphic(graphicWorld.createMovableDoodad(plane, material));
    }

    @Override
    public ClientGameObject createStaticDoodad(GraphicMesh mesh, Point3D position, Point3D direction) {
        return new ClientGameObjectGraphic(graphicWorld.createStaticDoodad(mesh, position, direction));
    }

    @Override
    public ClientGameObject createStaticDoodad(Box box, Material material, Point3D position, Point3D direction) {
        return new ClientGameObjectGraphic(graphicWorld.createStaticDoodad(box, material, position, direction));
    }

    @Override
    public ClientGameObject createStaticDoodad(Sphere sphere, Material material, Point3D position, Point3D direction) {
        return new ClientGameObjectGraphic(graphicWorld.createStaticDoodad(sphere, material, position, direction));
    }

    @Override
    public ClientGameObject createStaticDoodad(Plane plane, Material material, Point3D position, Point3D direction) {
        return new ClientGameObjectGraphic(graphicWorld.createStaticDoodad(plane, material, position, direction));
    }

    @Override
    public ClientGameObject createStaticObject(EntityId id, GraphicMesh shape, Point3D position, Point3D direction) {
        StaticBody body = this.physicWorld
                .createObject()
                .withId(id)
                .withShape(Sphere.fromRadius(100))
                .atPosition(position)
                .withDirection(direction)
                .buildStatic();
        GraphicObject object = this.graphicWorld.createStaticObject(id, shape, position, direction);
        return ClientGameObjectGraphicPhysic.withGraphicMaster(body, object);
    }

    @Override
    public ClientGameObject createStaticObject(EntityId id, Box box, Material material, Point3D position, Point3D direction) {
        StaticBody body = this.physicWorld
                .createObject()
                .withId(id)
                .withShape(box)
                .atPosition(position)
                .withDirection(direction)
                .buildStatic();
        GraphicObject object =  this.graphicWorld.createStaticObject(id, box, material, position, direction);
        return ClientGameObjectGraphicPhysic.withGraphicMaster(body, object);
    }

    @Override
    public ClientGameObject createStaticObject(EntityId id, Sphere sphere, Material material, Point3D position, Point3D direction) {
        StaticBody body = this.physicWorld
                .createObject()
                .withId(id)
                .withShape(sphere)
                .atPosition(position)
                .withDirection(direction)
                .buildStatic();
        GraphicObject object = this.graphicWorld.createStaticObject(id, sphere, material, position, direction);
        return ClientGameObjectGraphicPhysic.withGraphicMaster(body, object);
    }

    @Override
    public ClientGameObject createStaticObject(EntityId id, Plane plane, Material material, Point3D position, Point3D direction) {
        StaticBody body = this.physicWorld
                .createObject()
                .withId(id)
                .withShape(plane)
                .atPosition(position)
                .withDirection(direction)
                .buildStatic();
        GraphicObject object = this.graphicWorld.createStaticObject(id, plane, material, position, direction);
        return ClientGameObjectGraphicPhysic.withGraphicMaster(body, object);
    }

    @Override
    public ClientGameObject createMovableObject(EntityId id, GraphicMesh shape, Point3D position) {
        KinematicBody body = this.physicWorld
                .createObject()
                .withId(id)
                .withShape(Sphere.fromRadius(100))
                .atPosition(position)
                .buildKinematic();
        GraphicObject object = this.graphicWorld.createMovableObject(id, shape, position);
        return ClientGameObjectGraphicPhysic.withGraphicMaster(body, object);
    }

    @Override
    public ClientGameObject createMovableObject(EntityId id, Box box, Material material, Point3D position) {
        KinematicBody body = this.physicWorld
                .createObject()
                .withId(id)
                .withShape(box)
                .atPosition(position)
                .buildKinematic();
        GraphicObject object = this.graphicWorld.createMovableObject(id, box, material, position);
        return ClientGameObjectGraphicPhysic.withGraphicMaster(body, object);
    }

    @Override
    public ClientGameObject createMovableObject(EntityId id, Sphere sphere, Material material, Point3D position) {
        KinematicBody body = this.physicWorld
                .createObject()
                .withId(id)
                .withShape(sphere)
                .atPosition(position)
                .buildKinematic();
        GraphicObject object = this.graphicWorld.createMovableObject(id, sphere, material, position);
        return ClientGameObjectGraphicPhysic.withGraphicMaster(body, object);
    }

    @Override
    public ClientGameObject createMovableObject(EntityId id, Plane plane, Material material, Point3D position) {
        KinematicBody body = this.physicWorld
                .createObject()
                .withId(id)
                .withShape(plane)
                .atPosition(position)
                .buildKinematic();
        GraphicObject object = this.graphicWorld.createMovableObject(id, plane, material, position);
        return ClientGameObjectGraphicPhysic.withGraphicMaster(body, object);
    }

    @Override
    public ClientGameObject createDynamicObject(EntityId id, GraphicMesh shape, float mass, Point3D position) {
        DynamicBody body = this.physicWorld
                .createObject()
                .withId(id)
                .withMass(mass)
                .withShape(Sphere.fromRadius(100))
                .atPosition(position)
                .buildDynamic();
        GraphicObject object = this.graphicWorld.createMovableObject(id, shape, position);
        return ClientGameObjectGraphicPhysic.withDynamicMaster(body, object);
    }

    @Override
    public ClientGameObject createDynamicObject(EntityId id, Box shape, float mass, Material material, Point3D position) {
        DynamicBody body = this.physicWorld
                .createObject()
                .withId(id)
                .withMass(mass)
                .withShape(shape)
                .atPosition(position)
                .buildDynamic();
        GraphicObject object = this.graphicWorld.createMovableObject(id, shape, material, position);
        return ClientGameObjectGraphicPhysic.withDynamicMaster(body, object);
    }

    @Override
    public ClientGameObject createDynamicObject(EntityId id, Sphere shape, float mass, Material material, Point3D position) {
        DynamicBody body = this.physicWorld
                .createObject()
                .withMass(mass)
                .withId(id)
                .withShape(shape)
                .atPosition(position)
                .buildDynamic();
        GraphicObject object = this.graphicWorld.createMovableObject(id, shape, material, position);
        return ClientGameObjectGraphicPhysic.withDynamicMaster(body, object);
    }

    @Override
    public ClientGameObject createDynamicObject(EntityId id, Plane shape, float mass, Material material, Point3D position) {
        DynamicBody body = this.physicWorld
                .createObject()
                .withId(id)
                .withMass(mass)
                .withShape(shape)
                .atPosition(position)
                .buildDynamic();
        GraphicObject object = this.graphicWorld.createMovableObject(id, shape, material, position);
        return ClientGameObjectGraphicPhysic.withDynamicMaster(body, object);
    }

    @Override
    public BehavioredCamera createCamera(String name) {
        Camera cam = this.graphicWorld.createCamera(name);
        BehavioredCamera bCam = new BehavioredCamera(cam);
        this.cameras.put(cam, bCam);
        return bCam;
    }

    @Override
    public void setSkybox(Skybox sky) {
        this.graphicWorld.setSkybox(sky);
    }

    @Override
    public void setDebugMode() {
        this.graphicWorld.setDebugMode();
    }

    @Override
    public void setAmbientLight(Color color) {
        this.graphicWorld.setAmbientLight(color);
    }

    @Override
    public PointLight createPointLight(String name, Point3D position) {
        return this.graphicWorld.createPointLight(name, position);
    }

    @Override
    public ElectricArc createElectricArc(Point3D origin, Point3D end, float width) {
        return this.graphicWorld.createElectricArc(origin, end, width);
    }

    @Override
    public Explosion createExplosion() {
        return this.graphicWorld.createExplosion();
    }

    @Override
    public ParticleSystem createParticleSystem() {
        return this.graphicWorld.createParticleSystem();
    }

    @Override
    public Sky createSky() {
        return this.graphicWorld.createSky();
    }

    @Override
    public Ocean createOcean() {
        return this.graphicWorld.createOcean();
    }

    @Override
    public BehavioredCamera getDefaultCamera() {
        return this.cameras.get(this.graphicWorld.getDefaultCamera());
    }

    @Override
    public Line create3DLine() {
        return this.graphicWorld.create3DLine();
    }

    @Override
    public LensFlare createLensFlare(LensFlare.LensFlareMaterial mat, Point3D position) {
        return this.graphicWorld.createLensFlare(mat, position);
    }

    @Override
    public SpotLight createSpotLight(String name, Point3D position, Point3D direction) {
        return this.graphicWorld.createSpotLight(name, position, direction);
    }

    @Override
    public DirectionalLight createDirectionalLight(String name, Point3D position, Point3D direction) {
        return this.graphicWorld.createDirectionalLight(name, position, direction);
    }

    @Override
    public void serializeShapeFromMesh(String mesh, String file, String name) {
        this.graphicWorld.serializeShapeFromMesh(mesh, file, name);
    }

    @Override
    public boolean isDebug() {
        return this.graphicWorld.isDebug();
    }

    @Override
    public void deleteLight(Light light) {
        this.graphicWorld.deleteLight(light);
    }

    @Override
    public BehavioredCamera getCamera(String name) {
        return this.cameras.get(this.graphicWorld.getCamera(name));
    }

    @Override
    public Light getLight(String name) {
        return this.graphicWorld.getLight(name);
    }

    @Override
    public void deleteLight(String name) {
        this.graphicWorld.deleteLight(name);
    }

    @Override
    public MovableText createMovableText(String name, String text, Font font) {
        return this.graphicWorld.createMovableText(name, text, font);
    }

    @Override
    public BillboardSet createBillboardSet(Material material) {
        return this.graphicWorld.createBillboardSet(material);
    }
}
