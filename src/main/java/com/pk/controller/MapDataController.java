package com.pk.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pk.entity.UserEntity;
import com.pk.mapper.MapDataMapper;
import com.pk.util.Auth;
import com.pk.util.MyStaticStrings;
import com.pk.util.UserSession;

@RestController
@RequestMapping("/map-data")
public class MapDataController {
	private static final Logger logger = LoggerFactory.getLogger(MapDataController.class);
	@Autowired
	private MapDataMapper mapper;

	// 获得主页的随机动态---写成上拉下滑
	@RequestMapping("/get-map-data")
	public void getMapRandomData(HttpServletRequest req, HttpServletResponse rsp) {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String token = (String) dto.get("token");
		String longitude_SW = (String) dto.get("longitude_SW");
		String latitude_SW = (String) dto.get("latitude_SW");
		String longitude_NE = (String) dto.get("longitude_NE");
		String latitude_NE = (String) dto.get("latitude_NE");
		String searchSex = (String) dto.get("searchSex");
		String pageSize = (String) dto.get("pageSize");
		String nowPage = (String) dto.get("nowPage");

		UserEntity user = UserSession.get(req);
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		if (Auth.auth(token, req)) {
			List<Map<String, Object>> result = mapper.getMapRandomData(Double.parseDouble(latitude_NE),
					Double.parseDouble(latitude_SW), Double.parseDouble(longitude_SW), Double.parseDouble(longitude_NE),
					searchSex, user.getSex(), Integer.parseInt(pageSize),
					Integer.parseInt(nowPage) * Integer.parseInt(pageSize));
			result.stream().map(x -> {
				x.put("BASE_FILE_URL", MyStaticStrings.getBaseFileIp());
				if (mapper.checkWhetherPOP((String) x.get("VB_ID"), user.getId()).size() > 0) {
					x.put("ISSUPPORT", "yes");
				} else {
					x.put("ISSUPPORT", "no");
				}
				return x;
			}).collect(Collectors.toList());
			dataToClient.put("userId", user.getId());
			dataToClient.put("result", result);
			dataToClient.put("state", "1");
		} else {
			dataToClient.put("state", "0");
		}
		BaseController.backToClient(rsp, dataToClient);
	}

	// 获得我粉丝的动态
	@RequestMapping("/get-fans-dynamic")
	public void getMyFansDynamic(HttpServletRequest req, HttpServletResponse rsp) {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String token = (String) dto.get("token");
		String nowPage = (String) dto.get("nowPage");
		String pageSize = (String) dto.get("pageSize");
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		UserEntity user = UserSession.get(req);
		if (Auth.auth(token, req)) {
			List<Map<String, Object>> result = mapper.getMyFansDynamic(
					Integer.parseInt(nowPage) * Integer.parseInt(pageSize), Integer.parseInt(pageSize),
					UserSession.get(req).getSex(), UserSession.get(req).getId());
			result.stream().map(x -> {
				x.put("BASE_FILE_URL", MyStaticStrings.getBaseFileIp());
				if (mapper.checkWhetherPOP((String) x.get("VB_ID"), user.getId()).size() > 0) {
					x.put("ISSUPPORT", "yes");
				} else {
					x.put("ISSUPPORT", "no");
				}
				return x;
			}).collect(Collectors.toList());

			dataToClient.put("result", result);
			dataToClient.put("state", "1");
		} else {
			dataToClient.put("state", "0");
		}
		BaseController.backToClient(rsp, dataToClient);
	}

	// 获得我的粉丝的动态
	@RequestMapping("/get-concern-dynamic")
	public void getMyConcernnerDynamic(HttpServletRequest req, HttpServletResponse rsp) {
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String token = (String) dto.get("token");
		String nowPage = (String) dto.get("nowPage");
		String pageSize = (String) dto.get("pageSize");
		UserEntity user = UserSession.get(req);
		if (Auth.auth(token, req)) {
			List<Map<String, Object>> result = mapper.getMyConcernDynamic(
					Integer.parseInt(nowPage) * Integer.parseInt(pageSize), Integer.parseInt(pageSize),
					UserSession.get(req).getSex(), UserSession.get(req).getId());
			result.stream().map(x -> {
				x.put("BASE_FILE_URL", MyStaticStrings.getBaseFileIp());
				if (mapper.checkWhetherPOP((String) x.get("VB_ID"), user.getId()).size() > 0) {
					x.put("ISSUPPORT", "yes");
				} else {
					x.put("ISSUPPORT", "no");
				}
				return x;
			}).collect(Collectors.toList());
			dataToClient.put("result", result);
			dataToClient.put("state", "1");
		} else {
			dataToClient.put("state", "0");
		}
		BaseController.backToClient(rsp, dataToClient);
	}

	// 获得我的关注列表
	@RequestMapping("/get-my-concern")
	public void getMyConcern(HttpServletRequest req, HttpServletResponse rsp) {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String token = (String) dto.get("token");
		String nowPage = (String) dto.get("nowPage");
		String pageSize = (String) dto.get("pageSize");
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		UserEntity user = UserSession.get(req);
		if (Auth.auth(token, req)) {
			List<Map<String, Object>> result = mapper.getMyConcern(
					Integer.parseInt(nowPage) * Integer.parseInt(pageSize), Integer.parseInt(pageSize),
					UserSession.get(req).getId());
			result.stream().map(x -> {
				x.put("BASE_FILE_URL", MyStaticStrings.getBaseFileIp());
				return x;
			}).collect(Collectors.toList());
			dataToClient.put("result", result);
			dataToClient.put("state", "1");
		} else {
			dataToClient.put("state", "0");
		}
		BaseController.backToClient(rsp, dataToClient);
	}

	// 获得我的粉丝列表
	@RequestMapping("/get-fans")
	public void getFans(HttpServletRequest req, HttpServletResponse rsp) {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String token = (String) dto.get("token");
		String nowPage = (String) dto.get("nowPage");
		String pageSize = (String) dto.get("pageSize");
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		UserEntity user = UserSession.get(req);
		if (Auth.auth(token, req)) {
			List<Map<String, Object>> result = mapper.getFans(Integer.parseInt(nowPage) * Integer.parseInt(pageSize),
					Integer.parseInt(pageSize), UserSession.get(req).getId());
			result.stream().map(x -> {
				x.put("BASE_FILE_URL", MyStaticStrings.getBaseFileIp());
				return x;
			}).collect(Collectors.toList());
			dataToClient.put("result", result);
			dataToClient.put("state", "1");
		} else {
			dataToClient.put("state", "0");
		}
		BaseController.backToClient(rsp, dataToClient);
	}

	// 查找用户
	@RequestMapping("/find-user")
	public void findUser(HttpServletRequest req, HttpServletResponse rsp) {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String token = (String) dto.get("token");
		String searchStr = (String) dto.get("searchStr");
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		if (Auth.auth(token, req)) {
			List<Map<String, Object>> user = mapper.findUsersBySnnumber(searchStr);
			if (user.size() == 0) {
				System.out.println(searchStr);
				user = mapper.findUsersByUserName(searchStr);
			}
			if (user.size() != 0) {
				user.stream().map(x -> {
					x.put("BASE_FILE_URL", MyStaticStrings.getBaseFileIp());
					return x;
				}).collect(Collectors.toList());
				dataToClient.put("result", user);
				dataToClient.put("isSuccess", "1");// 成功
			} else {
				dataToClient.put("isSuccess", "0");// 失败
			}
			dataToClient.put("state", "1");
		} else {
			dataToClient.put("state", "0");
		}
		BaseController.backToClient(rsp, dataToClient);
	}

	// 获得动态(包括自己的或者别人的),就是足迹
	@RequestMapping("/get-foot")
	public void getMyOwnDynamic(HttpServletRequest req, HttpServletResponse rsp) {
		Map<String, Object> dto = BaseController.getParamAsDto(req);
		String token = (String) dto.get("token");
		String userId = (String) dto.get("userId");
		String nowPage = (String) dto.get("nowPage");
		String pageSize = (String) dto.get("pageSize");
		Map<String, Object> dataToClient = new HashMap<String, Object>();
		if (Auth.auth(token, req)) {
			List<Map<String, Object>> result = mapper.getDynamic(userId,
					Integer.parseInt(nowPage) * Integer.parseInt(pageSize), Integer.parseInt(pageSize));
			result.stream().map(x -> {
				x.put("BASE_FILE_URL", MyStaticStrings.getBaseFileIp());
				return x;
			}).collect(Collectors.toList());
			dataToClient.put("result", result);
			dataToClient.put("state", "1");
		} else {
			dataToClient.put("state", "0");
		}
		BaseController.backToClient(rsp, dataToClient);
	}

	// @RequestMapping("/getMyOwnDynamic")
	// public void getMyOwnDynamic(@RequestParam("token") String
	// token,
	// HttpServletRequest
	// req) {
	// Map<String, Object> dataToClient = new HashMap<String, Object>();
	// if (Auth.auth(token, req)) {
	//
	// } else {
	//
	// }
	// return null;
	// }
	// 获得我的粉丝的动态
	// 获得足迹的动态--本人、别人--不知道有啥用
	// @RequestMapping("/getFoot")
	// public void getFoot(@RequestParam("user_id") String
	// user_id,
	// @RequestParam("token") String
	// token,
	// HttpServletRequest req) {
	// Map<String, Object> dataToClient = new HashMap<String, Object>();
	// if (Auth.auth(token, req)) {
	// List<Map<String, Object>> result = mapper.getFoot(user_id);
	// result.stream().map(x -> {
	// x.put("BASE_FILE_URL", MyStaticStrings.getBaseFileIp());
	// return x;
	// }).collect(Collectors.toList());
	// dataToClient.put("userId", UserSession.get(req).getId());
	// dataToClient.put("result", result);
	// dataToClient.put("state", "1");
	// } else {
	// dataToClient.put("state", "0");
	// }
	//
	// BaseController.backToClient(rsp, dataToClient);
	// }
}
