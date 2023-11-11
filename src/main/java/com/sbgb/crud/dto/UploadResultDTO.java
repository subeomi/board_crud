package com.sbgb.crud.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadResultDTO {

    private String uuid;
    private String fileName;
    private boolean img;

    public String getList(){
        if (img) {
            return "s_" + uuid + "_" + fileName;
        } else {
            return "default.jpg";
        }
    }
}
