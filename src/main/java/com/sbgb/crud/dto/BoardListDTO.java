package com.sbgb.crud.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardListDTO {

    private Long bno;
    private String title;
    private String writer;
    private String regDate;
    private int status;
//    thumbnail
    private String fileName;
}
