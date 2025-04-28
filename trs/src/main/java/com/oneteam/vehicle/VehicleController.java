package com.oneteam.vehicle;

import com.oneteam.dto.ResDTO;
import com.oneteam.dto.VehicleReqDTO;
import com.oneteam.dto.VehicleSearchReqDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/vehicle")
@RequiredArgsConstructor
public class VehicleController implements VehicleControllerDocs {

    private final VehicleService vehicleService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
    @GetMapping
    public ResDTO findByVehicle(@PageableDefault(size = 4, sort = "no", direction = Sort.Direction.DESC) Pageable pageable, @ModelAttribute @Valid VehicleSearchReqDTO vehicleSearchReqDTO) {
        return vehicleService.findByVehicle(pageable, vehicleSearchReqDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
    @PostMapping
    public ResDTO findAll(@PageableDefault(size = 4, sort = "no", direction = Sort.Direction.DESC) Pageable pageable, @RequestBody @Valid VehicleSearchReqDTO vehicleSearchReqDTO) {
        return vehicleService.findAll(pageable, vehicleSearchReqDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
    @PostMapping("/{no:[0-9]+}")
    public ResDTO findByNo(@PathVariable("no") Long no, Authentication authentication) {
        return vehicleService.findByNo(no, authentication);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
    @PutMapping
    public ResDTO register(@ModelAttribute @Valid VehicleReqDTO vehicleReqDTO, Authentication authentication) {
        return vehicleService.register(vehicleReqDTO, authentication);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
    @PatchMapping("/{no:[0-9]+}")
    public ResDTO modify(@PathVariable("no") Long no, @ModelAttribute @Valid VehicleReqDTO vehicleReqDTO, Authentication authentication) {
        return vehicleService.modify(no, vehicleReqDTO, authentication);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MFR','ROLE_STG','ROLE_TRS','ROLE_DRI')")
    @DeleteMapping("/{no:[0-9]+}")
    public ResDTO delete(@PathVariable("no") Long no, Authentication authentication) {
        return vehicleService.delete(no, authentication);
    }

}
