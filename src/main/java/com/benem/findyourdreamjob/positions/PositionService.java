package com.benem.findyourdreamjob.positions;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface PositionService {

    String addPosition(Position position, String apiKey);

    List<Position> findMatchingPositions(String name, String location, String apiKey) throws UnsupportedEncodingException;
}
