package com.jongwon.dev.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

  @GetMapping("/")
  public String index() {
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
