package org.pum.shortly.repository;

import org.pum.shortly.model.AnalyticsRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalyticsRepository extends JpaRepository<AnalyticsRecord, Long> {
}
