package teste.domain.mappeamento;

import java.time.LocalDateTime;

import javax.persistence.Column;
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

import lombok.Data;
import teste.domain.curso.Curso;
import teste.domain.professor.Professor;

@Entity
@Table(name = "CURSO_PROFESSOR")
@NamedNativeQueries({
      @NamedNativeQuery(name = "CONSULTAR_POR_CURSO_PROFESSOR", query = "SELECT * FROM CURSO_PROFESSOR WHERE curso_id = :curso_id AND professor_id = :professor_id", resultClass = CursoProfessor.class)
})
@Data
public class CursoProfessor {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "curso_id", referencedColumnName = "id")
   private Curso curso;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "professor_id", referencedColumnName = "id")
   private Professor professor;

   @Column(name = "data_criacao")
   private LocalDateTime dataCriacao;

   @Column(name = "data_atualizacao")
   private LocalDateTime dataAtualizacao;

   public CursoProfessor() {
   }

   public CursoProfessor(Curso curso, Professor professor) {
      this.curso = curso;
      this.professor = professor;
   }

   public CursoProfessor(Curso curso, Professor professor, LocalDateTime dataCriacao, LocalDateTime dataAtualizacao) {
      this.curso = curso;
      this.professor = professor;
      this.dataCriacao = dataCriacao;
      this.dataAtualizacao = dataAtualizacao;
   }

}
