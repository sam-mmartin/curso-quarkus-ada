package teste.aluno;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;

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
      String requestBody = "{"
            + "\"nome\": \"Laine Araujo\","
            + " \"cpf\": \"830.173.730-10\","
            + " \"curso\": \"ANÁLISES QUÍMICA\""
            + "}";

      given()
            .header("Content-type", "application/json")
            .and().body(requestBody)
            .when().post("/alunos")
            .then().statusCode(201);
   }

   @Test
   public void testPutRequest() {
      String requestBody = "{\"nome\": \"Juliana Sintra\", \"cpf\": \"546.212.310-81\"}";

      given()
            .header("Content-type", "application/json")
            .and()
            .body(requestBody).when()
            .put("/alunos/1010-100")
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

   @Test
   public void testPostResquestAlunoSemNome() {
      String requestBody = "{"
            + "\"nome\": \"\","
            + " \"cpf\": \"001.002.003-04\","
            + " \"curso\": \"ANÁLISES QUÍMICA\""
            + "}";

      given()
            .header("Content-type", "application/json")
            .and().body(requestBody)
            .when().post("/alunos")
            .then().statusCode(400);
   }

   @Test
   public void testPostResquestAlunoSemCPF() {
      String requestBody = "{"
            + "\"nome\": \"Mallu Estácio\","
            + " \"cpf\": \"\","
            + " \"curso\": \"ANÁLISES QUÍMICA\""
            + "}";

      given()
            .header("Content-type", "application/json")
            .and().body(requestBody)
            .when().post("/alunos")
            .then().statusCode(400);
   }

   @Test
   public void testPutResquestAlunoSemNome() {
      String requestBody = "{\"nome\": \"\", \"cpf\": \"100.200.300-40\"}";

      given()
            .header("Content-type", "application/json")
            .and().body(requestBody)
            .when().put("/alunos/2")
            .then().statusCode(400);
   }

   @Test
   public void testPutResquestAlunoSemCPF() {
      String requestBody = "{\"nome\": \"Mallu Estácio\", \"cpf\": \"\"}";

      given()
            .header("Content-type", "application/json")
            .and().body(requestBody)
            .when().put("/alunos/3")
            .then().statusCode(400);
   }

   @Test
   public void testPostResquestAlunoNomeQuantidadeCaracteresInsuficiente() {
      String requestBody = "{"
            + "\"nome\": \"La\","
            + " \"cpf\": \"546.212.310-81\","
            + " \"curso\": \"ANÁLISES QUÍMICA\""
            + "}";

      Response response = given()
            .header("Content-type", "application/json")
            .and().body(requestBody)
            .when().post("/alunos")
            .then().extract().response();

      Assertions.assertEquals("O nome do aluno deve possuir no mínimo 3 e no máximo 50 caracteres",
            response.jsonPath().getString("texto"));
   }

   @Test
   public void testPostResquestAlunoCPFInvalido() {
      String requestBody = "{"
            + "\"nome\": \"Laine Araujo\","
            + " \"cpf\": \"001.002.003.04\","
            + " \"curso\": \"ANÁLISES QUÍMICA\""
            + "}";
      ;

      Response response = given()
            .header("Content-type", "application/json")
            .and().body(requestBody)
            .when().post("/alunos")
            .then().extract().response();

      Assertions.assertEquals("CPF inválido",
            response.jsonPath().getString("texto"));
   }
}
