package cn.wizzer.modules.controllers.platform.customer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.aop.interceptor.ioc.TransAop;
import org.nutz.dao.Cnd;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;
import org.nutz.trans.Trans;

import com.fasterxml.jackson.databind.annotation.JsonAppend.Attr;

import cn.wizzer.common.annotation.SLog;
import cn.wizzer.common.base.Result;
import cn.wizzer.common.filter.PrivateFilter;
import cn.wizzer.common.page.DataTableColumn;
import cn.wizzer.common.page.DataTableOrder;
import cn.wizzer.common.util.Reflect;
import cn.wizzer.common.util.StringUtil;
import cn.wizzer.common.util.Underline2Camel;
import cn.wizzer.modules.models.customer.Customer_main;
import cn.wizzer.modules.models.customer.Customer_main_copy;
import cn.wizzer.modules.models.sys.Sys_dict;
import cn.wizzer.modules.services.base.BaseAreaCodeService;
import cn.wizzer.modules.services.customer.CustomerMainCopyService;
import cn.wizzer.modules.services.customer.CustomerMainService;
import cn.wizzer.modules.services.service.ServiceMainService;

/**
 * @author memory
 * @time 2017-03-23 22:13:38
 * 
 */
@IocBean
@At("/platform/customer/customermain")
@Filters({ @By(type = PrivateFilter.class)})
public class CustomerMainController {
private static final Log log = Logs.get();
	@Inject
	CustomerMainService customerMainService;
	@Inject
	BaseAreaCodeService baseAreaCodeService;
	@Inject
	ServiceMainService serviceMainService;
	@Inject
	CustomerMainCopyService customerMainCopyService;

	@At("")
	@Ok("beetl:/platform/customer/main/index.html")
	@RequiresAuthentication
	public void index(HttpServletRequest req) {
		NutMap paramsMaps = new NutMap();
		//所属地市
		paramsMaps.put("provinceCode", "1");
		req.setAttribute("areaCodeList",baseAreaCodeService.data(paramsMaps,Cnd.NEW()));
		paramsMaps.clear();
		//业务类型
		req.setAttribute("serviceMainList",serviceMainService.data(paramsMaps,Cnd.NEW()));
		paramsMaps.clear();
		//是否
		List<Sys_dict> trueFalse = serviceMainService.getDict("7");
		req.setAttribute("trueFalse", trueFalse);
		//用户状态
		List<Sys_dict> customerStateList = serviceMainService.getDict("8");
		req.setAttribute("customerStateList", customerStateList);
	}
	
	@At
	@Ok("beetl:/platform/customer/main/add.html")
	@RequiresAuthentication
	public void add(HttpServletRequest req) {
		NutMap paramsMaps = new NutMap();
		//所属地市
		paramsMaps.put("provinceCode", "1");
		req.setAttribute("areaCodeList",baseAreaCodeService.data(paramsMaps,Cnd.NEW()));
		paramsMaps.clear();
		//业务类型
		req.setAttribute("serviceMainList",serviceMainService.data(paramsMaps,Cnd.NEW()));
		paramsMaps.clear();
		//是否
		List<Sys_dict> trueFalse = serviceMainService.getDict("7");
		req.setAttribute("trueFalse", trueFalse);
		//用户状态
		List<Sys_dict> customerStateList = serviceMainService.getDict("8");
		req.setAttribute("customerStateList", customerStateList);
	}
	
	@At
	@Ok("json")
	@RequiresPermissions("platform.customer.add")
	@SLog(tag = "Add", msg = "Add:customer_main")
	public Object addDo(@Param("..") Customer_main customer_main, HttpServletRequest req) {
		try {
			customerMainService.insert(customer_main);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}
	
	@At("/edit/?")
	@Ok("beetl:/platform/customer/main/edit.html")
	@RequiresAuthentication
	public Object edit(String id,HttpServletRequest req) {
		NutMap paramsMaps = new NutMap();
		//所属地市
		paramsMaps.put("provinceCode", "1");
		req.setAttribute("areaCodeList",baseAreaCodeService.data(paramsMaps,Cnd.NEW()));
		paramsMaps.clear();
		//业务类型
		req.setAttribute("serviceMainList",serviceMainService.data(paramsMaps,Cnd.NEW()));
		paramsMaps.clear();
		//是否
		List<Sys_dict> trueFalse = serviceMainService.getDict("7");
		req.setAttribute("trueFalse", trueFalse);
		//用户状态
		List<Sys_dict> customerStateList = serviceMainService.getDict("8");
		req.setAttribute("customerStateList", customerStateList);
		return customerMainService.fetch(id);
	}
	
	@At
	@Ok("json")
	@RequiresPermissions("platform.customer.edit")
	@SLog(tag = "Edit", msg = "Edit:customer_main")
	public Object editDo(@Param("..") Customer_main customer_main, HttpServletRequest req) {
		try {
			customerMainService.updateIgnoreNull(customer_main);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}
	
	@At({"/delete","/delete/?"})
	@Ok("json")
	@RequiresPermissions("platform.customer.delete")
	@SLog(tag = "Delete", msg = "Delete:customer_main")
	public Object delete(String id,@Param("ids") String[] ids, HttpServletRequest req) {
		try {
			if(ids!=null&&ids.length>0){
				customerMainService.delete(ids);
			}else{
				customerMainService.delete(id);
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
					   @Param("..") Customer_main customerMain) {
		Cnd cnd = Cnd.NEW();
		NutMap paramsMaps = new NutMap();
		NutMap conditionMap = new NutMap();
		conditionMap.put("customerNo", StringUtil.LIKE);
		conditionMap.put("broadbandAccount", StringUtil.LIKE);
		conditionMap.put("remark", StringUtil.LIKE);
		conditionMap.put("functionStartDate", StringUtil.GREATERTHANEQUAL);
		conditionMap.put("chargeStartDate", StringUtil.GREATERTHANEQUAL);
		conditionMap.put("functionEndDate", StringUtil.LESSTHANEQUAL);
		conditionMap.put("chargeEndDate", StringUtil.LESSTHANEQUAL);
		paramsMaps = Reflect.reflect(customerMain, conditionMap);
		conditionMap.clear();
//		if(!Strings.isEmpty(customerMain.getBlackList())){
//			cnd.and("F_JUDGE_BLACK_LIST(a.customer_no)","=",customerMain.getBlackList());
//			paramsMaps.remove("blackList");
//		}
		//这里因为表设计问题，应增加是否地推字段，而不是在remark中增加地推关键字！
//		if(!Strings.isEmpty(customerMain.getShielding()))
//			cnd.and("F_GET_DITUI(a.id)","=",customerMain.getShielding());
		//此处因为建表的原因需要强制转换字段名字
		for(String key:paramsMaps.keySet()){
			if(key.contains("~")){
    			String[] params = key.split("~");
    			conditionMap.put(params[0] + "~" + Underline2Camel.camel2Underline(params[1]),paramsMaps.get(key));
//				paramsMaps.remove(key);
			}else{
				conditionMap.put(Underline2Camel.camel2Underline(key),paramsMaps.get(key));
//				paramsMaps.remove(key);
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		//拼装更新时间
		try {
			if(!Strings.isEmpty(customerMain.getUpdateStartDate())) {
				date = sdf.parse(customerMain.getUpdateStartDate());
				conditionMap.put(StringUtil.GREATERTHANEQUAL + "_" + "opAt", date.getTime());
			}
			if(!Strings.isEmpty(customerMain.getUpdateEndDate())) {
				date = sdf.parse(customerMain.getUpdateEndDate());
				conditionMap.put(StringUtil.LESSTHANEQUAL + "_" + "opAt", date.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return customerMainService.data(length, start, draw, "customer:getMainCount", "customer:getMainList", conditionMap,"db/customer",null,cnd);
	}
	
	@At
	@Ok("json")
	@AdaptBy(type = UploadAdaptor.class, args = {"ioc:fileUpload"})
//	@RequiresPermissions("customer.manager.m.import")
	public Object importDo(@Param("Filedata") TempFile tf,
						   @Param("serviceId") int serviceId){
		try {
            List<String[]> list = customerMainCopyService.importDo(tf, "/execl/customerMain/", 1, 18);
            for(String[] str:list){
            	Customer_main_copy cmc = new Customer_main_copy();
            	cmc.setServiceId(serviceId);
            	cmc.setCustomerNo(str[0]);
            }
            return Result.success("system.success");
        } catch (Exception e) {
            log.debug("plugin install error", e);
            return Result.error("system.error");
        }
	}
	
	@At
	@Ok("json")
	public Object exportDo(@Param("serviceId") String serviceId,
						   @Param("count")	   String count,
						   @Param("cityCode")  String cityCode){
		try {
			customerMainService.exportDo(serviceId,count,cityCode);
            return Result.success("system.success");
        } catch (Exception e) {
            log.debug("exportDo error", e);
            return Result.error("system.error");
        } 
	}
	
	@At
	@Ok("json")
	public Object detailDo(@Param("id") String id,HttpServletRequest req){
		try {
            return Result.success("system.success",customerMainService.getDetail(id));
        } catch (Exception e) {
            log.debug("exportDo error", e);
            return Result.error("system.error");
        } 
	}
}