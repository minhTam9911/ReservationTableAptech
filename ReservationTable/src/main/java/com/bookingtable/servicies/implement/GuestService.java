package com.bookingtable.servicies.implement;

import com.bookingtable.dtos.GuestDto;
import com.bookingtable.mappers.GuestMapper;
import com.bookingtable.models.Guest;
import com.bookingtable.repositories.GuestRepository;
import com.bookingtable.servicies.IGuestService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class GuestService implements IGuestService {

    @Autowired
    private GuestRepository guestRepository;

    @Override
    public List<GuestDto> getAllGuest() {
        return guestRepository.findAll().stream()
                .map(GuestMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public GuestDto getGuestById(String id) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Guest not found with id: " + id));
        return GuestMapper.mapToDto(guest);
    }

    @Override
    public boolean createGuest(GuestDto guestDto) {
        Guest guest = GuestMapper.mapToModel(guestDto);
        Guest savedGuest = guestRepository.save(guest);
        return GuestMapper.mapToDto(savedGuest)!=null;
    }

    @Override
    public boolean updateGuest(String id, GuestDto guestDto) {
        Guest existingGuest = guestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Guest not found with id: " + id));

        Guest updatedGuest = GuestMapper.mapToModel(guestDto);
        updatedGuest.setId(existingGuest.getId()); // Ensure the ID remains the same

        Guest savedGuest = guestRepository.save(updatedGuest);
        return GuestMapper.mapToDto(savedGuest)!=null;
    }

    @Override
    public boolean deleteGuest(String id) {
        if (guestRepository.existsById(id)) {
            guestRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
