//        This file is part of the Yildiz-Online project, licenced under the MIT License
//        (MIT)
//
//        Copyright (c) 2016 Grégory Van den Borre
//
//        More infos available: http://yildiz.bitbucket.org
//
//        Permission is hereby granted, free of charge, to any person obtaining a copy
//        of this software and associated documentation files (the "Software"), to deal
//        in the Software without restriction, including without limitation the rights
//        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//        copies of the Software, and to permit persons to whom the Software is
//        furnished to do so, subject to the following conditions:
//
//        The above copyright notice and this permission notice shall be included in all
//        copies or substantial portions of the Software.
//
//        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//        SOFTWARE.

package be.yildiz.client.building;

import be.yildiz.client.data.ClientConstructionData;
import be.yildiz.common.translation.Key;
import be.yildiz.common.vector.Point3D;
import be.yildiz.module.graphic.ClientWorld;
import be.yildiz.module.graphic.Material;
import be.yildiz.module.graphic.gui.ButtonMaterial;
import be.yildiz.shared.building.BuildingData;
import be.yildiz.shared.data.EntityType;
import be.yildiz.shared.data.Instance;
import be.yildiz.shared.data.Level;
import be.yildiz.shared.data.TimeToBuild;
import be.yildiz.shared.resources.ResourceValue;
import be.yildiz.shared.resources.bonus.BonusResources;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Expose Building data and visual materialization.
 *
 * @author Grégory Van den Borre
 */
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ClientBuildingData implements ClientConstructionData, BuildingData {

    /**
     * Common data for the building data.
     */
    private final BuildingData data;

    /**
     * Visual 3D materialisation to be used in the game.
     */
    @Getter(value = AccessLevel.PACKAGE)
    private final ClientBuildingMaterialization materialization;

    /**
     * Visual 2D materialization to be used in the GUI.
     */
    @Getter(value = AccessLevel.PACKAGE)
    private final ClientBuildingGuiMaterialization guiMaterialization;

    /**
     * Generate the visual 3D materialization.
     *
     * @param world    World to build the materialization.
     * @param position Position the the materialization will be located.
     */
    public final void generateModel(final ClientWorld world, final Point3D position) {
        this.materialization.generate(world, position);
    }

    @Override
    public final Key getDescriptionKey() {
        return this.guiMaterialization.getDescriptionKey();
    }

    @Override
    public final Key getNameKey() {
        return this.guiMaterialization.getNameKey();
    }

    @Override
    public final Level getMaxLevel() {
        return this.data.getMaxLevel();
    }

    @Override
    public final ResourceValue getPrice(final Level level) {
        return this.data.getPrice(level);
    }

    @Override
    public final EntityType getType() {
        return this.data.getType();
    }

    @Override
    public final TimeToBuild getTimeToBuild(final Level level) {
        return this.data.getTimeToBuild(level);
    }

    @Override
    public final int getMaxPopulation(final Level level) {
        return this.data.getMaxPopulation(level);
    }

    @Override
    public final boolean hasRatioBonus() {
        return this.data.hasRatioBonus();
    }

    @Override
    public final ResourceValue getPrice() {
        return this.data.getPrice(Level.ONE);
    }

    @Override
    public final TimeToBuild getTimeToBuild() {
        return this.data.getTimeToBuild(Level.ONE);
    }

    @Override
    public final Material getAnimatedIcon() {
        return this.guiMaterialization.getIcon();
    }

    @Override
    public final ButtonMaterial getConstructionButton() {
        return this.guiMaterialization.getConstructionButton();
    }

    @Override
    public final BonusResources getStaffBonus(final int staffAllocated) {
        return this.data.getStaffBonus(staffAllocated);
    }

    @Override
    public final BonusResources getLevelBonus(final Level level) {
        return this.data.getLevelBonus(level);
    }

    @Override
    public final boolean isEmpty() {
        return this.data.isEmpty();
    }

    @Override
    public final boolean isBuilder() {
        return this.data.isBuilder();
    }

    @Override
    public final boolean isBuildable() {
        return this.data.isBuildable();
    }

    @Override
    public final Level getRequiredLevel() {
        return Level.ZERO;
    }

    @Override
    public final Instance getMaxInstances() {
        return Instance.UNIQUE;
    }
}
