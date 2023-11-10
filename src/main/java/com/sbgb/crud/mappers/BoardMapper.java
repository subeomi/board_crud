package com.sbgb.crud.mappers;

import com.sbgb.crud.dto.BoardDTO;
import com.sbgb.crud.dto.BoardListDTO;
import com.sbgb.crud.dto.PageRequestDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {

    int insertBoard(BoardDTO dto);

    int insertImages(List<Map<String, String>> imageList);

    List<BoardListDTO> getList(PageRequestDTO requestDTO);

    @Select("select * from tbl_board b where b.bno = #{bno}")
    BoardDTO selectOne(Long bno);

//    @Select("SELECT " +
//            "b.bno, b.title, b.writer, b.content, b.regDate, b.status, " +
//            "concat(uuid,'_',fileName) as fileName, i.ord " +
//            "FROM tbl_board b LEFT OUTER JOIN tbl_board_image i ON b.bno = i.bno " +
//            "WHERE b.bno = 1 " +
//            "ORDER BY i.ord")
//    BoardDTO selectOne(Long bno);

    @Select("select concat(uuid,'_',fileName) from tbl_board_image where bno=#{bno} order by ord  ")
    List<String> selectImages(Long bno);

    @Update("update tbl_board set title = #{title}, content = #{content} where bno = #{bno}")
    int updateOne(BoardDTO dto);

    @Update("update tbl_board set status = 1 where bno = #{bno}")
    int deleteOne(Long bno);

    @Delete("delete from tbl_board where bno = #{bno}")
    int deleteReal(Long bno);

    @Delete("delete from tbl_board_image where bno = #{bno}")
    int deleteImages(Long bno);
}
