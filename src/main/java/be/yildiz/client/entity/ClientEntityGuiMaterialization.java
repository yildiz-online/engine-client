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

package be.yildiz.client.entity;

import be.yildiz.client.data.ClientBaseGuiMaterialization;
import be.yildiz.common.translation.Key;
import be.yildiz.module.graphic.Material;
import be.yildiz.module.graphic.gui.ButtonMaterial;

/**
 * @author Grégory Van den Borre
 */
public class ClientEntityGuiMaterialization extends ClientBaseGuiMaterialization {

    private final Material animatedIcon;

    private final Material mapIcon;

    private final Material hostileMapIcon;

    protected ClientEntityGuiMaterialization(Key nameKey, Key descriptionKey, Material icon, Material animatedIcon, ButtonMaterial constructionButton, Material mapIcon, Material hostileMapIcon) {
        super(nameKey, descriptionKey, icon, constructionButton);
        this.animatedIcon = animatedIcon;
        this.mapIcon = mapIcon;
        this.hostileMapIcon = hostileMapIcon;
    }

    public Material getAnimatedIcon() {
        return animatedIcon;
    }

    public Material getMapIcon() {
        return mapIcon;
    }

    public Material getHostileMapIcon() {
        return hostileMapIcon;
    }
}
