package comrizsentosa.app.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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
    ProductShoppingResponse object = new ProductShoppingResponse(
        product.getId().toString(),
        product.getName(),
        product.getStockQuantity(),
        product.getImageUrl(),
        product.getIsActive(),
        product.getPrice(),
        product.getCreatedAt());

    return object;
  }

  @Transactional
  public ProductShoppingResponse updateProduct(String id, ProductShoppingRequest request) {
    UUID uuid = parseUUID(id);
    Product product = getProductById(id);

    product.setName(request.name());
    product.setPrice(request.price());
    product.setStockQuantity(request.stockQuantity());
    product.setImageUrl(request.imageUrl());

    Product saved = productRepo.save(product);
    ProductShoppingResponse object = new ProductShoppingResponse(
        saved.getId().toString(),
        saved.getName(),
        saved.getStockQuantity(),
        saved.getImageUrl(),
        saved.getIsActive(),
        saved.getPrice(),
        saved.getCreatedAt());

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
    ProductShoppingResponse object = new ProductShoppingResponse(
        product.getId().toString(),
        product.getName(),
        product.getStockQuantity(),
        product.getImageUrl(),
        product.getIsActive(),
        product.getPrice(),
        product.getCreatedAt());
    return object;
  }

  @Transactional(readOnly = true)
  public Page<ProductShoppingResponse> retrieveAllProduct(Pageable pageable) {
    Page<Product> product = productRepo.retrieveProduct(pageable);
    List<ProductShoppingResponse> content = product.stream()
        .map(p -> {
          return new ProductShoppingResponse(p.getId().toString(), p.getName(), p.getStockQuantity(),
              p.getImageUrl(), p.getIsActive(), p.getPrice(), p.getCreatedAt());
        })
        .toList();
    return new PageImpl<>(content, pageable, product.getTotalElements());
  }

  // code crud cart
  @Transactional
  public CartShoppingResponse addingProductToCart(CartShoppingRequest request) {
    Product product = getProductById(request.productId());
    Cart cart = new Cart();
    Long subtotal = (Long) product.getPrice().longValue() * request.quantity();

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
        .subTotal(BigDecimal.valueOf(subtotal))
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
}
