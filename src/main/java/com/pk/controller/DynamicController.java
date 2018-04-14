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
import com.pk.mapper.DynamicMapper;
import com.pk.util.Auth;
import com.pk.util.MyStaticStrings;
import com.pk.util.Upload;
import com.pk.util.UserSession;

@RestController
@RequestMapping("/dynamic")
public class DynamicController {

	@Autowired
	private DynamicMapper mapper;
	@Autowired
	private ConcernMapper concern;

	@RequestMapping("/get-personal-info")
	public void getPersonalInfo(HttpServletRequest req, HttpServletResponse rsp) {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String token = (String) dto.get("token");
		String user_id = (String) dto.get("user_id");
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		if (!Auth.auth(token, req)) {
			dataToClient.put("state", "0");
			BaseController.backToClient(rsp, dataToClient);
		}
		Map<String, Object> userInfo = mapper.getPersonInfo(user_id);

		Map<String, Object> dataForClient = new HashMap<String, Object>();
		System.out.println(UserSession.get(req).getId());
		dataForClient.put("concerned",
				concern.checkWhetherConcerned(UserSession.get(req).getId(), user_id).size() == 1 ? "yes" : "no");
		dataForClient.put("BASE_FILE_URL", MyStaticStrings.getBaseFileIp());
		dataForClient.put("UB_NAME", userInfo.get("UB_NAME"));
		dataForClient.put("UE_NAME", userInfo.get("UB_NAME"));
		dataForClient.put("UB_SEX", userInfo.get("UB_SEX"));
		dataForClient.put("UB_CONSTELLATION", userInfo.get("UB_CONSTELLATION"));
		dataForClient.put("UE_LOCATION", userInfo.get("UE_LOCATION"));
		dataForClient.put("UB_INTRODUCTION", userInfo.get("UB_INTRODUCTION"));
		dataForClient.put("UE_HEADICON", userInfo.get("UE_HEADICON"));
		dataForClient.put("UE_COMHEADICON", userInfo.get("UE_COMHEADICON"));
		dataForClient.put("UE_USER_BG", userInfo.get("UE_USER_BG"));
		dataForClient.put("SN_NUMBER", userInfo.get("SN_NUMBER"));
		dataForClient.put("NOTICE_COUNTS", userInfo.get("NOTICE_COUNTS"));
		dataForClient.put("FANS_COUNT", userInfo.get("FANS_COUNT"));
		dataForClient.put("CONCERN_COUNT", userInfo.get("CONCERN_COUNT"));
		dataForClient.put("USER_LEVEL", userInfo.get("LE_LEVEL"));
		dataForClient.put("USER_CH", userInfo.get("LE_CH"));
		dataToClient.put("result", dataForClient);
		dataToClient.put("isSuccess", "1");
		dataToClient.put("state", "1");
		BaseController.backToClient(rsp, dataToClient);
	}

	@RequestMapping("/delete-dynamic")
	public void dynamic(HttpServletRequest req, HttpServletResponse rsp) {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String token = (String) dto.get("token");
		String blog_id = (String) dto.get("blog_id");
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		UserEntity user = UserSession.get(req);
		Map<String, Object> result = mapper.whetherIsMyDynamic(blog_id, user.getId());
		if (Auth.auth(token, req)) {
			if (mapper.whetherIsMyDynamic(blog_id, user.getId()) != null) {
				mapper.deleteDynamic(blog_id);
				mapper.deleteMyPackage(blog_id);
				dataToClient.put("isSuccess", "1");
				dataToClient.put("state", "1");
			} else {
				dataToClient.put("state", "1");
				dataToClient.put("isSuccess", "0");
			}

		} else {
			dataToClient.put("state", "0");
		}
		BaseController.backToClient(rsp, dataToClient);
	}

	@RequestMapping("/save-personal-info")
	public void savePersonalInfo(@RequestParam("file") MultipartFile[] myfile, HttpServletRequest req,
			HttpServletResponse rsp) throws Exception {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String token = (String) dto.get("token");
		String constellation = (String) dto.get("constellation");
		String introduction = (String) dto.get("introduction");
		String location = (String) dto.get("location");
		String name = (String) dto.get("name");
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		if (Auth.auth(token, req)) {
			String IMAGEPATH = "";// 头像图片访问路径
			String COMIMAGEPATH = "";// 压缩头像图片访问路径
			Upload uploadFile = new Upload();
			UserEntity user = UserSession.get(req);
			Map<String, Object> map = uploadFile.uploadImage(myfile, req, rsp, user.getMobile(),
					MyStaticStrings.getPersonalInformationPath());
			IMAGEPATH = (String) map.get("ImageOutPath");
			COMIMAGEPATH = (String) map.get("ComImageOutPath");
			// 保存
			mapper.savePersonBaseInfo(user.getId(), constellation, introduction, name);
			mapper.savePersonExtInfo(location, COMIMAGEPATH, user.getId(), IMAGEPATH, constellation, introduction,
					name);
			dataToClient.put("UE_HEADICON", IMAGEPATH);
			dataToClient.put("isSuccess", "1");
			dataToClient.put("state", "1");
		} else {
			dataToClient.put("state", "0");
		}
		BaseController.backToClient(rsp, dataToClient);
	}

	@RequestMapping("/save-personal-head-icon")
	public void savePersonalHeadIcon(@RequestParam("file") MultipartFile[] myfile, HttpServletRequest req,
			HttpServletResponse rsp) throws Exception {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String token = (String) dto.get("token");
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		if (Auth.auth(token, req)) {
			String IMAGEPATH = "";// 头像图片访问路径
			String COMIMAGEPATH = "";// 压缩头像图片访问路径
			Upload uploadFile = new Upload();
			UserEntity user = UserSession.get(req);
			Map<String, Object> map = uploadFile.uploadImage(myfile, req, rsp, user.getMobile(),
					MyStaticStrings.getPersonalInformationPath());
			IMAGEPATH = (String) map.get("ImageOutPath");
			COMIMAGEPATH = (String) map.get("ComImageOutPath");
			// 保存头像
			mapper.saveHeadIcon(IMAGEPATH, COMIMAGEPATH, user.getId());
			dataToClient.put("UE_HEADICON", IMAGEPATH);
			dataToClient.put("isSuccess", "1");
			dataToClient.put("state", "1");
		} else {
			dataToClient.put("state", "0");
		}
		BaseController.backToClient(rsp, dataToClient);
	}

	@RequestMapping("/save")
	public void saveDynamic(@RequestParam("file") MultipartFile[] myfile, HttpServletRequest req,
			HttpServletResponse rsp) {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String token = (String) dto.get("token");
		String vb_latitude = (String) dto.get("vb_latitude");
		String vb_text = (String) dto.get("vb_text");
		String vb_range = (String) dto.get("vb_range");
		String vb_the_sex = (String) dto.get("vb_the_sex");
		String vb_title = (String) dto.get("vb_title");
		String vb_type = (String) dto.get("vb_type");
		String vb_friend_can_see = (String) dto.get("vb_friend_can_see");
		String vb_longitude = (String) dto.get("vb_longitude");
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		if (Auth.auth(token, req)) {
			dataToClient.put("state", "1");
			dataToClient.put("isSuccess", "1");
			Upload uploadfile = new Upload();
			String VB_IMAGE_PATH = "datanull";// 图片路径
			String VB_COMIMAGE_PATH = "datanull";// 压缩图片路径
			String VB_VIDEO_PATH = "datanull";// 视频路径
			try {
				VB_VIDEO_PATH = uploadfile.uploadVideo(myfile, req, rsp, UserSession.get(req).getMobile(),
						MyStaticStrings.getDynamicVideoPath());
				Map<String, Object> map = uploadfile.uploadImage(myfile, req, rsp, UserSession.get(req).getMobile(),
						MyStaticStrings.getDynamicImagePath());
				// String map = uploadfile.upload(myfile, req, rsp,
				// UserSession.get(req).getMobile(),
				// MyStaticStrings.getDynamicImagePath());
				// VB_IMAGE_PATH = map;
				VB_COMIMAGE_PATH = (String) map.get("ComImageOutPath");
				VB_IMAGE_PATH = (String) map.get("ImageOutPath");
			} catch (Exception e) {

				e.printStackTrace();
			}
			String VB_ID = UUID.randomUUID().toString();
			mapper.saveDynamic(VB_ID, VB_COMIMAGE_PATH, "0", VB_IMAGE_PATH, vb_latitude, UserSession.get(req).getId(),
					vb_longitude, "0", vb_range, vb_text, "", vb_the_sex, vb_title, vb_type, VB_VIDEO_PATH,
					vb_friend_can_see);
		} else {
			dataToClient.put("state", "0");
		}
		// 防止getWrite()正在使用中！
		// rsp.reset();
		BaseController.backToClient(rsp, dataToClient);
	}

	@RequestMapping("/save-only-for-us")
	public void saveDynamicByUs(@RequestParam("file") MultipartFile[] myfile, HttpServletRequest req,
			HttpServletResponse rsp) {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String token = (String) dto.get("token");
		String vb_latitude = (String) dto.get("vb_latitude");
		String vb_longitude = (String) dto.get("vb_longitude");
		String vb_the_sex = (String) dto.get("vb_the_sex");
		String vb_range = (String) dto.get("vb_range");
		String vb_text = (String) dto.get("vb_text");
		String vb_title = (String) dto.get("vb_title");
		String vb_type = (String) dto.get("vb_type");
		String vb_friend_can_see = (String) dto.get("vb_friend_can_see");
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		dataToClient.put("state", "1");
		dataToClient.put("isSuccess", "1");
		Upload uploadfile = new Upload();
		String VB_IMAGE_PATH = "datanull";// 图片路径
		String VB_COMIMAGE_PATH = "datanull";// 压缩图片路径
		String VB_VIDEO_PATH = "datanull";// 视频路径
		try {
			VB_VIDEO_PATH = uploadfile.uploadVideo(myfile, req, rsp, UserSession.get(req).getMobile(),
					MyStaticStrings.getDynamicVideoPath());
			Map<String, Object> map = uploadfile.uploadImage(myfile, req, rsp, UserSession.get(req).getMobile(),
					MyStaticStrings.getDynamicImagePath());
			// String map = uploadfile.upload(myfile, req, rsp,
			// UserSession.get(req).getMobile(),
			// MyStaticStrings.getDynamicImagePath());
			// VB_IMAGE_PATH = map;
			VB_COMIMAGE_PATH = (String) map.get("ComImageOutPath");
			VB_IMAGE_PATH = (String) map.get("ImageOutPath");
		} catch (Exception e) {

			e.printStackTrace();
		}
		String VB_ID = UUID.randomUUID().toString();
		mapper.saveDynamic(VB_ID, VB_COMIMAGE_PATH, "0", VB_IMAGE_PATH, vb_latitude, UserSession.get(req).getId(),
				vb_longitude, "0", vb_range, vb_text, "", vb_the_sex, vb_title, vb_type, VB_VIDEO_PATH,
				vb_friend_can_see);
		BaseController.backToClient(rsp, dataToClient);
	}
}
