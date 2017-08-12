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
import be.yildiz.common.Color;
import be.yildiz.common.id.EntityId;
import be.yildiz.common.shape.Box;
import be.yildiz.common.shape.Plane;
import be.yildiz.common.shape.Sphere;
import be.yildiz.common.vector.Point3D;
import be.yildiz.module.graphic.*;

/**
 * @author Grégory Van den Borre
 */
public interface ClientWorld {

    ClientGameEntity createMovableDoodad(Box box, Material material);

    ClientGameEntity createMovableDoodad(Sphere sphere, Material material);

    ClientGameEntity createMovableDoodad(Plane plane, Material material);

    ClientGameEntity createMovableDoodad(GraphicMesh mesh);

    ClientGameEntity createStaticDoodad(Box box, Material material, Point3D position, Point3D direction);

    ClientGameEntity createStaticDoodad(Plane plane, Material material, Point3D position, Point3D direction);

    ClientGameEntity createStaticDoodad(Sphere sphere, Material material, Point3D position, Point3D direction);

    ClientGameEntity createStaticDoodad(Sphere sphere, Material material, Point3D position);

    ClientGameEntity createStaticDoodad(GraphicMesh mesh, Point3D position, Point3D direction);

    ClientGameEntity createStaticObject(EntityId id, Box box, Material material, Point3D position, Point3D direction);

    ClientGameEntity createStaticObject(EntityId id, Sphere sphere, Material material, Point3D position, Point3D direction);

    ClientGameEntity createStaticObject(EntityId id, GraphicMesh shape, Point3D position);

    ClientGameEntity createMovableObject(EntityId id, Box box, Material material, Point3D position);

    ClientGameEntity createMovableObject(EntityId id, Sphere sphere, Material material, Point3D position);

    ClientGameEntity createMovableObject(EntityId id, GraphicMesh shape, Point3D position);

    AbstractCamera createCamera(String name);

    void setSkybox(Skybox sky);

    void setDebugMode();

    void setAmbientLight(Color color);

    PointLight createPointLight(String name, Point3D position);

    ElectricArc createElectricArc(Point3D origin, Point3D end, float width);

    Explosion createExplosion();

    AbstractParticleSystem createParticleSystem();

    Sky createSky();

    Ocean createOcean();

    AbstractCamera getDefaultCamera();

    Line create3DLine();

    LensFlare createLensFlare(LensFlare.LensFlareMaterial mat, Point3D position);

    SpotLight createSpotLight(String name, Point3D position, Point3D direction);

    DirectionalLight createDirectionalLight(String name, Point3D position, Point3D direction);

    void serializeShapeFromMesh(String mesh, String file, String name);

    boolean isDebug();

    void deleteLight(AbstractLight light);

    AbstractCamera getCamera(String name);

    AbstractLight getLight(String name);

    void deleteLight(String name);

    MovableText createMovableText(String name, String text, Font font);

    BillboardSet createBillboardSet(Material material);

    ClientGameEntity createStaticObject(EntityId id, GraphicMesh mesh, Point3D position, Point3D direction);
}
