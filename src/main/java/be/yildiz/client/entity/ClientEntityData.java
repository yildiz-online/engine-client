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

import be.yildiz.client.data.ClientConstructionData;
import be.yildiz.common.Instance;
import be.yildiz.common.Level;
import be.yildiz.common.translation.Key;
import be.yildiz.module.graphic.Material;
import be.yildiz.module.graphic.gui.ButtonMaterial;
import be.yildiz.shared.entity.EntityData;
import be.yildiz.shared.entity.GameEntityData;
import be.yildizgames.engine.feature.entity.data.EntityType;
import be.yildizgames.engine.feature.entity.module.ModuleGroup;
import be.yildizgames.engine.feature.resource.ResourceValue;

import java.time.Duration;

/**
 * @author Grégory van den Borre
 */
public class ClientEntityData implements ClientConstructionData, EntityData {
//FIXME move that code to gui materialization
    private final GameEntityData data;

    private final ClientEntityMaterialization materialization;

    private final ClientEntityGuiMaterialization guiMaterialization;

    protected ClientEntityData(GameEntityData data, ClientEntityMaterialization materialization, ClientEntityGuiMaterialization guiMaterialization) {
        this.data = data;
        this.materialization = materialization;
        this.guiMaterialization = guiMaterialization;
    }

    @Override
    public Key getNameKey() {
        return this.guiMaterialization.getNameKey();
    }

    @Override
    public EntityType getType() {
        return this.data.getType();
    }

    @Override
    public int getSize() {
        return this.data.getSize();
    }

    public Material getIcon() {
        return this.guiMaterialization.getIcon();
    }

    public Material getMapIcon() {
        return this.guiMaterialization.getMapIcon();
    }

    public Material getHostileMapIcon() {
        return this.guiMaterialization.getHostileMapIcon();
    }

    @Override
    public ButtonMaterial getConstructionButton() {
        return this.guiMaterialization.getConstructionButton();
    }

    @Override
    public Material getAnimatedIcon() {
        return this.guiMaterialization.getAnimatedIcon();
    }

    @Override
    public Key getDescriptionKey() {
        return this.guiMaterialization.getDescriptionKey();
    }

    public ResourceValue getPrice() {
        return this.data.getPrice();
    }

    public Duration getTimeToBuild() {
        return this.data.getTimeToBuild();
    }

    public Level getRequiredLevel() {
        return this.data.getRequiredLevel();
    }

    public boolean isBuildable() {
        return this.data.isBuildable();
    }

    public Instance getMaxInstances() {
        return this.data.getMaxInstances();
    }

    public ClientEntityMaterialization getMaterialization() {
        return materialization;
    }

    public ModuleGroup getDefaultModules() {
        return this.data.getDefaultModules();
    }
}
