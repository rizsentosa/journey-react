package comrizsentosa.app.util;

import java.util.EnumSet;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import com.github.f4b6a3.ulid.UlidCreator;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;

public class UlidGenerator implements BeforeExecutionGenerator {

  @Override
  public Object generate(SharedSessionContractImplementor session, Object owner, Object currentValue,
      EventType eventType) {
    return UlidCreator.getMonotonicUlid().toString();
  }

  @Override
  public EnumSet<EventType> getEventTypes() {
    return EnumSet.of(EventType.INSERT);
  }
}
