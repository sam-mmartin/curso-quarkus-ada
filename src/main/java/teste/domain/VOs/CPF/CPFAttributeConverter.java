package teste.domain.VOs.CPF;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

@Convert
public class CPFAttributeConverter implements AttributeConverter<CPF, String> {

   @Override
   public String convertToDatabaseColumn(CPF attribute) {
      return attribute == null ? null : attribute.getNumero();
   }

   @Override
   public CPF convertToEntityAttribute(String dbData) {
      return dbData == null ? null : new CPF(dbData);
   }

}
