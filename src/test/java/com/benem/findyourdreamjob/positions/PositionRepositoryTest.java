package com.benem.findyourdreamjob.positions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PositionRepositoryTest {

    @Autowired
    private PositionRepository positionRepository;


    @BeforeEach
    void setUp() {
        positionRepository.deleteAll();

        var position = Position.builder()
                .name("Finance")
                .location("London")
                .url("url")
                .build();

        positionRepository.save(position);
    }

    @Test
    void shouldSavePosition() {

        var position = Position.builder()
                .name("Accounting")
                .location("Berlin")
                .url("url")
                .build();

        positionRepository.save(position);

        List<Position> positions = positionRepository.findAll();

        assertTrue(positions.size() == 2);
    }

    @Test
    void ShouldFindPosition() {

        List<Position> positions = positionRepository.findPositionByNameIgnoreCaseAndLocationIgnoreCase("Finance","London");

        assertNotNull(positions);
        assertEquals(1 , positions.size());

    }

    @Test
    void ShouldNotFindPosition() {

        List<Position> positions = positionRepository.findPositionByNameIgnoreCaseAndLocationIgnoreCase("Finance","Budapest");

        assertEquals(0, positions.size());

    }
}