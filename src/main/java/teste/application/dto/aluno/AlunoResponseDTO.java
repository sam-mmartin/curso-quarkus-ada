package teste.application.dto.aluno;

public class AlunoResponseDTO {

   private int id;
   private String nome;
   private String cpf;
   private String matricula;
   private boolean status;

   public AlunoResponseDTO(int id, String nome, String cpf, String matricula, boolean status) {
      this.id = id;
      this.nome = nome;
      this.cpf = cpf;
      this.matricula = matricula;
      this.status = status;
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

   public String getMatricula() {
      return matricula;
   }

   public void setMatricula(String matricula) {
      this.matricula = matricula;
   }

   public boolean isStatus() {
      return status;
   }

   public void setStatus(boolean status) {
      this.status = status;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((matricula == null) ? 0 : matricula.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      AlunoResponseDTO other = (AlunoResponseDTO) obj;
      if (matricula == null) {
         if (other.matricula != null)
            return false;
      } else if (!matricula.equals(other.matricula))
         return false;
      return true;
   }

}
