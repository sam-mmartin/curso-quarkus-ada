package teste.curso;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CursoTest {

   @Test
   @Order(1)
   public void testPostRequest() {
      String requestBody = "{\"nomeDoCurso\": \"Alimentos\"}";

      Response response = given()
            .header("Content-type", "application/json")
            .and().body(requestBody)
            .when().post("/cursos")
            .then().extract().response();

      Assertions.assertTrue(response.statusCode() == 201);
   }

   @Test
   @Order(2)
   public void testGetRequest() {
      given().when().get("/cursos").then().statusCode(200);
   }

   @Test
   @Order(3)
   public void testGetRequestById() {
      given().when().get("/cursos/1").then().statusCode(200);
   }

   @Test
   @Order(4)
   public void testGetRequestByNomeDoCurso() {
      given().when().get("/cursos/buscar-por-nome/Alimentos").then().statusCode(200);
   }

   @Test
   @Order(5)
   public void testPutRequest() {
      String requestBody = "{\"nomeDoCurso\": \"Técnicos em Alimentos\"}";

      given()
            .header("Content-type", "application/json")
            .and().body(requestBody).when()
            .put("/cursos/1").then()
            .statusCode(200);
   }
}
