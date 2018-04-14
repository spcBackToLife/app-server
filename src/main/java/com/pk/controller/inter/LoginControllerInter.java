package com.pk.controller.inter;

import org.apache.ibatis.annotations.Param;

public interface LoginControllerInter {

  public void checkWtheCanReigister(@Param("phoneNumber") String phoneNumber);
}
