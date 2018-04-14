package com.pk.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface LoginMapper {

  // 查询手机号是否可以注册
  List<Map<String, Object>> checkWhetherCanRegisterPhone(@Param("phoneNumber") String phoneNumber);

  // 查询邮箱是否可以注册
  List<Map<String, Object>> checkWhetherCanRegisterEmail(@Param("email") String email);

  // 手机号注册
  void register(@Param("password") String password, @Param("phoneNumber") String phoneNumber,
      @Param("email") String email, @Param("id") String id);

  // 手机号找回密码
  void updatePasswordByPhone(@Param("phoneNumber") String phoneNumber,
      @Param("password") String password);

  // 邮箱找回密码
  void updatePasswordByEmail(@Param("email") String email, @Param("password") String password);

  // 得到用户的千寻号。
  List<Map<String, Object>> getSnumberByPhone(@Param("phoneNumber") String phoneNumber);

  List<Map<String, Object>> getSnumberByEmail(@Param("emial") String email);

  // 添加用户扩展信息
  void addUserExtInf(@Param("ue_id") String ue_id, @Param("ul_id") String ul_id,
      @Param("ue_name") String ue_name);

  // 添加用户基本信息
  void addBaseInf(@Param("ub_id") String ub_id, @Param("ul_id") String ul_id,
      @Param("ue_name") String ue_name, @Param("ul_mobile") String ul_mobile,
      @Param("ub_sex") String ub_sex);

  // 找到最新注册的人的Number
  List<Map<String, Object>> findLatestNumber();

  // 添加第一个注册号
  void addFirstNumber(@Param("sn_id") String sn_id, @Param("ul_id") String ul_id);

  // 添加snnumber
  void addNumber(@Param("sn_id") String sn_id, @Param("sn_count") String sn_count,
      @Param("ul_id") String ul_id, @Param("snnumber") String snnumber);

  // 添加等级表
  void addUserLevel(@Param("le_id") String le_id, @Param("user_id") String user_id);

  // 得到所有用户的ID和姓名
  List<Map<String, Object>> getNameAndId();

  // 得到用户的名字通过ID
  String getNameById(@Param("id") String id);

  // 添加性格
  void Personality(@Param("p_id") String p_id, @Param("login_id") String login_id,
      @Param("user_type") String user_type);

  Map<String, Object> Login(@Param("phoneNumber") String phoneNumber,
      @Param("password") String password);

  Map<String, Object> LoginByEmail(@Param("email") String email,
      @Param("password") String password);
}
