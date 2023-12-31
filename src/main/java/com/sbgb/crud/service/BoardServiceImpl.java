package com.sbgb.crud.service;

import com.sbgb.crud.dto.*;
import com.sbgb.crud.mappers.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardMapper mapper;

    @Override
    public PageResponseDTO<BoardListDTO> list(PageRequestDTO requestDTO) {

        List<BoardListDTO> list = mapper.getList(requestDTO);
        Long total = mapper.getBoardTotal(requestDTO);

        return new PageResponseDTO<>(list, total, requestDTO);
    }

    @Override
    public void register(BoardDTO dto) {
        
        if(dto.getWriter() == null) {
            dto.setWriter("테스터");
        }

        mapper.insertBoard(dto);
        log.info("====================");
        log.info(dto);

        if(dto.getFileNames() != null){

            List<String> fileNames = dto.getFileNames();

            Long bno = dto.getBno();

            AtomicInteger index = new AtomicInteger();

            List<Map<String, String>> list = fileNames.stream().map(str -> {
                String uuid = str.substring(0, 36);
                String fileName = str.substring(37);

                return Map.of("uuid", uuid, "fileName", fileName, "bno", "" + bno, "ord", "" + index.getAndIncrement());
            }).collect(Collectors.toList());

            log.info(list);

            mapper.insertImages(list);
        }

        log.info("====================");
    }

    @Override
    public BoardDTO getOne(Long bno) {

        BoardDTO dto = mapper.selectOne(bno);
        dto.setFileNames(mapper.selectImages(bno));

        log.info("====================");
        log.info(dto);

        return dto;
    }

    @Override
    public List<String> getOneImage(Long bno) {

        List<String> list = mapper.selectImages(bno);

        log.info("====================");
        log.info(list);

        return list;
    }

    @Override
    public void modify(BoardDTO dto) {

        // 게시글 수정
        mapper.updateOne(dto);
        log.info("====================");
        log.info(dto);

        if(dto.getFileNames() != null) {

            // 기존 이미지 삭제
            mapper.deleteImages(dto.getBno());

            // 새 이미지 추가
            List<String> fileNames = dto.getFileNames();

            Long bno = dto.getBno();

            AtomicInteger index = new AtomicInteger();

            List<Map<String, String>> list = fileNames.stream().map(str -> {
                String uuid = str.substring(0, 36);
                String fileName = str.substring(37);

                return Map.of("uuid", uuid, "fileName", fileName, "bno", "" + bno, "ord", "" + index.getAndIncrement());
            }).collect(Collectors.toList());

            log.info(list);

            mapper.insertImages(list);
            log.info("====================");
        } else {
            log.info("modify... no image");
        }

    }

    @Override
    public void delete(Long bno) {

        mapper.deleteOne(bno);

        log.info("====================");
        log.info(bno);
    }
}
