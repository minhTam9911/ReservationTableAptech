package com.bookingtable.dtos;

import com.bookingtable.models.CategoryRestaurant;
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
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDto {
		private String id;
	
	    @NotEmpty
	    private String name;
	
	    @NotEmpty
	    private String mainPhoneNumber;
	
	    @NotEmpty
	    private String faxNumber;

	    @NotEmpty
	    private String totalFreeNumber;
	
	    @NotEmpty
	    @Email
	    private String companyMail;
		 private String status;
	    private String website;
	   
	    
	    private String city;
	  
	    private String district;
	   
	    private String ward;
	  
	    private boolean active;

		private LocalDate created;
		
		private LocalDate updated;
		
		private CategoryRestaurantDto categoryRetaurantDto;
		private Integer categoryId;
		@NotEmpty
		private String description;
		@NotEmpty
		private String shortDescription;
		private ReceptionistDto receptionistDto;
		
	    @NotEmpty
	    private String address;
	   
	    private List<DinnerTableDto> dinnerTablesDto ;
	    
	    private List<ImageDto> imagesDto;
	    private String imageSrc;
	    
	    private ReservationAgentDto createBy;

		public RestaurantDto(String id, boolean active) {
			super();
			this.id = id;
			this.active = active;
		}
		private boolean collectionStatus;
	public String getFullAddress() {

		String fullAddress = "";
		if (address != null && !address.isEmpty()) {
			fullAddress += address + ", ";
		}
		if (ward != null && !ward.isEmpty()) {
			fullAddress += ward + ", ";
		}
		if (district != null && !district.isEmpty()) {
			fullAddress += district + ", ";
		}
		if (city != null && !city.isEmpty()) {
			fullAddress += city;
		}
		return fullAddress;
	}



}
