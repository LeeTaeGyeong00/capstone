package com.example.bangbang_gotgot.article.service;

import com.example.bangbang_gotgot.article.dto.ArticleDto;
import com.example.bangbang_gotgot.article.dto.BoardDTO;
import com.example.bangbang_gotgot.article.entity.Article;
import com.example.bangbang_gotgot.article.entity.ArticleFile;
import com.example.bangbang_gotgot.article.repository.ArticleFileRepository;
import com.example.bangbang_gotgot.article.repository.ArticleRepository;
import com.example.bangbang_gotgot.article.specification.ArticleSpecifications;
import com.example.bangbang_gotgot.member.entity.Like;
import com.example.bangbang_gotgot.member.entity.User;
import com.example.bangbang_gotgot.member.repository.LikeRepository;
import com.example.bangbang_gotgot.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleFileRepository articleFileRepository;
    private final LikeRepository likeRepository;

    // 게시글 랭킹(조회수) 리턴(메인)
    public List<Article> articleRanking() {
        List<Article> articles = articleRepository.findTop8ByOrderByViewDesc();
        return articles;
    };

    // 게시클 랭킹(조회수) 해당 사진 리턴(메인)
    public List<BoardDTO> articleRankingFiles() {
        List<Article> articles = articleRepository.findTop8ByOrderByViewDesc();

        List<Long> ids = new ArrayList<>();
        List<BoardDTO> files = new ArrayList<>();

        for (Article article : articles) {
            ids.add(article.getId());
        }
        for (Long id: ids) {
            List<BoardDTO> dtos = findFile(id);

            Optional<BoardDTO> minIdDto = dtos.stream()
                    .min(Comparator.comparingLong(BoardDTO::getId));

            BoardDTO foundDto = minIdDto.get();
            files.add(foundDto);
        }
        return files;
    }

    // 글 작성 처리
    public Article write(ArticleDto board, User user) {
        String start = board.getStartTime1() + ":" + board.getStartTime2();
        board.setStartTime1(start);

        String end = board.getEndTime1() + ":" + board.getEndTime2();
        board.setEndTime1(end);

        Article article = ArticleDto.makeArticle(board, user);
        Article savedArticle = articleRepository.save(article);

        return savedArticle;
    }


    // 관리자 글 작성 사진:  DB 저장
    public void writeBoard(MultipartFile file, List<MultipartFile> multiFiles, Article article) throws IOException {

        if (multiFiles.get(0).isEmpty()){ // 파일이 한개일 떄
            // 파일의 이름 가져옴
            String originalFilename = file.getOriginalFilename();

            // 서버 저장용 이름을 만듬
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;

            // 해당 경로에 파일 저장
            String savePath = "C:/springboot_img/" + storedFileName;
            file.transferTo(new File(savePath));

            // 해당 데이터 save 처리
            ArticleFile articleFile = ArticleFile.toBoardFileEntity(article, originalFilename, storedFileName);
            articleFileRepository.save(articleFile);

        } else{ // 다중 파일 처리

            // 파일의 이름 가져옴
            List<String> originalFilenames = new ArrayList<>();

            originalFilenames.add(file.getOriginalFilename());
            for (MultipartFile file1 : multiFiles) {
                originalFilenames.add(file1.getOriginalFilename());
            }

            // 서버 저장용 이름을 만듬
            List<String> storedFileNames = new ArrayList<>();

            String firstFile = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            storedFileNames.add(firstFile);
            for (MultipartFile file1 : multiFiles) {
                storedFileNames.add(UUID.randomUUID() + "_" + file1.getOriginalFilename());
            }

            // 해당 경로에 파일 저장
            try {
                String savePath = "C:/springboot_img/" + firstFile;
                file.transferTo(new File(savePath));
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (int i = 0; i < storedFileNames.size()-1;i++) {
                try {
                    String savePath = "C:/springboot_img/" + storedFileNames.get(i+1);
                    multiFiles.get(i).transferTo(new File(savePath));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 해당 데이터 save 처리
            for (int i = 0 ; i < originalFilenames.size(); i++) {
                ArticleFile articleFile = ArticleFile.toBoardFileEntity(article, originalFilenames.get(i), storedFileNames.get(i));
                articleFileRepository.save(articleFile);
            }

        }


    }

    // 게시판 수정: 게시글 찾기
    public Article findArticle(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        return article;
    }

    // 게시판 수정
    @Transactional
    public Article updateArticle(ArticleDto articleDto, User user, Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null || !Objects.equals(article.getUser().getId(), user.getId())) {
            return null;
        }
        String start = articleDto.getStartTime1() + ":" + articleDto.getStartTime2();
        String end = articleDto.getEndTime1() + ":" + articleDto.getEndTime2();

        article.setTitle(articleDto.getTitle());
        article.setWriter(articleDto.getWriter());
        article.setContent(articleDto.getContent());
        article.setAddress1(articleDto.getAddress1());
        article.setAddress2(articleDto.getAddress2());
        article.setAddress3(articleDto.getAddress3());
        article.setPhoneNumber(articleDto.getPhoneNumber());
        article.setModDate(LocalDateTime.now());
        article.setStartTime(start);
        article.setEndTime(end);


        Article savedArticle = articleRepository.save(article);

        return savedArticle;
    }

    // 게시판 수정: 사진
    @Transactional
    public String updateBoard(MultipartFile file, List<MultipartFile> multiFiles, Article article, Long id) throws IOException {

        List<ArticleFile> boardDTOS = articleFileRepository.findByArticleId(id);
        if (boardDTOS == null) {
            return null;
        }
        // 파일 삭제
        String folderPath = "C:/springboot_img/"; // 해당 폴더 경로

        for (ArticleFile articleFile : boardDTOS) {
            try {
                Path filePath = Paths.get(folderPath, articleFile.getStoredFileName()); // 파일 경로 생성
                Files.deleteIfExists(filePath); // 파일 삭제
                System.out.println("파일 삭제 성공");
            } catch (IOException e) {
                // 파일 삭제 실패 시 처리
                e.printStackTrace();
            }
        }

        articleFileRepository.deleteAll(boardDTOS);

        if (multiFiles.get(0).isEmpty()){ // 파일이 한개일 떄
            // 파일의 이름 가져옴
            String originalFilename = file.getOriginalFilename();

            // 서버 저장용 이름을 만듬
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;

            // 해당 경로에 파일 저장
            String savePath = "C:/springboot_img/" + storedFileName;
            file.transferTo(new File(savePath));

            // 해당 데이터 save 처리
            ArticleFile articleFile = ArticleFile.toBoardFileEntity(article, originalFilename, storedFileName);
            articleFileRepository.save(articleFile);

            return "board";

        } else{ // 다중 파일 처리

            // 파일의 이름 가져옴
            List<String> originalFilenames = new ArrayList<>();

            originalFilenames.add(file.getOriginalFilename());
            for (MultipartFile file1 : multiFiles) {
                originalFilenames.add(file1.getOriginalFilename());
            }

            // 서버 저장용 이름을 만듬
            List<String> storedFileNames = new ArrayList<>();

            String firstFile = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            storedFileNames.add(firstFile);
            for (MultipartFile file1 : multiFiles) {
                storedFileNames.add(UUID.randomUUID() + "_" + file1.getOriginalFilename());
            }

            // 해당 경로에 파일 저장
            try {
                String savePath = "C:/springboot_img/" + firstFile;
                file.transferTo(new File(savePath));
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (int i = 0; i < storedFileNames.size()-1;i++) {
                try {
                    String savePath = "C:/springboot_img/" + storedFileNames.get(i+1);
                    multiFiles.get(i).transferTo(new File(savePath));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 해당 데이터 save 처리
            for (int i = 0 ; i < originalFilenames.size(); i++) {
                ArticleFile articleFile = ArticleFile.toBoardFileEntity(article, originalFilenames.get(i), storedFileNames.get(i));
                articleFileRepository.save(articleFile);
            }

            return "board";

        }

    }


    // 게시글 목록: 검색
    public Page<Article> searchList(String searchKeyword, Pageable pageable, String option) {
        Specification<Article> spec = ArticleSpecifications.searchByKeyword(searchKeyword);
        Page<Article> result = articleRepository.findAll(spec, pageable);


        if (Objects.equals(option, "1")) {
            return result;
        } else if (Objects.equals(option, "2")) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("view").descending());
            return articleRepository.findAll(spec, pageable);

        } else if (Objects.equals(option, "3")) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("likes").descending());
            return articleRepository.findAll(spec, pageable);
        }
        return result;

    }

    public Page<Article> searchList2(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }


    public Page<Article> searchList3(String searchKeyword, Pageable pageable) {
        Specification<Article> spec = ArticleSpecifications.searchByKeyword(searchKeyword);
        return articleRepository.findAll(spec, pageable);
    }

    public Page<Article> searchList4(Pageable pageable, String option) {

        if (Objects.equals(option, "1")) {
            return articleRepository.findAll(pageable);
        } else if (Objects.equals(option, "2")) {
            return articleRepository.findAllByOrderByViewDesc(pageable);
        } else if (Objects.equals(option, "3")) {
            return articleRepository.findAllByOrderByLikesDesc(pageable);
        }
        return articleRepository.findAll(pageable);
    }


    // 게시글 목록: 지역
    public Page<Article> locate(Pageable pageable, String district, String option) {

        if (Objects.equals(option, "1")) {
            return articleRepository.findByAddress2(district, pageable);
        } else if (Objects.equals(option, "2")) {
            return articleRepository.findByAddress2OrderByViewDesc(district, pageable);
        } else if (Objects.equals(option, "3")) {
            return articleRepository.findByAddress2OrderByLikesDesc(district, pageable);
        }
        else {
            return articleRepository.findByAddress2(district, pageable);
        }

    }


    // 상세 페이지
    public Article findIdList(Long id, HttpServletRequest request, HttpServletResponse response) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("없는 id입니다."));

        int count = article.getView();
        if (!isArticleIdInCookies(request, id)) {
            count++;
            Article target = article.newArticle(article, count);
            System.out.println(target);
            articleRepository.save(target);

            // 쿠키에 게시물 ID 추가 (중복 조회 방지)
            addArticleIdToCookies(response, id);
        }
        System.out.println(article);

        return article;
    }

    // 쿠키에 게시물 ID 추가 (중복 조회 방지)
    private void addArticleIdToCookies(HttpServletResponse response, Long articleId) {
        Cookie cookie = new Cookie("viewed_article_" + articleId, "true");
        cookie.setMaxAge(24 * 60 * 60); // 1일 유지
        response.addCookie(cookie);
    }


    // 쿠키에서 해당 게시물 ID가 있는지 확인
    private boolean isArticleIdInCookies(HttpServletRequest request, Long articleId) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (("viewed_article_" + articleId).equals(cookie.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    // id에 해당하는 사진 찾기
    public List<BoardDTO> findFile(Long id) {

        return articleFileRepository.findByArticleId(id).stream()
                .map(ArticleFile -> BoardDTO.toBoardDTO(ArticleFile))
                .collect(Collectors.toList());

    }

    // list에 해당하는 사진들 찾기
    public List<BoardDTO> findListArticleFiles(Page<Article> list) {

        List<BoardDTO> articleFiles = new ArrayList<>();
        for (Article article : list) {
            // Article 객체에서 id 가져오기
            Long articleId = article.getId();
            articleFiles.addAll(findFile(articleId));

        }
        return articleFiles;

    }


    public Article findArticleById(Long articleId) {
        return articleRepository.findById(articleId).orElse(null);
    }
    public void saveLike(Like like) {
        likeRepository.save(like);
    }
}
