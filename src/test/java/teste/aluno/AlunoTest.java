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
      String requestBody = "{\"nome\": \"Juliana Minha Gata\", \"cpf\": \"001.002.003-04\"}";

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
}
