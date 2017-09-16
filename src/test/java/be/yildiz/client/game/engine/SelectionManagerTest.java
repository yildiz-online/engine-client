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

package be.yildiz.client.game.engine;

import be.yildiz.client.entity.ClientEntity;
import be.yildiz.client.entity.ClientEntityData;
import be.yildiz.common.collections.Lists;
import be.yildiz.common.id.PlayerId;
import be.yildiz.helper.Helper;
import be.yildiz.shared.entity.Entity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Grégory Van den Borre
 */
class SelectionManagerTest {

    @Test
    void test() {
        SelectionManager s = new SelectionManager(12);
        assertEquals(12, s.getMaxSelection());
        assertThrows(AssertionError.class, () -> new SelectionManager(0));
        assertThrows(AssertionError.class, () -> new SelectionManager(-1));
    }

    private ClientEntity aClientEntity(long id) {
        Entity e = Helper.anEntity(id, 5);
        return new ClientEntity(e, Mockito.mock(ClientEntityData.class), PlayerId.valueOf(5));
    }

    @Test
    void testAddToSelection() {
        //FIXME test with other player!
        ClientEntity e1 = aClientEntity(1);
        SelectionManager m = new SelectionManager(3);
        m.setSelection(e1);
        assertEquals(e1, m.getSelection().get());
        ClientEntity e2 = aClientEntity(5);
        m.setSelection(e2);
        assertEquals(1, m.getSelectionList().size());
        assertEquals(e2, m.getSelection().get());
        m.addToSelection(e1);
        assertEquals(2, m.getSelectionList().size());
        assertTrue(m.getSelectionList().contains(e1));
        assertTrue(m.getSelectionList().contains(e2));
        m.addToSelection(e1);
        assertEquals(2, m.getSelectionList().size());
        assertTrue(m.getSelectionList().contains(e1));
        assertTrue(m.getSelectionList().contains(e2));
        m.removeSelection(e1);
        assertEquals(1, m.getSelectionList().size());
        assertFalse(m.getSelectionList().contains(e1));
        assertTrue(m.getSelectionList().contains(e2));
        m.addToSelection(e1);
        ClientEntity e3 = aClientEntity(3);
        ClientEntity e4 = aClientEntity(4);
        m.addToSelection(e3);
        m.addToSelection(e4);
        assertEquals(3, m.getSelectionList().size());
        assertTrue(m.getSelectionList().contains(e1));
        assertTrue(m.getSelectionList().contains(e2));
        assertTrue(m.getSelectionList().contains(e3));
        // MAX size = 3
        assertFalse(m.getSelectionList().contains(e4));
    }

    @Test
    void testSetSelection() {
        SelectionManager m = new SelectionManager(5);
        ClientEntity e1 = aClientEntity(1);
        ClientEntity e2 = aClientEntity(2);
        ClientEntity e3 = aClientEntity(3);
        m.setSelection(e1);
        assertEquals(1, m.getSelectionList().size());
        assertTrue(m.getSelectionList().contains(e1));
        List<ClientEntity> l = Lists.newList();
        m.setSelection(l);
        assertEquals(1, m.getSelectionList().size());
        assertTrue(m.getSelectionList().contains(e1));
        l = Lists.newList();
        l.add(e2);
        l.add(e3);
        m.setSelection(l);
        assertEquals(2, m.getSelectionList().size());
        assertFalse(m.getSelectionList().contains(e1));
        assertTrue(m.getSelectionList().contains(e2));
        assertTrue(m.getSelectionList().contains(e3));
    }

    @Test
    void testIsSelected() {
        SelectionManager m = new SelectionManager(5);
        ClientEntity e1 = aClientEntity(1);
        ClientEntity e2 = aClientEntity(2);
        m.setSelection(e1);
        assertEquals(1, m.getSelectionList().size());
        assertTrue(m.getSelectionList().contains(e1));
        assertFalse(m.isSelected(e2));
        assertTrue(m.isSelected(e1));
    }

    @Test
    void testEntityDestroyed() {
        SelectionManager m = new SelectionManager(5);
        ClientEntity e1 = aClientEntity(1);
        m.setSelection(e1);
        assertEquals(1, m.getSelectionList().size());
        assertTrue(m.getSelectionList().contains(e1));
        m.entityDestroyed(e1);
        assertFalse(m.getSelectionList().contains(e1));
    }

    @Test
    void testGetSelection() {
        SelectionManager m = new SelectionManager(5);
        ClientEntity e1 = aClientEntity(1);
        ClientEntity e2 = aClientEntity(2);
        m.addToSelection(e1);
        m.addToSelection(e2);
        assertEquals(e1, m.getSelection().get());
        assertTrue(m.getSelection().isPresent());
        m.removeSelection(e1);
        m.removeSelection(e2);
        assertFalse(m.getSelection().isPresent());
    }

    @Test
    void testSetMultiSelection() {
        SelectionManager m = new SelectionManager(10);
        ClientEntity e1 = aClientEntity(1);
        ClientEntity e2 = aClientEntity(2);
        m.setSelection(e1);
        m.setSelection(e2);
        assertEquals(1, m.getSelectionList().size());
        assertEquals(e2, m.getSelection().get());
    }
}
