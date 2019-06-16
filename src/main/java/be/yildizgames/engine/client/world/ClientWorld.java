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
import be.yildizgames.module.color.Color;
import be.yildizgames.module.graphic.Font;
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
import be.yildizgames.module.physics.Gravity;

/**
 * @author Grégory Van den Borre
 */
public interface ClientWorld {

    void setGravity(float x, float y, float z);

    void setGravity(Gravity g);

    void addCollisionListener(CollisionListener l);

    void addGhostCollisionListener(CollisionListener l);


    /**
     * Create a new game object builder.
     * @return The game object builder.
     */
    ClientGameObjectBuilder createObject();

    ClientGameObject createObject(ClientGameObjectTemplate template);

    /**
     * Create a new camera.
     * @param name Camera name, must be unique.
     * @return The created camera.
     */
    BehavioredCamera createCamera(String name);

    /**
     * Set the current skybox.
     * @param sky Skybox to set.
     */
    void setSkybox(Skybox sky);

    void setDebugMode();

    /**
     * Set the ambiant light color.
     * @param color Color to use.
     */
    void setAmbientLight(Color color);



    ElectricArc createElectricArc(Point3D origin, Point3D end, float width);

    Explosion createExplosion();

    /**
     * Create a new particle system.
     * @return The created particle system.
     */
    ParticleSystem createParticleSystem();

    Sky createSky();

    Ocean createOcean();

    Line create3DLine();

    LensFlare createLensFlare(LensFlare.LensFlareMaterial mat, Point3D position);

    PointLight createPointLight(String name, Point3D position);

    SpotLight createSpotLight(String name, Point3D position, Point3D direction);

    DirectionalLight createDirectionalLight(String name, Point3D position, Point3D direction);

    void serializeShapeFromMesh(String mesh, String file, String name);

    boolean isDebug();

    BehavioredCamera getCamera(String name);

    Light getLight(String name);

    MovableText createMovableText(String name, String text, Font font);

    BillboardSet createBillboardSet(Material material);

    Query createQuery(RayProvider provider);

    GroundQuery createGroundQuery(RayProvider provider);

    /**
     * @deprecated Create explicitly the camera.
     * @return the created camera.
     */
    @Deprecated(since = "2.1.2", forRemoval = true)
    BehavioredCamera getDefaultCamera();

    /**
     * @deprecated Should be deleted in the light
     * @param light light.
     */
    @Deprecated(since = "2.1.2", forRemoval = true)
    void deleteLight(Light light);

    /**
     * @deprecated Should be deleted in the light
     * @param name light name.
     */
    @Deprecated(since = "2.1.2", forRemoval = true)
    void deleteLight(String name);
}
