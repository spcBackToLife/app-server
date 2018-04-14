package com.pk.util;

import javax.servlet.http.HttpServletRequest;

import com.pk.entity.UserEntity;


public class UserSession {
  public static void put(HttpServletRequest req, String Mobile, String Passwd, String ui, String id,
      String sex, String username, String email) {
    UserEntity user = new UserEntity();
    user.setMobile(Mobile);
    user.setPassword(Passwd);
    user.setToken(ui);
    user.setId(id);
    user.setSex(sex);
    user.setUsername(username);
    user.setEmail(email);
    System.out.println("user_login_id:" + user.getId());
    req.getSession().setAttribute("user", user);
  }

  public static UserEntity get(HttpServletRequest req) {
    return (UserEntity) req.getSession().getAttribute("user");
  }
}
