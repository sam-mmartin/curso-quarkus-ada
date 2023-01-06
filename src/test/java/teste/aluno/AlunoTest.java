package teste.aluno;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;

@QuarkusTest
public class AlunoTest {

      private static final int ID = 1;
      private static final int size = 3;
      private static final String[][] parameters = {
                  { "Unknow 1", "546.212.310-81", "1010-100", "Ativo" },
                  { "Unknow 2", "546.212.310-81", "1010-200", "Ativo" },
                  { "Unknow 3", "546.212.310-81", "1010-300", "Inativo" }
      };

      @Test
      void testListarTodosAlunos() {
            String[] keys = { "nome", "cpf", "matricula" };
            int[] cols = { 0, 1, 2 };

            Response response = given().when()
                        .get("/alunos")
                        .then().extract().response();

            assertEquals(200, response.statusCode());
            assertEquals(size, response.jsonPath().getList("id").size());
            executeAssertionsList(cols, response.jsonPath().get(), keys);
      }

      @Test
      void testBuscarAlunoPorId() {
            String[] keys = { "nome", "cpf", "matricula" };
            int[] cols = { 0, 1, 2 };

            var response = given().when()
                        .get("/alunos/" + ID)
                        .then().extract().response();

            assertEquals(200, response.statusCode());
            executeAssertions(0, cols, response.jsonPath().get(), keys);
      }

      @Test
      void testBuscarPorIdNotFound() {
            given().when()
                        .get("/alunos/4")
                        .then().statusCode(404);
      }

      @Test
      public void testBuscarAlunoPorMatricula() {
            String[] keys = { "nome", "cpf", "matricula" };
            int[] cols = { 0, 1, 2 };

            var response = given().when()
                        .get("/alunos/matricula/" + parameters[0][2])
                        .then().extract().response();

            assertEquals(200, response.statusCode());
            executeAssertions(0, cols, response.jsonPath().get(), keys);
      }

      @Test
      void testBuscarPorMatriculaNotFound() {
            given().when()
                        .get("/alunos/matricula/1000-000")
                        .then().statusCode(404);
      }

      @Test
      void testListarTodosAlunosDeUmCurso() {
            String[] keys = { "nome", "matricula", "estado" };
            int[] cols = { 0, 2, 3 };

            var response = given().when()
                        .get("/alunos/listar-por-curso/" + ID)
                        .then().extract().response();

            assertEquals(200, response.statusCode());
            executeAssertionsList(cols, response.jsonPath().get(), keys);
      }

      @Test
      void testMatricularAluno() {
            int id = 4;
            String requestBody = "{"
                        + "\"nome\": \"Laine Araujo\","
                        + " \"cpf\": \"830.173.730-10\","
                        + " \"curso\": \"ANÁLISES QUÍMICA\""
                        + "}";

            var response = given()
                        .header("Content-type", "application/json")
                        .and().body(requestBody)
                        .when().post("/alunos")
                        .then().extract().response();

            assertEquals(201, response.statusCode());

            response = given().when()
                        .get("/alunos/" + id)
                        .then().extract().response();

            if (response.statusCode() != 200) {
                  id = 5;
                  response = given().when()
                              .get("/alunos/" + id)
                              .then().extract().response();

            }

            assertEquals(200, response.statusCode());
            assertEquals("Laine Araujo", response.jsonPath().getString("nome"));
            assertEquals("830.173.730-10", response.jsonPath().getString("cpf"));
            assertEquals("ANÁLISES QUÍMICA", response.jsonPath().getString("curso"));
            assertEquals("Ativo", response.jsonPath().getString("estado"));

            // Apagar registro criado para não interferir nos outros testes
            response = given().when()
                        .delete("/alunos/" + id)
                        .then().extract().response();

            assertEquals(204, response.statusCode());
      }

      @ParameterizedTest
      @MethodSource("invalidFieldsCreate")
      void testPostResquestInvalidFields(
                  final String nome, final String cpf,
                  final String curso, final String errorMessage) {
            String requestBody = "{"
                        + "\"nome\": \"" + nome + "\","
                        + " \"cpf\": \"" + cpf + "\","
                        + " \"curso\": \"" + curso + "\""
                        + "}";

            var response = given()
                        .header("Content-type", "application/json")
                        .and().body(requestBody)
                        .when().post("/alunos")
                        .then().extract().response();

            assertEquals(400, response.statusCode());
            assertEquals(errorMessage, response.jsonPath().getString("message"));
      }

      @Test
      void testAtualizarCadastroAluno() {
            String requestBody = "{\"nome\": \"Juliana Sintra\", \"cpf\": \"830.173.730-10\"}";

            var response = given()
                        .header("Content-type", "application/json")
                        .and().body(requestBody).when()
                        .put("/alunos/1010-100")
                        .then().extract().response();

            assertEquals(200, response.statusCode());
            assertEquals("Cadastro atualizado com sucesso.", response.jsonPath().getString(("texto")));

            response = given().when()
                        .get("/alunos/" + ID)
                        .then().extract().response();

            assertEquals(200, response.statusCode());
            assertEquals("Juliana Sintra", response.jsonPath().getString("nome"));
            assertEquals("830.173.730-10", response.jsonPath().getString("cpf"));

            // Desfaz as alterações para não interferir nos outros testes
            requestBody = "{\"nome\": \"Unknow 1\", \"cpf\": \"546.212.310-81\"}";
            response = given()
                        .header("Content-type", "application/json")
                        .and().body(requestBody).when()
                        .put("/alunos/1010-100")
                        .then().extract().response();

            assertEquals(200, response.statusCode());
            assertEquals("Cadastro atualizado com sucesso.", response.jsonPath().getString(("texto")));
      }

      @ParameterizedTest
      @MethodSource("invalidFieldsUpdate")
      void testPutRequestInvalidFields(final String nome, final String cpf, final String errorMessage) {
            String requestBody = "{\"nome\": \"" + nome + "\", \"cpf\": \"" + cpf + "\"}";

            var response = given()
                        .header("Content-type", "application/json")
                        .and().body(requestBody).when()
                        .put("/alunos/1010-100")
                        .then().extract().response();

            assertEquals(400, response.statusCode());
            assertEquals(errorMessage, response.jsonPath().getString("message"));
      }

      @Test
      public void testRematriculaAluno() {
            given().when()
                        .put("/alunos/rematricular/1010-300")
                        .then().statusCode(200);

            // desfaz as alterações
            given().when()
                        .put("/alunos/cancelar-matricula/1010-300")
                        .then().statusCode(200);
      }

      @Test
      public void testCancelarMatricula() {
            given().when()
                        .put("/alunos/cancelar-matricula/1010-200")
                        .then().statusCode(200);

            // desfaz as alterações
            given().when()
                        .put("/alunos/rematricular/1010-200")
                        .then().statusCode(200);
      }

      private void executeAssertionsList(int[] cols, List<Map<String, String>> response, String[] keys) {
            int i = 0;

            for (var res : response) {
                  executeAssertions(i, cols, res, keys);
                  i++;
            }
      }

      private void executeAssertions(int row, int[] col, Map<String, String> response, String[] keys) {
            assertEquals(parameters[row][col[0]], response.get(keys[0]));
            assertEquals(parameters[row][col[1]], response.get(keys[1]));
            assertEquals(parameters[row][col[2]], response.get(keys[2]));
      }

      static Stream<Arguments> invalidFieldsCreate() {
            return Stream.of(
                        arguments("", "830.173.730-10", "ANÁLISES QUÍMICA", "É necessário informar o nome"),
                        arguments("Laine Araujo", "", "ANÁLISES QUÍMICA", "É necessário informar o CPF"),
                        arguments("Laine Araujo", "830.173.730-10", "", "É necessário informar o nome do curso"));
      }

      static Stream<Arguments> invalidFieldsUpdate() {
            return Stream.of(
                        arguments("", "830.173.730-10", "É necessário informar o nome"),
                        arguments("Laine Araujo", "", "É necessário informar o CPF"));
      }

}
