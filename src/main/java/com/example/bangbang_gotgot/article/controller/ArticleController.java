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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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


    @PostMapping("/writedo")
    public String writedo(Article board, Model model) {

        articleService.write(board);

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "board/message";
    }

    @GetMapping("/list")
    public String list(Model model,
                       @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                       String searchKeyword
    ) {
        Page<Article> list = null;
        if (searchKeyword == null) {
            list = articleService.list(pageable);

        } else {
            list = articleService.searchList(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "board/list";
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
