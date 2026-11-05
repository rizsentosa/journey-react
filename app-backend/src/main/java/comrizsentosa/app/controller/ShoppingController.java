package comrizsentosa.app.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import comrizsentosa.app.dtos.CartShoppingRequest;
import comrizsentosa.app.dtos.CartShoppingResponse;
import comrizsentosa.app.dtos.ProductShoppingRequest;
import comrizsentosa.app.dtos.ProductShoppingResponse;
import comrizsentosa.app.services.ShoppingCartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/public/shop")
@RequiredArgsConstructor
public class ShoppingController {
  private final ShoppingCartService shoppingService;

  // end point product
  @GetMapping("/products")
  public ResponseEntity<Page<ProductShoppingResponse>> findAllProduct(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "size", defaultValue = "10") Integer size,
      @RequestParam(value = "sort", defaultValue = "price") String sort,
      @RequestParam(value = "direction", defaultValue = "desc") String direction) {

    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort));
    Page<ProductShoppingResponse> response = shoppingService.retrieveAllProduct(pageable);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/products/{product-id}")
  public ResponseEntity<ProductShoppingResponse> findOneProduct(@PathVariable("product-id") String id) {
    ProductShoppingResponse response = shoppingService.findOneProduct(id);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/products")
  public ResponseEntity<ProductShoppingResponse> savedProduct(@RequestBody @Valid ProductShoppingRequest request) {
    ProductShoppingResponse response = shoppingService.saveProduct(request);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PutMapping("/products/{product-id}")
  public ResponseEntity<ProductShoppingResponse> updatedProduct(
      @PathVariable("product-id") String id,
      @RequestBody @Valid ProductShoppingRequest request) {

    ProductShoppingResponse response = shoppingService.updateProduct(id, request);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/products/{product-id}")
  public ResponseEntity<Map<String, Object>> destroyProduct(@PathVariable("product-id") String id) {
    shoppingService.destroyProduct(id);
    Map<String, Object> response = Map.of("status", "success", "message", "Product deleted successfully");
    return ResponseEntity.ok(response);
  }

  // end point cart
  @PostMapping("/cart")
  public ResponseEntity<CartShoppingResponse> addingProductToCart(
      @RequestBody @Valid CartShoppingRequest request) {
    CartShoppingResponse response = shoppingService.addingProductToCart(request);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
}
