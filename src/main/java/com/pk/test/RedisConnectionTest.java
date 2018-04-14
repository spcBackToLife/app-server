package com.pk.test;

import java.util.UUID;


public class RedisConnectionTest {

  public static void main(String[] args) {
    redisConTest();
  }

  public static void redisConTest() {
    // // 连接本地的 Redis 服务
    // Jedis jedis = new Jedis("localhost");
    // jedis.auth("Aa135412");
    //
    // System.out.println("Connection to server sucessfully");
    // // 查看服务是否运行
    // System.out.println("Server is running: " + jedis.ping() + jedis.get("aa"));
    int[] a = new int[3];
    for (int i = 0; i < 3; i++) {
      System.out.println(a[i]);
    }
  }

  public static void ab(AA t) {
    System.out.println(t.getAge());

  }

  public static void add(int t) {
    t = t + 1;
  }
}


class Cs extends AA {
  public Cs(String username, String age) {
    super(username, age);
    // TODO Auto-generated constructor stub
  }

  private String tt;

  public String getTt() {
    return tt;
  }

  public void setTt(String tt) {
    this.tt = tt;
  }

}


class Bs extends AA {
  public Bs(String username, String age, String te) {
    super(username, age);
    this.setTe(te);
  }

  private String te;

  public String getTe() {
    return te;
  }

  public void setTe(String te) {
    this.te = te;
  }

}


class AA {
  private String id;
  private String usrname;
  private String age;

  public AA(String username, String age) {
    this.setAge(age);
    this.setUsrname(username);
    this.setId(UUID.randomUUID().toString());
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsrname() {
    return usrname;
  }

  public void setUsrname(String usrname) {
    this.usrname = usrname;
  }

  public String getAge() {
    return age;
  }

  public void setAge(String age) {
    this.age = age;
  }

}
