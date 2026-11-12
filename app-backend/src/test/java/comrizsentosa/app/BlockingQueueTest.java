package comrizsentosa.app;

import java.util.Comparator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlockingQueueTest {

  @Test
  void arrayBlockingQueue() throws Exception {
    // final var queue = new ArrayBlockingQueue<>(10);
    // final var queue = new LinkedBlockingQueue<>();
    final var queue = new PriorityBlockingQueue<Person>(10, Comparator.comparingInt(Person::getAge));
    final AtomicInteger atomicInt = new AtomicInteger(1);


    try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      for (int count = 1; count <= 100; count++) {
        executor.submit(() -> {
          int ato = atomicInt.getAndIncrement();
          Person obj = Person.builder().name("john-" + ato).age((int) (Math.random() * 100)).gender(Person.Gender.MALE)
              .build();
          queue.put(obj);
          log.info("finish put data");
        });
      }
      executor.execute(() -> {
        while (!queue.isEmpty()) {
          try {
            Person data = (Person) queue.take();
            log.info("{}", data.toString());
          } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            ex.printStackTrace();
            break;
          }
        }
      });
      Thread.sleep(3000);
    }
  }

  @Builder
  @Getter
  @ToString
  static class Person {
    private String name;
    private Integer age;
    private Gender gender;

    static enum Gender {
      MALE, FEMALE
    }
  }
}
