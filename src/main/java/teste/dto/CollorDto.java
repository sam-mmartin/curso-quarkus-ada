package teste.dto;

import java.util.Objects;

public class CollorDto {

   private int id;
   private String description;

   public CollorDto() {
   }

   public CollorDto(int id, String description) {
      this.id = id;
      this.description = description;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, description);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      CollorDto other = (CollorDto) obj;
      if (id != other.id)
         return false;
      if (description == null) {
         if (other.description != null)
            return false;
      } else if (!description.equals(other.description))
         return false;
      return true;
   }

}
