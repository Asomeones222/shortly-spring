package org.pum.shortly.repository;

import org.pum.shortly.model.ShortURL;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortURLRepository extends JpaRepository<ShortURL, Integer> {
}
