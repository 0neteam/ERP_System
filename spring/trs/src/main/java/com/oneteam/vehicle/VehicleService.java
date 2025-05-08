package com.oneteam.vehicle;

import com.oneteam.dto.ResDTO;
import com.oneteam.dto.VehicleReqDTO;
import com.oneteam.dto.VehicleSearchReqDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface VehicleService {

    public ResDTO findByVehicle(Pageable pageable, VehicleSearchReqDTO vehicleSearchReqDTO);
    public ResDTO findAll(Pageable pageable, VehicleSearchReqDTO vehicleSearchReqDTO);
    public ResDTO findByNo(Long no, Authentication authentication);
    public ResDTO register(VehicleReqDTO vehicleReqDTO, Authentication authentication);
    public ResDTO modify(Long no, VehicleReqDTO vehicleReqDTO, Authentication authentication);
    public ResDTO delete(Long no, Authentication authentication);


}
