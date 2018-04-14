package com.pk.util;

public class RandomArray {

  public static void main(String[] args) {

  }

  /*
   * 生成不重复的整数随机数组
   */
  public static int[] randomArray(int arraySize, int numberRange) {
    int[] randomArray = new int[arraySize];
    int counts = 0;// 成功放置1个加1
    int randomNumber = 0;
    boolean firstTime = true;// 第一次出现
    while (counts < arraySize - 1) {
      randomNumber = new java.util.Random().nextInt(numberRange);
      for (int eachrandomArray : randomArray) {
        if (eachrandomArray == randomNumber) {
          firstTime = false;
          break;
        }
      }
      if (firstTime) {
        randomArray[counts] = randomNumber;
        counts++;
      }
      firstTime = true;

    }

    return randomArray;
  }
}
