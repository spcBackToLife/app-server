package com.pk.util;

import javax.servlet.http.HttpServletRequest;

public class Auth {


  public static boolean auth(String token, HttpServletRequest req) {
    if (UserSession.get(req) == null) {
      return false;
    }
    if (!token.equals(UserSession.get(req).getToken())) {
      return false;
    }
    return true;

  }
}
