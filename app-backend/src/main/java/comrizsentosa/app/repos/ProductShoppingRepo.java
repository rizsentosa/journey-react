package comrizsentosa.app.repos;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import comrizsentosa.app.models.shopping.Product;

public interface ProductShoppingRepo extends JpaRepository<Product, UUID> {

  Optional<Product> findById(UUID id);

  @Query("SELECT COUNT(product) FROM Product product")
  Long countProduct();

  @Query("SELECT p FROM Product p")
  Page<Product> retrieveProduct(Pageable pageable);

  @EntityGraph(attributePaths = { "cartItems" })
  @Query("SELECT p FROM Product p WHERE p.id = :id")
  Optional<Product> retrieveProductWithCart(@Param("id") UUID id);
}
