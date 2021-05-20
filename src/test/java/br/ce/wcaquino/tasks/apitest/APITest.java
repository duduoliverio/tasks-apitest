package br.ce.wcaquino.tasks.apitest;

import static io.restassured.RestAssured.given;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	@Test
	public void deveRetornarTasks() {
		given()
			.log().all()
		.when()
			.get("/todo")
		.then()
			.log().all()
		;
	}
	
	@Test
	public void deveAdicionarTaskComSucesso() {
		given()
			.body("{\"task\": \"Teste via API\", \"dueDate\": \"2022-05-20\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(201)
		;
	}

	@Test
	public void naoDeveAdicionarTaskInvalida() {
		given()
			.body("{\"task\": \"Teste via API\", \"dueDate\": \"2010-05-19\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"))
		;
	}
}
