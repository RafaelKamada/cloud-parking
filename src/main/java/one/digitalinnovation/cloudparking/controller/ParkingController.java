package one.digitalinnovation.cloudparking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import one.digitalinnovation.cloudparking.controller.dto.ParkingCreateDTO;
import one.digitalinnovation.cloudparking.controller.dto.ParkingDTO;
import one.digitalinnovation.cloudparking.controller.mapper.ParkingMapper;
import one.digitalinnovation.cloudparking.model.Parking;
import one.digitalinnovation.cloudparking.service.ParkingService;

@RestController
@RequestMapping("/parking")
@Api(tags = "ParkingController")
public class ParkingController {
	
	private final ParkingService parkingService;
	private final ParkingMapper parkingMapper;
	
	
	public ParkingController(ParkingService parkingService, ParkingMapper parkingMapper) {
		this.parkingService = parkingService;
		this.parkingMapper = parkingMapper;
	}
	
	@GetMapping
	@ApiOperation("Find All")
	public ResponseEntity<List<ParkingDTO>> findAll() {
		List<Parking> parkingList = parkingService.findAll();
		List<ParkingDTO> result  = parkingMapper.toParkingDTOList(parkingList);		
		return ResponseEntity.ok(result);	 	
	}
	
	@GetMapping("/{id}")
	@ApiOperation("Find By ID")
	public ResponseEntity<ParkingDTO> findById(@PathVariable String id) {
		Parking parking = parkingService.findById(id);
		if (parking == null) {
			return ResponseEntity.notFound().build();
		}
		
		ParkingDTO result = parkingMapper.toParkingDTO(parking);		
		return ResponseEntity.ok(result);		
	}
	

	@DeleteMapping("/{id}")
	@ApiOperation("Delete")
	public ResponseEntity<ParkingDTO> delete(@PathVariable String id) {
		parkingService.delete(id);
		return ResponseEntity.noContent().build();		
	}

	@PostMapping
	@ApiOperation("Create")
	public ResponseEntity<ParkingDTO> create(@RequestBody ParkingCreateDTO dto) {
		var parkingCreate = parkingMapper.toParkingCreate(dto);		
		Parking parking = parkingService.create(parkingCreate);
		ParkingDTO result = parkingMapper.toParkingDTO(parking);		
		return ResponseEntity.status(HttpStatus.CREATED).body(result);		
	}

	@PutMapping("/{id}")
	@ApiOperation("Update")
	public ResponseEntity<ParkingDTO> update(@PathVariable String id, @RequestBody ParkingCreateDTO dto) {
		var parkingCreate = parkingMapper.toParkingCreate(dto);		
		Parking parking = parkingService.update(id, parkingCreate);
		ParkingDTO result = parkingMapper.toParkingDTO(parking);		
		return ResponseEntity.status(HttpStatus.OK).body(result);		
	}
	

	@PostMapping("/{id}")
	@ApiOperation("CheckOut")
	public ResponseEntity<ParkingDTO> checkOut(@PathVariable String id) {
		Parking parking = parkingService.checkOut(id);
		ParkingDTO result = parkingMapper.toParkingDTO(parking);		
		return ResponseEntity.status(HttpStatus.OK).body(result);		
	}
}


