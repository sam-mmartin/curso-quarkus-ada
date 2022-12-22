package teste.domain.VOs.matricula;

import javax.persistence.AttributeConverter;

public class MatriculaAttributeConverter implements AttributeConverter<Matricula, String> {

   @Override
   public String convertToDatabaseColumn(Matricula attribute) {
      return attribute == null ? null : attribute.getNumero();
   }

   @Override
   public Matricula convertToEntityAttribute(String dbData) {
      return dbData == null ? null : new Matricula(dbData);
   }

}
