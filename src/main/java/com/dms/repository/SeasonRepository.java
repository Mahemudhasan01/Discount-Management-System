package com.dms.repository;

import com.dms.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeasonRepository  extends JpaRepository<Season, Long> {

    Optional<Season> findByName(String name);
}
