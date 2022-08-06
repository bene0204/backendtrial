package com.benem.findyourdreamjob.positions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository  extends JpaRepository<Position, Long> {
    List<Position> findPositionByNameIgnoreCaseAndLocationIgnoreCase(String name, String location);
}
