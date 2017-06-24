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
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author Grégory Van den Borre
 */
@RunWith(Enclosed.class)
public class LightDefinitionTest {

    public static class Constructor {

        @Test
        public void happyFlow() {
            LightDefinition ld = new LightDefinition();
            Assert.assertEquals("", ld.getName());
            Assert.assertEquals("point", ld.getType());
            Assert.assertEquals(Point3D.ZERO, ld.getPosition());
            Assert.assertEquals(Material.EMPTY_NAME, ld.getBurstMaterial());
            Assert.assertEquals(Material.EMPTY_NAME, ld.getHaloMaterial());
            Assert.assertEquals(Material.EMPTY_NAME, ld.getLightMaterial());
        }
    }

    public static class SetName {

        @Test
        public void happyFlow() {
            LightDefinition ld = new LightDefinition();
            ld.setName("aName");
            Assert.assertEquals("aName", ld.getName());
        }

        @Test(expected = IllegalArgumentException.class)
        public void withNull() {
            LightDefinition ld = new LightDefinition();
            ld.setName(null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void empty() {
            LightDefinition ld = new LightDefinition();
            ld.setName("");
        }
    }

    public static class SetType {

        @Test
        public void happyFlow() {
            LightDefinition ld = new LightDefinition();
            ld.setType("point");
            Assert.assertEquals("point", ld.getType());
        }

        @Test(expected = IllegalArgumentException.class)
        public void withNull() {
            LightDefinition ld = new LightDefinition();
            ld.setType(null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void withInvalidType() {
            LightDefinition ld = new LightDefinition();
            ld.setType("any");
        }
    }

    public static class SetPosition {

        @Test
        public void happyFlow() {
            LightDefinition ld = new LightDefinition();
            ld.setX("5");
            ld.setY("3");
            ld.setZ("7");
            Assert.assertEquals(Point3D.valueOf(5,3,7), ld.getPosition());
        }

        @Test(expected = IllegalArgumentException.class)
        public void withNullX() {
            LightDefinition ld = new LightDefinition();
            ld.setX(null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void withNullY() {
            LightDefinition ld = new LightDefinition();
            ld.setY(null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void withNullZ() {
            LightDefinition ld = new LightDefinition();
            ld.setZ(null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void withInvalidX() {
            LightDefinition ld = new LightDefinition();
            ld.setX("a");
        }

        @Test(expected = IllegalArgumentException.class)
        public void withInvalidY() {
            LightDefinition ld = new LightDefinition();
            ld.setY("a");
        }

        @Test(expected = IllegalArgumentException.class)
        public void withInvalidZ() {
            LightDefinition ld = new LightDefinition();
            ld.setZ("a");
        }
    }

    public static class SetLightMaterial {

        @Test
        public void happyFlow() {
            LightDefinition ld = new LightDefinition();
            ld.setLightMaterial("aLight");
            Assert.assertEquals("aLight", ld.getLightMaterial());
        }

        @Test(expected = IllegalArgumentException.class)
        public void withNull() {
            LightDefinition ld = new LightDefinition();
            ld.setLightMaterial(null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void empty() {
            LightDefinition ld = new LightDefinition();
            ld.setLightMaterial("");
        }
    }

    public static class SetBurstMaterial {

        @Test
        public void happyFlow() {
            LightDefinition ld = new LightDefinition();
            ld.setBurstMaterial("burst");
            Assert.assertEquals("burst", ld.getBurstMaterial());
        }

        @Test(expected = IllegalArgumentException.class)
        public void withNull() {
            LightDefinition ld = new LightDefinition();
            ld.setBurstMaterial(null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void empty() {
            LightDefinition ld = new LightDefinition();
            ld.setBurstMaterial("");
        }
    }

    public static class SetHaloMaterial {

        @Test
        public void happyFlow() {
            LightDefinition ld = new LightDefinition();
            ld.setHaloMaterial("halo");
            Assert.assertEquals("halo", ld.getHaloMaterial());
        }

        @Test(expected = IllegalArgumentException.class)
        public void withNull() {
            LightDefinition ld = new LightDefinition();
            ld.setHaloMaterial(null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void empty() {
            LightDefinition ld = new LightDefinition();
            ld.setHaloMaterial("");
        }
    }
}