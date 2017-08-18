/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2017 Grégory Van den Borre
 *
 *  More infos available: https://www.yildiz-games.be
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 */

package be.yildiz.client.game.engine;

import be.yildiz.client.entity.ClientGameEntity;
import be.yildiz.client.entity.ClientGameEntityGraphic;
import be.yildiz.client.entity.ClientGameEntityGraphicPhysic;
import be.yildiz.common.Color;
import be.yildiz.common.id.EntityId;
import be.yildiz.common.shape.Box;
import be.yildiz.common.shape.Plane;
import be.yildiz.common.shape.Sphere;
import be.yildiz.common.vector.Point3D;
import be.yildiz.module.graphic.*;
import be.yildiz.module.physics.*;

/**
 * @author Grégory Van den Borre
 */
public class GraphicPhysicWorld implements ClientWorld {

    private final GraphicWorld graphicWorld;

    private final PhysicWorld physicWorld;

    public GraphicPhysicWorld(GraphicWorld graphicWorld, PhysicWorld physicWorld) {
        this.graphicWorld = graphicWorld;
        this.physicWorld = physicWorld;
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
    public ClientGameEntity createMovableDoodad(GraphicMesh mesh) {
        return new ClientGameEntityGraphic(graphicWorld.createMovableDoodad(mesh));
    }

    @Override
    public ClientGameEntity createMovableDoodad(Box box, Material material) {
        return new ClientGameEntityGraphic(graphicWorld.createMovableDoodad(box, material));
    }

    @Override
    public ClientGameEntity createMovableDoodad(Sphere sphere, Material material) {
        return new ClientGameEntityGraphic(graphicWorld.createMovableDoodad(sphere, material));
    }

    @Override
    public ClientGameEntity createMovableDoodad(Plane plane, Material material) {
        return new ClientGameEntityGraphic(graphicWorld.createMovableDoodad(plane, material));
    }

    @Override
    public ClientGameEntity createStaticDoodad(GraphicMesh mesh, Point3D position, Point3D direction) {
        return new ClientGameEntityGraphic(graphicWorld.createStaticDoodad(mesh, position, direction));
    }

    @Override
    public ClientGameEntity createStaticDoodad(Box box, Material material, Point3D position, Point3D direction) {
        return new ClientGameEntityGraphic(graphicWorld.createStaticDoodad(box, material, position, direction));
    }

    @Override
    public ClientGameEntity createStaticDoodad(Sphere sphere, Material material, Point3D position, Point3D direction) {
        return new ClientGameEntityGraphic(graphicWorld.createStaticDoodad(sphere, material, position, direction));
    }

    @Override
    public ClientGameEntity createStaticDoodad(Plane plane, Material material, Point3D position, Point3D direction) {
        return new ClientGameEntityGraphic(graphicWorld.createStaticDoodad(plane, material, position, direction));
    }

    @Override
    public ClientGameEntity createStaticObject(EntityId id, GraphicMesh shape, Point3D position, Point3D direction) {
        StaticBody body = this.physicWorld
                .createObject()
                .withId(id)
                .withShape(new Sphere(100))
                .atPosition(position)
                .withDirection(direction)
                .buildStatic();
        GraphicObject object = this.graphicWorld.createStaticObject(id, shape, position, direction);
        return ClientGameEntityGraphicPhysic.withGraphicMaster(body, object);
    }

    @Override
    public ClientGameEntity createStaticObject(EntityId id, Box box, Material material, Point3D position, Point3D direction) {
        StaticBody body = this.physicWorld
                .createObject()
                .withId(id)
                .withShape(box)
                .atPosition(position)
                .withDirection(direction)
                .buildStatic();
        GraphicObject object =  this.graphicWorld.createStaticObject(id, box, material, position, direction);
        return ClientGameEntityGraphicPhysic.withGraphicMaster(body, object);
    }

    @Override
    public ClientGameEntity createStaticObject(EntityId id, Sphere sphere, Material material, Point3D position, Point3D direction) {
        StaticBody body = this.physicWorld
                .createObject()
                .withId(id)
                .withShape(sphere)
                .atPosition(position)
                .withDirection(direction)
                .buildStatic();
        GraphicObject object = this.graphicWorld.createStaticObject(id, sphere, material, position, direction);
        return ClientGameEntityGraphicPhysic.withGraphicMaster(body, object);
    }

    @Override
    public ClientGameEntity createStaticObject(EntityId id, Plane plane, Material material, Point3D position, Point3D direction) {
        StaticBody body = this.physicWorld
                .createObject()
                .withId(id)
                .withShape(plane)
                .atPosition(position)
                .withDirection(direction)
                .buildStatic();
        GraphicObject object = this.graphicWorld.createStaticObject(id, plane, material, position, direction);
        return ClientGameEntityGraphicPhysic.withGraphicMaster(body, object);
    }

    @Override
    public ClientGameEntity createMovableObject(EntityId id, GraphicMesh shape, Point3D position) {
        KinematicBody body = this.physicWorld
                .createObject()
                .withId(id)
                .withShape(new Sphere(100))
                .atPosition(position)
                .buildKinematic();
        GraphicObject object = this.graphicWorld.createMovableObject(id, shape, position);
        return ClientGameEntityGraphicPhysic.withGraphicMaster(body, object);
    }

    @Override
    public ClientGameEntity createMovableObject(EntityId id, Box box, Material material, Point3D position) {
        KinematicBody body = this.physicWorld
                .createObject()
                .withId(id)
                .withShape(box)
                .atPosition(position)
                .buildKinematic();
        GraphicObject object = this.graphicWorld.createMovableObject(id, box, material, position);
        return ClientGameEntityGraphicPhysic.withGraphicMaster(body, object);
    }

    @Override
    public ClientGameEntity createMovableObject(EntityId id, Sphere sphere, Material material, Point3D position) {
        KinematicBody body = this.physicWorld
                .createObject()
                .withId(id)
                .withShape(sphere)
                .atPosition(position)
                .buildKinematic();
        GraphicObject object = this.graphicWorld.createMovableObject(id, sphere, material, position);
        return ClientGameEntityGraphicPhysic.withGraphicMaster(body, object);
    }

    @Override
    public ClientGameEntity createMovableObject(EntityId id, Plane plane, Material material, Point3D position) {
        KinematicBody body = this.physicWorld
                .createObject()
                .withId(id)
                .withShape(plane)
                .atPosition(position)
                .buildKinematic();
        GraphicObject object = this.graphicWorld.createMovableObject(id, plane, material, position);
        return ClientGameEntityGraphicPhysic.withGraphicMaster(body, object);
    }

    @Override
    public ClientGameEntity createDynamicObject(EntityId id, GraphicMesh shape, float mass, Point3D position) {
        DynamicBody body = this.physicWorld
                .createObject()
                .withId(id)
                .withMass(mass)
                .withShape(new Sphere(100))
                .atPosition(position)
                .buildDynamic();
        GraphicObject object = this.graphicWorld.createMovableObject(id, shape, position);
        return ClientGameEntityGraphicPhysic.withDynamicMaster(body, object);
    }

    @Override
    public ClientGameEntity createDynamicObject(EntityId id, Box shape, float mass, Material material, Point3D position) {
        DynamicBody body = this.physicWorld
                .createObject()
                .withId(id)
                .withMass(mass)
                .withShape(shape)
                .atPosition(position)
                .buildDynamic();
        GraphicObject object = this.graphicWorld.createMovableObject(id, shape, material, position);
        return ClientGameEntityGraphicPhysic.withDynamicMaster(body, object);
    }

    @Override
    public ClientGameEntity createDynamicObject(EntityId id, Sphere shape, float mass, Material material, Point3D position) {
        DynamicBody body = this.physicWorld
                .createObject()
                .withMass(mass)
                .withId(id)
                .withShape(shape)
                .atPosition(position)
                .buildDynamic();
        GraphicObject object = this.graphicWorld.createMovableObject(id, shape, material, position);
        return ClientGameEntityGraphicPhysic.withDynamicMaster(body, object);
    }

    @Override
    public ClientGameEntity createDynamicObject(EntityId id, Plane shape, float mass, Material material, Point3D position) {
        DynamicBody body = this.physicWorld
                .createObject()
                .withId(id)
                .withMass(mass)
                .withShape(shape)
                .atPosition(position)
                .buildDynamic();
        GraphicObject object = this.graphicWorld.createMovableObject(id, shape, material, position);
        return ClientGameEntityGraphicPhysic.withDynamicMaster(body, object);
    }

    @Override
    public AbstractCamera createCamera(String name) {
        return this.graphicWorld.createCamera(name);
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
    public AbstractParticleSystem createParticleSystem() {
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
    public AbstractCamera getDefaultCamera() {
        return this.graphicWorld.getDefaultCamera();
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
    public void deleteLight(AbstractLight light) {
        this.graphicWorld.deleteLight(light);
    }

    @Override
    public AbstractCamera getCamera(String name) {
        return this.graphicWorld.getCamera(name);
    }

    @Override
    public AbstractLight getLight(String name) {
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
