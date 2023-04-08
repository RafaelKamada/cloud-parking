package one.digitalinnovation.cloudparking.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import one.digitalinnovation.cloudparking.model.Parking;

@Service
public class ParkingService {

	private static Map<String, Parking> parkingMap = new HashMap<>();
	
	static {
		var id = getUUID();
		Parking parking = new Parking(id, "DMSS-1111", "SC", "CELTA", "PRETO");
		parkingMap.put(id, parking);
	}
	
	private static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public List<Parking> findAll() {
		return parkingMap.values().stream().collect(Collectors.toList());
	}
}
