package com.pk.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pk.mapper.LoginMapper;
import com.pk.util.Aps;
import com.pk.util.Auth;
import com.pk.util.MyStaticStrings;
import com.pk.util.SendEmail;
import com.pk.util.SendMessage;
import com.pk.util.UserSession;

@RestController
@RequestMapping("/home")
public class LoginController {

	@Autowired
	LoginMapper mapper;

	@RequestMapping("/add-personality")
	public void addPersonality(HttpServletRequest req, HttpServletResponse rsp) {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String personality = (String) dto.get("personality");
		String token = (String) dto.get("token");
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		if (!Auth.auth(token, req)) {
			dataToClient.put("state", "0");
			BaseController.backToClient(rsp, dataToClient);
		}
		// 添加性格-id字段使用用户id则唯一:直接报错就不插入重复key
		mapper.Personality(UserSession.get(req).getId(), UserSession.get(req).getId(), personality);
		dataToClient.put("isSuccess", "1");
		dataToClient.put("state", "1");
		BaseController.backToClient(rsp, dataToClient);
	}

	@RequestMapping("/find-password")
	public void findPassword(HttpServletRequest req, HttpServletResponse rsp) throws Exception {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String phoneNumber = (String) dto.get("phoneNumber");
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		phoneNumber = Aps.encrypt_1(phoneNumber, MyStaticStrings.getApskey());
		if (mapper.checkWhetherCanRegisterPhone(phoneNumber).size() != 0) {
			String recognizeCode = String.valueOf((int) (Math.random() * 9000 + 1000));
			req.getSession().setAttribute("recognizeCode", recognizeCode);
			req.getSession().setAttribute("MOBILE", phoneNumber);
			dataToClient.put("recognizeCode", recognizeCode);
			Date lastSendTime = (Date) req.getSession().getAttribute("lastSendTime");
			if (lastSendTime == null && ((System.currentTimeMillis() - lastSendTime.getTime()) / 1000 > 50)) {
				SendMessage.sendMessage(phoneNumber, recognizeCode);
				req.getSession().setAttribute("lastSendTime", new Date());
			}
			dataToClient.put("state", "1");
			dataToClient.put("isSuccess", "1");
		} else {
			dataToClient.put("state", "0");
		}
		BaseController.backToClient(rsp, dataToClient);
	}

	@RequestMapping("/find-password-email")
	public void findPasswordByEmail(HttpServletRequest req, HttpServletResponse rsp) throws Exception {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String email = (String) dto.get("email");
		String email_dec = email;
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		email = Aps.encrypt_1(email, MyStaticStrings.getApskey());
		if (mapper.checkWhetherCanRegisterEmail(email).size() != 0) {
			String recognizeCode = String.valueOf((int) (Math.random() * 9000 + 1000));
			req.getSession().setAttribute("recognizeCode", recognizeCode);
			req.getSession().setAttribute("email", email);
			dataToClient.put("recognizeCode", recognizeCode);
			Date lastSendTime = (Date) req.getSession().getAttribute("lastSendTime");
			if (lastSendTime == null || ((System.currentTimeMillis() - lastSendTime.getTime()) / 1000 > 50)) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							SendEmail.sendEmail(email_dec, recognizeCode);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
				req.getSession().setAttribute("lastSendTime", new Date());
			}
			dataToClient.put("state", "1");
			dataToClient.put("isSuccess", "1");
		} else {
			dataToClient.put("state", "0");
		}
		BaseController.backToClient(rsp, dataToClient);
	}

	@RequestMapping("/update-password")
	public void updatePassword(HttpServletRequest req, HttpServletResponse rsp) {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String pword = (String) dto.get("pword");
		String recognizeCode = (String) dto.get("recognizeCode");
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		String sessionUser_mobile = (String) req.getSession().getAttribute("MOBILE");
		String sessionUser_recognizeCode = (String) req.getSession().getAttribute("recognizeCode");
		if (sessionUser_mobile != null && sessionUser_recognizeCode != null) {
			if (sessionUser_recognizeCode.equals(recognizeCode)) {
				// 更新
				pword = Aps.encrypt_1(pword, MyStaticStrings.getApskey());
				mapper.updatePasswordByPhone(sessionUser_mobile, pword);
				dataToClient.put("state", "1");
				dataToClient.put("isSuccess", "1");// 操作成功
			} else {
				dataToClient.put("state", "1");
				dataToClient.put("isSuccess", "2");// 验证码错误
			}
		} else {
			dataToClient.put("state", "0");
			dataToClient.put("isSuccess", "0");// 验证码为空！
		}
		BaseController.backToClient(rsp, dataToClient);
	}

	@RequestMapping("/update-password-email")
	public void updatePasswordByEmail(HttpServletRequest req, HttpServletResponse rsp) {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String pword = (String) dto.get("pword");
		String recognizeCode = (String) dto.get("recognizeCode");
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		String sessionUser_email = (String) req.getSession().getAttribute("email");
		String sessionUser_recognizeCode = (String) req.getSession().getAttribute("recognizeCode");
		if (sessionUser_email != null && sessionUser_recognizeCode != null) {
			if (sessionUser_recognizeCode.equals(recognizeCode)) {
				// 更新
				pword = Aps.encrypt_1(pword, MyStaticStrings.getApskey());
				mapper.updatePasswordByEmail(sessionUser_email, pword);
				dataToClient.put("state", "1");
				dataToClient.put("isSuccess", "1");// 操作成功
			} else {
				dataToClient.put("state", "1");
				dataToClient.put("isSuccess", "2");// 验证码错误
			}
		} else {
			dataToClient.put("state", "0");
			dataToClient.put("isSuccess", "0");// 验证码为空！
		}
		BaseController.backToClient(rsp, dataToClient);
	}

	@RequestMapping("/login")
	public void login(HttpServletRequest req, HttpServletResponse rsp) {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String account = (String) dto.get("account");
		String pword = (String) dto.get("pword");
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		Pattern pattern = Pattern.compile("^((13[0-9])|(17[0-9])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$"); // 正则表达式需要一段时间更新
		Matcher matcher = pattern.matcher(account);
		if (matcher.matches()) {
			account = Aps.encrypt_1(account, MyStaticStrings.getApskey());
			pword = Aps.encrypt_1(pword, MyStaticStrings.getApskey());
			Map<String, Object> user = mapper.Login(account, pword);

			if (user != null) {
				if (((String) (user.get("UL_ISDELETED"))).equals("0")) {
					String token = UUID.randomUUID().toString();
					UserSession.put(req, account, pword, token, (String) user.get("UL_ID"), (String) user.get("UB_SEX"),
							(String) user.get("UE_NAME"), "datanull");
					dataToClient.put("state", "1");
					dataToClient.put("isSuccess", "1");
					dataToClient.put("token", token);
					dataToClient.put("UL_ID", user.get("UL_ID"));
					dataToClient.put("SN_NUMBER", user.get("SN_NUMBER"));
				} else {
					dataToClient.put("state", "1");
					dataToClient.put("isSuccess", "-1");// 账号被封号
				}
			} else {

				dataToClient.put("state", "1");
				dataToClient.put("isSuccess", "0");// 账号不存在
			}
		} else {
			account = Aps.encrypt_1(account, MyStaticStrings.getApskey());
			pword = Aps.encrypt_1(pword, MyStaticStrings.getApskey());
			Map<String, Object> user = mapper.LoginByEmail(account, pword);
			if (user != null) {
				if (((String) (user.get("UL_ISDELETED"))).equals("0")) {
					String token = UUID.randomUUID().toString();
					UserSession.put(req, "datanull", pword, token, (String) user.get("UL_ID"),
							(String) user.get("UB_SEX"), (String) user.get("UE_NAME"), account);
					dataToClient.put("state", "1");
					dataToClient.put("isSuccess", "1");
					dataToClient.put("token", token);
					dataToClient.put("UL_ID", user.get("UL_ID"));
					dataToClient.put("SN_NUMBER", user.get("SN_NUMBER"));
				} else {
					dataToClient.put("state", "1");
					dataToClient.put("isSuccess", "-1");// 账号被封号
				}
			} else {

				dataToClient.put("state", "1");
				dataToClient.put("isSuccess", "0");// 账号不存在
			}
		}
		BaseController.backToClient(rsp, dataToClient);
	}

	@RequestMapping("/register")
	public void register(HttpServletRequest req, HttpServletResponse rsp) throws Exception {
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String sex = (String) dto.get("sex");
		String pword = (String) dto.get("pword");
		String name = (String) dto.get("name");
		String phoneNumber = (String) dto.get("phoneNumber");
		String recognizeCode = (String) dto.get("recognizeCode");
		if (req.getSession().getAttribute("recognizeCode").equals(recognizeCode)) {
			if (mapper.checkWhetherCanRegisterPhone((String) req.getSession().getAttribute("MOBILE")).size() == 0) {
				phoneNumber = Aps.encrypt_1(phoneNumber, MyStaticStrings.getApskey());
				pword = Aps.encrypt_1(pword, MyStaticStrings.getApskey());
				String uuid = UUID.randomUUID().toString();
				String snnumber = "";
				// 查询用户的snnumber通过手机号
				List<Map<String, Object>> userSnnumber = mapper.getSnumberByPhone(phoneNumber);
				if (userSnnumber.size() == 0) {
					// 添加到登录表
					mapper.register(pword, phoneNumber, "datanull", uuid);
					// 添加进入登记表
					mapper.addUserLevel(UUID.randomUUID().toString(), uuid);
					// 添加个人扩展信息
					mapper.addUserExtInf(UUID.randomUUID().toString(), uuid, name);
					// 添加个人信息
					mapper.addBaseInf(UUID.randomUUID().toString(), uuid, name, phoneNumber, sex);
					// 添加snnumber:1.查询数据库目前最大的，在上面加1. 若是没数据会报错，但不可能没数据，所以不做处理。
					List<Map<String, Object>> maxNum = mapper.findLatestNumber();
					String maxNumber = "";
					String maxCount = "";
					if (maxNum.size() == 0) {
						maxNumber = "100000";
						maxCount = "0";
					} else {
						maxNumber = (1 + Integer.parseInt((String) maxNum.get(0).get("SN_NUMBER"))) + "";
						maxCount = (1 + (int) maxNum.get(0).get("SN_COUNT")) + "";
					}
					snnumber = maxNumber;
					mapper.addNumber(UUID.randomUUID().toString(), maxCount, uuid, maxNumber);
				} else {
					mapper.addFirstNumber(UUID.randomUUID().toString(), uuid);
					snnumber = "100000";
				}
				String token = UUID.randomUUID().toString();
				// 注册成功相当于登录，把用户相关信息放入session中
				UserSession.put(req, phoneNumber, pword, token, uuid, sex, name, "datanull");
				dataToClient.put("token", token);
				dataToClient.put("SN_NUMBER", snnumber);
				dataToClient.put("UL_ID", uuid);
			} else {
				// 防止重复注册
			}
			// 注册成功
			dataToClient.put("state", "1");
			dataToClient.put("isSuccess", "1");
		} else {
			// 验证码错误
			dataToClient.put("state", "1");
			dataToClient.put("isSuccess", "2");// 验证码错误
		}
		BaseController.backToClient(rsp, dataToClient);
	}

	@RequestMapping("/register-email")
	public void registerByEmail(HttpServletRequest req, HttpServletResponse rsp) throws Exception {
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String sex = (String) dto.get("sex");
		String pword = (String) dto.get("pword");
		String name = (String) dto.get("name");
		String email = (String) dto.get("email");
		String recognizeCode = (String) dto.get("recognizeCode");
		if (req.getSession().getAttribute("recognizeCode").equals(recognizeCode)) {
			if (mapper.checkWhetherCanRegisterEmail((String) req.getSession().getAttribute("email")).size() == 0) {
				email = Aps.encrypt_1(email, MyStaticStrings.getApskey());
				pword = Aps.encrypt_1(pword, MyStaticStrings.getApskey());
				String uuid = UUID.randomUUID().toString();
				String snnumber = "";
				// 查询用户的snnumber通过手机号
				List<Map<String, Object>> userSnnumber = mapper.getSnumberByPhone(email);
				if (userSnnumber.size() == 0) {
					// 添加到登录表
					mapper.register(pword, "datanull", email, uuid);
					// 添加进入登记表
					mapper.addUserLevel(UUID.randomUUID().toString(), uuid);
					// 添加个人扩展信息
					mapper.addUserExtInf(UUID.randomUUID().toString(), uuid, name);
					// 添加个人信息
					mapper.addBaseInf(UUID.randomUUID().toString(), uuid, name, "datanull", sex);
					// 添加snnumber:1.查询数据库目前最大的，在上面加1. 若是没数据会报错，但不可能没数据，所以不做处理。
					List<Map<String, Object>> maxNum = mapper.findLatestNumber();
					String maxNumber = "";
					String maxCount = "";
					if (maxNum.size() == 0) {
						maxNumber = "100000";
						maxCount = "0";
					} else {
						maxNumber = (1 + Integer.parseInt((String) maxNum.get(0).get("SN_NUMBER"))) + "";
						maxCount = (1 + (int) maxNum.get(0).get("SN_COUNT")) + "";
					}
					snnumber = maxNumber;
					mapper.addNumber(UUID.randomUUID().toString(), maxCount, uuid, maxNumber);
				} else {
					mapper.addFirstNumber(UUID.randomUUID().toString(), uuid);
					snnumber = "100000";
				}
				String token = UUID.randomUUID().toString();
				// 注册成功相当于登录，把用户相关信息放入session中
				UserSession.put(req, "datanull", pword, token, uuid, sex, name, email);
				dataToClient.put("token", token);
				dataToClient.put("SN_NUMBER", snnumber);
				dataToClient.put("UL_ID", uuid);
			} else {
				// 防止重复注册
			}
			// 注册成功
			dataToClient.put("state", "1");
			dataToClient.put("isSuccess", "1");
		} else {
			// 验证码错误
			dataToClient.put("state", "1");
			dataToClient.put("isSuccess", "2");// 验证码错误
		}
		BaseController.backToClient(rsp, dataToClient);
	}

	@RequestMapping("/check-whether-can-register")
	public void checkWtheCanReigister(HttpServletRequest req, HttpServletResponse rsp) throws Exception {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String phoneNumber = (String) dto.get("phoneNumber");
		Pattern pattern = Pattern.compile("^((13[0-9])|(17[0-9])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$"); // 正则表达式需要一段时间更新
		Matcher matcher = pattern.matcher(phoneNumber);
		if (matcher.matches()) {
			// 进行查询
			phoneNumber = Aps.encrypt_1(phoneNumber, MyStaticStrings.getApskey());
			if (mapper.checkWhetherCanRegisterPhone(phoneNumber).size() == 0) {
				String recognizeCode = String.valueOf((int) (Math.random() * 9000 + 1000));
				req.getSession().setAttribute("recognizeCode", recognizeCode);
				req.getSession().setAttribute("MOBILE", phoneNumber);
				// 短信发送验证码
				Date lastSendTime = (Date) req.getSession().getAttribute("lastSendTime");
				if (lastSendTime == null || ((System.currentTimeMillis() - lastSendTime.getTime()) / 1000 > 50)) {
					SendMessage.sendMessage(phoneNumber, recognizeCode);
					req.getSession().setAttribute("lastSendTime", new Date());
				}
				Map<String, Object> dataToClient = new HashMap<String, Object>() {
					{
						put("recognizeCode", recognizeCode);
						put("isSuccess", "1");
						put("state", "1");
					}
				};
				BaseController.backToClient(rsp, dataToClient);
			} else {
				Map<String, Object> dataToClient = new HashMap<String, Object>() {
					{
						put("recognizeCode", "");
						put("isSuccess", "0");
						put("state", "1");
					}
				};
				BaseController.backToClient(rsp, dataToClient);
			}

		} else

		{
			Map<String, Object> dataToClient = new HashMap<String, Object>() {
				{
					put("state", "0");
				}
			};
			BaseController.backToClient(rsp, dataToClient);
		}
	}

	@RequestMapping("/check-whether-can-register-email")
	public void checkWtheCanReigisterByEmail(HttpServletRequest req, HttpServletResponse rsp) throws Exception {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String email = (String) dto.get("email");
		String email_dec = email;
		System.out.println("email:" + email);
		Pattern pattern = Pattern.compile("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}"); // 正则表达式需要一段时间更新
		Matcher matcher = pattern.matcher(email);
		if (matcher.matches()) {
			// 进行查询
			email = Aps.encrypt_1(email, MyStaticStrings.getApskey());
			if (mapper.checkWhetherCanRegisterEmail(email).size() == 0) {
				String recognizeCode = String.valueOf((int) (Math.random() * 9000 + 1000));
				req.getSession().setAttribute("recognizeCode", recognizeCode);
				req.getSession().setAttribute("email", email);
				// 短信发送验证码
				Date lastSendTime = (Date) req.getSession().getAttribute("lastSendTime");
				if (lastSendTime == null || ((System.currentTimeMillis() - lastSendTime.getTime()) / 1000 > 50)) {
					Map<String, Object> dataToClient = new HashMap<String, Object>() {
						{
							put("recognizeCode", recognizeCode);
							put("isSuccess", "1");
							put("state", "1");
						}
					};
					System.out.println("recognizeCode:" + dataToClient.get("recognizeCode"));
					BaseController.backToClient(rsp, dataToClient);
					// SendEmail.sendEmail(email_dec, recognizeCode);
					SendEmail.send(email_dec, "engram@paralzone.com", "您的验证码：" + recognizeCode, "engram验证",
							"您的验证码为：" + recognizeCode, "engram", "");
					req.getSession().setAttribute("lastSendTime", new Date());
				} else {

					Map<String, Object> dataToClient = new HashMap<String, Object>() {
						{
							put("recognizeCode", recognizeCode);
							put("isSuccess", "1");
							put("state", "1");
						}
					};
					BaseController.backToClient(rsp, dataToClient);

				}
			} else {
				Map<String, Object> dataToClient = new HashMap<String, Object>() {
					{
						put("recognizeCode", "");
						put("isSuccess", "0");
						put("state", "1");
					}
				};
				BaseController.backToClient(rsp, dataToClient);
			}

		} else

		{
			Map<String, Object> dataToClient = new HashMap<String, Object>() {
				{
					put("state", "0");
				}
			};
			BaseController.backToClient(rsp, dataToClient);
		}
	}

}
