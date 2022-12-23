package teste.domain.curso;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import teste.domain.aluno.Aluno;
import teste.domain.disciplina.Disciplina;
import teste.domain.professor.Professor;

@Entity
@Table(name = "CURSO")
@Getter
@Setter
public class Curso {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;

   @NotBlank(message = "O nome do curso é obrigatório")
   private String nomeDoCurso;

   @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
   @JoinTable(name = "CURSO_DISCIPLINA", joinColumns = { @JoinColumn(name = "curso_id") }, inverseJoinColumns = {
         @JoinColumn(name = "disciplina_id") })
   private List<Disciplina> disciplinasDoCurso;

   @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
   @JoinTable(name = "CURSO_PROFESSOR", joinColumns = { @JoinColumn(name = "curso_id") }, inverseJoinColumns = {
         @JoinColumn(name = "professor_id") })
   private List<Professor> professoresLotados;

   @OneToMany(mappedBy = "cursoMatriculado", fetch = FetchType.LAZY)
   private List<Aluno> alunosMatriculados;

   public Curso() {
   }

   public Curso(String nomeDoCurso) {
      this.nomeDoCurso = nomeDoCurso;
   }

}
