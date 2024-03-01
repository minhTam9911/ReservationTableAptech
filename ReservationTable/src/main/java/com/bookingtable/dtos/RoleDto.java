package com.bookingtable.dtos;

<<<<<<< HEAD
=======
import java.util.ArrayList;
import java.util.Collection;


import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
>>>>>>> e0a44d4fbac169b2a4c7f108cd5eb9007b3bdf15
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
