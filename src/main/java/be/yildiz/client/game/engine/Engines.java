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

import be.yildiz.module.graphic.GraphicEngine;
import be.yildiz.module.network.client.AbstractNetworkEngineClient;
import be.yildiz.module.sound.SoundEngine;

/**
 * Provide the engines implementation to use.
 *
 * @author Grégory Van den Borre
 */
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

    private Engines(GraphicEngine graphic, SoundEngine audio, AbstractNetworkEngineClient network) {
        this.graphic = graphic;
        this.audio = audio;
        this.network = network;
    }

    public GraphicEngine getGraphic() {
        return graphic;
    }

    public SoundEngine getAudio() {
        return audio;
    }

    public AbstractNetworkEngineClient getNetwork() {
        return network;
    }

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
            return new Engines(this.getGraphicEngine(), this.getAudioEngine(), this.getNetworkEngine());
        }

        protected abstract AbstractNetworkEngineClient getNetworkEngine();

        protected abstract SoundEngine getAudioEngine();

        protected abstract GraphicEngine getGraphicEngine();
    }
}
