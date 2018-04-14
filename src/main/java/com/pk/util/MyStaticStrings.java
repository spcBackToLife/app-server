package com.pk.util;

import java.util.HashMap;
import java.util.Map;

public class MyStaticStrings {
  private final static String Aes64Key = "SO9e6%N4#w8Ss2&3";// aes64key
  private final static String BASE_FILE_IP = "http://paralzone.com:8084/";
  // private final static String BASE_FILE_IP = "http://192.168.199.107:8084/";
  // private final static String BASE_FILE_IP="http://16315j49e4.imwork.net/";//图片访问baseurl
  // 2017-07-12 12:23:12
  private final static String ApsKey = "123hcke21";// apskey
  private final static String root_path = "\\home\\user\\engram\\EngramFileSpring\\";
  private final static String dynamic_image_path = "dynamic_image";
  private final static String dynamic_voice_path = "dynamic_voice";
  private final static String dynamic_video_path = "dynamic_video";
  private final static String comment_image_path = "comment_image";
  private final static String comment_voice_path = "comment_voice";
  private final static String personal_information_path = "personal_information";
  private final static String dynamicType_quickText = "quickText";// 快文
  private final static String dynamicType_quickSaying = "quickSaying";// 快语
  private final static String dynamicType_quickImage = "qucikImage";// 快图
  private final static String dynamicType_Blog = "blog";// 博文
  private final static String dynamicType_mood = "mood";// 心情
  private final static String[] phoneNumbers =
      {"15608215916", "18382319502", "18482186455", "15680517959"};

  public static String getRootPath() {
    return root_path;
  }

  public static String getDynamicVideoPath() {
    return dynamic_video_path;
  }

  private final static Map<String, String> levelexp = new HashMap<String, String>() {
    {
      put("1", "10");
      put("2", "12");
      put("3", "16");
      put("4", "22");
      put("5", "34");
      put("6", "54");
      put("7", "92");
      put("8", "167");
      put("9", "318");
      put("10", "637");
      put("11", "1341");
      put("12", "2957");
      put("13", "6814");
      put("14", "16385");
      put("15", "41035");
      put("16", "106862");
      put("17", "288954");
      put("18", "810184");
      put("19", "2352534");
    }
  };

  public static Map<String, String> getLevelexp() {
    return levelexp;
  }

  public static String[] getPhonenumbers() {
    return phoneNumbers;
  }

  public static String getAes64key() {
    return Aes64Key;
  }

  public static String getBaseFileIp() {
    return BASE_FILE_IP;
  }

  public static String getApskey() {
    return ApsKey;
  }

  public static String getDynamicImagePath() {
    return dynamic_image_path;
  }

  public static String getDynamicVoicePath() {
    return dynamic_voice_path;
  }

  public static String getCommentImagePath() {
    return comment_image_path;
  }

  public static String getCommentVoicePath() {
    return comment_voice_path;
  }

  public static String getPersonalInformationPath() {
    return personal_information_path;
  }

  public static String getDynamictypeQuicktext() {
    return dynamicType_quickText;
  }

  public static String getDynamictypeQuicksaying() {
    return dynamicType_quickSaying;
  }

  public static String getDynamictypeQuickimage() {
    return dynamicType_quickImage;
  }

  public static String getDynamictypeBlog() {
    return dynamicType_Blog;
  }

  public static String getDynamictypeMood() {
    return dynamicType_mood;
  }



}
