package teste.domain.disciplina;

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
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import teste.domain.curso.Curso;
import teste.domain.professor.Professor;

@Entity
@Table(name = "DISCIPLINA")
@Getter
@Setter
public class Disciplina {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
   private String nomeDaDisciplina;

   @ManyToMany(mappedBy = "disciplinasDoCurso", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
   private List<Curso> cursos;

   @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
   @JoinTable(name = "DISCIPLINA_PROFESSOR", joinColumns = {
         @JoinColumn(name = "disciplina_id") }, inverseJoinColumns = { @JoinColumn(name = "professor_id") })
   private List<Professor> professores;

   public Disciplina() {
   }

   public Disciplina(String nomeDaDisciplina) {
      this.nomeDaDisciplina = nomeDaDisciplina;
   }

}
