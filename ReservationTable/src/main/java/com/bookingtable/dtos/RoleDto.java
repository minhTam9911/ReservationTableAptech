package com.bookingtable.dtos;

<<<<<<< HEAD

=======
>>>>>>> de7f023c0008e921f407b223d25e750028396412
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoleDto {
	
	private Integer id;
	@NotEmpty(message = "The name cannot be empty")
	private String name;
}
