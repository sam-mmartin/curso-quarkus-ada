package teste.domain.professor;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import teste.domain.VOs.CPF.CPF;
import teste.domain.VOs.CPF.CPFAttributeConverter;
import teste.domain.VOs.Matricula.Matricula;
import teste.domain.VOs.Matricula.MatriculaAttributeConverter;

@Entity
@Table(name = "PROFESSOR")
@NamedNativeQueries({
      @NamedNativeQuery(name = "CONSULTAR_PROFESSORES", query = "SELECT * FROM PROFESSOR", resultClass = Professor.class),
      @NamedNativeQuery(name = "CONSULTAR_PROFESSOR_POR_MATRICULA", query = "SELECT * FROM PROFESSOR WHERE matricula = :matricula", resultClass = Professor.class)
})
@Getter
@Setter
public class Professor {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;
   private String nome;
   @Convert(converter = CPFAttributeConverter.class)
   private CPF cpf;
   @Convert(converter = MatriculaAttributeConverter.class)
   private Matricula matricula;
   private boolean status;

   public Professor() {
   }

   public Professor(String nome, CPF cpf) {
      this.nome = nome;
      this.cpf = cpf;
   }

}
