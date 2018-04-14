package com.pk.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface DynamicMapper {

  // 保存动态
  void saveDynamic(@Param("vb_id") String vb_id, @Param("vb_com_img_path") String vb_com_img_path,
      @Param("vb_comment_count") String vb_comment_count, @Param("vb_img_path") String vb_img_path,
      @Param("vb_latitude") String vb_latitude, @Param("vb_login_id") String vb_login_id,
      @Param("vb_longitude") String vb_longitude, @Param("vb_pop_count") String vb_pop_count,
      @Param("vb_range") String vb_range, @Param("vb_text") String vb_text,
      @Param("vb_the_one_id") String vb_the_one_id, @Param("vb_the_sex") String vb_the_sex,
      @Param("vb_title") String vb_title, @Param("vb_type") String vb_type,
      @Param("vb_video_path") String vb_video_path,
      @Param("vb_friend_can_see") String vb_friend_can_see);

  void savePersonBaseInfo(@Param("user_id") String user_id,
      @Param("constellation") String constellation, @Param("introduction") String introduction,
      @Param("name") String name);

  Map<String, Object> getPersonInfo(@Param("user_id") String user_id);

  void deleteDynamic(@Param("blog_id") String blog_id);

  void deleteMyPackage(@Param("blog_id") String blog_id);

  Map<String, Object> whetherIsMyDynamic(@Param("blog_id") String blog_id,
      @Param("user_id") String user_id);

  void savePersonExtInfo(@Param("location") String location,
      @Param("com_head_icon") String com_head_icon, @Param("user_id") String user_id,
      @Param("head_icon") String head_icon, @Param("constellation") String constellation,
      @Param("introduction") String introduction, @Param("name") String name);

  void saveHeadIcon(@Param("head_icon") String head_icon,
      @Param("com_head_icon") String com_head_icon, @Param("user_id") String user_id);

}
