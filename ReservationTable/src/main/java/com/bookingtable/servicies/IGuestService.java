package com.bookingtable.servicies;

import com.bookingtable.dtos.DinnerTableDto;
import com.bookingtable.dtos.GuestDto;

import java.util.List;
import java.util.UUID;

public interface IGuestService {
    public List<GuestDto> getAllGuest();

    public GuestDto getGuestById(UUID id);

    public boolean createGuest(GuestDto guestDto) ;
    public boolean updateGuest(String id, GuestDto guestDto);

    public boolean deleteGuest(String id);
}
