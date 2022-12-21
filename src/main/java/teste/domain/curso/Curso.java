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
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import teste.domain.disciplina.Disciplina;

@Entity
@Table(name = "CURSO")
@Getter
@Setter
public class Curso {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
   private String nomeDoCurso;
   @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
   @JoinTable(name = "CURSO_DISCIPLINA", joinColumns = { @JoinColumn(name = "curso_id") }, inverseJoinColumns = {
         @JoinColumn(name = "disciplina_id") })
   private List<Disciplina> disciplinasDoCurso;

   public Curso() {
   }

   public Curso(String nomeDoCurso) {
      this.nomeDoCurso = nomeDoCurso;
   }

}
