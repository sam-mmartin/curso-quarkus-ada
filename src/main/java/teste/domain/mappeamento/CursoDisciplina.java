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
import teste.domain.curso.Curso;
import teste.domain.disciplina.Disciplina;

@Entity
@Table(name = "CURSO_DISCIPLINA")
@Data
public class CursoDisciplina {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   @ManyToOne
   @JoinColumn(name = "curso_id", referencedColumnName = "id")
   private Curso curso;

   @ManyToOne
   @JoinColumn(name = "disciplina_id", referencedColumnName = "id")
   private Disciplina disciplina;

   @Column(name = "data_criacao")
   private LocalDateTime dataCriacao;

   @Column(name = "data_atualizacao")
   private LocalDateTime dataAtualizacao;

   public CursoDisciplina() {
   }

   public CursoDisciplina(Curso curso, Disciplina disciplina, LocalDateTime dataCriacao,
         LocalDateTime dataAtualizacao) {
      this.curso = curso;
      this.disciplina = disciplina;
      this.dataCriacao = dataCriacao;
      this.dataAtualizacao = dataAtualizacao;
   }

}
