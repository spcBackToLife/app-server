package com.pk.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * 
 * @ClassName: JavaEmailTest
 * @Description: 发送邮件
 * @author wenniuwuren
 * @date 2014-12-25 下午3:37:27
 * 
 */
public class EmailYB implements Serializable {

	private static final long serialVersionUID = -1;
	private static EmailYB bean = new EmailYB();

	// 发件人信息：帐号、密码、发件所用SMTP服务器(请到相关邮箱开启SMTP支持)
	private String fromEmailAccount = "QianXunEngram@qq.com";// 发件人账号
	private String fromEmailPassword = "LiSH19940102";// 发件人密码
	private Long affixFileSize = 1048576L * 10l;// 允许发送的最大附件大小(字节)
	private String emailServerIP = "smtp-mail.outlook.com";// 服务器的IP或域名

	public static EmailYB getBean() {
		return bean;
	}

	/**
	 * @see 发邮件
	 * @param toEmail
	 *            收件人地址
	 * @param title
	 *            邮件标题
	 * @param templetPath
	 *            模板路径(物理路径)
	 * @param args
	 *            模板中需要替换的值
	 * @param affixPath
	 *            附件的路径(物理路径)
	 * @return boolean
	 */
	public Boolean sendEmail(String toEmail, String title, String templetPath, String[] args, String affixPath) {
		Boolean result = false;
		try {
			Properties properties = new Properties();
			Session session = Session.getInstance(properties, null);
			properties.put("mail.smtp.host", emailServerIP);
			properties.put("mail.smtp.auth", "true");// smtp校验
			properties.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
																		// SMTP
																		// 服务器地址
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.EnableSSL.enable", "true");
			properties.setProperty("mail.smtp.socketFactory.fallback", "false");
			Transport transport = session.getTransport("smtp");
			transport.connect(emailServerIP, fromEmailAccount, fromEmailPassword);
			Message message = new MimeMessage(session);
			message.setSubject(title);// 邮件主题
			Address address[] = { new InternetAddress(fromEmailAccount) };
			message.addFrom(address);
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));// 收件人地址
			message.setSentDate(new Date());// 发送时间

			// start mail正文
			String content = readTemplet(templetPath);// 读取邮件模板的内容
			for (int i = 0; i < args.length; i++) {
				content = content.replace("{" + i + "}", args[i]);// 替换模板中的占位符
			}
			MimeMultipart multipart = new MimeMultipart();
			MimeBodyPart contentPart = new MimeBodyPart();
			contentPart.setDataHandler(new DataHandler(content, "text/html;charset=gbk"));// 正文内容
			multipart.addBodyPart(contentPart);// 正文
			if (null != affixPath && !"".equals(affixPath)) {
				File file = new File(affixPath);
				if (file.exists() && !file.isDirectory() && file.length() <= affixFileSize) {// 附件存在且小于10M
					MimeBodyPart affixPart = new MimeBodyPart();
					affixPart.setDataHandler(new DataHandler(new FileDataSource(affixPath)));// 读取附件
					affixPart.setFileName(MimeUtility.encodeText(file.getName()));// 设置附件标题
					multipart.addBodyPart(affixPart);// 设置附件
				}
			}
			message.setContent(multipart);
			// end mail正文

			message.saveChanges();// 保存发送信息
			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));// 发送邮件
			transport.close();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 读模板文件
	private String readTemplet(String templetPath) throws IOException {
		InputStream input = null;
		InputStreamReader read = null;
		BufferedReader reader = null;

		if (!new File(templetPath).exists()) {
			return "";
		}
		try {
			input = new FileInputStream(templetPath);
			read = new InputStreamReader(input, "UTF-8");
			reader = new BufferedReader(read);
			String line;
			String result = "";
			while ((line = reader.readLine()) != null) {
				result += line + "\n";
			}
			return result.substring(result.indexOf("<html>"));
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			reader.close();
			read.close();
			input.close();
		}
	}

	// 异步执行
	public Boolean asynchronizedExecutor(final String toEmail, final String title, final String templetPath,
			final String[] emailModel) {
		Boolean flag = Boolean.FALSE;
		FutureTask<Boolean> futureTask = null;
		ExecutorService excutorService = Executors.newCachedThreadPool();

		// 执行任务
		futureTask = new FutureTask<Boolean>(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return bean.sendEmail(toEmail, title, templetPath, emailModel, templetPath);// 发送邮件;
			}

		});
		excutorService.submit(futureTask);
		try {
			// 任务没超时说明发送成功
			flag = futureTask.get(5L, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			futureTask.cancel(true);
			e.printStackTrace();
		} catch (ExecutionException e) {
			futureTask.cancel(true);
			e.printStackTrace();
		} catch (TimeoutException e) {
			futureTask.cancel(true);
			e.printStackTrace();
		} finally {
			excutorService.shutdown();
		}
		return flag;
	}

	public static void main(String[] args) {
		try {

			final String email = "email1@qq.com;email2@qq.com";// 收件人地址(用;隔开可以发多人邮件)
			final String title = "圣诞快乐";// 邮件标题
			final String url = "https://www.google.com";
			final String img = "http://www.0756jy.cn/uploads/allimg/101210/6_101210102543_1.jpg"; // 图片地址
			final String templetPath = "E:\\M.txt"; // 邮件正文文档兼附件
			final String[] toEmails = email.split(";");

			for (int i = 0; i < toEmails.length; i++) {
				String[] emailModel = new String[] { "文牛武人", "wenniuwuren", toEmails[i], url, url, url, img };// 邮件模板的参数设置
				System.out.println("发送状态:" + bean.asynchronizedExecutor(toEmails[i], title, templetPath, emailModel));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
