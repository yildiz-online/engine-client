/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2018 Grégory Van den Borre
 *
 * More infos available: https://www.yildiz-games.be
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
package be.yildizgames.client.ui;

import be.yildizgames.client.game.engine.GameEngine;
import be.yildizgames.module.graphic.gui.GuiFactory;
import be.yildizgames.module.graphic.gui.Window;
import be.yildizgames.module.graphic.gui.Zorder;
import be.yildizgames.module.graphic.gui.button.Button;
import be.yildizgames.module.graphic.gui.textline.TextLine;

/**
 * Menu used for close confirmation.
 *
 * @author Van den Borre Grégory
 */
public final class CloseMenu extends Window {

    /**
     * Notification text.
     */
    private final TextLine title;

    /**
     * GuiButton to confirm to close the game.
     */
    private Button ok;
    /**
     * GuiButton to close this window.
     */
    private Button cancel;
    /**
     * Flag to check if connection lost message box is open.
     */
    private boolean connectionLostOpen;

    private final CloseMenuLocale locale;

    /**
     * Full constructor.
     */
    public CloseMenu(CloseMenuTemplate template, GuiFactory factory, GameEngine engine) {
        super(
                factory.container()
                        .withName("close_menu")
                        .withBackground(template.getCloseMenuBackground())
                        .withSize(template.getCloseMenuSize())
                        .build(),
                factory,
                template.getCloseMenuFont(),
                new Zorder(620),
                engine.getEventDispatcher(),
                Parameter.NO_TITLE_BAR,
                Parameter.NOT_MOVABLE);
        this.setPosition(template.getPosition());
        this.locale = template.getLocale();
        this.title = factory.textLine(this.getContainer(), template.getTitleTemplate(), this.locale.getTitle());
        this.ok = factory.button(this.getContainer(), template.getButtonTemplate(), engine::stopGameLoop);
        this.cancel = factory.button(this.getContainer(), template.getButtonTemplate(), this::hide);
        this.hide();
    }

    /**
     * Show connection lost message box.
     */
    public void showConnectionLost() {
        this.connectionLostOpen = true;
        this.show();
        this.cancel.hide();
        this.ok.setPosition(100, 130);
        this.title.setText(this.locale.getConnectionLost());
    }

    /**
     * Show quit game message box.
     */
    public void showQuitGame() {
        if (!this.connectionLostOpen) {
            this.setActive(true);
            this.show();
        }
    }

    /**
     * Empty implementation.
     *
     * @param show Unused.
     */
    @Override
    protected void setVisibleImpl(final boolean show) {
        //Does nothing
    }

    /**
     * Empty implementation.
     *
     * @param active Unused.
     */
    @Override
    protected void setActiveImpl(final boolean active) {
        //Does nothing
    }
}
