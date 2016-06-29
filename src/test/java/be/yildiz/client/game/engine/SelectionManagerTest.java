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

package be.yildiz.client.game.engine;

import be.yildiz.client.entity.ClientEntity;
import be.yildiz.client.entity.ClientEntityData;
import be.yildiz.common.collections.Lists;
import be.yildiz.common.id.PlayerId;
import be.yildiz.shared.entity.BaseEntity;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.util.List;

/**
 * @author Grégory Van den Borre
 */
public class SelectionManagerTest {

    @Rule
    public final ExpectedException rule = ExpectedException.none();

    @Test
    public void test() {
        SelectionManager s = new SelectionManager(12);
        Assert.assertEquals(12, s.getMaxSelection());
        this.rule.expect(IllegalArgumentException.class);
        s = new SelectionManager(0);
        this.rule.expect(IllegalArgumentException.class);
        s = new SelectionManager(-1);
    }

    //FIXME use id
    private ClientEntity aClientEntity(long id) {
        return new ClientEntity(BaseEntity.WORLD, Mockito.mock(ClientEntityData.class), PlayerId.WORLD);
    }

    @Test
    public void testAddToSelection() {
        //FIXME test with other player!
        ClientEntity e1 = aClientEntity(1);
        SelectionManager m = new SelectionManager(3);
        m.setSelection(e1);
        Assert.assertEquals(e1, m.getSelection().get());
        ClientEntity e2 = aClientEntity(5);
        m.setSelection(e2);
        Assert.assertEquals(1, m.getSelectionList().size());
        Assert.assertEquals(e2, m.getSelection().get());
        m.addToSelection(e1);
        Assert.assertEquals(2, m.getSelectionList().size());
        Assert.assertTrue(m.getSelectionList().contains(e1));
        Assert.assertTrue(m.getSelectionList().contains(e2));
        m.addToSelection(e1);
        Assert.assertEquals(2, m.getSelectionList().size());
        Assert.assertTrue(m.getSelectionList().contains(e1));
        Assert.assertTrue(m.getSelectionList().contains(e2));
        m.removeSelection(e1);
        Assert.assertEquals(1, m.getSelectionList().size());
        Assert.assertFalse(m.getSelectionList().contains(e1));
        Assert.assertTrue(m.getSelectionList().contains(e2));
        m.addToSelection(e1);
        ClientEntity e3 = aClientEntity(3);
        ClientEntity e4 = aClientEntity(4);
        m.addToSelection(e3);
        m.addToSelection(e4);
        Assert.assertEquals(3, m.getSelectionList().size());
        Assert.assertTrue(m.getSelectionList().contains(e1));
        Assert.assertTrue(m.getSelectionList().contains(e2));
        Assert.assertTrue(m.getSelectionList().contains(e3));
        // MAX size = 3
        Assert.assertFalse(m.getSelectionList().contains(e4));
    }

    @Test
    public void testSetSelection() {
        SelectionManager m = new SelectionManager(5);
        ClientEntity e1 = aClientEntity(1);
        ClientEntity e2 = aClientEntity(2);
        ClientEntity e3 = aClientEntity(3);
        m.setSelection(e1);
        Assert.assertEquals(1, m.getSelectionList().size());
        Assert.assertTrue(m.getSelectionList().contains(e1));
        List<ClientEntity> l = Lists.newList();
        m.setSelection(l);
        Assert.assertEquals(1, m.getSelectionList().size());
        Assert.assertTrue(m.getSelectionList().contains(e1));
        l = Lists.newList();
        l.add(e2);
        l.add(e3);
        m.setSelection(l);
        Assert.assertEquals(2, m.getSelectionList().size());
        Assert.assertFalse(m.getSelectionList().contains(e1));
        Assert.assertTrue(m.getSelectionList().contains(e2));
        Assert.assertTrue(m.getSelectionList().contains(e3));
    }

    @Test
    public void testIsSelected() {
        SelectionManager m = new SelectionManager(5);
        ClientEntity e1 = aClientEntity(1);
        ClientEntity e2 = aClientEntity(2);
        m.setSelection(e1);
        Assert.assertEquals(1, m.getSelectionList().size());
        Assert.assertTrue(m.getSelectionList().contains(e1));
        Assert.assertFalse(m.isSelected(e2));
        Assert.assertTrue(m.isSelected(e1));
    }

    @Test
    public void testEntityDestroyed() {
        SelectionManager m = new SelectionManager(5);
        ClientEntity e1 = aClientEntity(1);
        m.setSelection(e1);
        Assert.assertEquals(1, m.getSelectionList().size());
        Assert.assertTrue(m.getSelectionList().contains(e1));
        m.entityDestroyed(e1);
        Assert.assertFalse(m.getSelectionList().contains(e1));
    }

    @Test
    public void testGetSelection() {
        SelectionManager m = new SelectionManager(5);
        ClientEntity e1 = aClientEntity(1);
        ClientEntity e2 = aClientEntity(2);
        m.addToSelection(e1);
        m.addToSelection(e2);
        Assert.assertEquals(e1, m.getSelection().get());
        Assert.assertTrue(m.getSelection().isPresent());
        m.removeSelection(e1);
        m.removeSelection(e2);
        Assert.assertFalse(m.getSelection().isPresent());
    }

    @Ignore
    @Test
    public void testSetMultiSelection() {
        SelectionManager m = new SelectionManager(10);
        ClientEntity e1 = aClientEntity(1);
        ClientEntity e2 = aClientEntity(2);
        m.setSelection(e1);
        m.setSelection(e2);
        Assert.assertEquals(1, m.getSelectionList().size());
        Assert.assertEquals(e2, m.getSelection().get());
    }
}
