package com.bookingtable.servicies;

//import com.bookingtable.dtos.PermissionDto;
import com.bookingtable.dtos.RateDto;

import java.util.List;

public interface IRateService {
    public List<RateDto> getAllRate();
    public List<RateDto> getRateByReservationId(String id);


    public RateDto getRateById(Integer id);

    public boolean createRate(RateDto rateDto) ;

    public boolean updateRate(Integer id, RateDto rateDto);

    public boolean deleteRate(Integer id);

}
