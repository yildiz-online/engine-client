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

import be.yildiz.module.graphic.MaterialPass.BlendMode;
import be.yildiz.module.graphic.MaterialPass.SceneBlend;
import be.yildiz.module.graphic.MaterialPass.Transparency;
import lombok.Getter;
import lombok.Setter;

/**
 * Data definition for a material.
 *
 * @author Grégory Van den Borre
 */
public final class SimpleMaterialDefinition {

    /**
     * Material transparent capability.
     */
    @Getter
    private Transparency transparency;

    /**
     * Path to the texture file used with the material.
     */
    @Getter
    @Setter
    private String path;

    /**
     * Path to the glow mask file used with the material, if any.
     */
    @Getter
    @Setter
    private String glowFile;

    /**
     * Material name, must be unique.
     */
    @Getter
    @Setter
    private String name;

    /**
     * <code>true</code> if the material rendering is affected by the lightning.
     */
    @Getter
    private boolean affectedByLight;

    /**
     * Material first pass blend mode.
     */
    @Getter
    private BlendMode blend;

    /**
     * Scene blend value.
     */
    @Getter
    private SceneBlend sceneBlend1;

    /**
     * Second scene blend value.
     */
    @Getter
    private SceneBlend sceneBlend2;

    /**
     * Path for second texture.
     */
    @Getter
    @Setter
    private String path2;

    /**
     * Simple constructor, initialize with empty values.
     */
    SimpleMaterialDefinition() {
        super();
        this.transparency = Transparency.NONE;
        this.blend = BlendMode.NONE;
        this.sceneBlend1 = SceneBlend.NONE;
        this.sceneBlend2 = SceneBlend.NONE;
        this.path = "";
        this.path2 = "";
        this.glowFile = "";
        this.name = "";
        this.affectedByLight = true;
    }

    /**
     * Set scene blend values, separated by '&'.
     *
     * @param value Value for the scene blend.
     */
    void setSceneBlend(final String value) {
        String[] values = value.split("&");
        this.sceneBlend1 = SceneBlend.valueOf(values[0].toUpperCase());
        this.sceneBlend2 = SceneBlend.valueOf(values[1].toUpperCase());
    }

    /**
     * Set the transparency value.
     *
     * @param transparencyMode New Transparent capability for the material.
     * @throw {@link IllegalArgumentException} If the capability cannot be
     * recognized(should be "alpha" or "color" or "none").
     */
    void setTransparency(final String transparencyMode) {
        switch (transparencyMode) {
            case "alpha":
                this.transparency = Transparency.ALPHA;
                break;
            case "color":
                this.transparency = Transparency.COLOR;
                break;
            case "none":
                this.transparency = Transparency.NONE;
                break;
            default:
                throw new IllegalArgumentException(transparencyMode + " is not a valid transparency value, "
                        + "only 'alpha', 'color' or 'none' are accepted.");
        }
    }

    /**
     * @param affected <code>true</code> if the material should be affected by the
     *                 lightning, only "true" and "false" values accepted.
     */
    void setAffectedByLight(final String affected) {
        switch (affected) {
            case "true":
                this.affectedByLight = true;
                break;
            case "false":
                this.affectedByLight = false;
                break;
            default:
                throw new IllegalArgumentException(affected + " is not a valid  value, " + "only 'true' or 'false' are accepted.");
        }
    }

    /**
     * Set the material first pass blend mode.
     *
     * @param blendMode Blend type, accepted values are "add", "subtract", "none",
     *                  "min," "max".
     */
    void setBlend(final String blendMode) {
        switch (blendMode) {
            case "add":
                this.blend = BlendMode.ADD;
                break;
            case "subtract":
                this.blend = BlendMode.SUBTRACT;
                break;
            case "none":
                this.blend = BlendMode.NONE;
                break;
            case "min":
                this.blend = BlendMode.MIN;
                break;
            case "max":
                this.blend = BlendMode.MAX;
                break;
            default:
                throw new IllegalArgumentException("unknown value");
        }

    }
}
