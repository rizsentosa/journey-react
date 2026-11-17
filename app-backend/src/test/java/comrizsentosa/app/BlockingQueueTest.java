package comrizsentosa.app;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

@Slf4j
public class BlockingQueueTest {

  @Autowired
  private ObjectMapper mapper;

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

  @Test
  void testOrderService() throws Exception {
    DoProcessCRUD<AuditContext, Long, OrderRequest, OrderResponse> orderService = new OrderService();

    // 1. create
    OrderRequest newOrder = new OrderRequest(
        UUID.randomUUID(),
        "LAPTOP-PRO-16",
        2,
        new BigDecimal("1499.99"));
    Long orderId = orderService.savingData(newOrder);

    // 2. Read One
    OrderResponse fetched = orderService.findOneData(orderId);
    log.info("created {}", fetched);

    // 3. Update with Context
    OrderRequest amendedOrder = new OrderRequest(
        fetched.customerId(),
        "LAPTOP-PRO-16",
        3,
        new BigDecimal("1499.99"));
    OrderResponse updated = orderService.updatingData(
        amendedOrder,
        orderId,
        new AuditContext("admin_jane", "Customer requested extra unit"));
    log.info("updated: {}", updated);

    log.info("all orders {}", orderService.findingAllData().size());
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

  /**
   * T1 is Context
   * T2 is ID
   * T3 is request
   * T4 is response
   * DoProcessCRUD
   * 
   * @param <T>
   */
  sealed interface DoProcessCRUD<T1, T2, T3, T4> permits ProcessCRUDAbstract {
    Long savingData(T3 var1);

    T4 updatingData(T3 var1, T2 var2, T1 var3);

    Boolean deletingData(T2 var1);

    List<T4> findingAllData();

    T4 findOneData(T2 var1);
  }

  non-sealed abstract static class ProcessCRUDAbstract<REQ, ID, CTX, RES> implements DoProcessCRUD<CTX, ID, REQ, RES> {

    @Override
    public Long savingData(REQ var1) {
      throw new UnsupportedOperationException("savingData not implemented");
    }

    @Override
    public RES updatingData(REQ var1, ID var2, CTX var3) {
      throw new UnsupportedOperationException("updatingData not implemented");
    }

    @Override
    public Boolean deletingData(ID var1) {
      throw new UnsupportedOperationException("deletingData not implemented");
    }

    @Override
    public List<RES> findingAllData() {
      return List.of();
    }

    @Override
    public RES findOneData(ID var1) {
      throw new UnsupportedOperationException("findOneData not implemented");
    }
  }

  static record OrderRequest(
      UUID customerId,
      String itemSku,
      Integer quantity,
      BigDecimal unitPrice) {
  }

  static record AuditContext(String updatedBy, String reason) {
  }

  static record OrderResponse(
      Long id,
      UUID customerId, String itemSku, Integer quantity, BigDecimal totalAmount, String status, Instant updatedAt) {
  }

  static class OrderService extends ProcessCRUDAbstract<OrderRequest, Long, AuditContext, OrderResponse> {
    private final Map<Long, OrderResponse> stores = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1000);

    @Override
    public Long savingData(OrderRequest request) {
      long newId = idGenerator.incrementAndGet();
      BigDecimal total = request.unitPrice().multiply(BigDecimal.valueOf(request.quantity()));
      OrderResponse created = new OrderResponse(newId, request.customerId(), request.itemSku(), request.quantity(),
          total, "CREATED", Instant.now());
      stores.put(newId, created);
      return newId;
    }

    @Override
    public OrderResponse updatingData(OrderRequest request, Long id, AuditContext ctx) {
      OrderResponse orderExisting = findOneData(id);
      BigDecimal updatedTotal = request.unitPrice().multiply(BigDecimal.valueOf(request.quantity()));
      OrderResponse updated = new OrderResponse(id, request.customerId(), request.itemSku(), request.quantity(),
          updatedTotal, "UPDATED_BY_" + ctx.updatedBy().toUpperCase(), Instant.now());
      stores.put(id, updated);
      return updated;
    }

    @Override
    public List<OrderResponse> findingAllData() {
      return Collections.unmodifiableList(List.copyOf(stores.values()));
    }

    @Override
    public OrderResponse findOneData(Long id) {
      OrderResponse order = stores.get(id);
      if (order == null) {
        throw new NoSuchElementException("Order not found with id: " + id);
      }
      return order;
    }
  }
}
