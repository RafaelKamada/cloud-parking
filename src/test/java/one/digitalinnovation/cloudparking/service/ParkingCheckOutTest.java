package one.digitalinnovation.cloudparking.service;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import io.restassured.RestAssured;
import one.digitalinnovation.cloudparking.controller.AbstractContainerBase;
import one.digitalinnovation.cloudparking.controller.dto.ParkingCreateDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParkingCheckOutTest extends AbstractContainerBase{
	
	@LocalServerPort
	private int randomPort; 
	
	private String idPost;
	
	@BeforeEach
	public void setUpTest() {
		System.out.println("random: " + randomPort);
		RestAssured.port = randomPort;
		
		
		var createDTO = new ParkingCreateDTO();
		createDTO.setColor("AMARELO");
		createDTO.setLicense("WRT-5555");
		createDTO.setModel("Brasilia");
		createDTO.setState("SP");
		
		idPost = RestAssured.given()
					.when()
					.auth().basic("user", "Dio@123456")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.body(createDTO)
					.post("/parking")
					.then()
					.extract().path("id");
		
		System.out.println("----------------ID inserido------------");
		System.out.println(idPost);
	}
	
	@Test
	void getBill() {
		RestAssured.given()
		.when()
		.auth().basic("user", "Dio@123456")
		.post("/parking/" + idPost)
		.then()
		//.extract().response().body().prettyPrint();		
		.statusCode(HttpStatus.OK.value())
		.body("bill", Matchers.equalTo(5.0f));
	}
}
