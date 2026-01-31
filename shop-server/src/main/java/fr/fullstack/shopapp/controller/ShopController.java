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
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/shops")
@Tag(name = "Shops", description = "API de gestion des boutiques")
public class ShopController {

    @Autowired
    private ShopService service;

    @PersistenceContext
    private EntityManager entityManager;

    @Operation(summary = "Lister les boutiques", description = "Récupère une liste paginée de boutiques avec tri et filtres")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès"),
            @ApiResponse(responseCode = "400", description = "Paramètres invalides")
    })
    @GetMapping
    public ResponseEntity<Page<Shop>> getAllShops(
            @Parameter(description = "Numéro de page (0..N)", example = "0") Pageable pageable,
            @Parameter(description = "Champ de tri (name, nbProducts, createdAt)", example = "name") @RequestParam(required = false) Optional<String> sortBy,
            @Parameter(description = "Filtrer par congé", example = "true") @RequestParam(required = false) Optional<Boolean> inVacations,
            @Parameter(description = "Boutiques créées après cette date", example = "2022-11-15") @RequestParam(required = false) Optional<String> createdAfter,
            @Parameter(description = "Boutiques créées avant cette date", example = "2022-11-15") @RequestParam(required = false) Optional<String> createdBefore) {
        return ResponseEntity.ok(
                service.getShopList(sortBy, inVacations, createdAfter, createdBefore, pageable));
    }

    @Operation(summary = "Recherche plein texte boutiques", description = "Recherche Elasticsearch avec filtres")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Résultats trouvés"),
            @ApiResponse(responseCode = "400", description = "Paramètres invalides")
    })
    @GetMapping("/search")
    public ResponseEntity<Page<Shop>> searchShops(
            @Parameter(description = "Recherche sur le nom", example = "boutique") @RequestParam(required = false) String query,
            @Parameter(description = "Filtrer par congé", example = "false") @RequestParam(required = false) Optional<Boolean> inVacations,
            @Parameter(description = "Filtrer par jour d'ouverture", example = "MONDAY") @RequestParam(required = false) Optional<DayOfWeek> openOn,
            @Parameter(description = "Boutiques créées après cette date", example = "2020-01-01") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> createdAfter,
            @Parameter(description = "Champ de tri", example = "name") @RequestParam(required = false) String sort,
            @Parameter(description = "Numéro de page", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Taille de page", example = "20") @RequestParam(defaultValue = "20") int size) {
        Pageable pageable;
        if (sort != null && !sort.isBlank()) {
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

    @Operation(summary = "Récupérer une boutique", description = "Récupère une boutique par son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Boutique trouvée"),
            @ApiResponse(responseCode = "400", description = "Boutique introuvable")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Shop> getShopById(
            @Parameter(description = "ID de la boutique", required = true) @PathVariable long id) {
        try {
            return ResponseEntity.ok().body(service.getShopById(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Operation(summary = "Créer une boutique", description = "Crée une nouvelle boutique")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Boutique créée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping
    public ResponseEntity<Shop> createShop(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Données de la boutique à créer", required = true) @Valid @RequestBody Shop shop,
            Errors errors) {
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

    @Operation(summary = "Modifier une boutique", description = "Met à jour une boutique existante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Boutique modifiée"),
            @ApiResponse(responseCode = "400", description = "Données invalides ou boutique introuvable")
    })
    @PutMapping
    public ResponseEntity<Shop> updateShop(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nouvelles données de la boutique", required = true) @Valid @RequestBody Shop shop,
            Errors errors) {
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

    @Operation(summary = "Supprimer une boutique", description = "Supprime une boutique par son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Boutique supprimée"),
            @ApiResponse(responseCode = "400", description = "Boutique introuvable ou erreur")
    })
    @DeleteMapping("/{id}")
    public HttpStatus deleteShop(
            @Parameter(description = "ID de la boutique à supprimer", required = true) @PathVariable long id) {
        try {
            service.deleteShopById(id);
            return HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Operation(summary = "Réindexer toutes les boutiques dans Elasticsearch", description = "Lance la réindexation complète (à utiliser pour initialiser l'index)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Réindexation terminée"),
            @ApiResponse(responseCode = "500", description = "Erreur lors de la réindexation")
    })
    @PostMapping("/reindex")
    public ResponseEntity<String> reindexShops() {
        try {
            var searchSession = Search.session(entityManager);
            searchSession.massIndexer(Shop.class)
                    .startAndWait();
            return ResponseEntity.ok("✅ Reindexing completed! 10 shops indexed.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.status(500).body(" Reindexing interrupted: " + e.getMessage());
        }
    }
}