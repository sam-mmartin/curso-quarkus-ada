package teste.application.dto.professor;

public class ProfessorResponseDTO {

   private int id;
   private String nome;
   private String cpf;
   private String matricula;
   private boolean status;

   public ProfessorResponseDTO(int id, String nome, String cpf, String matricula, boolean status) {
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
      result = prime * result + id;
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
      ProfessorResponseDTO other = (ProfessorResponseDTO) obj;
      if (id != other.id)
         return false;
      if (matricula == null) {
         if (other.matricula != null)
            return false;
      } else if (!matricula.equals(other.matricula))
         return false;
      return true;
   }

}
