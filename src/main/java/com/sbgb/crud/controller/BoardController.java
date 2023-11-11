package com.sbgb.crud.controller;

import com.sbgb.crud.dto.BoardDTO;
import com.sbgb.crud.dto.PageRequestDTO;
import com.sbgb.crud.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/board/")
public class BoardController {

    private final BoardService service;

    @GetMapping("list")
    public void list(PageRequestDTO requestDTO, Model model){

        model.addAttribute("res", service.list(requestDTO));
    }

    @GetMapping("read/{bno}")
    public String getOne(
            @PathVariable("bno") Long bno,
            PageRequestDTO requestDTO,
            Model model
    ) {

        BoardDTO dto = service.getOne(bno);
        model.addAttribute("dto", dto);
        log.info("get... bno: " + bno);

        return "/board/read";
    }

    @GetMapping("image/{bno}")
    @ResponseBody
    public List<String> getImages(@PathVariable("bno") Long bno) {

        log.info("get image... bno: " + bno);
        return service.getOneImage(bno);
    }

    @PostMapping("register")
    public String register(BoardDTO dto) {

        log.info("post... insert");
        service.register(dto);

        return "redirect:/board/list";
    }

    @GetMapping("modify/{bno}")
    public String modifyGet(
            @PathVariable("bno") Long bno,
            PageRequestDTO requestDTO,
            Model model
    ) {

        BoardDTO dto = service.getOne(bno);
        model.addAttribute("dto", dto);
        log.info("modify get... bno: " + bno);

        return "/board/modify";
    }

    @PostMapping("modify/{bno}")
    public String modify(@PathVariable("bno") Long bno, BoardDTO dto) {

        log.info("post... modify bno: " + bno);
        service.modify(dto);

        return "redirect:/board/read/" + bno;
    }

    @PostMapping("delete/{bno}")
    public String delete(@PathVariable("bno") Long bno) {

        log.info("post... delete bno: " + bno);
        service.delete(bno);

        return "redirect:/board/list";
    }
}
