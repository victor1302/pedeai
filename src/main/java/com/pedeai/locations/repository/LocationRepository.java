package com.pedeai.locations.repository;

import com.pedeai.locations.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
