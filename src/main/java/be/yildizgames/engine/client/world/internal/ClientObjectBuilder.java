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

package be.yildizgames.engine.client.world.internal;

import be.yildizgames.common.geometry.Point3D;
import be.yildizgames.common.model.EntityId;
import be.yildizgames.common.shape.Box;
import be.yildizgames.common.shape.Plane;
import be.yildizgames.common.shape.Sphere;
import be.yildizgames.engine.client.world.ClientGameObject;
import be.yildizgames.engine.client.world.ClientGameObjectBuilder;
import be.yildizgames.module.graphic.GraphicMesh;
import be.yildizgames.module.graphic.GraphicObjectBuilder;
import be.yildizgames.module.graphic.material.Material;
import be.yildizgames.module.physics.GhostObject;
import be.yildizgames.module.physics.PhysicMesh;
import be.yildizgames.module.physics.PhysicObjectBuilder;

class ClientObjectBuilder implements ClientGameObjectBuilder {

    private final PhysicObjectBuilder physicObjectBuilder;

    private final GraphicObjectBuilder graphicObjectBuilder;

    ClientObjectBuilder(PhysicObjectBuilder physicObjectBuilder, GraphicObjectBuilder graphicObjectBuilder) {
        this.physicObjectBuilder = physicObjectBuilder;
        this.graphicObjectBuilder = graphicObjectBuilder;
    }

    @Override
    public final ClientGameObjectBuilder withId(EntityId id) {
        this.physicObjectBuilder.withId(id);
        this.graphicObjectBuilder.withId(id);
        return this;
    }

    @Override
    public final ClientGameObjectBuilder withGraphicShape(Box box) {
        this.graphicObjectBuilder.withShape(box);
        return this;
    }

    @Override
    public final ClientGameObjectBuilder withGraphicShape(Sphere sphere) {
        this.graphicObjectBuilder.withShape(sphere);
        return this;
    }

    @Override
    public final ClientGameObjectBuilder withGraphicShape(Plane plane) {
        this.graphicObjectBuilder.withShape(plane);
        return this;
    }

    @Override
    public final ClientGameObjectBuilder withGraphicShape(GraphicMesh mesh) {
        this.graphicObjectBuilder.withShape(mesh);
        return this;
    }

    @Override
    public final ClientGameObjectBuilder withPhysicShape(Box box) {
        this.physicObjectBuilder.withShape(box);
        return this;
    }

    @Override
    public final ClientGameObjectBuilder withPhysicShape(Sphere sphere) {
        this.physicObjectBuilder.withShape(sphere);
        return this;
    }

    @Override
    public final ClientGameObjectBuilder withPhysicShape(Plane plane) {
        this.physicObjectBuilder.withShape(plane);
        return this;
    }

    @Override
    public final ClientGameObjectBuilder withPhysicShape(PhysicMesh mesh) {
        this.physicObjectBuilder.withShape(mesh);
        return this;
    }

    @Override
    public final ClientGameObjectBuilder withShape(Box box) {
        this.physicObjectBuilder.withShape(box);
        this.graphicObjectBuilder.withShape(box);
        return this;
    }

    @Override
    public final ClientGameObjectBuilder withShape(Sphere sphere) {
        this.physicObjectBuilder.withShape(sphere);
        this.graphicObjectBuilder.withShape(sphere);
        return this;
    }

    @Override
    public final ClientGameObjectBuilder withShape(Plane plane) {
        this.physicObjectBuilder.withShape(plane);
        this.graphicObjectBuilder.withShape(plane);
        return this;
    }

    @Override
    public final ClientGameObjectBuilder withShape(GraphicMesh graphicMesh, PhysicMesh physicMesh) {
        this.graphicObjectBuilder.withShape(graphicMesh);
        this.physicObjectBuilder.withShape(physicMesh);
        return this;
    }

    @Override
    public final ClientGameObjectBuilder withMaterial(Material material) {
        this.graphicObjectBuilder.withMaterial(material);
        return this;
    }

    @Override
    public final ClientGameObjectBuilder atPosition(Point3D position) {
        this.graphicObjectBuilder.atPosition(position);
        this.physicObjectBuilder.atPosition(position);
        return this;
    }

    @Override
    public final ClientGameObjectBuilder withDirection(Point3D direction) {
        this.graphicObjectBuilder.withDirection(direction);
        this.physicObjectBuilder.withDirection(direction);
        return this;
    }

    @Override
    public final ClientGameObjectBuilder withMass(float mass) {
        this.physicObjectBuilder.withMass(mass);
        return this;
    }

    @Override
    public final GhostObject buildGhost() {
        return this.physicObjectBuilder.buildGhost();
    }

    @Override
    public final ClientGameObject buildMovableObject() {
        return ClientGameObjectGraphicPhysic.withGraphicMaster(this.physicObjectBuilder.buildKinematic(), this.graphicObjectBuilder.buildMovable());
    }

    @Override
    public final ClientGameObject buildStaticObject() {
        return ClientGameObjectGraphicPhysic.withGraphicMaster(this.physicObjectBuilder.buildStatic(), this.graphicObjectBuilder.buildStatic());
    }

    @Override
    public final ClientGameObject buildDynamicObject() {
        return ClientGameObjectGraphicPhysic.withDynamicMaster(this.physicObjectBuilder.buildStatic(), this.graphicObjectBuilder.buildStatic());
    }

}
