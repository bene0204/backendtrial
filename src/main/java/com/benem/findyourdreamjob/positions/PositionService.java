package com.benem.findyourdreamjob.positions;

import java.util.List;

public interface PositionService {

    String addPosition(Position position, String apiKey) throws Exception;

    List<Position> findMatchingPositions(String name, String location, String apiKey) throws Exception;
}
