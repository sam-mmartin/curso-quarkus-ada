package teste.domain.curso;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import teste.domain.aluno.Aluno;
import teste.domain.mappeamento.CursoDisciplina;
import teste.domain.mappeamento.CursoProfessor;

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

      @OneToMany(mappedBy = "curso")
      private List<CursoDisciplina> disciplinasDoCurso;

      @OneToMany(mappedBy = "curso")
      private List<CursoProfessor> professoresLotados;

      @OneToMany(mappedBy = "cursoMatriculado", fetch = FetchType.LAZY)
      private List<Aluno> alunosMatriculados;

      @Column(name = "data_criacao")
      private LocalDateTime dataCriacao;

      @Column(name = "data_atualizacao")
      private LocalDateTime dataAtualizacao;

      private String observacao;

      public Curso() {
      }

      public Curso(String nomeDoCurso) {
            this.nomeDoCurso = nomeDoCurso;
      }

}
