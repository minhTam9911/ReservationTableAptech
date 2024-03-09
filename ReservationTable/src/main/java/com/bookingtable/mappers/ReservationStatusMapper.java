package com.bookingtable.mappers;



import java.util.stream.Collectors;

import com.bookingtable.dtos.ReservationStatusDto;
import com.bookingtable.models.ReservationStatus;

public class ReservationStatusMapper {
    public static ReservationStatus mapToModel(ReservationStatusDto reservationTableStatusDto) {
        return ReservationStatus.builder()
        		.id(reservationTableStatusDto.getId())
        		.status(reservationTableStatusDto.getStatus())
        		.reason(reservationTableStatusDto.getReason())
        		.description(reservationTableStatusDto.getDescription())
        		.build();
    }

    public static ReservationStatusDto mapToDto(ReservationStatus reservationStatus) {
    	 return ReservationStatusDto.builder()
         		.id(reservationStatus.getId())
         		.status(reservationStatus.getStatus())
         		.reason(reservationStatus.getReason())
         		.description(reservationStatus.getDescription())
         		.reservationsDto(reservationStatus.getReservations().stream().map(i-> ReservationMapper.mapToDto(i)).collect(Collectors.toList()))
         		.build();
}
}
