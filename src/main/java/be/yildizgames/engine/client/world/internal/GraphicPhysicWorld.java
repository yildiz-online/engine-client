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

package be.yildizgames.engine.client.world.internal;

import be.yildizgames.common.gameobject.CollisionListener;
import be.yildizgames.common.geometry.Point3D;
import be.yildizgames.engine.client.world.ClientGameObject;
import be.yildizgames.engine.client.world.ClientGameObjectBuilder;
import be.yildizgames.engine.client.world.ClientGameObjectTemplate;
import be.yildizgames.engine.client.world.ClientWorld;
import be.yildizgames.module.color.Color;
import be.yildizgames.module.graphic.Font;
import be.yildizgames.module.graphic.GraphicWorld;
import be.yildizgames.module.graphic.RayProvider;
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
import be.yildizgames.module.graphic.misc.SkyBox;
import be.yildizgames.module.graphic.particle.ParticleSystem;
import be.yildizgames.module.graphic.query.GroundQuery;
import be.yildizgames.module.graphic.query.Query;
import be.yildizgames.module.physics.Gravity;
import be.yildizgames.module.physics.PhysicWorld;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Grégory Van den Borre
 */
public class GraphicPhysicWorld implements ClientWorld {

    private final Map<Camera, BehavioredCamera> cameras = new HashMap<>();

    private final GraphicWorld graphicWorld;

    private final PhysicWorld physicWorld;

    public GraphicPhysicWorld(GraphicWorld graphicWorld, PhysicWorld physicWorld) {
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
    public ClientGameObjectBuilder createObject() {
        return new ClientObjectBuilder(this.physicWorld.createObject(), this.graphicWorld.createObject());
    }

    @Override
    public ClientGameObject createObject(ClientGameObjectTemplate template) {
        ClientGameObjectBuilder builder = this.createObject();
        if(template.isDynamic()) {
            return builder.buildDynamicObject();
        } else if (template.isMovable()) {
            return builder.buildMovableObject();
        } else {
            return builder.buildStaticObject();
        }
    }

    @Override
    public BehavioredCamera createCamera(String name) {
        Camera cam = this.graphicWorld.createCamera(name);
        BehavioredCamera bCam = new BehavioredCamera(cam);
        this.cameras.put(cam, bCam);
        return bCam;
    }

    @Override
    public void setSkyBox(SkyBox sky) {
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

    @Override
    public Query createQuery(RayProvider provider) {
        return this.graphicWorld.createQuery(provider);
    }

    @Override
    public GroundQuery createGroundQuery(RayProvider provider) {
        return this.graphicWorld.createGroundQuery(provider);
    }
}
