package com.pk.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CommentMapper {
  void saveComment(@Param("co_id") String co_id, @Param("blog_creater_id") String blog_creater_id,
      @Param("blog_id") String blog_id, @Param("com_img_path") String com_img_path,
      @Param("commenter_id") String commenter_id, @Param("img_path") String img_path,
      @Param("comment_level") String comment_level, @Param("be_replied_id") String be_replied_id,
      @Param("content") String content, @Param("voicePath") String voicePath);

  // 更新与我相关时间
  void updateTimeOfNotice(@Param("comment_id") String comment_id);

  String getRepliedUserId(@Param("co_replied_id") String co_replied_id);

  Long getNoticeCount(@Param("ul_id") String ul_id);

  // 插入与我相关个数表
  void addNoticeCount(@Param("no_id") String no_id,
      @Param("blog_creater_id") String blog_creater_id);

  void addNotice(@Param("no_id") String no_id, @Param("blog_creater_id") String blog_creater_id,
      @Param("blog_id") String blog_id, @Param("comment_id") String comment_id,
      @Param("commenter_id") String commenter_id);

  void updateWheTherHasChild(@Param("co_replied_id") String co_replied_id);

  void updateCommentCount(@Param("blog_id") String blog_id);

  List<Map<String, Object>> getCommentToAuthorSelf(@Param("blog_id") String blog_id,
      @Param("pageSize") int pageSize, @Param("allCount") int allCount);

  List<Map<String, Object>> getCommentToMe(@Param("blog_id") String blog_id,
      @Param("pageSize") int pageSize, @Param("allCount") int allCount,
      @Param("ul_id") String ul_id);

  List<Map<String, Object>> getChildrenOfCommentAuthor(@Param("blog_id") String blog_id,
      @Param("comment_id") String comment_id, @Param("commenter_id") String commenter_id);

  List<Map<String, Object>> getChildrenOfCommentOthers(@Param("blog_id") String blog_id,
      @Param("comment_id") String comment_id, @Param("commenter_id") String commenter_id);

  List<Map<String, Object>> getNotice(@Param("ul_id") String ul_id, @Param("pageSize") int pageSize,
      @Param("allCount") int allCount);

  void deleteNotice(@Param("no_id") String no_id);

  void deleteNoticeCount(@Param("ul_id") String ul_id);

  List<Map<String, Object>> getNoticeChildren(@Param("co_id") String co_id);

}
