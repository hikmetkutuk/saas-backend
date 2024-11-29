package com.develop.saas.repository;

import com.develop.saas.model.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    // Find by name (case-insensitive)
    Optional<Category> findByNameIgnoreCase(String name);

    // Check if category exists by name
    boolean existsByNameIgnoreCase(String name);

    // Custom query to find categories with at least one script
    @Query("SELECT c FROM Category c JOIN c.scripts s GROUP BY c HAVING COUNT(s) > 0")
    List<Category> findCategoriesWithScripts();

    // Retrieve only non-deleted categories
    List<Category> findByDeletedFalse();
}
