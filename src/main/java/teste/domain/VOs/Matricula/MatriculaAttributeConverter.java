package teste.domain.VOs.Matricula;

import javax.persistence.AttributeConverter;

public class MatriculaAttributeConverter implements AttributeConverter<Matricula, String> {

   @Override
   public String convertToDatabaseColumn(Matricula attribute) {
      return attribute == null ? null : attribute.toString();
   }

   @Override
   public Matricula convertToEntityAttribute(String dbData) {
      return dbData == null ? null : new Matricula(dbData);
   }

}
