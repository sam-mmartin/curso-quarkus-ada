package teste.vos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import teste.domain.VOs.matricula.Matricula;
import teste.domain.VOs.matricula.MatriculaAttributeConverter;

public class MatriculaAttributeConverterTest {

   private static final MatriculaAttributeConverter mac = new MatriculaAttributeConverter();
   private static final Matricula MATRICULA = new Matricula("NUMERO");

   @Test
   void testConvertToDatabaseColumn() {
      var res = mac.convertToDatabaseColumn(new Matricula("NUMERO"));

      assertEquals("NUMERO", res);
   }

   @Test
   void testConvertToDatabaseColumnNull() {
      var res = mac.convertToDatabaseColumn(null);

      assertNull(res);
   }

   @Test
   void testConvertToEntityAttribute() {
      var res = mac.convertToEntityAttribute("NUMERO");
      assertEquals("NUMERO", res.getNumero());
      assertEquals(MATRICULA.hashCode(), res.hashCode());
   }

   @Test
   void testConvertToEntityAttributeNull() {
      var res = mac.convertToEntityAttribute(null);
      assertNull(res);
   }
}
