package teste.domain.disciplina;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import teste.domain.mappeamento.CursoDisciplina;
import teste.domain.mappeamento.ProfessorDisciplina;

@Entity
@Table(name = "DISCIPLINA")
@Getter
@Setter
public class Disciplina {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
   private String nomeDaDisciplina;

   @OneToMany(mappedBy = "disciplina")
   private List<CursoDisciplina> cursos;

   @OneToMany(mappedBy = "disciplina")
   private List<ProfessorDisciplina> professores;

   @Column(name = "data_criacao")
   private LocalDateTime dataCriacao;

   @Column(name = "data_atualizacao")
   private LocalDateTime dataAtualizacao;

   public Disciplina() {
   }

   public Disciplina(String nomeDaDisciplina) {
      this.nomeDaDisciplina = nomeDaDisciplina;
   }

}
