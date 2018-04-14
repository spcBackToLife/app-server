package com.pk.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pk.util.Aes64;

import net.sf.json.JSONObject;

/*
 *作用：提供所有controller公用的方法放于此处
 *作者：pk
 *时间：2016.11.13
 * */
public class BaseController {
	// 正式方法
	/*
	 * 接收参数并进行解密参数封装到Map：dto中.
	 * 
	 */
	public static Map<String, Object> getParamAsDto(HttpServletRequest req) {
		System.out.println("--------------接收参数:getParamAsDto----------------");
		Map<String, Object> dto = new HashMap<String, Object>();// 用于存放接收的参数
		Map<String, String[]> dataFromApp = req.getParameterMap();
		String[] EncString = dataFromApp.get("echoAgs");// 获得加密的字符串
		if (EncString == null || EncString[0].equals("")) {
		} else {
			String decString = "";// 存放解密后的字符串
			try {
				decString = Aes64.decryptAES(EncString[0], Aes64.IV_STRING);// 解密
			} catch (Exception e) {
				e.printStackTrace();
			}
			JSONObject jsonData = JSONObject.fromObject(decString);// 解密后的字符串转化成json,写入dto
			Iterator<String> keyIterator = jsonData.keySet().iterator();
			System.out.println("接收的参数是：" + jsonData);
			while (keyIterator.hasNext()) {
				String key = keyIterator.next();
				String value = (String) (jsonData.get(key));
				dto.put(key, value);
			}

		}
		String passwd = (String) dto.get("pword");
		if (passwd == null) {
			System.out.println("获得的参数dto:" + dto);
		}
		return dto;
	}

	/*
	 * 加密信息，并且返回给客户端
	 */
	public static void backToClient(HttpServletResponse rsp, Object data) {
		try {
			// rsp.setCharacterEncoding("utf-8");
			JSONObject dataToClient = JSONObject.fromObject(data);
			JSONObject jsonDataToClient = new JSONObject();
			String encryData = "";// 加密数据
			try {
				encryData = Aes64.encryptAES(dataToClient.toString(), Aes64.IV_STRING);
			} catch (Exception e) {
				e.printStackTrace();
			}
			jsonDataToClient.put("echoAgs", encryData);
			rsp.getWriter().print(jsonDataToClient);

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	// 测试使用方法
	/*
	 * 加密信息，并且返回给客户端
	 */
	/*
	 * public static void backToClient(HttpServletResponse rsp,Object
	 * dataToClient){ try { // rsp.setCharacterEncoding("utf-8"); JSONObject
	 * jsonDataToClient=new JSONObject(); String encryData="";//加密数据 try {
	 * encryData=Aes64.encryptAES(dataToClient.toString(), Aes64.IV_STRING); }
	 * catch (Exception e) { e.printStackTrace(); }
	 * System.out.println("回传的参数是："+dataToClient);
	 * rsp.getWriter().print(dataToClient); } catch (IOException e) {
	 * 
	 * e.printStackTrace(); }
	 * 
	 * } public static Map<String, Object> getParamAsDto(HttpServletRequest req)
	 * { System.out.println("--------------接收参数:getParamAsDto----------------");
	 * Map<String, Object> dto = new HashMap<String, Object>();//用于存放接收的参数
	 * Map<String, String[]> dataFromApp = req.getParameterMap();
	 * Iterator<String> keyIterator = (Iterator<String>)
	 * dataFromApp.keySet().iterator();
	 * System.out.println("接收的参数是："+dataFromApp); while (keyIterator.hasNext())
	 * { String key = (String) keyIterator.next(); String value = ((String[])
	 * (dataFromApp.get(key)))[0]; dto.put(key, value); }
	 * 
	 * System.out.println("获得的参数dto:"+dto); return dto; }
	 */

}
