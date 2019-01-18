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

package be.yildizgames.engine.client.world.internal;

import be.yildizgames.common.gameobject.Movable;
import be.yildizgames.common.geometry.Point3D;
import be.yildizgames.common.model.EntityId;
import be.yildizgames.engine.client.world.ClientGameObject;
import be.yildizgames.module.graphic.GraphicObject;
import be.yildizgames.module.physics.BaseBody;

/**
 * @author Grégory Van den Borre
 */
class ClientGameObjectGraphicPhysic extends BaseClientGameObject {

    private final BaseBody physicBody;

    private final GraphicObject graphicObject;

    /**
     * Means the physic body is leading the object, in case of dynamic body.
     */
    private final boolean physicMaster;

    private ClientGameObjectGraphicPhysic(BaseBody physicBody, GraphicObject graphicObject, boolean physicMaster) {
        super(graphicObject);
        this.physicBody = physicBody;
        this.graphicObject = graphicObject;
        this.physicMaster = physicMaster;
        if(physicMaster) {
            this.physicBody.addChild(graphicObject);
        } else {
            this.graphicObject.addChild(physicBody);
        }
    }

    static ClientGameObject withDynamicMaster(BaseBody physicBody, GraphicObject graphicObject) {
        return new ClientGameObjectGraphicPhysic(physicBody, graphicObject, true);
    }


    static ClientGameObject withGraphicMaster(BaseBody physicBody, GraphicObject graphicObject) {
        return new ClientGameObjectGraphicPhysic(physicBody, graphicObject, false);
    }

    @Override
    public final ClientGameObject rotate(float yaw, float pitch) {
        if(!physicMaster) {
            this.graphicObject.rotate(yaw, pitch);
        }
        return this;
    }

    @Override
    public final ClientGameObject lookAt(Point3D target) {
        if(!physicMaster) {
            this.graphicObject.lookAt(target);
        }
        return this;
    }


    @Override
    public final EntityId getId() {
        return this.physicBody.getId();
    }

    @Override
    public final void rotate(float x, float y, float z, float w) {
        if(!physicMaster) {
            this.graphicObject.rotate(x, y, z, w);
        }
    }

    @Override
    public final void scale(float x, float y, float z) {
        this.graphicObject.scale(x, y, z);
        this.physicBody.scale(x, y, z);
    }

    @Override
    public final void delete() {
        this.graphicObject.delete();
        this.physicBody.delete();
    }

    @Override
    public final void sleep(boolean b) {
        this.physicBody.sleep(b);
    }

    @Override
    public final void attachTo(Movable other) {
        if(!physicMaster) {
            this.graphicObject.attachTo(other);
        }
        //a dynamic body cannot have parent.
    }

    @Override
    public final void addChild(Movable other) {
        if(!physicMaster) {
            this.graphicObject.addChild(other);
        } else {
            this.physicBody.addChild(other);
        }
    }

    @Override
    public void removeChild(Movable child) {
        if(!physicMaster) {
            this.graphicObject.removeChild(child);
        } else {
            this.physicBody.removeChild(child);
        }
    }

    @Override
    public final void attachToOptional(Movable other) {
        if(!physicMaster) {
            this.graphicObject.attachToOptional(other);
        }
        //a dynamic body cannot have parent.
    }

    @Override
    public final void detachFromParent() {
        if(!physicMaster) {
            this.graphicObject.detachFromParent();
        }
        //a dynamic body cannot have parent.
    }

    @Override
    public void setPosition(Point3D newPosition) {
        if(!physicMaster) {
            this.graphicObject.setPosition(newPosition);
        } else {
            this.physicBody.setPosition(newPosition);
        }
    }

    @Override
    public final void setDirection(Point3D newDirection) {
        if(!physicMaster) {
            this.graphicObject.setDirection(newDirection);
        } else {
            this.physicBody.setDirection(newDirection);
        }
    }

    @Override
    public final void setPosition(float posX, float posY, float posZ) {
        if(!physicMaster) {
            this.graphicObject.setPosition(posX, posY, posZ);
        } else {
            this.physicBody.setPosition(posX, posY, posZ);
        }
    }

    @Override
    public final void setDirection(float dirX, float dirY, float dirZ) {
        if(!physicMaster) {
            this.graphicObject.setDirection(dirX, dirY, dirZ);
        } else {
            this.physicBody.setDirection(dirX, dirY, dirZ);
        }
    }

    @Override
    public final void addOptionalChild(Movable child) {
        if(!physicMaster) {
            this.graphicObject.addOptionalChild(child);
        } else {
            this.physicBody.addOptionalChild(child);
        }
    }

    @Override
    public final Movable getInternal() {
        if(!this.physicMaster) {
            return this.graphicObject.getInternal();
        }
        return this.physicBody;
    }
}
