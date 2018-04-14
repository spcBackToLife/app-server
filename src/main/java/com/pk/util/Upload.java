package com.pk.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

public class Upload {
  // 用于存放类型枚举
  public Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();


  // 所有文件的路径=fileRootPath+basePath+"字符串"
  /*******************************************************************************************************/
  // 图片
  public Map<String, Object> uploadImage(MultipartFile[] myfile, HttpServletRequest req,
      HttpServletResponse resp, String mobile, String basePath) throws Exception {
    resp.setCharacterEncoding("utf-8");
    String ImageSavePath = "";// 图片存放位置
    String comImageSavePath = "";// 压缩图片存放位置
    String ComImageOutPath = "";// 压缩图片输出路径(即需要保存的访问路径)
    String ImageOutPath = "";// 未压缩图片输出路径
    // 将各个文件的根目录放在tomcat的resources下面，而非项目下面的。
    String fileRootPath = MyStaticStrings.getRootPath();

    for (int i = 0; i < myfile.length; i++) {

      MultipartFile mfile = myfile[i];
      System.out.println((i + 1) + "张！");
      if (mfile.getSize() == 0) {
        break;
      }
      FILE_TYPE_MAP.clear();
      getPicFileType();// 选择限制的图片类型的初始数组。
      InputStream is = mfile.getInputStream();
      // 转为字节流，读前10位
      ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
      int a = 0;
      for (int t = 0; t < 10; t++) {
        a = is.read();
        bytestream.write(a);
      }
      byte imgdata[] = bytestream.toByteArray();
      System.out.println("文件类型：" + getFileTypeByStream(imgdata));
      if (getFileTypeByStream(imgdata) == null) {

        System.out.println("-------文件类型不对---------");

      } else {
        try {
          String h = getFileTypeByStream(imgdata);

          SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
          String newFileName = "image_" + sdf.format(new Date()) + i + "." + h + "";// 文件名
          ImageSavePath = fileRootPath + basePath + "\\images\\" + mobile + "\\" + newFileName;// 即将写入的d盘
                                                                                               // 访问的时候是全路径
          String dirPath = fileRootPath + basePath + "\\images\\" + mobile + "\\";

          File dir = new File(dirPath);
          // 文件夹的创建
          if (!dir.exists()) { // 判断文件夹是否存在
            dir.mkdirs(); // 不存在则创建
          }
          // 写入文件
          File ImageSaveFile = new File(ImageSavePath);
          mfile.transferTo(ImageSaveFile);
          System.out.println("第" + (i + 1) + "张！");
          System.out.println("图片输出保存路径：" + ImageSavePath);

          // 压缩图片处理
          comImageSavePath =
              fileRootPath + basePath + "\\Comimages\\" + mobile + "\\" + newFileName;
          String comImageSavePathFile = fileRootPath + basePath + "\\Comimages\\" + mobile;// 文件夹路径

          File ComImageSaveFile = new File(comImageSavePathFile);
          if (!ComImageSaveFile.exists()) { // 判断文件夹是否存在
            ComImageSaveFile.mkdirs(); // 不存在则创建
          }
          ImgCompress.Tosmallerpic(comImageSavePathFile, comImageSavePathFile, newFileName, 400,
              400, (float) 0.85);
          System.out.println("压缩图片输出保存路径：" + ImageSavePath);


        } catch (Exception e) {
          e.printStackTrace();
        }
        // D:\EngramFile\dynamic_image\images\gJSj9mKbda7+dB+jOvIP+g==\image_201707092048550.jpg
        if (!ImageSavePath.equals("")) {
          ComImageOutPath =
              ComImageOutPath + comImageSavePath.substring(fileRootPath.length()) + ",";
          ImageOutPath = ImageOutPath + ImageSavePath.substring(fileRootPath.length()) + ",";
        }
      }

    }
    if (!ImageOutPath.equals("")) {
      ImageOutPath = ImageOutPath.substring(0, ImageOutPath.length() - 1);
      ImageOutPath = ImageOutPath.replaceAll("\\\\", "/");
      ComImageOutPath = ComImageOutPath.substring(0, ComImageOutPath.length() - 1);
      ComImageOutPath = ComImageOutPath.replaceAll("\\\\", "/");
    }
    Map<String, Object> outPath = new HashMap<String, Object>();
    outPath.put("ImageOutPath", ImageOutPath);
    outPath.put("ComImageOutPath", ComImageOutPath);
    return outPath;


  }


  /*******************************************************************************************************/
  // 存储视频路径
  public String uploadVideo(MultipartFile[] myfile, HttpServletRequest req,
      HttpServletResponse resp, String mobile, String basePath) throws Exception {
    resp.setCharacterEncoding("utf-8");
    System.out.println("-------------------声音上传---------------------");
    String voiceSavePath = "";
    String voiceOutPath = "";
    String fileRootPath = MyStaticStrings.getRootPath();

    for (int i = 0; i < myfile.length; i++) {
      MultipartFile mfile = myfile[i];
      if (mfile.getSize() == 0) {
        System.out.println("没有文件！");
      }
      FILE_TYPE_MAP.clear();
      getVideoFileType();
      InputStream is = mfile.getInputStream();
      // 转为字节流，读前10位
      ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
      int a = 0;
      for (int t = 0; t < 10; t++) {
        a = is.read();
        bytestream.write(a);
      }
      byte imgdata[] = bytestream.toByteArray();

      String fileName1 = mfile.getContentType();
      System.out.println("VideofileType:" + fileName1);

      if (fileName1.equals("mp4")) {
        // if (getFileTypeByStream(imgdata) == null) {
        // resp.getWriter().print("非指定类型");

        // } else {
        try {
          SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
          String newFileName = "video_" + sdf.format(new Date()) + i + ".mp4";
          voiceSavePath = fileRootPath + basePath + "\\video\\" + mobile + "\\" + newFileName;
          String dir = fileRootPath + basePath + "\\video\\" + mobile + "\\";
          File f = new File(dir);
          if (!f.exists()) { // 判断文件夹是否存在
            f.mkdirs(); // 不存在则创建
          }
          File videoPath = new File(voiceSavePath);
          mfile.transferTo(videoPath);
          System.out.println("第" + (i + 1) + "个！");
          System.out.println(voiceSavePath);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      if (!voiceSavePath.equals("")) {
        voiceOutPath = voiceOutPath + voiceSavePath.substring(fileRootPath.length()) + ",";
        voiceOutPath = voiceOutPath.replaceAll("\\\\", "/");
      }
    }
    if (!voiceOutPath.equals("")) {
      voiceOutPath = voiceOutPath.substring(0, voiceOutPath.length() - 1);
    }
    System.out.println("voiceOutPath:---->" + voiceOutPath);
    return voiceOutPath;


  }


  public String uploadVoice(MultipartFile[] myfile, HttpServletRequest req,
      HttpServletResponse resp, String mobile, String basePath) throws Exception {
    resp.setCharacterEncoding("utf-8");
    System.out.println("-------------------声音上传---------------------");
    String voiceSavePath = "";
    String voiceOutPath = "";
    String fileRootPath = req.getSession().getServletContext().getRealPath("/");
    String projectName = req.getServletContext().getServletContextName();// 当前项目文件夹名字
    fileRootPath = MyStaticStrings.getRootPath();

    for (int i = 0; i < myfile.length; i++) {
      MultipartFile mfile = myfile[i];
      if (mfile.getSize() == 0) {
        System.out.println("没有文件！");
      }
      FILE_TYPE_MAP.clear();
      getAudioFileType();
      InputStream is = mfile.getInputStream();
      // 转为字节流，读前10位
      ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
      int a = 0;
      for (int t = 0; t < 10; t++) {
        a = is.read();
        bytestream.write(a);
      }
      byte imgdata[] = bytestream.toByteArray();

      String fileName1 = mfile.getContentType();
      System.out.println("VideofileType:" + fileName1);

      if (fileName1.equals("wav")) {
        // if (getFileTypeByStream(imgdata) == null) {
        // resp.getWriter().print("非指定类型");

        // } else {
        try {
          SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
          String newFileName = "voice_" + sdf.format(new Date()) + i + ".wav";
          voiceSavePath = fileRootPath + basePath + "\\voice\\" + mobile + "\\" + newFileName;
          String dir = fileRootPath + basePath + "\\voice\\" + mobile + "\\";

          File f = new File(dir);
          if (!f.exists()) { // 判断文件夹是否存在
            f.mkdirs(); // 不存在则创建
          }
          File videoPath = new File(voiceSavePath);
          mfile.transferTo(videoPath);
          System.out.println("第" + (i + 1) + "个！");
          System.out.println(voiceSavePath);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      if (!voiceSavePath.equals("")) {
        voiceOutPath = voiceOutPath + voiceSavePath.substring(fileRootPath.length()) + ",";
        voiceOutPath = voiceOutPath.replaceAll("\\\\", "/");
      }
    }
    if (!voiceOutPath.equals("")) {
      voiceOutPath = voiceOutPath.substring(0, voiceOutPath.length() - 1);
    }
    System.out.println("voiceOutPath:---->" + voiceOutPath);
    return voiceOutPath;


  }

  /****************************************************************************************************/
  public String getFileTypeByStream(byte[] b) {
    String filetypeHex = String.valueOf(getFileHexString(b));
    System.out.println(filetypeHex);
    Iterator<Entry<String, String>> entryiterator = FILE_TYPE_MAP.entrySet().iterator();
    while (entryiterator.hasNext()) {
      Entry<String, String> entry = entryiterator.next();
      String fileTypeHexValue = entry.getValue().toUpperCase();
      if (filetypeHex.toUpperCase().startsWith(fileTypeHexValue)) {
        return entry.getKey();
      }
    }
    return null;
  }

  public String getFileHexString(byte[] b) {
    StringBuilder stringBuilder = new StringBuilder();
    if (b == null || b.length <= 0) {
      return null;
    }
    for (int i = 0; i < b.length; i++) {
      int v = b[i] & 0xFF;
      String hv = Integer.toHexString(v);
      if (hv.length() < 2) {
        stringBuilder.append(0);
      }
      stringBuilder.append(hv);
    }
    return stringBuilder.toString();
  }

  public void getVideoFileType() {
    FILE_TYPE_MAP.put("ram", "2E7261FD"); // Real Audio (ram)
    FILE_TYPE_MAP.put("rm", "2E524D46"); // Real Media (rm)
    FILE_TYPE_MAP.put("mov", "00000014667479707174"); // Quicktime (mov)
    // FILE_TYPE_MAP.put("rmvb", "2e524d46000000120001"); // rmvb
    FILE_TYPE_MAP.put("avi", "41564920");
    FILE_TYPE_MAP.put("avi", "52494646b440c02b4156");
    FILE_TYPE_MAP.put("flv", "464C5601050000000900");
    FILE_TYPE_MAP.put("mp4", "00000020667479706d70");
    FILE_TYPE_MAP.put("wmv", "3026b2758e66CF11a6d9");
    // FILE_TYPE_MAP.put("3gp", "00000014667479703367");
    FILE_TYPE_MAP.put("mkv", "1a45dfa3010000000000");
  }

  public void getPicFileType() {
    FILE_TYPE_MAP.put("jpg", "FFD8FF"); // JPEG (jpg)
    FILE_TYPE_MAP.put("png", "89504E47"); // PNG (png)
    FILE_TYPE_MAP.put("gif", "47494638"); // GIF (gif)
    FILE_TYPE_MAP.put("bmp", "424D"); // Windows Bitmap (bmp)
    FILE_TYPE_MAP.put("png", "89504E470D0a1a0a0000"); // PNG (png)
    FILE_TYPE_MAP.put("bmp", "424d228c010000000000"); // 16色位图(bmp)
    FILE_TYPE_MAP.put("bmp", "424d8240090000000000"); // 24位位图(bmp)
    FILE_TYPE_MAP.put("bmp", "424d8e1b030000000000"); // 256色位图(bmp)
  }

  public void getAudioFileType() {
    FILE_TYPE_MAP.put("wav", "57415645"); // Wave (wav)
    FILE_TYPE_MAP.put("mid", "4D546864"); // MIDI (mid)
    FILE_TYPE_MAP.put("mp3", "49443303000000002176");
    FILE_TYPE_MAP.put("wav", "52494646e27807005741");

    FILE_TYPE_MAP.put("aac", "fff1508003fffcda004c");
    FILE_TYPE_MAP.put("wv", "7776706ba22100000704");
    FILE_TYPE_MAP.put("flac", "664c6143800000221200");
  }


  // FILE_TYPE_MAP.put("ffd8ffe000104a464946", "jpg"); //JPEG (jpg)
  // FILE_TYPE_MAP.put("89504e470d0a1a0a0000", "png"); //PNG (png)
  // FILE_TYPE_MAP.put("47494638396126026f01", "gif"); //GIF (gif)
  // FILE_TYPE_MAP.put("49492a00227105008037", "tif"); //TIFF (tif)
  // FILE_TYPE_MAP.put("424d228c010000000000", "bmp"); //16色位图(bmp)
  // FILE_TYPE_MAP.put("424d8240090000000000", "bmp"); //24位位图(bmp)
  // FILE_TYPE_MAP.put("424d8e1b030000000000", "bmp"); //256色位图(bmp)
  // FILE_TYPE_MAP.put("41433130313500000000", "dwg"); //CAD (dwg)
  // FILE_TYPE_MAP.put("3c21444f435459504520", "html"); //HTML (html)
  // FILE_TYPE_MAP.put("3c21646f637479706520", "htm"); //HTM (htm)
  // FILE_TYPE_MAP.put("48544d4c207b0d0a0942", "css"); //css
  // FILE_TYPE_MAP.put("696b2e71623d696b2e71", "js"); //js
  // FILE_TYPE_MAP.put("7b5c727466315c616e73", "rtf"); //Rich Text Format (rtf)
  // FILE_TYPE_MAP.put("38425053000100000000", "psd"); //Photoshop (psd)
  // FILE_TYPE_MAP.put("46726f6d3a203d3f6762", "eml"); //Email [Outlook Express 6] (eml)
  // FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "doc"); //MS Excel 注意：word、msi 和 excel的文件头一样
  // FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "vsd"); //Visio 绘图
  // FILE_TYPE_MAP.put("5374616E64617264204A", "mdb"); //MS Access (mdb)
  // FILE_TYPE_MAP.put("252150532D41646F6265", "ps");
  // FILE_TYPE_MAP.put("255044462d312e350d0a", "pdf"); //AdobeAcrobat (pdf)
  // FILE_TYPE_MAP.put("2e524d46000000120001", "rmvb"); //rmvb/rm相同
  // FILE_TYPE_MAP.put("464c5601050000000900", "flv"); //flv与f4v相同
  // FILE_TYPE_MAP.put("00000020667479706d70", "mp4");
  // FILE_TYPE_MAP.put("49443303000000002176", "mp3");
  // FILE_TYPE_MAP.put("000001ba210001000180", "mpg"); //
  // FILE_TYPE_MAP.put("3026b2758e66cf11a6d9", "wmv"); //wmv与asf相同
  // FILE_TYPE_MAP.put("52494646e27807005741", "wav"); //Wave (wav)
  // FILE_TYPE_MAP.put("52494646d07d60074156", "avi");
  // FILE_TYPE_MAP.put("4d546864000000060001", "mid"); //MIDI (mid)
  // FILE_TYPE_MAP.put("504b0304140000000800", "zip");
  // FILE_TYPE_MAP.put("526172211a0700cf9073", "rar");
  // FILE_TYPE_MAP.put("235468697320636f6e66", "ini");
  // FILE_TYPE_MAP.put("504b03040a0000000000", "jar");
  // FILE_TYPE_MAP.put("4d5a9000030000000400", "exe");//可执行文件
  // FILE_TYPE_MAP.put("3c25402070616765206c", "jsp");//jsp文件
  // FILE_TYPE_MAP.put("4d616e69666573742d56", "mf");//MF文件
  // FILE_TYPE_MAP.put("3c3f786d6c2076657273", "xml");//xml文件
  // FILE_TYPE_MAP.put("494e5345525420494e54", "sql");//xml文件
  // FILE_TYPE_MAP.put("7061636b616765207765", "java");//java文件
  // FILE_TYPE_MAP.put("406563686f206f66660d", "bat");//bat文件
  // FILE_TYPE_MAP.put("1f8b0800000000000000", "gz");//gz文件
  // FILE_TYPE_MAP.put("6c6f67346a2e726f6f74", "properties");//bat文件
  // FILE_TYPE_MAP.put("cafebabe0000002e0041", "class");//bat文件
  // FILE_TYPE_MAP.put("49545346030000006000", "chm");//bat文件
  // FILE_TYPE_MAP.put("04000000010000001300", "mxp");//bat文件
  // FILE_TYPE_MAP.put("504b0304140006000800", "docx");//docx文件
  // FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "wps");//WPS文字wps、表格et、演示dps都是一样的
  // FILE_TYPE_MAP.put("6431303a637265617465", "torrent");
  //
  //
  // FILE_TYPE_MAP.put("6D6F6F76", "mov"); //Quicktime (mov)
  // FILE_TYPE_MAP.put("FF575043", "wpd"); //WordPerfect (wpd)
  // FILE_TYPE_MAP.put("CFAD12FEC5FD746F", "dbx"); //Outlook Express (dbx)
  // FILE_TYPE_MAP.put("2142444E", "pst"); //Outlook (pst)
  // FILE_TYPE_MAP.put("AC9EBD8F", "qdf"); //Quicken (qdf)
  // FILE_TYPE_MAP.put("E3828596", "pwl"); //Windows Password (pwl)
  // FILE_TYPE_MAP.put("2E7261FD", "ram"); //Real Audio (ram)

}
