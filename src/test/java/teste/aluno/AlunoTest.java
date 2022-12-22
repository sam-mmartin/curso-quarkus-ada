package teste.aluno;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class AlunoTest {

   @Test
   public void testGetRequest() {
      given().when().get("/alunos").then().statusCode(200);
   }

   @Test
   public void testGetRequestById() {
      given().when().get("/alunos/1").then().statusCode(200);
   }

   @Test
   public void testGetByMatricula() {
      given().when().get("/alunos/matricula/1010-100").then().statusCode(200);
   }

   @Test
   public void testPostRequest() {
      String requestBody = "{\"nome\": \"Laine Araujo\", \"cpf\": \"100.200.300-40\"}";

      given()
            .header("Content-type", "application/json")
            .and().body(requestBody)
            .when().post("/alunos")
            .then().statusCode(201);
   }

   @Test
   public void testPutRequest() {
      String requestBody = "{\"nome\": \"Juliana Sintra\", \"cpf\": \"001.002.003-04\"}";

      given()
            .header("Content-type", "application/json")
            .and()
            .body(requestBody).when()
            .put("/alunos/atualizar-cadastro/1010-100")
            .then().statusCode(200);
   }

   @Test
   public void testCancelarMatricula() {
      given().when().put("/alunos/cancelar-matricula/2020-200").then().statusCode(200);
   }

   @Test
   public void testRematriculaAluno() {
      given().when().put("/alunos/rematricular/3030-300").then().statusCode(200);
   }
}
