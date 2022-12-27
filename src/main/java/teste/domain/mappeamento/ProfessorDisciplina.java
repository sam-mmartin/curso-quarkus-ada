package teste.domain.mappeamento;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import teste.domain.disciplina.Disciplina;
import teste.domain.professor.Professor;

@Entity
@Table(name = "PROFESSOR_DISCIPLINA")
@Data
public class ProfessorDisciplina {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   @ManyToOne
   @JoinColumn(name = "professor_id", referencedColumnName = "id")
   private Professor professor;

   @ManyToOne
   @JoinColumn(name = "disciplina_id", referencedColumnName = "id")
   private Disciplina disciplina;

   @Column(name = "data_criacao")
   private LocalDateTime dataCriacao;

   @Column(name = "data_atualizacao")
   private LocalDateTime dataAtualizacao;

   public ProfessorDisciplina() {
   }

   public ProfessorDisciplina(Professor professor, Disciplina disciplina, LocalDateTime dataCriacao,
         LocalDateTime dataAtualizacao) {
      this.professor = professor;
      this.disciplina = disciplina;
      this.dataCriacao = dataCriacao;
      this.dataAtualizacao = dataAtualizacao;
   }

}
