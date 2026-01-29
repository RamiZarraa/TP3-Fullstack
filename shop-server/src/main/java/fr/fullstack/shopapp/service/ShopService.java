package fr.fullstack.shopapp.service;

import fr.fullstack.shopapp.model.Product;
import fr.fullstack.shopapp.model.Shop;
import fr.fullstack.shopapp.repository.ShopRepository;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.engine.search.sort.dsl.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.hibernate.search.mapper.orm.session.SearchSession;
import org.hibernate.search.engine.search.predicate.dsl.SearchPredicateFactory;
import org.hibernate.search.engine.search.predicate.dsl.PredicateFinalStep;
import java.time.DayOfWeek;

@Service
public class ShopService {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ShopRepository shopRepository;

    @Transactional(readOnly = true)
    public Page<Shop> searchShops(
            String query,
            Optional<Boolean> inVacations,
            Optional<DayOfWeek> openOn,
            Optional<LocalDate> createdAfter,
            Pageable pageable) {
        SearchSession searchSession = Search.session(em);

        var searchQueryBuilder = searchSession.search(Shop.class)
                .where((f, root) -> {
                    List<PredicateFinalStep> predicates = new ArrayList<>();

                    // ✅ Full-text search avec meilleur scoring
                    if (query != null && !query.isBlank()) {
                        predicates.add(f.bool()
                                // 1. Correspondance exacte (boost x10)
                                .should(f.match()
                                        .field("name")
                                        .matching(query)
                                        .boost(10.0f))
                                // 2. Correspondance exacte case-insensitive (boost x8)
                                .should(f.match()
                                        .field("name_sort")
                                        .matching(query.toLowerCase())
                                        .boost(8.0f))
                                // 3. Phrase exacte (boost x5)
                                .should(f.phrase()
                                        .field("name")
                                        .matching(query)
                                        .boost(5.0f))
                                // 4. Fuzzy matching pour fautes de frappe (boost x2)
                                .should(f.match()
                                        .field("name")
                                        .matching(query)
                                        .fuzzy(2)
                                        .boost(2.0f))
                                // 5. Wildcard pour recherche partielle (boost x1)
                                .should(f.wildcard()
                                        .field("name")
                                        .matching(query.toLowerCase() + "*")
                                        .boost(1.0f)));
                    }

                    // Filter by vacation status
                    if (inVacations.isPresent()) {
                        predicates.add(f.match()
                                .field("inVacations")
                                .matching(inVacations.get()));
                    }

                    // Filter by creation date
                    if (createdAfter.isPresent()) {
                        predicates.add(f.range()
                                .field("createdAt")
                                .atLeast(createdAfter.get()));
                    }

                    // Filter by opening day
                    if (openOn.isPresent()) {
                        predicates.add(f.match()
                                .field("openingHours.day")
                                .matching(openOn.get().getValue()));
                    }

                    // Combine all predicates with AND
                    if (predicates.isEmpty()) {
                        root.add(f.matchAll());
                    } else {
                        for (PredicateFinalStep predicate : predicates) {
                            root.add(predicate);
                        }
                    }
                });

        // Tri
        var sortedQuery = searchQueryBuilder.sort(sortBuilder -> {
            if (pageable.getSort().isSorted()) {
                Sort.Order order = pageable.getSort().iterator().next();
                String field = order.getProperty();
                SortOrder direction = order.isAscending() ? SortOrder.ASC : SortOrder.DESC;

                if ("name".equals(field)) {
                    field = "name_sort";
                }

                return sortBuilder.field(field).order(direction);
            } else {
                // ✅ Tri par score (pertinence) par défaut
                return sortBuilder.score();
            }
        });

        // Execute search with pagination
        var searchResult = sortedQuery.fetch((int) pageable.getOffset(), pageable.getPageSize());

        List<Shop> shops = searchResult.hits();
        long total = searchResult.total().hitCount();

        return new PageImpl<>(shops, pageable, total);
    }

    // ...existing code...

    @Transactional
    public Shop createShop(Shop shop) throws Exception {
        try {
            Shop newShop = shopRepository.save(shop);
            em.flush();
            em.refresh(newShop);
            return newShop;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public void deleteShopById(long id) throws Exception {
        try {
            Shop shop = getShop(id);
            deleteNestedRelations(shop);
            shopRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Shop getShopById(long id) throws Exception {
        try {
            return getShop(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Page<Shop> getShopList(
            Optional<String> sortBy,
            Optional<Boolean> inVacations,
            Optional<String> createdBefore,
            Optional<String> createdAfter,
            Pageable pageable) {
        if (sortBy.isPresent()) {
            switch (sortBy.get()) {
                case "name":
                    return shopRepository.findByOrderByNameAsc(pageable);
                case "createdAt":
                    return shopRepository.findByOrderByCreatedAtAsc(pageable);
                default:
                    return shopRepository.findByOrderByNbProductsAsc(pageable);
            }
        }

        Page<Shop> shopList = getShopListWithFilter(inVacations, createdBefore, createdAfter, pageable);
        if (shopList != null) {
            return shopList;
        }

        return shopRepository.findByOrderByIdAsc(pageable);
    }

    @Transactional
    public Shop updateShop(Shop shop) throws Exception {
        try {
            getShop(shop.getId());
            return this.createShop(shop);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private void deleteNestedRelations(Shop shop) {
        List<Product> products = shop.getProducts();
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            product.setShop(null);
            em.merge(product);
            em.flush();
        }
    }

    private Shop getShop(Long id) throws Exception {
        Optional<Shop> shop = shopRepository.findById(id);
        if (shop.isEmpty()) {
            throw new Exception("Shop with id " + id + " not found");
        }
        return shop.get();
    }

    private Page<Shop> getShopListWithFilter(
            Optional<Boolean> inVacations,
            Optional<String> createdAfter,
            Optional<String> createdBefore,
            Pageable pageable) {
        if (inVacations.isPresent() && createdBefore.isPresent() && createdAfter.isPresent()) {
            return shopRepository.findByInVacationsAndCreatedAtGreaterThanAndCreatedAtLessThan(
                    inVacations.get(),
                    LocalDate.parse(createdAfter.get()),
                    LocalDate.parse(createdBefore.get()),
                    pageable);
        }

        if (inVacations.isPresent() && createdBefore.isPresent()) {
            return shopRepository.findByInVacationsAndCreatedAtLessThan(
                    inVacations.get(), LocalDate.parse(createdBefore.get()), pageable);
        }

        if (inVacations.isPresent() && createdAfter.isPresent()) {
            return shopRepository.findByInVacationsAndCreatedAtGreaterThan(
                    inVacations.get(), LocalDate.parse(createdAfter.get()), pageable);
        }

        if (inVacations.isPresent()) {
            return shopRepository.findByInVacations(inVacations.get(), pageable);
        }

        if (createdBefore.isPresent() && createdAfter.isPresent()) {
            return shopRepository.findByCreatedAtBetween(
                    LocalDate.parse(createdAfter.get()), LocalDate.parse(createdBefore.get()), pageable);
        }

        if (createdBefore.isPresent()) {
            return shopRepository.findByCreatedAtLessThan(
                    LocalDate.parse(createdBefore.get()), pageable);
        }

        if (createdAfter.isPresent()) {
            return shopRepository.findByCreatedAtGreaterThan(
                    LocalDate.parse(createdAfter.get()), pageable);
        }

        return null;
    }
}