package teste.domain.aluno;

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
@Table(name = "ALUNO", schema = "vm_mysql_db")
@NamedNativeQueries({
      @NamedNativeQuery(name = "CONSULTAR_ALUNOS", query = "SELECT * FROM ALUNO", resultClass = Aluno.class),
      @NamedNativeQuery(name = "CONSULTAR_ALUNO_POR_MATRICULA", query = "SELECT * FROM ALUNO WHERE matricula = :matricula", resultClass = Aluno.class)
})
@Getter
@Setter
public class Aluno {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;
   private String nome;
   @Convert(converter = CPFAttributeConverter.class)
   private CPF cpf;
   @Convert(converter = MatriculaAttributeConverter.class)
   private Matricula matricula;
   private boolean status;

   public Aluno() {
   }

   public Aluno(String nome, CPF cpf) {
      this.nome = nome;
      this.cpf = cpf;
   }

}
