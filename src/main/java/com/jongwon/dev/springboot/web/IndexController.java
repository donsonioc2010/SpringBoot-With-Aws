package com.jongwon.dev.springboot.web;

import com.jongwon.dev.springboot.config.auth.LoginUser;
import com.jongwon.dev.springboot.config.auth.dto.SessionUser;
import com.jongwon.dev.springboot.service.PostsService;
import com.jongwon.dev.springboot.web.dto.PostsResponseDto;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

  private final PostsService postsService;

  private final HttpSession httpSession;

  @GetMapping("/")
  public String index(Model model, @LoginUser SessionUser user) {
    model.addAttribute("posts", postsService.findAllDesc());

    if (user != null) {
      model.addAttribute("userName", user.getName());
    }
    /*ㅠ
    Mustache Starter 때문에 .mustache는 생략이 된다.
    앞의 경로는 src/main/resources/templates/가 생략된 상태
     */
    return "index";
  }

  @GetMapping("/posts/save")
  public String postsSave() {
    return "posts-save";
  }

  @GetMapping("/posts/update/{id}")
  public String postUpdate(@PathVariable Long id, Model model) {

    PostsResponseDto dto = postsService.findById(id);
    model.addAttribute("post", dto);

    return "posts-update";
  }

}
