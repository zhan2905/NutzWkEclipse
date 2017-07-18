package cn.wizzer.modules.controllers.platform.customer;

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
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.*;

import com.beust.jcommander.Strings;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wizzer.modules.models.customer.CustomerStateConfig;
import cn.wizzer.modules.models.sys.Sys_dict;
import cn.wizzer.modules.services.customer.CustomerStateConfigService;
import cn.wizzer.modules.services.service.ServiceMainService;

/**
 * @author memory
 * @time 2017-04-19 22:23:25
 * 
 */
@IocBean
@At("/platform/customer/customerstateconfig")
@Filters({ @By(type = PrivateFilter.class)})
public class CustomerStateConfigController {
private static final Log log = Logs.get();
	@Inject
	CustomerStateConfigService customerStateConfigService;
	@Inject
	ServiceMainService serviceMainService;

	@At("")
	@Ok("beetl:/platform/customer/stateConfig/index.html")
	@RequiresAuthentication
	public void index(HttpServletRequest req) {
		NutMap paramsMaps = new NutMap();
		//业务类型
		req.setAttribute("serviceMainList",serviceMainService.data(paramsMaps,Cnd.NEW()));
	}
	
	@At
	@Ok("beetl:/platform/customer/stateConfig/add.html")
	@RequiresAuthentication
	public void add(HttpServletRequest req) {
		NutMap paramsMaps = new NutMap();
		//业务类型
		req.setAttribute("serviceMainList",serviceMainService.data(paramsMaps,Cnd.NEW()));
		//是否
		List<Sys_dict> customerState = customerStateConfigService.getDict("8");
		req.setAttribute("customerState", customerState);
	}
	
	@At
	@Ok("json")
	@RequiresPermissions("customer.manager.stateConfig.add")
	@SLog(tag = "Add", msg = "Add:customerStateConfig")
	public Object addDo(@Param("..") CustomerStateConfig customerStateConfig, HttpServletRequest req) {
		try {
			customerStateConfigService.insert(customerStateConfig);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}
	
	@At("/edit/?")
	@Ok("beetl:/platform/customer/stateConfig/edit.html")
	@RequiresAuthentication
	public Object edit(String id,HttpServletRequest req) {
		NutMap paramsMaps = new NutMap();
		//业务类型
		req.setAttribute("serviceMainList",serviceMainService.data(paramsMaps,Cnd.NEW()));
		//是否
		List<Sys_dict> customerState = customerStateConfigService.getDict("8");
		req.setAttribute("customerState", customerState);
		return customerStateConfigService.fetch(id);
	}
	
	@At
	@Ok("json")
	@RequiresPermissions("customer.manager.stateConfig.edit")
	@SLog(tag = "Edit", msg = "Edit:customerStateConfig")
	public Object editDo(@Param("..") CustomerStateConfig customerStateConfig, HttpServletRequest req) {
		try {
			customerStateConfigService.updateIgnoreNull(customerStateConfig);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}
	
	@At({"/delete","/delete/?"})
	@Ok("json")
	@RequiresPermissions("customer.manager.stateConfig.delete")
	@SLog(tag = "Delete", msg = "Delete:customerStateConfig")
	public Object delete(String id,@Param("ids") String[] ids, HttpServletRequest req) {
		try {
			if(ids!=null&&ids.length>0){
				customerStateConfigService.delete(ids);
			}else{
				customerStateConfigService.delete(id);
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
					   @Param("::order") List<DataTableOrder> order, 
					   @Param("::columns") List<DataTableColumn> columns,
					   @Param("..") CustomerStateConfig customerStateConfig) {
		Cnd cnd = Cnd.NEW();
		if(null != customerStateConfig.getServiceId())
			cnd.and("serviceId","=",customerStateConfig.getServiceId());
		Map<String,String> se = new HashMap<>();
		se.put("customerStateCode", "8");
		return customerStateConfigService.dataCode(length, start, draw, order, columns, cnd, "service_main", null, se);
	}

}