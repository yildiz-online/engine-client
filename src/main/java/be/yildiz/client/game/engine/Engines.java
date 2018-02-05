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

package be.yildiz.client.game.engine;

import be.yildiz.module.graphic.GraphicEngine;
import be.yildiz.module.graphic.dummy.DummyGraphicEngine;
import be.yildiz.module.network.client.AbstractNetworkEngineClient;
import be.yildiz.module.network.client.DummyNetworkEngine;
import be.yildiz.module.physics.DummyPhysicEngine;
import be.yildiz.module.physics.PhysicEngine;
import be.yildiz.module.sound.AudioEngine;
import be.yildiz.module.sound.dummy.DummyAudioEngine;

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
    private final AudioEngine audio;

    /**
     * Engine handling the network client part.
     */
    private final AbstractNetworkEngineClient network;

    private final PhysicEngine physics;

    private Engines(GraphicEngine graphic, AudioEngine audio, AbstractNetworkEngineClient network, PhysicEngine physicEngine) {
        this.graphic = graphic;
        this.audio = audio;
        this.network = network;
        this.physics = physicEngine;
    }

    public GraphicEngine getGraphic() {
        return graphic;
    }

    public PhysicEngine getPhysics() {
        return physics;
    }

    public AudioEngine getAudio() {
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
    public static class EnginesBuilder {

        private GraphicEngine graphicEngine = new DummyGraphicEngine();

        private AudioEngine audioEngine = new DummyAudioEngine();

        private AbstractNetworkEngineClient networkEngine = new DummyNetworkEngine();

        private PhysicEngine physicEngine = new DummyPhysicEngine();

        /**
         * Add a graphic engine implementation.
         *
         * @return This object.
         */
        public final EnginesBuilder withGraphicEngine(GraphicEngine engine) {
            this.graphicEngine = engine;
            return this;
        }

        /**
         * Add an audio engine implementation.
         *
         * @return This object.
         */
        public final EnginesBuilder withAudioEngine(AudioEngine engine) {
            this.audioEngine = engine;
            return this;
        }

        /**
         * Add a network client implementation.
         *
         * @return This object.
         */
        public final EnginesBuilder withNetworkEngine(AbstractNetworkEngineClient engine) {
            this.networkEngine = engine;
            return this;
        }

        /**
         * Add a network client implementation.
         *
         * @return This object.
         */
        public final EnginesBuilder withPhysicEngine(PhysicEngine engine) {
            this.physicEngine = engine;
            return this;
        }

        /**
         * @return The generated Engines object.
         */
        public final Engines build() {
            return new Engines(this.graphicEngine, this.audioEngine, this.networkEngine, this.physicEngine);
        }
    }
}
