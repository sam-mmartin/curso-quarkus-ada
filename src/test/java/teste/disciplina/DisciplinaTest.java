package teste.disciplina;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class DisciplinaTest {

   @Test
   public void testGetRequest() {
      given().when().get("/disciplinas").then().statusCode(200);
   }

   @Test
   public void testGetRequestById() {
      given().when().get("/disciplinas/1").then().statusCode(200);
   }

   @Test
   public void testPostRequest() {
      String requestBody = "{\"nomeDaDisciplina\": \"Biologia\"}";

      given()
            .header("Content-type", "application/json")
            .and().body(requestBody).when()
            .post("/disciplinas").then()
            .statusCode(201);
   }

   @Test
   public void testPutRequest() {
      String requestBody = "{\"nomeDaDisciplina\": \"Filosofia\"}";

      given()
            .header("Content-type", "application/json")
            .and().body(requestBody).when()
            .put("/disciplinas/5").then()
            .statusCode(200);
   }

   @Test
   public void testDeleteRequest() {
      given().when().delete("/disciplinas/5").then().statusCode(204);
   }

   @Test
   public void testGetRequestByName() {
      given().when().get("/disciplinas/buscar-por-nome/MATEMÁTICA").then().statusCode(200);
   }
}
