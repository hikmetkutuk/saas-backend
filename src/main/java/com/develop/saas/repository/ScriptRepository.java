package com.develop.saas.repository;

import com.develop.saas.model.Script;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScriptRepository extends JpaRepository<Script, Long> {

    /**
     * The @EntityGraph makes the relationships you specify during the query Eager
     * and only changes this behaviour for that specific query.
     * So, instead of making all Lazy relationships Eager, you can selectively load specific relationships.
     */
    @NotNull @EntityGraph(attributePaths = "categories")
    Optional<Script> findById(@NotNull Long id);
}
