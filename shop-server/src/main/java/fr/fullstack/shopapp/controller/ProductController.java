package fr.fullstack.shopapp.controller;

import fr.fullstack.shopapp.model.Product;
import fr.fullstack.shopapp.service.ProductService;
import fr.fullstack.shopapp.util.ErrorValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @Operation(summary = "Create a product")
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, Errors errors) {
        if (errors.hasErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ErrorValidation.getErrorValidationMessage(errors));
        }

        try {
            return ResponseEntity.ok(service.createProduct(product));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Operation(summary = "Delete a product by its id")
    @DeleteMapping("/{id}")
    public HttpStatus deleteProduct(@PathVariable long id) {
        try {
            service.deleteProductById(id);
            return HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Operation(summary = "Get a product by id")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable long id) {
        try {
            return ResponseEntity.ok().body(service.getProductById(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Operation(summary = "Update a product")
    @PutMapping
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product, Errors errors) {
        if (errors.hasErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ErrorValidation.getErrorValidationMessage(errors));
        }

        try {
            return ResponseEntity.ok().body(service.updateProduct(product));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Operation(summary = "Get products with search and sort options")
    @GetMapping
    public ResponseEntity<Page<Product>> getProducts(
            @Parameter(description = "Search term") @RequestParam(required = false) String search,
            @Parameter(description = "Field to sort by") @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "asc") String sortDirection,
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Shop ID") @RequestParam(required = false) Optional<Long> shopId,
            @Parameter(description = "Category ID") @RequestParam(required = false) Optional<Long> categoryId) {

        // ✅ CORRECTION : Créer le Sort AVANT le Pageable
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDirection)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> products;

        if (search != null && !search.trim().isEmpty()) {
            products = service.searchProducts(search, pageable);
        } else {
            products = service.getShopProductList(shopId, categoryId, pageable);
        }

        return ResponseEntity.ok(products);
    }
}