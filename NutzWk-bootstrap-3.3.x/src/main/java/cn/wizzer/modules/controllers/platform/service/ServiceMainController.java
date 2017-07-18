package cn.wizzer.modules.controllers.platform.service;

import cn.wizzer.common.annotation.SLog;
import cn.wizzer.common.base.Result;
import cn.wizzer.common.filter.PrivateFilter;
import cn.wizzer.common.page.DataTableColumn;
import cn.wizzer.common.page.DataTableOrder;
import cn.wizzer.common.util.StringUtil;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.*;

import com.beust.jcommander.Strings;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wizzer.modules.models.service.Service_main;
import cn.wizzer.modules.models.sys.Sys_dict;
import cn.wizzer.modules.services.service.ServiceMainService;

/**
 * @author Wizzer.cn
 * @time 2017-03-06 23:37:02
 * 
 */
@IocBean
@At("/platform/service/servicemain")
@Filters({ @By(type = PrivateFilter.class)})
public class ServiceMainController {
private static final Log log = Logs.get();
	@Inject
	ServiceMainService serviceMainService;

	@At("")
	@Ok("beetl:/platform/service/index.html")
	@RequiresAuthentication
	public void index() {
		
	}
	
	@At
	@Ok("beetl:/platform/service/add.html")
	@RequiresAuthentication
	public void add(HttpServletRequest req) {
		//获取业务类型码表
		List<Sys_dict> serviceType = serviceMainService.getDict("1");
		req.setAttribute("serviceType", serviceType);
		//号码类型2
		List<Sys_dict> noType = serviceMainService.getDict("2");
		req.setAttribute("noType", noType);
		//计费规则3
		List<Sys_dict> chargeType = serviceMainService.getDict("3");
		req.setAttribute("chargeType", chargeType);
		//计费途径4
		List<Sys_dict> chargeWay = serviceMainService.getDict("4");
		req.setAttribute("chargeWay", chargeWay);
		//业务状态5
		List<Sys_dict> serviceSts = serviceMainService.getDict("5");
		req.setAttribute("serviceSts", serviceSts);
		//所属公司6
		List<Sys_dict> company= serviceMainService.getDict("6");
		req.setAttribute("company", company);
	}
	
	@At
	@Ok("json")
	@RequiresPermissions("platform.service.add")
	@SLog(tag = "Add", msg = "Add:service_main")
	public Object addDo(@Param("..") Service_main service_main, HttpServletRequest req) {
		try {
			serviceMainService.insert(service_main,false,false,true);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}
	
	@At("/edit/?")
	@Ok("beetl:/platform/service/edit.html")
	@RequiresAuthentication
	public Object edit(HttpServletRequest req,int id) {
		//获取业务类型码表
		List<Sys_dict> serviceType = serviceMainService.getDict("1");
		req.setAttribute("serviceType", serviceType);
		//号码类型2
		List<Sys_dict> noType = serviceMainService.getDict("2");
		req.setAttribute("noType", noType);
		//计费规则3
		List<Sys_dict> chargeType = serviceMainService.getDict("3");
		req.setAttribute("chargeType", chargeType);
		//计费途径4
		List<Sys_dict> chargeWay = serviceMainService.getDict("4");
		req.setAttribute("chargeWay", chargeWay);
		//业务状态5
		List<Sys_dict> serviceSts = serviceMainService.getDict("5");
		req.setAttribute("serviceSts", serviceSts);
		//所属公司6
		List<Sys_dict> company= serviceMainService.getDict("6");
		req.setAttribute("company", company);
		return serviceMainService.fetch(id);
	}
	
	@At
	@Ok("json")
	@RequiresPermissions("platform.service.edit")
	@SLog(tag = "Edit", msg = "Edit:service_main")
	public Object editDo(@Param("..") Service_main service_main, HttpServletRequest req) {
		try {
			serviceMainService.updateIgnoreNull(service_main);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}
	
	@At({"/delete","/delete/?"})
	@Ok("json")
	@RequiresPermissions("platform.service.delete")
	@SLog(tag = "Delete", msg = "Delete:service_main")
	public Object delete(int id,@Param("ids") String[] ids, HttpServletRequest req) {
		try {
			if(ids!=null&&ids.length>0){
				serviceMainService.delete(ids);
			}else{
				serviceMainService.delete(id);
			}
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}
	
	@At
	@Ok("json:full")
	@RequiresAuthentication
	public Object data(@Param("length") int length, 
					   @Param("start") int start, 
					   @Param("draw") int draw, 
					   @Param("..") Service_main serviceMain,
					   @Param("::order") List<DataTableOrder> order, 
					   @Param("::columns") List<DataTableColumn> columns) {
		Cnd cnd = Cnd.where("1","=",1);
		if(!Strings.isStringEmpty(serviceMain.getService_name()))
			cnd.and("SERVICE_NAME","like",StringUtil.transferLike(serviceMain.getService_name()));
		Map<String,String> se = new HashMap<>();
		se.put("service_type", "1");
		se.put("no_type", "2");
		se.put("charge_type", "3");
		se.put("charge_way", "4");
		se.put("service_sts", "5");
		se.put("company_id", "6");
		return serviceMainService.dataCode(length, start, draw, order, columns, cnd, null, null, se);
	}
	
}