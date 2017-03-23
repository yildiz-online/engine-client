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
import be.yildiz.common.collections.Lists;
import be.yildiz.common.collections.Sets;
import be.yildiz.common.util.Checker;
import be.yildiz.shared.entity.DestructionListener;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Manage all unit selection logic.
 *
 * @author Grégory Van den Borre
 */
public final class SelectionManager implements DestructionListener<ClientEntity> {

    /**
     * List of all selected units.
     */
    private final Set<ClientEntity> selectionList = Sets.newInsertionOrderedSet();

    /**
     * Flag to check if the previous selection should be cleared when a new one is made.
     */

    /**
     * List of all view to update when selection status change.
     */
    private final List<SelectionListener> listeners = Lists.newList();

    /**
     * Maximum number of selectable entities.
     */
    private final int maxSelection;

    /**
     * Constructor.
     *
     * @param maxSelection Set the maximum number of selectable entities.
     */
    public SelectionManager(final int maxSelection) {
        super();
        Checker.exceptionNotGreaterThanZero(maxSelection);
        this.maxSelection = maxSelection;
    }

    /**
     * Set a entity to as selection, only if it is selectable.
     *
     * @param entity Entity selected.
     */
    public void setSelection(final ClientEntity entity) {
        this.addSelection(entity, true, false);
    }

    /**
     * Add an entity to the current selection.
     *
     * @param entity Entity to add.
     */
    public void addToSelection(final ClientEntity entity) {
        this.addSelection(entity, true, true);
    }

    /**
     * Remove a unit to the current selection.
     *
     * @param unit Unit no longer selected.
     */
    public void removeSelection(final ClientEntity unit) {
        this.addSelection(unit, false, true);
    }

    /**
     * Add or remove a unit to the current selection.
     *
     * @param unit     Unit selected.
     * @param selected <code>true</code> if the unit must be selected, <code>false</code> if it must be unselected.
     */
    private void addSelection(final ClientEntity unit, final boolean selected, final boolean multi) {
        if (!unit.isSelectable()) {
            return;
        }
        if (!multi && selected) {
            this.selectionList.clear();
        }
        if (selected && this.selectionList.size() < this.maxSelection) {
            this.selectionList.add(unit);
        } else if (!selected) {
            this.selectionList.remove(unit);
        }
        this.updateView();
    }

    /**
     * Update all registered listeners.
     */
    private void updateView() {
        switch (this.selectionList.size()) {
            case 0:
                this.listeners.forEach(SelectionListener::clearSelection);
                break;
            case 1:
                ClientEntity e = this.selectionList.iterator().next();
                this.listeners.forEach(v -> v.updateSelection(e));
                break;
            default:
                Set<ClientEntity> unmodifiable = Collections.unmodifiableSet(this.selectionList);
                this.listeners.forEach(v -> v.updateSelection(unmodifiable));
                break;
        }
    }

    /**
     * Add a new selection listener to be notified then the selection state change.
     *
     * @param listener Listener to add.
     */
    public void addSelectionListener(final SelectionListener listener) {
        this.listeners.add(listener);
    }

    /**
     * @return The first selected unit.
     */
    public Optional<ClientEntity> getSelection() {
        return this.selectionList.stream().findFirst();
    }

    /**
     * Set the current selection.
     *
     * @param list Units selected, if the list is empty, selection is not replaced, if the list is bigger than the max selection, it is cropped.
     */
    public void setSelection(final List<ClientEntity> list) {
        if (list.size() == 1) {
            this.addToSelection(list.get(0));
        } else if (!list.isEmpty()) {
            this.selectionList.clear();
            List<ClientEntity> result = list.stream()
                    .filter(ClientEntity::isSelectable)
                    .limit(this.maxSelection)
                    .collect(Collectors.toList());
            this.selectionList.addAll(result);
            this.updateView();
        }
    }

    /**
     * @return all selected entities.
     */
    public List<ClientEntity> getSelectionList() {
        return Lists.newList(this.selectionList);
    }

    /**
     * Check if an entity is selected.
     *
     * @param entity Entity to check.
     * @return <code>true</code> if selected.
     */
    public boolean isSelected(final ClientEntity entity) {
        return this.selectionList.contains(entity);
    }

    @Override
    public void entityDestroyed(final ClientEntity e) {
        this.addSelection(e, false, true);
    }

    public int getMaxSelection() {
        return maxSelection;
    }
}
