package teste.aluno;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AlunoTest {

   @Test
   @Order(1)
   public void testPostRequest() {
      String requestBody = "{\"nome\": \"Juliana Cintra\", \"cpf\": \"001.002.003-04\"}";

      Response response = given()
            .header("Content-type", "application/json")
            .and().body(requestBody)
            .when().post("/alunos")
            .then().extract().response();

      Assertions.assertTrue(response.statusCode() == 201);
   }

   @Test
   @Order(2)
   public void testGetRequest() {
      given().when().get("/alunos").then().statusCode(200);
   }

   @Test
   @Order(3)
   public void testGetRequestById() {
      given().when().get("/alunos/1").then().statusCode(200);
   }

   @Test
   @Order(4)
   public void testPutRequest() {

      Response responseGET = given().when().get("/alunos/1").then().extract().response();
      String matricula = responseGET.jsonPath().getString("matricula");
      String requestBody = "{\"nome\": \"Juliana Sintra\", \"cpf\": \"001.002.003-04\"}";

      Response response = given()
            .header("Content-type", "application/json")
            .and()
            .body(requestBody).when()
            .put("/alunos/atualizar-cadastro/" + matricula)
            .then().extract().response();

      Assertions.assertTrue(response.statusCode() == 200);
      Assertions.assertEquals("Cadastro atualizado com sucesso.", response.jsonPath().getString("texto"));
   }

   @Test
   @Order(5)
   public void testGetByMatricula() {

      Response responseGET = given().when().get("/alunos/1").then().extract().response();
      String matricula = responseGET.jsonPath().getString("matricula");

      given().when().get("/alunos/matricula/" + matricula).then().statusCode(200);
   }

   @Test
   @Order(6)
   public void testCancelarMatricula() {
      Response responseGET = given().when().get("/alunos/1").then().extract().response();
      String matricula = responseGET.jsonPath().getString("matricula");

      given().when().put("/alunos/cancelar-matricula/" + matricula).then().statusCode(200);
   }

   @Test
   @Order(7)
   public void testRematriculaAluno() {

      Response responseGET = given().when().get("/alunos/1").then().extract().response();
      String matricula = responseGET.jsonPath().getString("matricula");

      given().when().put("/alunos/rematricular/" + matricula).then().statusCode(200);
   }
}
