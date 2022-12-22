package teste.domain.aluno;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Getter;
import lombok.Setter;
import teste.domain.VOs.matricula.Matricula;
import teste.domain.VOs.matricula.MatriculaAttributeConverter;

@Entity
@Table(name = "ALUNO")
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
   @NotBlank(message = "O nome do aluno é obrigatório")
   @Size(min = 3, max = 50, message = "O nome do aluno deve possuir no mínimo 3 e no máximo 50 caracteres")
   private String nome;
   @NotBlank(message = "O CPF é obrigatório")
   @CPF(message = "CPF inválido")
   private String cpf;
   @Convert(converter = MatriculaAttributeConverter.class)
   private Matricula matricula;
   private boolean status;

   public Aluno() {
   }

   public Aluno(String nome, String cpf) {
      this.nome = nome;
      this.cpf = cpf;
   }

}
