package teste.professor;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class ProfessorTest {

   @Test
   public void testGetRequest() {
      given().when().get("/professores").then().statusCode(200);
   }

   @Test
   public void testGetRequestById() {
      given().when().get("/professores/1").then().statusCode(200);
   }

   @Test
   public void testGetRequestByMatricula() {
      given().when().get("/professores/matricula/1000-000").then().statusCode(200);
   }

   @Test
   public void testPostRequest() {
      String requestBody = "{\"nome\": \"Andrew Mackbow\", \"cpf\": \"999.999.999-99\"}";

      given()
            .header("Content-type", "application/json")
            .and().body(requestBody).when()
            .post("/professores").then()
            .statusCode(201);
   }

   @Test
   public void testPutRequest() {
      String requestBody = "{\"nome\": \"Sam M. Martin\", \"cpf\": \"004.005.003-38\"}";

      given()
            .header("Content-type", "application/json")
            .and().body(requestBody).when()
            .put("/professores/1000-000").then()
            .statusCode(200);
   }
}
