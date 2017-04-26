/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2017 Grégory Van den Borre
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
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 */

package be.yildiz.client.game.engine.parser;

import be.yildiz.common.vector.Point3D;
import be.yildiz.module.graphic.Material;

/**
 * Data definition to create a light from an external resource(i.e parsing a
 * script file).
 *
 * @author Grégory Van den Borre
 */
final class LightDefinition {

    /**
     * Light name, must be unique.
     */
    private String name = "";

    /**
     * Light type, accepted values are "point" and "directional"..
     */
    private String type = "point";

    /**
     * Position of the light.
     */
    private Point3D position = Point3D.ZERO;

    /**
     * Material for the light part of the lens flare, if any.
     */
    private String lightMaterial = Material.EMPTY_NAME;

    /**
     * Material for the halo part of the lens flare, if any.
     */
    private String haloMaterial = Material.EMPTY_NAME;

    /**
     * Material for the burst part of the lens flare, if any.
     */
    private String burstMaterial = Material.EMPTY_NAME;

    public LightDefinition() {
        super();
    }

    /**
     * Set the light position X.
     *
     * @param positionX light X position value.
     * @throws IllegalArgumentException if positionX is null or empty or invalid.
     */
    void setX(final String positionX) {
        if(positionX == null) {
            throw new IllegalArgumentException("positionX is mandatory");
        }
        this.position = Point3D.xyz(Float.parseFloat(positionX), this.position.y, this.position.z);
    }

    /**
     * Set the light position Y.
     *
     * @param positionY light Y position value.
     * @throws IllegalArgumentException if positionY is null or empty or invalid.
     */
    void setY(final String positionY) {
        if(positionY == null) {
            throw new IllegalArgumentException("positionY is mandatory");
        }
        this.position = Point3D.xyz(this.position.x, Float.parseFloat(positionY), this.position.z);
    }

    /**
     * Set the light position Z.
     *
     * @param positionZ light Z position value.
     * @throws IllegalArgumentException if positionZ is null or empty or invalid.
     */
    void setZ(final String positionZ) {
        if(positionZ == null) {
            throw new IllegalArgumentException("positionZ is mandatory");
        }
        this.position = Point3D.xyz(this.position.x, this.position.y, Float.parseFloat(positionZ));
    }

    /**
     * Set the material to use for the lens flare.
     *
     * @param light Light material of lens flare.
     * @throws IllegalArgumentException if light is null or empty.
     */
    void setLightMaterial(final String light) {
        if(light == null || light.isEmpty()) {
            throw new IllegalArgumentException("Light material is mandatory");
        }
        this.lightMaterial = light;
    }

    /**
     * Set the material to use for the lens flare.
     *
     * @param halo Halo material of lens flare.
     * @throws IllegalArgumentException if halo is null or empty.
     */
    void setHaloMaterial(final String halo) {
        if(halo == null || halo.isEmpty()) {
            throw new IllegalArgumentException("Halo material is mandatory");
        }
        this.haloMaterial = halo;
    }

    /**
     * Set the material to use for the lens flare.
     *
     * @param burst Burst material of lens flare.
     * @throws IllegalArgumentException if burst is null or empty.
     */
    void setBurstMaterial(final String burst) {
        if(burst == null || burst.isEmpty()) {
            throw new IllegalArgumentException("Burst material is mandatory");
        }
        this.burstMaterial = burst;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Point3D getPosition() {
        return position;
    }

    public String getLightMaterial() {
        return lightMaterial;
    }

    public String getHaloMaterial() {
        return haloMaterial;
    }

    public String getBurstMaterial() {
        return burstMaterial;
    }

    /**
     * Set the light name.
     * @param name Light name, must be unique, cannot be null or empty.
     * @throws IllegalArgumentException if name is null or empty.
     */
    public void setName(String name) {
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name is mandatory");
        }
        this.name = name;
    }

    /**
     * Set the light type.
     * @param type Light type, must be unique, cannot be null or empty.
     * @throws IllegalArgumentException if name is null or empty or not with an allowed value(point).
     */
    public void setType(String type) {
        if(type == null) {
            throw new IllegalArgumentException("Type is mandatory");
        }
        if(!"point".equals(type)) {
            throw new IllegalArgumentException("Type values allowed: point");
        }
        this.type = type;
    }
}
