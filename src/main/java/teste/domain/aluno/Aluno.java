package teste.domain.aluno;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import io.smallrye.common.constraint.NotNull;
import lombok.Getter;
import lombok.Setter;
import teste.domain.VOs.matricula.Matricula;
import teste.domain.VOs.matricula.MatriculaAttributeConverter;
import teste.domain.curso.Curso;

@Entity
@Table(name = "ALUNO")
@NamedNativeQueries({
      @NamedNativeQuery(name = "CONSULTAR_ALUNOS", query = "SELECT * FROM ALUNO", resultClass = Aluno.class),
      @NamedNativeQuery(name = "CONSULTAR_ALUNO_POR_MATRICULA", query = "SELECT * FROM ALUNO WHERE matricula = :matricula", resultClass = Aluno.class),
      @NamedNativeQuery(name = "CONSULTAR_ALUNOS_POR_CURSO", query = "SELECT * FROM ALUNO WHERE curso_id = :curso_id", resultClass = Aluno.class)
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
   @NotNull
   private Matricula matricula;

   private boolean estado;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "curso_id", referencedColumnName = "id")
   private Curso cursoMatriculado;

   @Column(name = "data_criacao")
   private LocalDateTime dataCriacao;

   @Column(name = "data_atualizacao")
   private LocalDateTime dataAtualizacao;

   public Aluno() {
   }

   public Aluno(String nome, String cpf, Curso cursoMatriculado) {
      this.nome = nome;
      this.cpf = cpf;
      this.cursoMatriculado = cursoMatriculado;
   }

}
