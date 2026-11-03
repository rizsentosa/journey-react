package comrizsentosa.app.repos;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import comrizsentosa.app.models.shopping.Cart;

public interface CartShoppingRepo extends JpaRepository<Cart, UUID> {

  @EntityGraph(attributePaths = "product")
  Optional<Cart> findById(UUID id);

  @Query("SELECT cart FROM Cart cart")
  @EntityGraph(attributePaths = "product")
  List<Cart> getCartWithProduct();

}
