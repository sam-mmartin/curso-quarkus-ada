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
      String requestBody = "{"
            + "\"nome\": \"Andrew Mackbow\","
            + "\"cpf\": \"803.927.250-53\""
            + "}";

      given()
            .header("Content-type", "application/json")
            .and().body(requestBody).when()
            .post("/professores").then()
            .statusCode(201);
   }

   @Test
   public void testPutRequest() {
      String requestBody = "{\"nome\": \"Sam M. Martin\", \"cpf\": \"803.927.250-53\"}";

      given()
            .header("Content-type", "application/json")
            .and().body(requestBody).when()
            .put("/professores/1000-000").then()
            .statusCode(200);
   }

   @Test
   public void testPutRequestLecionarDisciplina() {
      String requestBody = "{\"nomeDaDisciplina\": \"MATEMÁTICA\"}";

      given()
            .header("Content-type", "application/json")
            .and().body(requestBody).when()
            .put("/professores/lecionar-disciplina/1000-000").then()
            .statusCode(200);
   }

   @Test
   public void testPutRequestPararDeLecionarDisciplina() {
      String requestBody = "{\"nomeDaDisciplina\": \"QUÍMICA\"}";

      given()
            .header("Content-type", "application/json")
            .and().body(requestBody).when()
            .put("/professores/remover-disciplina/1000-000").then()
            .statusCode(200);
   }

   @Test
   public void testPutRequestAdicionarCurso() {
      String requestBody = "{\"nomeDoCurso\": \"ANÁLISES QUÍMICA\"}";

      given()
            .header("Content-type", "application/json")
            .and().body(requestBody).when()
            .put("/professores/adicionar-curso/1000-000").then()
            .statusCode(200);
   }

   @Test
   public void testPutRequestRemoverCurso() {
      String requestBody = "{\"nomeDoCurso\": \"TÉCNICO EM INFORMÁTICA\"}";

      given()
            .header("Content-type", "application/json")
            .and().body(requestBody).when()
            .put("/professores/remover-curso/1000-000").then()
            .statusCode(200);
   }
}
