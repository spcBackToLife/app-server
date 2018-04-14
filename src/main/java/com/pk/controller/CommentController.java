package com.pk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pk.entity.UserEntity;
import com.pk.mapper.CommentMapper;
import com.pk.util.Auth;
import com.pk.util.MyStaticStrings;
import com.pk.util.Upload;
import com.pk.util.UserSession;

@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentMapper mapper;

	/*
	 * 0.增加经验。 <1>是1级评论：评论人是否是作者本身。 是：不提示任何人。 否： 提示作者。 <2>是2级回复：评论人是否是作者本身。
	 * 是：提示评论人。 否： 提示评论人。
	 */
	@RequestMapping("/save-text-comment")
	public void save(HttpServletRequest req, HttpServletResponse rsp) {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String blog_id = (String) dto.get("blog_id");
		String be_replied_id = (String) dto.get("be_replied_id");
		String blog_creater_id = (String) dto.get("blog_creater_id");
		String level = (String) dto.get("level");
		String content = (String) dto.get("content");
		String token = (String) dto.get("token");
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		UserEntity user = UserSession.get(req);// 评论人
		if (Auth.auth(token, req)) {
			// 增加经验,提示作者.NEED TO FINISH
			// 保存评论
			String comment_id = UUID.randomUUID().toString();
			mapper.saveComment(comment_id, blog_creater_id, blog_id, "datanull", user.getId(), "datanull", level,
					be_replied_id, content, "datanull");
			// 添加与我相关
			mapper.addNotice(UUID.randomUUID().toString(), blog_creater_id, blog_id, comment_id, user.getId());
			// 添加与我相关个数
			mapper.addNoticeCount(UUID.randomUUID().toString(), mapper.getRepliedUserId(be_replied_id));
			// 如果是回复，更新评论的child。
			if (Integer.parseInt(level) == 2) {
				mapper.updateWheTherHasChild(be_replied_id);
			}
			// 更新评论数目
			mapper.updateCommentCount(blog_id);
			dataToClient.put("state", "1");

		} else {
			dataToClient.put("state", "0");
		}

		BaseController.backToClient(rsp, dataToClient);
	}

	@RequestMapping("/get-notice-count")
	public void getNoticeCount(HttpServletRequest req, HttpServletResponse rsp) {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String token = (String) dto.get("token");
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		UserEntity user = UserSession.get(req);
		if (!Auth.auth(token, req)) {
			dataToClient.put("state", "0");
			BaseController.backToClient(rsp, dataToClient);
		}

		dataToClient.put("isSuccess", "1");
		dataToClient.put("commentCount", mapper.getNoticeCount(user.getId()));
		dataToClient.put("state", "1");
		BaseController.backToClient(rsp, dataToClient);
	}

	@RequestMapping("/get-notice")
	public void getNotice(HttpServletRequest req, HttpServletResponse rsp) {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String token = (String) dto.get("token");
		String nowPage = (String) dto.get("nowPage");
		String pageSize = (String) dto.get("pageSize");

		Map<String, Object> dataToClient = new HashMap<String, Object>();
		UserEntity user = UserSession.get(req);
		if (!Auth.auth(token, req)) {
			dataToClient.put("state", "0");
			BaseController.backToClient(rsp, dataToClient);
		}
		List<Map<String, Object>> result = mapper.getNotice(UserSession.get(req).getId(), Integer.parseInt(pageSize),
				Integer.parseInt(nowPage) * Integer.parseInt(pageSize));
		// 取消个人与我相关提示个数
		mapper.deleteNoticeCount(user.getId());
		result.stream().map(x -> {
			mapper.deleteNotice((String) x.get("NO_ID"));
			x.put("BASE_FILE_URL", MyStaticStrings.getBaseFileIp());
			// 查找子集
			x.put("reply", mapper.getNoticeChildren((String) x.get("CO_ID")));
			return x;
		}).collect(Collectors.toList());
		dataToClient.put("isSuccess", "1");
		dataToClient.put("result", result);
		dataToClient.put("state", "1");
		BaseController.backToClient(rsp, dataToClient);
	}

	@RequestMapping("/save-voice-comment")
	public void saveVoice(HttpServletRequest req, HttpServletResponse rsp, @RequestParam("file") MultipartFile[] myfile)
			throws Exception {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String token = (String) dto.get("token");
		String blog_id = (String) dto.get("blog_id");
		String blog_creater_id = (String) dto.get("blog_creater_id");
		String be_replied_id = (String) dto.get("be_replied_id");
		String level = (String) dto.get("level");
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		UserEntity user = UserSession.get(req);// 评论人
		if (Auth.auth(token, req)) {
			// 增加经验,提示作者.NEED TO FINISH
			Upload up = new Upload();
			String voicePath = up.uploadVoice(myfile, req, rsp, user.getMobile(),
					MyStaticStrings.getDynamicVoicePath());
			// 保存评论
			String comment_id = UUID.randomUUID().toString();
			mapper.saveComment(comment_id, blog_creater_id, blog_id, "datanull", user.getId(), "datanull", level,
					be_replied_id, "datanull", voicePath);
			// 添加与我相关
			mapper.addNotice(UUID.randomUUID().toString(), blog_creater_id, blog_id, comment_id, user.getId());
			// 添加与我相关个数
			mapper.addNoticeCount(UUID.randomUUID().toString(), user.getId());
			// 如果是回复，更新评论的child。
			if (Integer.parseInt(level) == 2) {
				mapper.updateWheTherHasChild(be_replied_id);
			}
			// 更新评论数目
			mapper.updateCommentCount(blog_id);
			dataToClient.put("state", "1");

		} else {
			dataToClient.put("state", "0");
		}

		BaseController.backToClient(rsp, dataToClient);
	}

	// 作者本身得到评论
	@RequestMapping("/get-comment-author-self")
	public void getComment(HttpServletRequest req, HttpServletResponse rsp) {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String pageSize = (String) dto.get("pageSize");
		String nowPage = (String) dto.get("nowPage");
		String blog_id = (String) dto.get("blog_id");
		String token = (String) dto.get("token");
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		UserEntity user = UserSession.get(req);
		if (!Auth.auth(token, req)) {
			dataToClient.put("state", "0");
			BaseController.backToClient(rsp, dataToClient);
		}
		List<Map<String, Object>> dataForClient = mapper.getCommentToAuthorSelf(blog_id, Integer.parseInt(pageSize),
				Integer.parseInt(pageSize) * Integer.parseInt(nowPage));
		// 查询每条对应的子回复
		String CO_ID = "";
		Map<String, Object> CO_ID_map = new HashMap<String, Object>();
		List<Map<String, Object>> reply = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < dataForClient.size(); i++) {
			CO_ID_map = dataForClient.get(i);
			CO_ID = (String) CO_ID_map.get("CO_ID");// 得到每条1级的ID
			reply = mapper.getChildrenOfCommentAuthor(blog_id, CO_ID, user.getId());
			for (Map<String, Object> eachreply : reply) {
				eachreply.put("BASE_FILE_URL", MyStaticStrings.getBaseFileIp());
			}
			CO_ID_map.put("reply", reply);
			dataForClient.set(i, CO_ID_map);
		}
		dataForClient.stream().map(x -> x.put("BASE_FILE_URL", MyStaticStrings.getBaseFileIp()))
				.collect(Collectors.toList());
		dataToClient.put("isSuccess", "1");
		dataToClient.put("result", dataForClient);
		dataToClient.put("state", "1");

		BaseController.backToClient(rsp, dataToClient);
	}

	// 对方看到的评论
	@RequestMapping("/get-comment-others-see")
	public void getCommentByAnother(HttpServletRequest req, HttpServletResponse rsp) {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String pageSize = (String) dto.get("pageSize");
		String nowPage = (String) dto.get("nowPage");
		String blog_id = (String) dto.get("blog_id");
		String token = (String) dto.get("token");
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		UserEntity user = UserSession.get(req);
		if (!Auth.auth(token, req)) {
			dataToClient.put("state", "0");
			BaseController.backToClient(rsp, dataToClient);
		}
		List<Map<String, Object>> dataForClient = mapper.getCommentToMe(blog_id, Integer.parseInt(pageSize),
				Integer.parseInt(pageSize) * Integer.parseInt(nowPage), user.getId());
		// 查询每条对应的子回复
		String CO_ID = "";
		Map<String, Object> CO_ID_map = new HashMap<String, Object>();
		List<Map<String, Object>> reply = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < dataForClient.size(); i++) {
			CO_ID_map = dataForClient.get(i);
			CO_ID = (String) CO_ID_map.get("CO_ID");// 得到每条1级的ID
			reply = mapper.getChildrenOfCommentOthers(blog_id, CO_ID, user.getId());
			for (Map<String, Object> eachreply : reply) {
				eachreply.put("BASE_FILE_URL", MyStaticStrings.getBaseFileIp());
			}
			CO_ID_map.put("reply", reply);
			dataForClient.set(i, CO_ID_map);
		}
		dataForClient.stream().map(x -> x.put("BASE_FILE_URL", MyStaticStrings.getBaseFileIp()))
				.collect(Collectors.toList());
		dataToClient.put("isSuccess", "1");
		dataToClient.put("result", dataForClient);
		dataToClient.put("state", "1");

		BaseController.backToClient(rsp, dataToClient);
	}
}

// HttpServletRequest req, HttpServletResponse rsp, @RequestParam("token")
// String token) {
// Map<String, Object> dataToClient = new HashMap<String, Object>();
// UserEntity user = UserSession.get(req);
// if (Auth.auth(token, req)) {
// if (true) {
// dataToClient.put("isSuccess", "1");
// dataToClient.put("state", "1");
// } else {
// dataToClient.put("state", "1");
// dataToClient.put("isSuccess", "0");
// }
// } else {
// dataToClient.put("state", "0");
// }
