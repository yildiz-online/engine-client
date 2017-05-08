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

import be.yildiz.common.gameobject.Movable;
import be.yildiz.common.id.ActionId;
import be.yildiz.common.id.EntityId;
import be.yildiz.common.id.PlayerId;
import be.yildiz.common.translation.Key;
import be.yildiz.common.vector.Point3D;
import be.yildiz.module.graphic.Material;
import be.yildiz.shared.data.EntityType;
import be.yildiz.shared.data.State;
import be.yildiz.shared.data.ViewDistance;
import be.yildiz.shared.entity.BaseEntity;
import be.yildiz.shared.entity.Entity;
import be.yildiz.shared.entity.action.AbstractAttack;
import be.yildiz.shared.entity.action.Action;
import be.yildiz.shared.entity.fields.AttackHitResult;
import be.yildiz.shared.entity.fields.Target;
import be.yildiz.shared.entity.module.Module;
import be.yildiz.shared.entity.module.ModuleGroup;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Grégory Van den Borre
 */
public class ClientEntity implements Entity {

    public static final ClientEntity WORLD = new ClientEntity(BaseEntity.WORLD, new ClientEntityData(null, null, null), PlayerId.WORLD);

    private final Entity entity;

    private final ClientEntityData data;

    private final PlayerId player;

    public ClientEntity(Entity entity, ClientEntityData data, PlayerId player) {
        assert entity != null;
        assert data != null;
        assert player != null;
        this.entity = entity;
        this.data = data;
        this.player = player;
    }

    /**
     * @return The translation key of this entity name.
     */
    //@Ensures return value != null
    public Key getNameKey() {
        return this.data.getNameKey();
    }

    public Material getMapIcon() {
        return data.getMapIcon();
    }

    public Material getHostileMapIcon() {
        return this.data.getHostileMapIcon();
    }

    public float getSize() {
        return this.data.getSize();
    }

    public Material getIcon() {
        return this.data.getIcon();
    }

    @Override
    public Action attack(Target target) {
        return this.entity.attack(target);
    }

    @Override
    public void startAction(final ActionId id, final Target target, final Point3D dest) {
        this.entity.startAction(id, target, dest);
    }

    @Override
    public PlayerId getOwner() {
        return this.entity.getOwner();
    }

    @Override
    public void setOwner(final PlayerId owner) {
        this.entity.setOwner(owner);
    }

    @Override
    public EntityId getId() {
        return this.entity.getId();
    }

    @Override
    public int getHitPoints() {
        return this.entity.getHitPoints();
    }

    @Override
    public void setHitPoints(int hitPoint) {
        this.entity.setHitPoints(hitPoint);
    }

    @Override
    public Point3D getPosition() {
        return this.entity.getPosition();
    }

    @Override
    public void setPosition(final Point3D position) {
        this.entity.setPosition(position);
    }

    @Override
    public EntityType getType() {
        return this.entity.getType();
    }

    /**
     * @return true if the entity can be selected.
     */
    public boolean isSelectable() {
        return isMine();
    }

    @Override
    public void addState(final State state) {
        this.entity.addState(state);
    }

    public boolean isMine() {
        return this.getOwner().equals(this.player);
    }

    @Override
    public Action getAction(final ActionId actionId) {
        return this.entity.getAction(actionId);
    }

    @Override
    public boolean see(final Entity e) {
        return this.entity.see(e);
    }

    @Override
    public boolean isSeeing(final Entity unseen) {
        return this.entity.isSeeing(unseen);
    }

    @Override
    public Point3D getDirection() {
        return this.entity.getDirection();
    }

    @Override
    public void setDirection(final Point3D direction) {
        this.entity.setDirection(direction);
    }

    @Override
    public boolean hasState(State state) {
        return this.entity.hasState(state);
    }

    @Override
    public void removeState(State state) {
        this.entity.removeState(state);
    }

    @Override
    public void lookAt(Point3D destination) {
        this.entity.lookAt(destination);
    }

    @Override
    public void delete() {
        this.entity.delete();
    }

    @Override
    public boolean isDeleted() {
        return this.entity.isDeleted();
    }

    @Override
    public boolean isZeroHp() {
        return this.entity.isZeroHp();
    }

    @Override
    public void hit(AttackHitResult hit) {
        this.entity.hit(hit);
    }

    @Override
    public Action move(Point3D dest) {
        return this.entity.move(dest);
    }

    @Override
    public void startAction(Action a) {
        this.entity.startAction(a);
    }

    @Override
    public List<Action> getActionRunning() {
        return this.entity.getActionRunning();
    }

    @Override
    public List<Action> getActionDone() {
        return this.entity.getActionDone();
    }

    @Override
    public int getMaxHitPoints() {
        return this.entity.getMaxHitPoints();
    }

    @Override
    public int getEnergyPoints() {
        return this.entity.getEnergyPoints();
    }

    @Override
    public void setEnergyPoints(int energy) {
        this.entity.setEnergyPoints(energy);
    }

    @Override
    public int getMaxEnergyPoints() {
        return this.entity.getMaxEnergyPoints();
    }

    @Override
    public void stopAttack() {
        this.entity.stopAttack();
    }

    @Override
    public List<Action> getActions() {
        return this.entity.getActions();
    }

    @Override
    public ViewDistance getLineOfSight() {
        return this.entity.getLineOfSight();
    }

    @Override
    public float getHitPointsRatio() {
        return this.entity.getHitPointsRatio();
    }

    @Override
    public float getEnergyPointsRatio() {
        return this.entity.getHitPointsRatio();
    }

    @Override
    public boolean isAttacking() {
        return this.entity.isAttacking();
    }

    // @Override
    // public Move getMoveAction() {
    // return this.entity.getMoveAction();
    // }

    @Override
    public AbstractAttack getAttackAction() {
        return this.entity.getAttackAction();
    }

    @Override
    public Action getProtectAction() {
        return this.entity.getProtectAction();
    }

    @Override
    public Action getGenerateEnergyAction() {
        return this.entity.getGenerateEnergyAction();
    }

    @Override
    public ModuleGroup getModules() {
        return this.entity.getModules();
    }

    @Override
    public void doActions(long time) {
        this.entity.doActions(time);

    }

    @Override
    public String toString() {
        return this.entity.toString();
    }

    @Override
    public Set<PlayerId> getSeenBy() {
        return this.entity.getSeenBy();
    }

    @Override
    public boolean hasSameOwnerAs(Entity e) {
        return this.entity.hasSameOwnerAs(e);
    }

    @Override
    public String getName() {
        return this.entity.getName();
    }

    @Override
    public void setName(String name) {
        this.entity.setName(name);
    }

    // FIXME return only action data instead, avoid exposing action outside of
    // the entity
    @Override
    public Action getPreparedAction() {
        return this.entity.getPreparedAction();
    }

    @Override
    public Module getAdditional1() {
        return this.entity.getAdditional1();
    }

    @Override
    public Module getAdditional2() {
        return this.entity.getAdditional2();
    }

    @Override
    public Module getAdditional3() {
        return this.entity.getAdditional3();
    }

    @Override
    public void prepareAction(Optional<ActionId> action) {
        this.entity.prepareAction(action);
    }

    @Override
    public void setTarget(Target t) {
        this.entity.setTarget(t);
    }

    @Override
    public void setDestination(Point3D p) {
        this.entity.setDestination(p);
    }

    @Override
    public void startPreparedAction() {
        this.entity.startPreparedAction();
    }
}
