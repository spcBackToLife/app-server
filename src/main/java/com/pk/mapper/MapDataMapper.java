package com.pk.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface MapDataMapper {
  List<Map<String, Object>> getMapRandomData(@Param("vb_latitude_ne") Double vb_latitude_ne,
      @Param("vb_latitude_sw") Double vb_latitude_sw,
      @Param("vb_longitude_sw") Double vb_longitude_sw,
      @Param("vb_longitude_ne") Double vb_longitude_ne, @Param("sex") String sex,
      @Param("userSex") String userSex, @Param("pageSize") int pageSize,
      @Param("allCount") int allCount);

  // 查询是否已经点赞过
  List<Map<String, Object>> checkWhetherPOP(@Param("vb_id") String vb_id,
      @Param("user_id") String user_id);

  // // 获得用户足迹
  // List<Map<String, Object>> getFoot(@Param("user_id") String user_id);

  // 获得用户的动态，自己的--别人的
  List<Map<String, Object>> getDynamic(@Param("vb_login_id") String vb_login_id,
      @Param("allCount") int allCount, @Param("pageSize") int pageSize);

  // 获得我关注的人动态
  List<Map<String, Object>> getMyConcernDynamic(@Param("allCount") int allCount,
      @Param("pageSize") int pageSize, @Param("theSex") String theSex,
      @Param("ul_id") String ul_id);

  // 获得我粉丝的动态
  List<Map<String, Object>> getMyFansDynamic(@Param("allCount") int allCount,
      @Param("pageSize") int pageSize, @Param("theSex") String theSex,
      @Param("ul_id") String ul_id);

  // 获得我关注的人列表
  List<Map<String, Object>> getMyConcern(@Param("allCount") int allCount,
      @Param("pageSize") int pageSize, @Param("user_id") String user_id);

  // 获得我的粉丝列表
  List<Map<String, Object>> getFans(@Param("allCount") int allCount,
      @Param("pageSize") int pageSize, @Param("user_id") String user_id);

  // 搜索用户-通过snnumber
  List<Map<String, Object>> findUsersBySnnumber(@Param("searchOptions") String searchOptions);

  // 搜索用户-通过昵称
  List<Map<String, Object>> findUsersByUserName(@Param("userName") String userName);

  // 根据手机号获得用户的ID
  String getUserIdBySnnumber(@Param("snnumber") String snnumber);
}
