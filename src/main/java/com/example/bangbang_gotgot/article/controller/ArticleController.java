package com.example.bangbang_gotgot.article.controller;

import com.example.bangbang_gotgot.article.entity.Article;
import com.example.bangbang_gotgot.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/write")
    public String write() {
        return "board/write";
    }

    @GetMapping("/restaurant_write")
    public String restaurant_write() {
        return "RestaurantWrite/RestaurantWrite";
    }


    @PostMapping("/writedo")
    public String writedo(Article board, Model model) {

        articleService.write(board);

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "board/message";
    }

    @GetMapping("/list")
    public String list(Model model,
                       @PageableDefault(page = 0, size = 9, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                       @RequestParam(value = "searchKeyword", required = false)String searchKeyword,
                       @RequestParam(value = "option", required = false)String option)
     {
         System.out.println(searchKeyword);
         System.out.println(option);

        Page<Article> list = null;

        if (option == null) {
            if (searchKeyword == null){

                list = articleService.searchList2(pageable);
            }else {

                list = articleService.searchList3(searchKeyword, pageable);
            }
        } else {
            list = articleService.searchList(searchKeyword, pageable, option);
            if (Objects.equals(searchKeyword, "")){
                System.out.println("3출력");
                list = articleService.searchList4(pageable, option);
            }else {
                System.out.println("4출력");
                list = articleService.searchList(searchKeyword, pageable, option);

            }
        }



        System.out.println("Total elements: " + list.getTotalElements());
            for (Article article : list) {
                System.out.println("Article ID: " + article.getId());
            }


         int nowPage = list.getPageable().getPageNumber()+1; // or Pageable.getPageNumber() 현재페이지
         int totalPage = list.getTotalPages(); // 총 페이지
         int pageSize = 5; // 한 페이지에 보여질 페이지 수

         int startPage = ((nowPage-1) / pageSize) * pageSize + 1;
         int endPage = Math.min(startPage + pageSize - 1, totalPage);

         model.addAttribute("searchKeyword",searchKeyword);
        model.addAttribute("option", option);
        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);


        return "LocalCategory/LocalCategory";
    }

    @GetMapping("/location")
    public String list2(Model model,
                       @PageableDefault(page = 0, size = 9, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                       @RequestParam("district")String district,
                        @RequestParam(value = "option", required = false)String option)
    {

        Page<Article> list = null;
        list = articleService.locate(pageable, district, option);

        System.out.println(option);


        int nowPage = list.getPageable().getPageNumber()+1; // or Pageable.getPageNumber() 현재페이지
        int totalPage = list.getTotalPages(); // 총 페이지
        int pageSize = 5; // 한 페이지에 보여질 페이지 수

        int startPage = ((nowPage-1) / pageSize) * pageSize + 1;
        int endPage = Math.min(startPage + pageSize - 1, totalPage);

        model.addAttribute("district", district);
        model.addAttribute("option", option);
        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);


        return "LocalCategory/LocalCategory2";
    }


    @GetMapping("/view/{id}")
    public String view(Model model, @PathVariable("id") Long id) {
        model.addAttribute("board", articleService.view(id));
        return "board/view";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        articleService.deleteById(id);

        return "redirect:/board/list";
    }

    @GetMapping("/modify/{id}")
    public String modify(@PathVariable("id") Long id,
                         Model model) {
        model.addAttribute("board", articleService.view(id));

        return "board/modify";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Article board) {

        Article boardTemp = articleService.view(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        articleService.write(boardTemp);

        return "redirect:/board/list";
    }

}
