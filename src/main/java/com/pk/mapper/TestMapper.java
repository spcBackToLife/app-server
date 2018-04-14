package com.pk.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TestMapper {
  public List<Map<String, Object>> testName(@Param("id") String id);

  public Map<String, Object> getNotice();
}
