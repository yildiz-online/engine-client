package be.yildizgames.engine.client;

import be.yildizgames.common.exception.implementation.ImplementationException;
import be.yildizgames.common.model.Version;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GameEngineFactoryTest {

    @Nested
    class GetWithGameVersion {

        @Test
        void happyFlow() {
            GameEngine engine = GameEngineFactory.getWithGameVersion(Version.release(1,0,0,0));
            Assertions.assertNotNull(engine);
        }

        @Test
        void withNull() {
            Assertions.assertThrows(ImplementationException.class, () -> GameEngineFactory.getWithGameVersion(null));
        }

    }

}
