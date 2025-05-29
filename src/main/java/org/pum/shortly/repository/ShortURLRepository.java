package org.pum.shortly.repository;

import org.pum.shortly.model.ShortURL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortURLRepository extends JpaRepository<ShortURL, Long> {
    Optional<ShortURL> findByCode(String shortCode);

    boolean existsByCode(String shortCode);

}
