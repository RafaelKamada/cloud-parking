package one.digitalinnovation.cloudparking.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import one.digitalinnovation.cloudparking.exception.ParkingNotFoundException;
import one.digitalinnovation.cloudparking.model.Parking;
import one.digitalinnovation.cloudparking.repository.IParkingRepository;

@Service
public class ParkingService {

	private final IParkingRepository parkingRepository;
	
	public ParkingService(IParkingRepository parkingRepository) {
		this.parkingRepository = parkingRepository;
	}
	
	private static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Parking> findAll() {
		return parkingRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Parking findById(String id) {
		return parkingRepository.findById(id).orElseThrow(() -> new ParkingNotFoundException(id)); 
	}

	@Transactional
	public Parking create(Parking parkingCreate) {
		String uuid = getUUID();
		parkingCreate.setId(uuid);
		parkingCreate.setEntryDate(LocalDateTime.now());
		parkingRepository.save(parkingCreate);
		return parkingCreate;
	}

	@Transactional
	public void delete(String id) {
		findById(id);
		parkingRepository.deleteById(id);
	}

	@Transactional
	public Parking update(String id, Parking parkingCreate) {
		Parking parking = findById(id);
		parking.setColor(parkingCreate.getColor());
		parking.setState(parkingCreate.getState());
		parking.setModel(parkingCreate.getModel());
		parking.setLicense(parkingCreate.getLicense());
		parkingRepository.save(parking);
		return parking;
	}

	@Transactional
	public Parking checkOut(String id) {
		Parking parking = findById(id);
		parking.setExitDate(LocalDateTime.now());
		parking.setBill(ParkingCheckOut.getBill(parking));
		parkingRepository.save(parking);
		return parking;
	}
}

