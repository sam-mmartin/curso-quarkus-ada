package teste.application.dto;

import teste.domain.VOs.CPF.CPF;
import teste.domain.aluno.Aluno;

public class AlunoDTO {

   private int id;
   private String nome;
   private String cpf;
   private String matricula;
   private boolean status;

   public AlunoDTO(int id, String nome, String cpf, boolean status) {
      this.id = id;
      this.nome = nome;
      this.cpf = cpf;
      this.status = status;
   }

   public Aluno criarAluno() {
      return new Aluno(id, nome, new CPF(cpf), status);
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

   public String getCpf() {
      return cpf;
   }

   public void setCpf(String cpf) {
      this.cpf = cpf;
   }

   public boolean isStatus() {
      return status;
   }

   public void setStatus(boolean status) {
      this.status = status;
   }

   public String getMatricula() {
      return matricula;
   }

   public void setMatricula(String matricula) {
      this.matricula = matricula;
   }

}
