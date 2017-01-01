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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data definition to create a light from an external resource(i.e parsing a
 * script file).
 *
 * @author Grégory Van den Borre
 */
@NoArgsConstructor
final class LightDefinition {

    /**
     * Light name, must be unique.
     */
    @Getter
    @Setter
    private String name = "";

    /**
     * Light type, accepted values are "point" and "directional"..
     */
    @Getter
    @Setter
    private String type = "point";

    /**
     * Position of the light.
     */
    @Getter
    private Point3D position = Point3D.ZERO;

    /**
     * Material for the light part of the lens flare, if any.
     */
    @Getter
    private Material lightMaterial = Material.empty();

    /**
     * Material for the halo part of the lens flare, if any.
     */
    @Getter
    private Material haloMaterial = Material.empty();

    /**
     * Material for the burst part of the lens flare, if any.
     */
    @Getter
    private Material burstMaterial = Material.empty();

    /**
     * Set the light position X.
     *
     * @param positionX light X position value.
     */
    void setX(final String positionX) {
        this.position = Point3D.xyz(Float.parseFloat(positionX), this.position.y, this.position.z);
    }

    /**
     * Set the light position Y.
     *
     * @param positionY light Y position value.
     */
    void setY(final String positionY) {
        this.position = Point3D.xyz(this.position.x, Float.parseFloat(positionY), this.position.z);
    }

    /**
     * Set the light position Z.
     *
     * @param positionZ light Z position value.
     */
    void setZ(final String positionZ) {
        this.position = Point3D.xyz(this.position.x, this.position.z, Float.parseFloat(positionZ));
    }

    /**
     * Set the material to use for the lens flare.
     *
     * @param light Light material of lens flare.
     */
    void setLightMaterial(final String light) {
        this.lightMaterial = Material.get(light);
    }

    /**
     * Set the material to use for the lens flare.
     *
     * @param halo Halo material of lens flare.
     */
    void setHaloMaterial(final String halo) {
        this.haloMaterial = Material.get(halo);
    }

    /**
     * Set the material to use for the lens flare.
     *
     * @param burst Burst material of lens flare.
     */
    void setBurstMaterial(final String burst) {
        this.burstMaterial = Material.get(burst);
    }
}
