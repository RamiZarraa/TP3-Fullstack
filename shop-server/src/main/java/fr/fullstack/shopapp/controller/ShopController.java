package fr.fullstack.shopapp.controller;

import fr.fullstack.shopapp.model.Shop;
import fr.fullstack.shopapp.service.ShopService;
import fr.fullstack.shopapp.util.ErrorValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
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

import org.hibernate.search.mapper.orm.Search;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import java.util.Optional;

import java.time.DayOfWeek;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/shops")
public class ShopController {

    @Autowired
    private ShopService service;

    @PersistenceContext
    private EntityManager entityManager;

    @Operation(summary = "Get shops (sorting and filtering are possible)")
    @GetMapping
    public ResponseEntity<Page<Shop>> getAllShops(
            @Parameter(description = "Results page you want to retrieve (0..N)", example = "0") Pageable pageable,
            @Parameter(description = "To sort the shops. Possible values are 'name', 'nbProducts' and 'createdAt'", example = "name") @RequestParam(required = false) Optional<String> sortBy,
            @Parameter(description = "Define that the shops must be in vacations or not", example = "true") @RequestParam(required = false) Optional<Boolean> inVacations,
            @Parameter(description = "Define that the shops must be created after this date", example = "2022-11-15") @RequestParam(required = false) Optional<String> createdAfter,
            @Parameter(description = "Define that the shops must be created before this date", example = "2022-11-15") @RequestParam(required = false) Optional<String> createdBefore

    ) {
        return ResponseEntity.ok(
                service.getShopList(sortBy, inVacations, createdAfter, createdBefore, pageable));
    }

    @Operation(summary = "Search shops using Elasticsearch (full-text search with filters)")
    @GetMapping("/search")
    public ResponseEntity<Page<Shop>> searchShops(
            @Parameter(description = "Search query for shop name (supports fuzzy matching)", example = "boutique") @RequestParam(required = false) String query,

            @Parameter(description = "Filter by vacation status", example = "false") @RequestParam(required = false) Optional<Boolean> inVacations,

            @Parameter(description = "Filter by opening day (MONDAY, TUESDAY, etc.)", example = "MONDAY") @RequestParam(required = false) Optional<DayOfWeek> openOn,

            @Parameter(description = "Filter shops created after this date", example = "2020-01-01") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> createdAfter,

            @Parameter(description = "Sort by field (name, createdAt, nbProducts)", example = "name") @RequestParam(required = false) String sort,

            @Parameter(description = "Results page you want to retrieve (0..N)", example = "0") @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Number of records per page", example = "20") @RequestParam(defaultValue = "20") int size) {
        // Créer le Pageable avec tri si demandé
        Pageable pageable;
        if (sort != null && !sort.isBlank()) {
            // Extraire la direction du tri (ex: "name,asc" ou "name,desc")
            String[] sortParams = sort.split(",");
            String field = sortParams[0];
            Sort.Direction direction = sortParams.length > 1 && "desc".equalsIgnoreCase(sortParams[1])
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;

            pageable = PageRequest.of(page, size, Sort.by(direction, field));
        } else {
            pageable = PageRequest.of(page, size);
        }

        return ResponseEntity.ok(
                service.searchShops(query, inVacations, openOn, createdAfter, pageable));
    }

    @Operation(summary = "Get a shop by id")
    @GetMapping("/{id}")
    public ResponseEntity<Shop> getShopById(@PathVariable long id) {
        try {
            return ResponseEntity.ok().body(service.getShopById(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Operation(summary = "Create a shop")
    @PostMapping
    public ResponseEntity<Shop> createShop(@Valid @RequestBody Shop shop, Errors errors) {
        if (errors.hasErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ErrorValidation.getErrorValidationMessage(errors));
        }

        try {
            return ResponseEntity.ok(service.createShop(shop));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Operation(summary = "Update a shop")
    @PutMapping
    public ResponseEntity<Shop> updateShop(@Valid @RequestBody Shop shop, Errors errors) {
        if (errors.hasErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ErrorValidation.getErrorValidationMessage(errors));
        }

        try {
            return ResponseEntity.ok().body(service.updateShop(shop));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Operation(summary = "Delete a shop by its id")
    @DeleteMapping("/{id}")
    public HttpStatus deleteShop(@PathVariable long id) {
        try {
            service.deleteShopById(id);
            return HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Operation(summary = "Reindex all shops in Elasticsearch (run once to populate the index)")
    @PostMapping("/reindex")
    public ResponseEntity<String> reindexShops() {
        try {
            var searchSession = Search.session(entityManager);
            searchSession.massIndexer(Shop.class)
                    .startAndWait();
            return ResponseEntity.ok("✅ Reindexing completed! 10 shops indexed.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.status(500).body("❌ Reindexing interrupted: " + e.getMessage());
        }
    }

}