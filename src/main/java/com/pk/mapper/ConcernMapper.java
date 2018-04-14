package com.pk.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ConcernMapper {
  List<Map<String, Object>> checkWhetherConcerned(@Param("ul_id") String ul_id,
      @Param("concerned_id") String concerned_id);

  void addConcern(@Param("cc_id") String cc_id, @Param("concerned_id") String concerned_id,
      @Param("ul_id") String ul_id, @Param("remark") String remark);

  Map<String, Object> checkWhetherReported(@Param("blog_id") String blog_id,
      @Param("reporter") String reporter);

  void updateReportCount(@Param("blog_id") String blog_id);

  Map<String, Object> checkWhetherDianzaned(@Param("blog_id") String blog_id,
      @Param("user_id") String user_id);

  void addDianZanCount(@Param("blog_id") String blog_id);

  void addDianZan(@Param("dz_id") String dz_id, @Param("blog_id") String blog_id,
      @Param("user_id") String user_id);

  void saveBg(@Param("com_img_path") String com_img_path, @Param("img_path") String img_path,
      @Param("user_id") String user_id);

  void addReport(@Param("blog_id") String blog_id, @Param("reporter") String reporter);

  void changeRemarkName(@Param("nick_name") String nick_name,
      @Param("concerned_id") String concerned_id, @Param("ul_id") String ul_id);

  void cancelConcern(@Param("ul_id") String ul_id, @Param("concerned_id") String concerned_id);

  List<Map<String, Object>> checkWheTherConcernedEachOther(@Param("myself_id") String myself_id,
      @Param("user_id") String user_id);
}
