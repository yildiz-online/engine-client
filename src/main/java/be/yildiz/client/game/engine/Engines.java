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

import be.yildiz.module.graphic.GraphicEngine;
import be.yildiz.module.network.client.AbstractNetworkEngineClient;
import be.yildiz.module.sound.SoundEngine;
import be.yildiz.module.window.WindowEngine;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Provide the engines implementation to use.
 *
 * @author Grégory Van den Borre
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Engines {

    /**
     * Engine handling the graphic display.
     */
    private final GraphicEngine graphic;

    /**
     * Engine handling audio playing.
     */
    private final SoundEngine audio;

    /**
     * Engine handling the network client part.
     */
    private final AbstractNetworkEngineClient network;

    /**
     * Engine handling the canvas creation and inputs.
     */
    private final WindowEngine window;

    /**
     * Builder to create a new Engines object.
     *
     * @author Grégory Van den Borre
     */
    public static abstract class EnginesTemplate {

        /**
         * Add a graphic engine implementation.
         *
         * @return This object.
         */
        public abstract EnginesTemplate withGraphicEngine();

        /**
         * Add an audio engine implemenation.
         *
         * @return This object.
         */
        public abstract EnginesTemplate withAudioEngine();

        /**
         * Add a network client implemenation.
         *
         * @return This object.
         */
        public abstract EnginesTemplate withNetworkEngine();

        /**
         * @return The generated Engines object.
         */
        public final Engines build() {
            return new Engines(this.getGraphicEngine(), this.getAudioEngine(), this.getNetworkEngine(), this.getWindowEngine());
        }

        protected abstract WindowEngine getWindowEngine();

        protected abstract AbstractNetworkEngineClient getNetworkEngine();

        protected abstract SoundEngine getAudioEngine();

        protected abstract GraphicEngine getGraphicEngine();
    }
}
