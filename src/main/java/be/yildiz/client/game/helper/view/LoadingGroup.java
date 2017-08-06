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

package be.yildiz.client.game.helper.view;

import be.yildiz.client.game.engine.GameEngine;
import be.yildiz.common.Coordinates;
import be.yildiz.common.Position;
import be.yildiz.common.Size;
import be.yildiz.common.collections.Lists;
import be.yildiz.common.resource.Resource;
import be.yildiz.common.translation.Key;
import be.yildiz.module.graphic.Font;
import be.yildiz.module.graphic.Material;
import be.yildiz.module.graphic.gui.*;

import java.util.List;

/**
 * A loading group is a class to build a loading view and load resources.
 *
 * @author Grégory Van den Borre
 */
public final class LoadingGroup {

    /**
     * List of resources to load.
     */
    private final List<Resource> toLoad;

    /**
     * View to display the loading screen.
     */
    private final View window;

    /**
     * Text line.
     */
    private final TextLine text;

    /**
     * Game engine.
     */
    private final GameEngine engine;

    /**
     * Full constructor.
     *
     * @param engine Game engine.
     * @param font   Font to use.
     * @param background Background image to display.
     * @param message Message to display.
     */
    public LoadingGroup(final GameEngine engine, final Font font, final Material background, final String message) {
        super();
        this.engine = engine;
        this.toLoad = Lists.newList();
        GuiContainer container = engine.getGuiManager()
                .container()
                .withName("loading")
                .withBackground(background)
                .fullScreen()
                .build();
        this.text = engine.getGuiManager().
                textLine()
                .withName("loadText")
                .withCoordinates(new Coordinates(new Size(container.getWidth(), 20), new Position(0, engine.getGuiManager().getScreenSize().height >> 1)))
                .withFont(font)
                .build(container)
                .setText(Key.get(message));
        this.text.setLeftFromParent(Element.PositionRelativeLeft.CENTER);
        this.window = new SimpleView(container, new Zorder(10), null);
    }

    /**
     * Display the loading screen.
     */
    public void load() {
        this.window.show();
        this.engine.render();
        for (final Resource r : this.toLoad) {
            this.engine.render();
            r.load();
        }
        this.toLoad.clear();
    }

    /**
     * Add a resource to load.
     *
     * @param r Resource to load.
     */
    public void addResource(final Resource r) {
        this.toLoad.add(r);
    }

    /**
     * Set background material.
     *
     * @param material Background material.
     */
    public void setBackground(final Material material) {
        this.window.setBackground(material);
    }

    /**
     * Set the text font.
     *
     * @param font Font to set.
     */
    public void setFont(final Font font) {
        this.text.setFont(font);
    }

    /**
     * Delete the window.
     */
    public void delete() {
        this.window.delete();
    }
}
