package com.oneteam.item;

import com.oneteam.domain.item.ItemEntity;
import com.oneteam.domain.item.ItemRepository;
import com.oneteam.dto.ItemDTO;
import com.oneteam.dto.ItemReqDTO;
import com.oneteam.dto.ResDTO;
import com.oneteam.util.FileComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImp implements ItemService {

  private final ItemRepository itemRepository;
  private final FileComponent fileComponent;

  @Override
  public ResDTO findAll(Pageable pageable, String no, String name) {
    boolean status = false;
    String message = "조회된 품목이 없습니다.";
    Object result = null;
    no = (no == null) ? "" : no;
    name = (name == null) ? "" : name;
    log.info("no : {}, name : {}", no, name);
    Page<ItemEntity> items = itemRepository.findAllByUseYn('Y', no, name, pageable);
    if(items != null) {
      status = true;
      result = ItemDTO.findByItems(items);
      message = null;
    }
    return ResDTO.builder().status(status).result(result).message(message).build();
  }

  @Override
  public ResDTO findByNo(Long no, Authentication authentication) {
    boolean status = false;
    String message = "존재하지 않는 품목 입니다.";
    Object result = null;
    ItemEntity item = itemRepository.findByNoAndUseYn(no, 'Y').orElseThrow(() -> new RuntimeException("존재하지 않는 품목 입니다."));
    if(item.getNo() > 0) {
      status = true;
      result = ItemDTO.findByItem(item);
      message = null;
    }
    return ResDTO.builder().status(status).result(result).message(message).build();
  }

  @Transactional
  @Override
  public ResDTO register(ItemReqDTO itemReqDTO, Authentication authentication) {
    boolean status = false;
    String message = "정상적으로 품목이 등록 되지 않았습니다.";
    Object result = null;
    Long fileNo = fileComponent.upload(itemReqDTO.getFile(), authentication);
    ItemEntity item = ItemEntity.builder()
        .name(itemReqDTO.getName())
        .bundle(itemReqDTO.getBundle())
        .fileNo(fileNo)
        .build();
    if(itemReqDTO.getPrice() != null) {
      item.setPrice(itemReqDTO.getPrice());
    }
    item.setUseYn('Y');
    item.setRegUserNo(Long.parseLong(authentication.getName()));
    item = itemRepository.save(item);
    if(item.getNo() > 0) {
      status = true;
      result = ItemDTO.findByItem(item);
      message = null;
    }
    return ResDTO.builder().status(status).result(result).message(message).build();
  }

  @Transactional
  @Override
  public ResDTO modify(Long no, ItemReqDTO itemReqDTO, Authentication authentication) {
    boolean status = false;
    String message = "정상적으로 품목이 수정 되지 않았습니다.";
    Object result = null;
    Long fileNo = itemReqDTO.getFileNo();
    if(itemReqDTO.getFile() != null) {
      fileNo = fileComponent.upload(itemReqDTO.getFile(), authentication);
    }
    ItemEntity item = itemRepository.findByNoAndUseYn(no, 'Y').orElseThrow(() -> new RuntimeException("존재하지 않는 품목 입니다."));
    item.setName(itemReqDTO.getName());
    item.setBundle(itemReqDTO.getBundle());
    item.setPrice(itemReqDTO.getPrice());
    item.setFileNo(fileNo);
    item.setModUserNo(Long.parseLong(authentication.getName()));
    item = itemRepository.save(item);
    if(item.getNo() > 0) {
      status = true;
      result = ItemDTO.findByItem(item);
      message = null;
    }
    return ResDTO.builder().status(status).result(result).message(message).build();
  }

  @Transactional
  @Override
  public ResDTO delete(Long no, Authentication authentication) {
    boolean status = false;
    String message = "정상적으로 품목이 삭제 되지 않았습니다.";
    Object result = null;
    ItemEntity item = itemRepository.findByNoAndUseYn(no, 'Y').orElseThrow(() -> new RuntimeException("존재하지 않는 품목 입니다."));
    item.setUseYn('N');
    item.setModUserNo(Long.parseLong(authentication.getName()));
    item = itemRepository.save(item);
    if(item.getNo() > 0) {
      status = true;
      message = null;
    }
    return ResDTO.builder().status(status).result(result).message(message).build();
  }

}