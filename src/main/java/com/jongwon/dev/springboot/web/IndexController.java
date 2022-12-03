package com.jongwon.dev.springboot.web;

import com.jongwon.dev.springboot.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

  private final PostsService postsService;

  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("posts", postsService.findAllDesc());


    /*
    Mustache Starter 때문에 .mustache는 생략이 된다.
    앞의 경로는 src/main/resources/templates/가 생략된 상태
     */
    return "index";
  }

  @GetMapping("/posts/save")
  public String postsSave() {
    return "posts-save";
  }
}
