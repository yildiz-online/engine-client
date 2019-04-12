package be.yildizgames.engine.client;

import be.yildizgames.common.client.config.Configuration;
import be.yildizgames.common.exception.implementation.ImplementationException;
import be.yildizgames.common.model.Version;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class GameEngineFactoryTest {

    @Nested
    public class Build {

        @Test
        public void happyFlow() {
            GameEngine engine = GameEngineFactory.build(Configuration.getInstance(), Version.release(1,0,0,0));
            Assertions.assertNotNull(engine);
        }

        @Test
        public void withNullVersion() {
            Assertions.assertThrows(ImplementationException.class, () -> GameEngineFactory.build(Configuration.getInstance(), null));
        }

    }

}
