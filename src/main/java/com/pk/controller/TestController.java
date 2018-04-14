package com.pk.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pk.mapper.LoginMapper;
import com.pk.mapper.TestMapper;

@RestController
@RequestMapping("/notice")
public class TestController {

  @Autowired
  private TestMapper mapper;

  @Autowired
  private LoginMapper login;

  @RequestMapping(value = "/notice")
  public List<Map<String, Object>> getTest(@RequestParam String id) {
    List<Map<String, Object>> list = mapper.testName(id);
    System.out.println("aa:");
    return mapper.testName(id);
  }

  @RequestMapping(value = "/user-notice")
  public Map<String, Object> userNotice() {
    Map<String, Object> result = mapper.getNotice();
    if (result != null) {
      result.put("have", "yes");// 有
    } else {
      result = new HashMap<String, Object>() {
        {
          put("have", "no");
        }
      };
    }
    return result;
  }

  @RequestMapping(value = "/login")
  public void getUserName() {
    String username = login.getNameById("3173b282-98c1-49a6-b486-6c599489c738");
    System.out.println(username);
  }
}
