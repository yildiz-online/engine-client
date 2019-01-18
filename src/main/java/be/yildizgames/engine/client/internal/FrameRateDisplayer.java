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

package be.yildizgames.engine.client.internal;

import be.yildizgames.common.client.debug.DebugListener;
import be.yildizgames.common.frame.StartFrameListener;
import be.yildizgames.module.graphic.FpsProvider;

/**
 * Provide the application current framerate.
 *
 * @author Grégory Van den Borre
 */
public final class FrameRateDisplayer extends StartFrameListener {

    /**
     * Registered DebugListener.
     */
    private final DebugListener debugListener;

    /**
     * Object providing the frame rate.
     */
    private final FpsProvider provider;

    /**
     * Create a new instance of frame rate displayer.
     * @param debugListener To display the frame rate value. [Requires not null].
     * @param provider Provide the frame rate value. [Requires not null].
     */
    public FrameRateDisplayer(DebugListener debugListener, FpsProvider provider) {
        super();
        this.debugListener = debugListener;
        this.provider = provider;
    }

    /**
     * Update the FPS and notify the debug listener.
     * @return true.
     */
    @Override
    public boolean frameStarted() {
        this.debugListener.updateFps(this.provider.getFPS());
        return true;
    }

}
