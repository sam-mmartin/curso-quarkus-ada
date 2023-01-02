package teste.aluno;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import com.google.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;
import teste.application.services.AlunoService;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AlunoServiceTest {

   @Inject
   AlunoService service;

}
