// package teste.curso;

// import org.junit.jupiter.api.Test;
// import io.quarkus.test.junit.QuarkusTest;
// import static io.restassured.RestAssured.given;

// @QuarkusTest
// public class CursoTest {

// @Test
// public void testGetRequest() {
// given().when().get("/cursos").then().statusCode(200);
// }

// @Test
// public void testGetRequestById() {
// given().when().get("/cursos/1").then().statusCode(200);
// }

// @Test
// public void testGetRequestByNomeDoCurso() {
// given().when().get("/cursos/buscar-por-nome/ANÁLISES
// QUÍMICA").then().statusCode(200);
// }

// @Test
// public void testPostRequest() {
// String requestBody = "{\"nomeDoCurso\": \"ALIMENTOS\"}";

// given()
// .header("Content-type", "application/json")
// .and().body(requestBody)
// .when().post("/cursos")
// .then().statusCode(201);
// }

// @Test
// public void testPutRequest() {
// String requestBody = "{\"nomeDoCurso\": \"TÉCNICO EM BIOTECNOLOGIA\"}";

// given()
// .header("Content-type", "application/json")
// .and().body(requestBody).when()
// .put("/cursos/5").then()
// .statusCode(200);
// }

// @Test
// public void testPutDisciplinaOnCurso() {
// String requestBody = "{\"nomeDaDisciplina\": \"QUÍMICA\"}";

// given()
// .header("Content-type", "application/json")
// .and().body(requestBody).when()
// .put("/cursos/adicionar-disciplina/1").then()
// .statusCode(200);
// }

// @Test
// public void testGetRequestGradeCurricular() {
// given().when().get("/cursos/grade-curricular/1").then().statusCode(200);
// }

// @Test
// public void testGetRequestAllCursosAndDisciplinas() {
// given().when().get("/cursos/cursos-disciplinas").then().statusCode(200);
// }

// @Test
// public void testDeleteRequest() {
// given().when().delete("/cursos/1").then().statusCode(204);
// }
// }
