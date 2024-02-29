package com.bookingtable.dtos;

import com.bookingtable.models.DinnerTable;
import com.bookingtable.models.Image;
import com.bookingtable.models.ReservationAgent;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDto {
	
		private String id;
		@NotNull
	    @NotEmpty
	    private String name;
		@NotNull
	    @NotEmpty
	    private String mainPhoneNumber;
		@NotNull
	    @NotEmpty
	    private String faxNumber;
		@NotNull
	    @NotEmpty
	    private String tollFreeNumber;
		@NotNull
	    @NotEmpty
	    @Email
	    private String companyMail;
		 private String status;
	    private String website;
	    @NotNull
	    @NotEmpty
	    private String city;
	    @NotNull
	    @NotEmpty
	    private String district;
	    @NotNull
	    @NotEmpty
	    private String ward;
	    
		private LocalDate created;
		
		private LocalDate updated;
		private ReservationAgentDto reservationAgentDto;
		@NotNull
	    @NotEmpty
	    private String address;
	   
	    private Collection<DinnerTableDto> dinnerTablesDto = new ArrayList<>();
	    
	    private Collection<ImageDto> imagesDto = new ArrayList<>();

}
