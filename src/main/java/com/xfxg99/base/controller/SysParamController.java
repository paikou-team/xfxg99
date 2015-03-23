package com.xfxg99.base.controller;
 
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xfxg99.base.model.SysParameter;
import com.xfxg99.base.service.SysParameterService;
import com.xfxg99.base.viewmodel.CustomerVM;
import com.xfxg99.base.viewmodel.UserVM;
import com.xfxg99.core.ListResult;
import com.xfxg99.core.Result;

@Scope("prototype")
@Controller
@RequestMapping("/sysparam")
public class SysParamController {

	@Resource(name = "sysParameterService")
	protected SysParameterService sysParameterService;

	/*
	 * 获取注册用户列表，以下拉列表形式呈现；
	 */
	@RequestMapping(value = "getSysParamList.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String getSysParamList(HttpServletRequest request) throws Exception {

		ListResult<SysParameter> rs = sysParameterService.loadSysParamList();

		return rs.toJson();
	}

	/*
	 * 保存警员信息逻辑
	 */
	@RequestMapping(value = "saveSysParamObj.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String saveSysParamObj(SysParameter paramters, HttpServletRequest request)
			throws Exception {
		try {
			HttpSession session = request.getSession();
			UserVM user = (UserVM) session.getAttribute("user");
			if (user == null) {
				Result<SysParameter> s = new Result<SysParameter>(null, true,
						false, false, "页面过期，请重新登录");
				return s.toJson();
			} else {
				if (paramters != null) {
					sysParameterService.insert(paramters);
				}
				Result<SysParameter> s = new Result<SysParameter>(null, true,
						false, false, "保存成功");
				return s.toJson();
			}
		} catch (Exception ex) {
			Result<SysParameter> s = new Result<SysParameter>(null, false,
					false, false, "调用后台方法出错");
			return s.toJson();
		}
	}

	/*
	 * 保存警员信息逻辑
	 */
	@RequestMapping(value = "saveSysParamList.do", produces = "application/json;charset=UTF-8")
	public @ResponseBody
	String saveSysParamList(
			@RequestParam(value = "dataStr", required = false) String query,
			HttpServletRequest request) throws Exception {
		try {
			HttpSession session = request.getSession();
			UserVM user = (UserVM) session.getAttribute("user");
			if (user == null) {
				Result<CustomerVM> s = new Result<CustomerVM>(null, true,
						false, false, "页面过期，请重新登录");
				return s.toJson();
			} else {
				if (query.length() > 3) {
					JSONArray jsonArray = JSONArray.fromObject(query);
					@SuppressWarnings({ "unchecked"})
					List<SysParameter> ls = (List<SysParameter>) JSONArray.toCollection(
							jsonArray, SysParameter.class);
					if (ls.size() > 0) {
						for (SysParameter p : ls) {
							sysParameterService.updateByPrimaryKey(p);
						}
						Result<CustomerVM> s = new Result<CustomerVM>(null,
								true, false, false, "保存成功");
						return s.toJson();
					} else {
						Result<CustomerVM> s = new Result<CustomerVM>(null,
								false, false, false, "保存失败，传入后台数据解析错误");
						return s.toJson();
					}
				} else {

					Result<CustomerVM> s = new Result<CustomerVM>(null, false,
							false, false, "保存失败，传入后台数据为空");
					return s.toJson();
				}

			}
		} catch (Exception ex) {
			Result<CustomerVM> s = new Result<CustomerVM>(null, false, false,
					false, "调用后台方法出错");
			return s.toJson();
		}
	}
}
