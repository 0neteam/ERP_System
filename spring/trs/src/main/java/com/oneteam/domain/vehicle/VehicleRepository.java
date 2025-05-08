package com.oneteam.domain.vehicle;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

  public Page<VehicleEntity> findAllByUseYnAndStatusAndRegNumberContaining(char useYn, Integer status, String regNumber, Pageable pageable);
  public Page<VehicleEntity> findAllByUseYnAndStatusAndNameContaining(char useYn, Integer status, String name, Pageable pageable);
  public Page<VehicleEntity> findAllByUseYnAndStatusAndType(char useYn, Integer status, Integer type, Pageable pageable);

  public Page<VehicleEntity> findAllByUseYn(char useYn, Pageable pageable);
  public Page<VehicleEntity> findAllByUseYnAndRegNumberContaining(char useYn, String regNumber, Pageable pageable);
  public Page<VehicleEntity> findAllByUseYnAndNameContaining(char useYn, String name, Pageable pageable);
  public Page<VehicleEntity> findAllByUseYnAndType(char useYn, Integer type, Pageable pageable);
  public Page<VehicleEntity> findAllByUseYnAndLicence(char useYn, Integer licence, Pageable pageable);
  public Page<VehicleEntity> findAllByUseYnAndStatus(char useYn, Integer status, Pageable pageable);

  public Optional<VehicleEntity> findByUseYnAndNo(char useYn, Long no);

}
