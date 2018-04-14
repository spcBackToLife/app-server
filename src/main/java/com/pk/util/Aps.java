package com.pk.util;

/*
 * 用于对存入数据库的需要加密的数据进行加密的类
 */

import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Aps {

  private final static String DES = "DES";

  public static void main(String arg0[]) {
    // String strs[]={"4GyGHf3iX+80PWSUUNhxfg== ","mCd+X1SEKlu4nqfDArkwUQ== ",
    // "dIXRQ0Orne5wSCJPVRmdmQ== ","a+G3UxT4Bge+/FLEAoxnXA== ","82PZbi37r6LIJBpdg/b2qg== ",
    // "5emdoWWhkIf+esDkm6GXsg== ","lAv1ZyN7ZZn/v6IoZQK+7Q== ","PepXIeRuQ9inOR4cWmfUiA==
    // ","tANwkGA+KM/qqeeJIoVdrw== ","1bKNObr20Au/dNmGASEhBw== ",
    // "+n8oF0JVJRJUfO/i5qqJAQ== ","5azPCYCVVNBaPwrAFUoFMA== ","8S+gTT1YwZ0Z5L70UI0qkg==
    // ","A4v9o9SPJArIJBpdg/b2qg== ","gJSj9mKbda770jV+x18w1A== ",
    // "nNtXUlQJJQO4nVx7eb4BuQ== ","OBoPwKb9Rh5x2nhTOSDMMg== ","DezHlo6C+5BZN9bq/AeupQ==
    // ","R9g9VcBv0jrE4VYEGye/Ng== ","7hMwSILkQijziQI/PL0ciA== ",
    // "Gp39ldEQFqJIYwZ4HqpwpA== ","EMANlqN05GIIbjfsYrjg8g== ","2e6hIOFZ090BIyZZVL6dpw==
    // ","ZZWNLn6/Xiy8UBlOxptZgA== ","oa/NtwYQMgyqpYHyf8LqWw== ",
    // "8svAJoSM7Bn/L7gm9EClEA== ","kD+i0otHw1R57T4rYjPBkw== ","PFOjNEOad1YZ+mzbVW+QDQ==
    // ","xkfpD7qmTHsWAuvfmVWAjw== "};
    // String enstr="+9tQoMeLrWP87SQutZIQJA==";//18383319506--123456
    // try {
    // String destr="";
    // for(String s:strs){
    // destr=destr+decrypt(s, "123hcke21")+",";
    // }
    // destr=destr.substring(0,destr.length()-1);
    // String numbers[]=destr.split(",");
    // for(String m:numbers){
    // SendMessage s=new SendMessage();
    // s.sendMessage(m, "");
    // }
    // System.out.println(destr);
    // } catch (Exception e) {
    // // TODO: handle exception
    // }
    //
  }

  public static String encrypt(String data, String key) throws Exception {
    byte[] bt = encrypt(data.getBytes(), key.getBytes());
    String strs = new BASE64Encoder().encode(bt);
    return strs;
  }

  public static String decrypt(String data, String key) throws IOException, Exception {
    if (data == null) return null;
    BASE64Decoder decoder = new BASE64Decoder();
    byte[] buf = decoder.decodeBuffer(data);
    byte[] bt = decrypt(buf, key.getBytes());
    return new String(bt);
  }

  private static byte[] encrypt(byte[] data, byte[] key) throws Exception {

    SecureRandom sr = new SecureRandom();

    DESKeySpec dks = new DESKeySpec(key);

    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
    SecretKey securekey = keyFactory.generateSecret(dks);

    Cipher cipher = Cipher.getInstance(DES);

    cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

    return cipher.doFinal(data);
  }


  private static byte[] decrypt(byte[] data, byte[] key) throws Exception {

    SecureRandom sr = new SecureRandom();

    DESKeySpec dks = new DESKeySpec(key);

    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
    SecretKey securekey = keyFactory.generateSecret(dks);

    Cipher cipher = Cipher.getInstance(DES);

    cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

    return cipher.doFinal(data);
  }

  public static String encrypt_1(String s, String key) {
    String t = "";
    try {
      t = Aps.encrypt(s, key);
    } catch (IOException e) {

      e.printStackTrace();
    } catch (Exception e) {

      e.printStackTrace();
    }
    return t;
  }
}

