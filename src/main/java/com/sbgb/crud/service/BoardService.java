package com.sbgb.crud.service;

import com.sbgb.crud.dto.BoardDTO;
import com.sbgb.crud.dto.BoardListDTO;
import com.sbgb.crud.dto.PageRequestDTO;
import com.sbgb.crud.dto.PageResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BoardService {

    PageResponseDTO<BoardListDTO> list(PageRequestDTO requestDTO);

    void register(BoardDTO dto);

    BoardDTO getOne(Long bno);

    List<String> getOneImage(Long bno);

    void modify(BoardDTO dto);

    void delete(Long bno);

}
