package com.sbgb.crud.mappers;

import com.sbgb.crud.dto.BoardDTO;
import com.sbgb.crud.dto.PageRequestDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@SpringBootTest
@Log4j2
public class BoardMapperTests {

    @Autowired
    private BoardMapper mapper;

    @Test
    public void testSelectOne() {
        log.info(mapper.selectOne(1L));
    }

    @Test
    public void testSelectImage() {
        log.info(mapper.selectImages(1L));
    }

    @Test
    public void getList() {
        PageRequestDTO dto = new PageRequestDTO();

        log.info(mapper.getList(dto));
    }

    @Commit
    @Transactional
    @Test
    public void testInsert() {
        for (int i = 0; i < 5; i++) {
            BoardDTO dto = BoardDTO.builder()
                    .title("테스트 제목 " + i)
                    .writer("테스터")
                    .content("테스트 내용 " + i)
                    .build();

            log.info(mapper.insertBoard(dto));
        }
    }

    @Commit
    @Transactional
    @Test
    public void testRegister() {

        String uuidStr = UUID.randomUUID().toString();

        BoardDTO dto = BoardDTO.builder()
                .title("테스트 제목 ")
                .writer("테스터")
                .content("테스트 내용 ")
                .fileNames(List.of(uuidStr + "_" + "f1.jpg",uuidStr + "_" + "f2.jpg"))
                .build();

        List<String> fileNames = dto.getFileNames();

        mapper.insertBoard(dto);

        Long bno = dto.getBno();

        log.info(bno+" <<<<");

        AtomicInteger index = new AtomicInteger();

        List<Map<String,String>> list = fileNames.stream().map(str -> {
            String uuid = str.substring(0, 36);
            String fileName = str.substring(37);

            return Map.of("uuid", uuid, "fileName", fileName,"bno", ""+bno, "ord", "" + index.getAndIncrement());

        }).collect(Collectors.toList());

        log.info(list);

        log.info("===================" + mapper.insertImages(list));
    }

    @Commit
    @Transactional
    @Test
    public void testUpdate() {
        BoardDTO dto = BoardDTO.builder()
                .bno(1L)
                .title("거북이")
                .content("두루미")
                .build();

        log.info(mapper.updateOne(dto));
    }

    @Commit
    @Transactional
    @Test
    public void testDelete() {

        log.info(mapper.deleteOne(2L));
    }

    @Commit
    @Transactional
    @Test
    public void testDeleteReal() {

        log.info(mapper.deleteReal(3L));
    }

    @Commit
    @Transactional
    @Test
    public void testDeleteImage() {

        log.info(mapper.deleteImages(10L));
    }
}
