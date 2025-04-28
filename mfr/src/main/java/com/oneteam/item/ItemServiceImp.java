package com.oneteam.item;

import com.oneteam.domain.item.ItemEntity;
import com.oneteam.domain.item.ItemRepository;
import com.oneteam.dto.ItemDTO;
import com.oneteam.dto.ItemReqDTO;
import com.oneteam.dto.ResDTO;
import com.oneteam.util.FileComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

}
