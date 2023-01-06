package teste.domain.professor;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import teste.domain.VOs.matricula.Matricula;
import teste.domain.VOs.matricula.MatriculaAttributeConverter;
import teste.domain.mappeamento.CursoProfessor;
import teste.domain.mappeamento.ProfessorDisciplina;

@Entity
@Table(name = "PROFESSOR")
@NamedNativeQueries({
      @NamedNativeQuery(name = "CONSULTAR_PROFESSORES", query = "SELECT * FROM PROFESSOR", resultClass = Professor.class),
      @NamedNativeQuery(name = "CONSULTAR_PROFESSOR_POR_MATRICULA", query = "SELECT * FROM PROFESSOR WHERE matricula = :matricula", resultClass = Professor.class)
})
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Professor {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   @NotBlank(message = "O nome é obrigatório")
   @Size(min = 3, max = 50, message = "O nome do professor deve possuir no mínimo 3 e no máximo 50 caracteres")
   private String nome;

   @CPF(message = "CPF inválido")
   private String cpf;

   @Convert(converter = MatriculaAttributeConverter.class)
   private Matricula matricula;

   private boolean estado;

   @OneToMany(mappedBy = "professor")
   private List<CursoProfessor> cursosLecionados;

   @OneToMany(mappedBy = "professor")
   private List<ProfessorDisciplina> disciplinasLecionadas;

   @Column(name = "data_criacao")
   private LocalDateTime dataCriacao;

   @Column(name = "data_atualizacao")
   private LocalDateTime dataAtualizacao;

   private String observacao;

   public Professor(String nome, String cpf) {
      this.nome = nome;
      this.cpf = cpf;
   }

   public Professor(String nome, String cpf, Matricula matricula, boolean estado, LocalDateTime dataCriacao,
         LocalDateTime dataAtualizacao, String observacao) {
      this.nome = nome;
      this.cpf = cpf;
      this.matricula = matricula;
      this.estado = estado;
      this.dataCriacao = dataCriacao;
      this.dataAtualizacao = dataAtualizacao;
      this.observacao = observacao;
   }

}
