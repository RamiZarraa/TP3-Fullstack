package fr.fullstack.shopapp.controller;

import fr.fullstack.shopapp.model.Category;
import fr.fullstack.shopapp.service.CategoryService;
import fr.fullstack.shopapp.util.ErrorValidation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Categories", description = "API de gestion des catégories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @Operation(summary = "Créer une catégorie", description = "Crée une nouvelle catégorie")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Catégorie créée"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping
    public ResponseEntity<Category> createCategory(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Données de la catégorie à créer", required = true) @Valid @RequestBody Category category,
            Errors errors) {
        if (errors.hasErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ErrorValidation.getErrorValidationMessage(errors));
        }
        try {
            return ResponseEntity.ok(service.createCategory(category));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Operation(summary = "Supprimer une catégorie", description = "Supprime une catégorie par son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Catégorie supprimée"),
            @ApiResponse(responseCode = "400", description = "Catégorie introuvable ou erreur")
    })
    @DeleteMapping("/{id}")
    public HttpStatus deleteCategory(
            @Parameter(description = "ID de la catégorie à supprimer", required = true) @PathVariable long id) {
        try {
            service.deleteCategoryById(id);
            return HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Operation(summary = "Lister les catégories", description = "Récupère une liste paginée de catégories")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    })
    @GetMapping
    public ResponseEntity<Page<Category>> getAllCategories(
            @Parameter(description = "Numéro de page (0..N)", example = "0") Pageable pageable) {
        return ResponseEntity.ok(service.getCategoryList(pageable));
    }

    @Operation(summary = "Récupérer une catégorie", description = "Récupère une catégorie par son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Catégorie trouvée"),
            @ApiResponse(responseCode = "400", description = "Catégorie introuvable")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(
            @Parameter(description = "ID de la catégorie", required = true) @PathVariable long id) {
        try {
            return ResponseEntity.ok().body(service.getCategoryById(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Operation(summary = "Modifier une catégorie", description = "Met à jour une catégorie existante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Catégorie modifiée"),
            @ApiResponse(responseCode = "400", description = "Données invalides ou catégorie introuvable")
    })
    @PutMapping
    public ResponseEntity<Category> updateCategory(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Nouvelles données de la catégorie", required = true) @Valid @RequestBody Category category,
            Errors errors) {
        if (errors.hasErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ErrorValidation.getErrorValidationMessage(errors));
        }
        try {
            return ResponseEntity.ok().body(service.updateCategory(category));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}