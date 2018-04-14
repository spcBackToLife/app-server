package com.pk.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pk.entity.UserEntity;
import com.pk.mapper.ConcernMapper;
import com.pk.util.Auth;
import com.pk.util.MyStaticStrings;
import com.pk.util.Upload;
import com.pk.util.UserSession;

@RestController
@RequestMapping("/concern")
public class ConcernController {

	@Autowired
	private ConcernMapper mapper;

	@RequestMapping("/add-concern")
	public void addConcern(HttpServletRequest req, HttpServletResponse rsp) {

		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String concerned_id = (String) dto.get("concerned_id");
		String beConcernedName = (String) dto.get("beConcernedName");
		String token = (String) dto.get("token");
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		UserEntity user = UserSession.get(req);
		if (Auth.auth(token, req)) {
			if (mapper.checkWhetherConcerned(user.getId(), concerned_id).size() == 0) {
				mapper.addConcern(UUID.randomUUID().toString(), concerned_id, user.getId(), beConcernedName);
				dataToClient.put("isSuccess", "1");
				dataToClient.put("state", "1");
			} else {
				dataToClient.put("isSuccess", "0");// 关注失败!
				dataToClient.put("state", "1");
			}
		} else {
			dataToClient.put("state", "0");
		}
		BaseController.backToClient(rsp, dataToClient);
	}

	@RequestMapping("/change-remark-name")
	public void changeName(HttpServletRequest req, HttpServletResponse rsp) {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String concerned_id = (String) dto.get("concerned_id");
		String remark_name = (String) dto.get("remark_name");
		String token = (String) dto.get("token");
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		UserEntity user = UserSession.get(req);
		if (!Auth.auth(token, req)) {
			dataToClient.put("state", "0");
			BaseController.backToClient(rsp, dataToClient);
		}
		mapper.changeRemarkName(remark_name, concerned_id, user.getId());
		dataToClient.put("state", "1");
		dataToClient.put("isSuccess", "1");
		BaseController.backToClient(rsp, dataToClient);
	}

	@RequestMapping("/cancel-concern")
	public void cancel(HttpServletRequest req, HttpServletResponse rsp) {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String concerned_id = (String) dto.get("concerned_id");
		String token = (String) dto.get("token");
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		UserEntity user = UserSession.get(req);
		if (!Auth.auth(token, req)) {
			dataToClient.put("state", "0");
			BaseController.backToClient(rsp, dataToClient);
		}
		mapper.cancelConcern(user.getId(), concerned_id);
		dataToClient.put("state", "1");
		dataToClient.put("isSuccess", "1");
		BaseController.backToClient(rsp, dataToClient);
	}

	@RequestMapping("/add-report")
	public void report(HttpServletRequest req, HttpServletResponse rsp) {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String blog_id = (String) dto.get("blog_id");
		String token = (String) dto.get("token");
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		UserEntity user = UserSession.get(req);
		if (Auth.auth(token, req)) {
			if (mapper.checkWhetherReported(blog_id, user.getId()) == null) {
				mapper.addReport(blog_id, user.getId());
				mapper.updateReportCount(blog_id);
				dataToClient.put("isSuccess", "1");
				dataToClient.put("state", "1");
			} else {
				dataToClient.put("state", "1");
				dataToClient.put("isSuccess", "0");// 已经删除
			}
		} else {
			dataToClient.put("state", "0");
		}
		BaseController.backToClient(rsp, dataToClient);
	}

	@RequestMapping("/add-dianzan")
	public void dianzan(HttpServletRequest req, HttpServletResponse rsp) {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String blog_id = (String) dto.get("blog_id");
		String token = (String) dto.get("token");
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		UserEntity user = UserSession.get(req);
		if (Auth.auth(token, req)) {
			if (mapper.checkWhetherDianzaned(blog_id, user.getId()) == null) {
				mapper.addDianZan(UUID.randomUUID().toString(), blog_id, user.getId());
				mapper.addDianZanCount(blog_id);
				dataToClient.put("isSuccess", "1");// 点赞成功
				dataToClient.put("state", "1");
			} else {
				dataToClient.put("state", "1");
				dataToClient.put("isSuccess", "0");// 已经点赞
			}
		} else {
			dataToClient.put("state", "0");
		}
		BaseController.backToClient(rsp, dataToClient);
	}

	@RequestMapping("/add-bg")
	public void addBg(@RequestParam("file") MultipartFile[] myfile, HttpServletRequest req, HttpServletResponse rsp)
			throws Exception {
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String token = (String) dto.get("token");
		UserEntity user = UserSession.get(req);
		if (Auth.auth(token, req)) {
			String IMAGEPATH = "";// 头像图片访问路径
			String COMIMAGEPATH = "";// 压缩头像图片访问路径
			Upload uploadFile = new Upload();
			Map<String, Object> map = uploadFile.uploadImage(myfile, req, rsp, user.getMobile(),
					MyStaticStrings.getPersonalInformationPath());
			IMAGEPATH = (String) map.get("ImageOutPath");
			COMIMAGEPATH = (String) map.get("ComImageOutPath");
			// 保存头像
			mapper.saveBg(COMIMAGEPATH, IMAGEPATH, user.getId());
			dataToClient.put("UE_USER_BG", IMAGEPATH);
			dataToClient.put("UE_USER_COMBG", COMIMAGEPATH);
			dataToClient.put("isSuccess", "1");
			dataToClient.put("state", "1");
		} else {
			dataToClient.put("state", "0");
		}
		BaseController.backToClient(rsp, dataToClient);
	}
}
