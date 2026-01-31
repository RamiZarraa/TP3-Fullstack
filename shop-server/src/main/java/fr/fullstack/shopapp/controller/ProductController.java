package fr.fullstack.shopapp.controller;

import fr.fullstack.shopapp.model.Product;
import fr.fullstack.shopapp.service.ProductService;
import fr.fullstack.shopapp.util.ErrorValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Products", description = "API de gestion des produits")
public class ProductController {

    @Autowired
    private ProductService service;

    @Operation(summary = "Créer un produit", description = "Crée un nouveau produit avec ses traductions localisées")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produit créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping
    public ResponseEntity<Product> createProduct(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Données du produit à créer", required = true) @Valid @RequestBody Product product,
            Errors errors) {
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

    @Operation(summary = "Supprimer un produit", description = "Supprime un produit par son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produit supprimé avec succès"),
            @ApiResponse(responseCode = "400", description = "Produit introuvable ou erreur")
    })
    @DeleteMapping("/{id}")
    public HttpStatus deleteProduct(
            @Parameter(description = "ID du produit à supprimer", required = true) @PathVariable long id) {
        try {
            service.deleteProductById(id);
            return HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Operation(summary = "Récupérer un produit", description = "Récupère un produit par son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produit trouvé"),
            @ApiResponse(responseCode = "400", description = "Produit introuvable")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(
            @Parameter(description = "ID du produit", required = true) @PathVariable long id) {
        try {
            return ResponseEntity.ok().body(service.getProductById(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Operation(summary = "Modifier un produit", description = "Met à jour un produit existant par son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produit modifié avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides ou produit introuvable")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @Parameter(description = "ID du produit à modifier", required = true) @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nouvelles données du produit", required = true) @Valid @RequestBody Product product,
            Errors errors) {
        if (errors.hasErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ErrorValidation.getErrorValidationMessage(errors));
        }

        try {
            product.setId(id);
            return ResponseEntity.ok().body(service.updateProduct(product));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Operation(summary = "Lister les produits", description = "Récupère une liste paginée de produits avec options de recherche, tri et filtrage")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "400", description = "Paramètres invalides")
    })
    @GetMapping
    public ResponseEntity<Page<Product>> getProducts(
            @Parameter(description = "Terme de recherche (nom ou description)") @RequestParam(required = false) String search,
            @Parameter(description = "Champ de tri (id, price, name)") @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Direction du tri (asc, desc)") @RequestParam(defaultValue = "asc") String sortDirection,
            @Parameter(description = "Numéro de page (commence à 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Nombre d'éléments par page") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "ID de la boutique pour filtrer") @RequestParam(required = false) Optional<Long> shopId,
            @Parameter(description = "ID de la catégorie pour filtrer") @RequestParam(required = false) Optional<Long> categoryId) {

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