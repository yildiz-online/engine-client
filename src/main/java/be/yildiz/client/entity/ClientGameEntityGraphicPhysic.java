/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2018 Grégory Van den Borre
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

package be.yildiz.client.entity;

import be.yildiz.module.graphic.GraphicObject;
import be.yildiz.module.physics.BaseBody;
import be.yildizgames.common.gameobject.Movable;
import be.yildizgames.common.geometry.Point3D;
import be.yildizgames.common.model.EntityId;

/**
 * @author Grégory Van den Borre
 */
public class ClientGameEntityGraphicPhysic extends BaseClientGameEntity {

    private final BaseBody physicBody;

    private final GraphicObject graphicObject;

    /**
     * Means the physic body is leading the object, in case of dynamic body.
     */
    private final boolean physicMaster;

    private ClientGameEntityGraphicPhysic(BaseBody physicBody, GraphicObject graphicObject, boolean physicMaster) {
        super(graphicObject);
        this.physicBody = physicBody;
        this.graphicObject = graphicObject;
        this.physicMaster = physicMaster;
        if(physicMaster) {
            this.physicBody.addChild(graphicObject);
        }
        //FIXME will not work when implementation of dummy physic body -> todo this.graphic.addchild(physic);
    }

    public static ClientGameEntityGraphicPhysic withDynamicMaster(BaseBody physicBody, GraphicObject graphicObject) {
        return new ClientGameEntityGraphicPhysic(physicBody, graphicObject, true);
    }


    public static ClientGameEntityGraphicPhysic withGraphicMaster(BaseBody physicBody, GraphicObject graphicObject) {
        return new ClientGameEntityGraphicPhysic(physicBody, graphicObject, false);
    }

    @Override
    public ClientGameEntityGraphicPhysic rotate(float yaw, float pitch) {
        if(!physicMaster) {
            this.graphicObject.rotate(yaw, pitch);
        }
        return this;
    }

    @Override
    public ClientGameEntityGraphicPhysic lookAt(Point3D target) {
        if(!physicMaster) {
            this.graphicObject.lookAt(target);
        }
        return this;
    }


    @Override
    public EntityId getId() {
        return this.physicBody.getId();
    }

    @Override
    public void rotate(float x, float y, float z, float w) {
        if(!physicMaster) {
            this.graphicObject.rotate(x, y, z, w);
        }
    }

    @Override
    public void scale(float x, float y, float z) {
        this.graphicObject.scale(x, y, z);
        this.physicBody.scale(x, y, z);
    }

    @Override
    public void delete() {
        this.graphicObject.delete();
        this.physicBody.delete();
    }

    @Override
    public void sleep(boolean b) {
        this.physicBody.sleep(b);
    }

    @Override
    public void attachTo(Movable other) {
        if(!physicMaster) {
            this.graphicObject.attachTo(other);
        }
        //a dynamic body cannot have parent.
    }

    @Override
    public void addChild(Movable other) {
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
    public void attachToOptional(Movable other) {
        if(!physicMaster) {
            this.graphicObject.attachToOptional(other);
        }
        //a dynamic body cannot have parent.
    }

    @Override
    public void detachFromParent() {
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
    public void setDirection(Point3D newDirection) {
        if(!physicMaster) {
            this.graphicObject.setDirection(newDirection);
        } else {
            this.physicBody.setDirection(newDirection);
        }
    }

    @Override
    public void setPosition(float posX, float posY, float posZ) {
        if(!physicMaster) {
            this.graphicObject.setPosition(posX, posY, posZ);
        } else {
            this.physicBody.setPosition(posX, posY, posZ);
        }
    }

    @Override
    public void setDirection(float dirX, float dirY, float dirZ) {
        if(!physicMaster) {
            this.graphicObject.setDirection(dirX, dirY, dirZ);
        } else {
            this.physicBody.setDirection(dirX, dirY, dirZ);
        }
    }

    @Override
    public void addOptionalChild(Movable child) {
        if(!physicMaster) {
            this.graphicObject.addOptionalChild(child);
        } else {
            this.physicBody.addOptionalChild(child);
        }
    }

    @Override
    public Movable getInternal() {
        if(!this.physicMaster) {
            return this.graphicObject.getInternal();
        }
        return this.physicBody;
    }
}
