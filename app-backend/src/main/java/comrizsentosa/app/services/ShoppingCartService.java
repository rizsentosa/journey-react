package comrizsentosa.app.services;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import comrizsentosa.app.dtos.CartShoppingRequest;
import comrizsentosa.app.dtos.CartShoppingResponse;
import comrizsentosa.app.dtos.ProductShoppingRequest;
import comrizsentosa.app.dtos.ProductShoppingResponse;
import comrizsentosa.app.exception.BadRequestException;
import comrizsentosa.app.exception.ResourceNotFoundException;
import comrizsentosa.app.models.shopping.Cart;
import comrizsentosa.app.models.shopping.Product;
import comrizsentosa.app.repos.CartShoppingRepo;
import comrizsentosa.app.repos.ProductShoppingRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {
  private final ProductShoppingRepo productRepo;
  private final CartShoppingRepo cartRepo;

  // code crud product
  @Transactional
  public ProductShoppingResponse saveProduct(ProductShoppingRequest request) {
    Product product = Product.builder()
        .name(request.name())
        .price(request.price())
        .imageUrl(request.imageUrl())
        .stockQuantity(request.stockQuantity())
        .build();

    product = productRepo.save(product);
    ProductShoppingResponse object = ProductShoppingResponse.builder()
        .id(product.getId().toString())
        .name(product.getName())
        .stockQuantity(product.getStockQuantity())
        .imageUrl(product.getImageUrl())
        .isActive(product.getIsActive())
        .price(product.getPrice())
        .createdDate(product.getCreatedAt())
        .build();

    return object;
  }

  @Transactional
  public ProductShoppingResponse updateProduct(String id, ProductShoppingRequest request) {
    Product product = getProductById(id);

    product.setName(request.name());
    product.setPrice(request.price());
    product.setStockQuantity(request.stockQuantity());
    product.setImageUrl(request.imageUrl());

    Product saved = productRepo.save(product);
    ProductShoppingResponse object = ProductShoppingResponse.builder()
        .id(saved.getId().toString())
        .name(saved.getName())
        .stockQuantity(saved.getStockQuantity())
        .imageUrl(saved.getImageUrl())
        .isActive(saved.getIsActive())
        .price(saved.getPrice())
        .createdDate(saved.getCreatedAt())
        .build();

    return object;
  }

  @Transactional
  public void destroyProduct(String id) {
    Product product = getProductById(id);
    productRepo.delete(product);
  }

  @Transactional(readOnly = true)
  public ProductShoppingResponse findOneProduct(String id) {
    Product product = getProductById(id);
    ProductShoppingResponse object = null;

    if (product.getCartItems().isEmpty()) {
      object = ProductShoppingResponse.builder()
          .id(product.getId().toString())
          .name(product.getName())
          .stockQuantity(product.getStockQuantity())
          .imageUrl(product.getImageUrl())
          .isActive(product.getIsActive())
          .price(product.getPrice())
          .createdDate(product.getCreatedAt())
          .build();
      return object;
    }

    object = ProductShoppingResponse.builder()
        .id(product.getId().toString())
        .name(product.getName())
        .stockQuantity(product.getStockQuantity())
        .imageUrl(product.getImageUrl())
        .isActive(product.getIsActive())
        .price(product.getPrice())
        .cartItems(mapToList(product.getCartItems(), c -> {
          return CartShoppingResponse.builder()
              .id(c.getId().toString())
              .name(c.getProduct().getName())
              .quantity(c.getQuantity())
              .price(c.getProduct().getPrice())
              .subTotal(product.getPrice().multiply(BigDecimal.valueOf(c.getQuantity())))
              .addedAt(c.getAddedAt())
              .build();
        }))
        .createdDate(product.getCreatedAt())
        .build();

    return object;
  }

  @Transactional(readOnly = true)
  public Page<ProductShoppingResponse> retrieveAllProduct(Pageable pageable) {
    Page<Product> product = productRepo.retrieveProduct(pageable);
    List<ProductShoppingResponse> content = mapToList(product.getContent(), p -> {
      if (p.getCartItems().isEmpty()) {
        return ProductShoppingResponse.builder()
            .id(p.getId().toString())
            .name(p.getName())
            .stockQuantity(p.getStockQuantity())
            .imageUrl(p.getImageUrl())
            .isActive(p.getIsActive())
            .price(p.getPrice())
            .createdDate(p.getCreatedAt())
            .build();
      }

      var cart = mapToList(p.getCartItems(), c -> {
        return CartShoppingResponse.builder()
            .id(c.getId().toString())
            .name(c.getProduct().getName())
            .quantity(c.getQuantity())
            .price(c.getProduct().getPrice())
            .subTotal(p.getPrice().multiply(BigDecimal.valueOf(c.getQuantity())))
            .addedAt(c.getAddedAt())
            .build();
      });

      return ProductShoppingResponse.builder()
          .id(p.getId().toString())
          .name(p.getName())
          .stockQuantity(p.getStockQuantity())
          .imageUrl(p.getImageUrl())
          .isActive(p.getIsActive())
          .price(p.getPrice())
          .cartItems(cart)
          .createdDate(p.getCreatedAt())
          .build();
    });
    return new PageImpl<>(content, pageable, product.getTotalElements());
  }

  // code crud cart
  @Transactional
  public CartShoppingResponse addingProductToCart(CartShoppingRequest request) {
    Product product = getProductById(request.productId());
    Cart cart = new Cart();
    BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(request.quantity()));

    cart.setQuantity(request.quantity());
    cart.setAddedAt(request.addedAt());
    cart.setTotal(subtotal);
    cart.setProduct(product);
    Cart saved = cartRepo.save(cart);

    CartShoppingResponse object = CartShoppingResponse.builder()
        .id(saved.getId().toString())
        .name(saved.getProduct().getName())
        .quantity(saved.getQuantity())
        .price(saved.getProduct().getPrice())
        .subTotal(subtotal)
        .addedAt(saved.getAddedAt())
        .build();
    return object;
  }

  @Transactional
  public void destroyCartToProduct() {
  }

  public void getAllCartWithProduct() {
  }

  // utilities
  private UUID parseUUID(String id) {
    try {
      return UUID.fromString(id);
    } catch (IllegalArgumentException e) {
      throw new BadRequestException("not valid id: " + id);
    }
  }

  private Long calculateTotalProduct() {
    return productRepo.countProduct();
  }

  private Product getProductById(String id) {
    UUID uuid = parseUUID(id);
    Product product = productRepo.findById(uuid)
        .orElseThrow(() -> new ResourceNotFoundException("data not found with id " + id));
    return product;
  }

  private <T, R> List<R> mapToList(List<T> source, Function<T, R> mapper) {
    if (source == null || source.isEmpty()) {
      return Collections.emptyList();
    }
    return source.stream().map(mapper).toList();
  }
}
