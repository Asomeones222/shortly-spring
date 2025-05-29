package org.pum.shortly.repository;

import org.pum.shortly.model.URLClick;
import org.springframework.data.jpa.repository.JpaRepository;

public interface URLClickRepository extends JpaRepository<URLClick, Long> {
}
