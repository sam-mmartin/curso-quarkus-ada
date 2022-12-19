package teste.domain.pessoa;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import teste.domain.VOs.CPF.CPF;

public abstract class Pessoa {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;
   private String nome;
   private CPF cpf;

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

}
