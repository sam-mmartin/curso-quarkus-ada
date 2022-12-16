package teste.domain.aluno;

import teste.domain.VOs.CPF;
import teste.domain.VOs.Matricula;

public class Aluno {

   private int id;
   private Matricula matricula;
   private String nome;
   private CPF cpf;
   private boolean status;

   public Aluno() {
   }

   public Aluno(int id, Matricula matricula, String nome, CPF cpf, boolean status) {
      this.id = id;
      this.matricula = matricula;
      this.nome = nome;
      this.cpf = cpf;
      this.status = status;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public Matricula getMatricula() {
      return matricula;
   }

   public void setMatricula(Matricula matricula) {
      this.matricula = matricula;
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

   public boolean isStatus() {
      return status;
   }

   public void setStatus(boolean status) {
      this.status = status;
   }

}
