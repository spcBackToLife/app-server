package com.pk.email;

public class MailSendDemo {

	public static void main(String[] args) {
		AppConfig config = ConfigLoader.load(ConfigLoader.ConfigType.Mail);
		MAILSend submail = new MAILSend(config);
		// submail.addTo("leo@submail.cn","leo");
		submail.addTo("703351566@qq.com", "leo");
		submail.addCc("mailer@submail.cn", "");
		submail.addBcc("leo@drinkfans.com", "");
		submail.setSender("engram@paralzone.com", "SUBMAIL");
		submail.setReply("pikun@cnpc.com.cn");
		submail.setSubject("邮箱");
		submail.setText("engram");
		// submail.addAttachment("D:\\Program
		// Files\\eclipse-php-luna-SR1-win32\\eclipse\\epl-v10.html");
		submail.setHtml("hello,pikun");
		submail.addVar("name", "leo");
		submail.addVar("age", "32");
		submail.addLink("developer", "http://submail.cn/chs/developer");
		submail.addLink("store", "http://submail.cn/chs/store");
		submail.addHeaders("X-Accept", "zh-cn");
		submail.addHeaders("X-Mailer", "leo App");
		submail.send();
	}

	public void send(String to, String sender, String text, String subject, String html, String senderName,
			String reply) {
		AppConfig config = ConfigLoader.load(ConfigLoader.ConfigType.Mail);
		MAILSend submail = new MAILSend(config);
		// submail.addTo("leo@submail.cn","leo");
		submail.addTo(to, "engram");
		// submail.addCc("mailer@submail.cn", "");
		// submail.addBcc("leo@drinkfans.com", "");
		submail.setSender(sender, senderName);
		submail.setReply(reply);
		submail.setSubject(subject);
		submail.setText(text);
		// submail.addAttachment("D:\\Program
		// Files\\eclipse-php-luna-SR1-win32\\eclipse\\epl-v10.html");
		submail.setHtml(html);
		submail.addLink("developer", "http://submail.cn/chs/developer");
		submail.addLink("store", "http://submail.cn/chs/store");
		submail.addHeaders("X-Accept", "zh-cn");
		submail.addHeaders("X-Mailer", "leo App");
		submail.send();
	}
}
