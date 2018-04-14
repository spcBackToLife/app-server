package com.pk.email;

public class MessageXSendDemo {

	public static void main(String[] args) {
		AppConfig config = ConfigLoader.load(ConfigLoader.ConfigType.Message);
		MESSAGEXsend submail = new MESSAGEXsend(config);
		submail.addTo("18516632554");
		submail.setProject("MApf82");
		submail.addVar("code", "a你好aaaa");
		submail.xsend();
	}
}
