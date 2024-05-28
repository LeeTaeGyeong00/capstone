package com.example.bangbang_gotgot.article.dto;

import com.example.bangbang_gotgot.article.entity.ArticleFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long id;
//    private MultipartFile boardFile; // Controller 파일 담는 용도
    private String originalFileName; // 원본 파일 이름
    private String storedFileName; // 서버 저장용 파일 이름


    public static BoardDTO toBoardDTO(ArticleFile articleFile) {
        BoardDTO boardDTO = new BoardDTO();

        boardDTO.setId(articleFile.getId()); // 보드파일 id
        boardDTO.setOriginalFileName(articleFile.getOriginalFileName());
        boardDTO.setStoredFileName(articleFile.getStoredFileName());

        return boardDTO;
    }
}
