package teste.domain.professor;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Getter;
import lombok.Setter;
import teste.domain.VOs.matricula.Matricula;
import teste.domain.VOs.matricula.MatriculaAttributeConverter;
import teste.domain.curso.Curso;
import teste.domain.disciplina.Disciplina;

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

   @NotBlank(message = "O nome é obrigatório")
   private String nome;

   @NotBlank(message = "O CPF é obrigatório")
   @CPF(message = "CPF inválido")
   private String cpf;

   @Convert(converter = MatriculaAttributeConverter.class)
   private Matricula matricula;

   private boolean estado;

   @ManyToMany(mappedBy = "professoresLotados", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
   private List<Curso> cursosLecionados;

   @ManyToMany(mappedBy = "professores", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
   private List<Disciplina> disciplinasLecionadas;

   public Professor() {
   }

   public Professor(String nome, String cpf) {
      this.nome = nome;
      this.cpf = cpf;
   }
}
