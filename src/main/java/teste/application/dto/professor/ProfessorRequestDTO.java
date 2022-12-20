package teste.application.dto.professor;

import teste.domain.VOs.CPF.CPF;
import teste.domain.professor.Professor;

public class ProfessorRequestDTO {

   private String nome;
   private String cpf;

   public ProfessorRequestDTO(String nome, String cpf) {
      this.nome = nome;
      this.cpf = cpf;
   }

   public Professor criaProfessor() {
      return new Professor(nome, new CPF(cpf));
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
