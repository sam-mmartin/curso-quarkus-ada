package teste.application.dto.aluno;

import teste.domain.VOs.CPF.CPF;
import teste.domain.aluno.Aluno;

public class AlunoRequestDTO {

   private String nome;
   private String cpf;

   public AlunoRequestDTO(String nome, String cpf) {
      this.nome = nome;
      this.cpf = cpf;
   }

   public Aluno criarAluno() {
      return new Aluno(nome, new CPF(cpf));
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

}
