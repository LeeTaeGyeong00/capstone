package com.example.bangbang_gotgot.article.controller;

import com.example.bangbang_gotgot.article.dto.ArticleDto;
import com.example.bangbang_gotgot.article.dto.BoardDTO;
import com.example.bangbang_gotgot.article.entity.Article;
import com.example.bangbang_gotgot.article.entity.ArticleFile;
import com.example.bangbang_gotgot.article.entity.Review;
import com.example.bangbang_gotgot.article.service.ArticleService;
import com.example.bangbang_gotgot.article.service.ReviewService;
import com.example.bangbang_gotgot.member.entity.Like;
import com.example.bangbang_gotgot.member.entity.User;
import com.example.bangbang_gotgot.member.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Component
public class ArticleController {

    private final ArticleService articleService;
    private final LikeRepository likeRepository;
    private final ReviewService reviewService;

    // 관리자 게시글 쓰기
    @GetMapping("/restaurant_write")
    public String restaurant_write() {
        return "LocalCategory/RestaurantWrite";
    }

    // 관리자 게시글 쓰기
    @PostMapping("/restaurant_write")
    public String writedo(ArticleDto articleDto, Model model, @RequestParam("file") MultipartFile file
                          ,@RequestParam(value = "multiFiles", required = false) List<MultipartFile> multiFiles,
                          HttpSession session
    ) throws IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // 유저 정보가 없으면 로그인 페이지로 리다이렉트
            return "redirect:/bangbang/auth/sign-up";
        }
        Article article = articleService.write(articleDto, user);
        articleService.writeBoard(file, multiFiles, article);

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "redirect:/board/list";
    }

    // 관리자 게시글 수정하기
    @GetMapping("/update/{id}/store")
    public String updateStore(@PathVariable Long id, Model model) {
        Article article = articleService.findArticle(id);
        List<BoardDTO> boardDTO = articleService.findFile(id);
        if (article == null) {
            return "redirect:/bangbang/auth/sign-up";
        }

        String[] startTime = article.getStartTime().split(":");
        String[] endTime = article.getEndTime().split(":");

        model.addAttribute("article", article);
        model.addAttribute("startTime",startTime);
        model.addAttribute("endTime",endTime);
        model.addAttribute("imagefiles",boardDTO);
        return "LocalCategory/RestaurantUpdate";
    }

    // 관리자 게시글 수정하기
    @Transactional
    @PostMapping("/update/{id}/store")
    public String updateStoreProc(@PathVariable Long id, ArticleDto articleDto, Model model, @RequestParam("file") MultipartFile file
            ,@RequestParam(value = "multiFiles", required = false) List<MultipartFile> multiFiles,
                                  HttpSession session) throws IOException
    {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // 유저 정보가 없으면 로그인 페이지로 리다이렉트
            return "redirect:/bangbang/auth/sign-up";
        }
        try {

            Article article = articleService.updateArticle(articleDto, user, id);
            String boardDto = articleService.updateBoard(file, multiFiles, article, id);
            if (boardDto == null) {
                return "error/404";
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return "error/404";
        }
//        model.addAttribute("searchUrl", "/board/list");

        return "redirect:/board/list";
    }




    // 게시글 상세페이지
    @Value("${map.api.key}")
    private String key;

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Article article = articleService.findIdList(id ,request, response);
        User sessionUser = (User) session.getAttribute("user");
        List<BoardDTO> boardDTO = articleService.findFile(id);

        User user = sessionUser != null ? sessionUser : new User();
        System.out.println(user.getId());
        System.out.println(article.getUserId().getId());
        // 리뷰 조회
        List<Review> reviews = reviewService.findReviewsByArticleId(id);

        model.addAttribute("user", sessionUser != null ? sessionUser : new User());
        model.addAttribute("article",article);
        model.addAttribute("key",key);
        model.addAttribute("imagefiles",boardDTO);
        model.addAttribute("reviews", reviews);
        return "contact";
    }

    // 게시글 좋아요
    @PostMapping("/like/{articleId}")
    public String likeArticle(@PathVariable Long articleId, HttpSession session) {
        // 세션에서 현재 로그인한 사용자의 정보 가져오기
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            // 로그인되지 않은 사용자일 경우 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }

        // 사용자가 좋아요를 누른 게시물과 사용자 정보를 이용하여 Like 객체 생성
        Like like = new Like();
        like.setUserNo(sessionUser);
        like.setArticleNo(articleService.findArticleById(articleId)); // ArticleService를 통해 게시물 조회

        // 생성한 Like 객체를 저장
        likeRepository.save(like);

        log.info("Like saved for articleId: {}", articleId);
        // 좋아요를 누른 게시물 상세 페이지로 리다이렉트
        return "redirect:/board/detail/" + articleId;
    }
    // 리뷰 쓰기
    @GetMapping("/review-write/{id}")
    public String review(@PathVariable Long id, Model model) {
        Article article = articleService.findArticleById(id);
        model.addAttribute("article", article);
        return "LocalCategory/CreatingBulletinBoard";
    }
    // 게시글 목록 (검색 + 콤보박스)
    @GetMapping("/list")
    public String list(Model model,
                       @PageableDefault(page = 0, size = 9, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                       @RequestParam(value = "searchKeyword", required = false)String searchKeyword,
                       @RequestParam(value = "option", required = false)String option)
     {
         System.out.println(searchKeyword);
         System.out.println(option);

        Page<Article> list = null;

        // 검색, 옵션선택에 따라 담기는 리스트 값
        if (option == null) {
            if (searchKeyword == null){

                list = articleService.searchList2(pageable);
            }else {

                list = articleService.searchList3(searchKeyword, pageable);
            }
        } else {

            if (Objects.equals(searchKeyword, "")){
                System.out.println("3출력");
                list = articleService.searchList4(pageable, option);
            }else {
                System.out.println("4출력");
                list = articleService.searchList(searchKeyword, pageable, option);

            }
        }

        // 리스트 값 디버깅
        System.out.println("Total elements: " + list.getTotalElements());
            for (Article article : list) {
                System.out.println("Article ID: " + article.getId());
            }


         // 리스트에 해당하는 이미지 저장
         List<BoardDTO> files = fileList(list);


            // 페이지 설정
         int nowPage = list.getPageable().getPageNumber()+1; // or Pageable.getPageNumber() 현재페이지
         int totalPage = list.getTotalPages(); // 총 페이지
         int pageSize = 5; // 한 페이지에 보여질 페이지 수

         int startPage = ((nowPage-1) / pageSize) * pageSize + 1;
         int endPage = Math.min(startPage + pageSize - 1, totalPage);

         model.addAttribute("searchKeyword",searchKeyword);
        model.addAttribute("option", option);
        model.addAttribute("list", list);
        model.addAttribute("files", files);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);


        return "LocalCategory/LocalCategory";
    }


    // 게시글 목록 (지역 선택 + 콤보박스)
    @GetMapping("/location")
    public String list2(Model model,
                       @PageableDefault(page = 0, size = 9, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                       @RequestParam("district")String district,
                        @RequestParam(value = "option", required = false)String option)
    {

        // 지역 선택 + 콤보박스 선택
        Page<Article> list = articleService.locate(pageable, district, option);

        System.out.println(option);

        // 해당 파일 저장
        List<BoardDTO> files = fileList(list);


        int nowPage = list.getPageable().getPageNumber()+1; // or Pageable.getPageNumber() 현재페이지
        int totalPage = list.getTotalPages(); // 총 페이지
        int pageSize = 5; // 한 페이지에 보여질 페이지 수

        int startPage = ((nowPage-1) / pageSize) * pageSize + 1;
        int endPage = Math.min(startPage + pageSize - 1, totalPage);


        model.addAttribute("district", district);
        model.addAttribute("option", option);
        model.addAttribute("list", list);
        model.addAttribute("files", files);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);


        return "LocalCategory/LocalCategory2";
    }


    // 리스트에 해당하는 이미지 저장
    private List<BoardDTO> fileList(Page<Article> list) {
        List<BoardDTO> articleFiles = articleService.findListArticleFiles(list);
        Set<Long> ids = new HashSet<>();
        List<BoardDTO> files = new ArrayList<>();

        for (BoardDTO boardDTO : articleFiles) {
            ids.add(boardDTO.getArticleId());
        }

        for (Long id : ids) {
            List<BoardDTO> dtos = articleService.findFile(id);

            Optional<BoardDTO> minIdDto = dtos.stream()
                    .min(Comparator.comparingLong(BoardDTO::getId));

            BoardDTO foundDto = minIdDto.get();
            files.add(foundDto);
        }
        return files;
    }




}
