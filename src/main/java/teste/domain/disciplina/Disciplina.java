package teste.domain.disciplina;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import teste.domain.curso.Curso;

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

   public Disciplina() {
   }

   public Disciplina(String nomeDaDisciplina) {
      this.nomeDaDisciplina = nomeDaDisciplina;
   }

}
