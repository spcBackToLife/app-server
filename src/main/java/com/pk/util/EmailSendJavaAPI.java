package com.pk.util;

import java.io.UnsupportedEncodingException;

public class EmailSendJavaAPI {

	static String url = "cloud.mysubmail.com";

	public static void main(String[] args) throws UnsupportedEncodingException {

	}

	public static void sendEmail() {
		// AppConfig config = ConfigLoader.load(ConfigLoader.ConfigType.Mail);
		// MAILSend submail = new MAILSend(config);
		// submail.addTo("leo@submail.cn","leo");
		// submail.setSender("no-reply@submail.cn","SUBMAIL");
		// submail.setSubject("test SDK");
		// submail.setText("test SDK text");
		// submail.setHtml("test SDK html");
		// submail.send();
	}

}
//// Instantiate an HttpClient
// HttpClient client = new HttpClient();
//
//// Instantiate a GET HTTP method
// PostMethod method = new PostMethod(url);
// method.setRequestHeader("Content-type", "text/xml; charset=utf-8");
//
//// Define name-value pairs to set into the QueryString
// NameValuePair nvp1 = new NameValuePair("appid",
//// "e6fcb25f22d233c102cbe69335b047cf");
// NameValuePair nvp2 = new NameValuePair("to", "1457431899@qq.com");
// NameValuePair nvp3 = new NameValuePair("from", "703351566@submail.com");
// NameValuePair nvp4 = new NameValuePair("asynchronous", "true");
// NameValuePair nvp5 = new NameValuePair("signature",
//// "e6fcb25f22d233c102cbe69335b047cf");
// method.setQueryString(new NameValuePair[] { nvp1, nvp2, nvp3, nvp4, nvp5 });
// method.setQueryString(URLDecoder.decode(method.getQueryString(), "utf-8"));
//
// try {
// int statusCode = client.executeMethod(method);
//
// System.out.println("Status Code = " + statusCode);
// System.out.println("QueryString>>> " + method.getQueryString());
// System.out.println("Status Text>>>" + HttpStatus.getStatusText(statusCode));
//
// // Get data as a String
// System.out.println(method.getResponseBodyAsString());
//
// // OR as a byte array
// byte[] res = method.getResponseBody();
//
// // write to file
// FileOutputStream fos = new FileOutputStream("donepage.html");
// fos.write(res);
//
// // release connection
// method.releaseConnection();
// } catch (IOException e) {
// e.printStackTrace();
// }