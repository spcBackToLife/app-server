package com.pk.util;

import org.json.JSONException;
import org.json.JSONObject;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class SendMessage {

  public static void main(String[] args) {

  }

  public static int sendMessage(String phoneNumber, String code) throws JSONException {
    // 官网的URL
    String url = "http://gw.api.taobao.com/router/rest";
    // 成为开发者，创建应用后系统自动生成
    String appkey = "23535540";
    String secret = "157eeb4930540357d7cce2bda2f6ef94";
    // 短信模板的内容
    JSONObject js = new JSONObject();
    js.put("code", code);
    TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
    AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
    req.setExtend("123456");
    req.setSmsType("normal");
    req.setSmsFreeSignName("验证码");// 签名名称
    System.out.println(js.toString());
    req.setSmsParamString(js.toString());
    req.setRecNum(phoneNumber);
    req.setSmsTemplateCode("SMS_63160420");
    try {
      AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
      System.out.println(rsp.getBody());
      return 1;
    } catch (Exception e) {
      // TODO: handle exception
      return -1;
    }
  }
}

