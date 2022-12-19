package com.jongwon.dev.springboot.web;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Environment 의 경우에는 스프링부트를 실행하지 않고 테스트가 가능하다
 * <p>
 * Environment의 경우 자바 클래스(인터페이스) 이기 떄문에 쉽게 테스트가 가능하며 테스트에서는 MockEnvironment를 활용하여 구현이 가능하다
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProfileControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void profile은_인증없이_호출된다() throws Exception {
    String expected = "default";

    ResponseEntity<String> response = restTemplate.getForEntity("/profile", String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo(expected);
  }

  @Test
  public void real_profile가_조회된다() {
    //given
    String expectedProfile = "real";
    MockEnvironment env = new MockEnvironment();
    env.addActiveProfile(expectedProfile);
    env.addActiveProfile("oauth");
    env.addActiveProfile("real-db");

    ProfileController controller = new ProfileController(env);

    //when
    String profile = controller.profile();

    //then
    assertThat(profile).isEqualTo(expectedProfile);
  }

  @Test
  public void real_profile가_없으면_첫_번째가_조회된다() {
    //given
    String expectedProfile = "oauth";
    MockEnvironment env = new MockEnvironment();

    env.addActiveProfile(expectedProfile);
    env.addActiveProfile("real-db");

    ProfileController controller = new ProfileController(env);

    //when
    String profile = controller.profile();

    //then
    assertThat(profile).isEqualTo(expectedProfile);
  }

  @Test
  public void active_profile이_없으면_default가_조회된다() {
    //given
    String expectedProfile = "default";

    MockEnvironment env = new MockEnvironment();
    ProfileController controller = new ProfileController(env);

    //when
    String profile = controller.profile();

    //then
    assertThat(profile).isEqualTo(expectedProfile);
  }

}