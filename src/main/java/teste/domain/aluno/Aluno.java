package teste.domain.aluno;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import teste.domain.VOs.CPF.CPF;
import teste.domain.VOs.CPF.CPFAttributeConverter;
import teste.domain.VOs.Matricula.Matricula;
import teste.domain.VOs.Matricula.MatriculaAttributeConverter;

@Entity
@Table(name = "ALUNO")
@NamedNativeQueries({
      @NamedNativeQuery(name = "CONSULTAR_ALUNOS", query = "SELECT * FROM ALUNO", resultClass = Aluno.class),
      @NamedNativeQuery(name = "CONSULTAR_ALUNO_POR_MATRICULA", query = "SELECT * FROM ALUNO WHERE matricula = :matricula", resultClass = Aluno.class)
})
public class Aluno {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;
   private String nome;
   @Convert(converter = CPFAttributeConverter.class)
   private CPF cpf;
   @Convert(converter = MatriculaAttributeConverter.class)
   private Matricula matricula;
   private boolean status;

   public Aluno() {
   }

   public Aluno(String nome, CPF cpf) {
      this.nome = nome;
      this.cpf = cpf;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getNome() {
      return nome;
   }

   public void setNome(String nome) {
      this.nome = nome;
   }

   public CPF getCpf() {
      return cpf;
   }

   public void setCpf(CPF cpf) {
      this.cpf = cpf;
   }

   public Matricula getMatricula() {
      return matricula;
   }

   public void setMatricula(Matricula matricula) {
      this.matricula = matricula;
   }

   public boolean isStatus() {
      return status;
   }

   public void setStatus(boolean status) {
      this.status = status;
   }

}
