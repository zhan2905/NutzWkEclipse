package cn.wizzer.modules.controllers.platform.customer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.lang.Strings;
import org.nutz.lang.random.R;
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

import cn.wizzer.common.annotation.SLog;
import cn.wizzer.common.base.Globals;
import cn.wizzer.common.base.Result;
import cn.wizzer.common.filter.PrivateFilter;
import cn.wizzer.common.page.DataTableColumn;
import cn.wizzer.common.page.DataTableOrder;
import cn.wizzer.common.util.PoiExcelUtils;
import cn.wizzer.common.util.StringUtil;
import cn.wizzer.modules.models.customer.CustomerBlackList;
import cn.wizzer.modules.services.customer.CustomerBlackListService;

/**
 * @author memory
 * @time 2017-04-11 22:31:58
 * 
 */
@IocBean
@At("/platform/customer/customerblacklist")
@Filters({ @By(type = PrivateFilter.class)})
public class CustomerBlackListController {
private static final Log log = Logs.get();
	@Inject
	CustomerBlackListService customerBlackListService;

	@At("")
	@Ok("beetl:/platform/customer/blacklist/index.html")
	@RequiresAuthentication
	public void index() {
		
	}
	
	@At
	@Ok("beetl:/platform/customer/blacklist/add.html")
	@RequiresAuthentication
	public void add() {
	
	}
	
	@At
	@Ok("json")
	@RequiresPermissions("customer.manager.blacklist.add")
	@SLog(tag = "Add", msg = "Add:customerBlackList")
	public Object addDo(@Param("..") CustomerBlackList customerBlackList, HttpServletRequest req) {
		try {
			customerBlackListService.insert(customerBlackList);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}
	
	@At("/edit/?")
	@Ok("beetl:/platform/customer/blacklist/edit.html")
	@RequiresAuthentication
	public Object edit(String id) {
		return customerBlackListService.fetch(id);
	}
	
	@At
	@Ok("json")
	@RequiresPermissions("customer.manager.blacklist.edit")
	@SLog(tag = "Edit", msg = "Edit:customerBlackList")
	public Object editDo(@Param("..") CustomerBlackList customerBlackList, HttpServletRequest req) {
		try {
			customerBlackListService.updateIgnoreNull(customerBlackList);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}
	
	@At({"/delete","/delete/?"})
	@Ok("json")
	@RequiresPermissions("customer.manager.blacklist.delete")
	@SLog(tag = "Delete", msg = "Delete:customerBlackList")
	public Object delete(String id,@Param("ids") String[] ids, HttpServletRequest req) {
		try {
			if(ids!=null&&ids.length>0){
				customerBlackListService.delete(ids);
			}else{
				customerBlackListService.delete(id);
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
					   @Param("..") CustomerBlackList customerBlackList) {
		Cnd cnd = Cnd.NEW();
		if(!Strings.isEmpty(customerBlackList.getUserNo()))
			cnd.and("userNo","like",StringUtil.transferLike(customerBlackList.getUserNo()));
		return customerBlackListService.data(length, start, draw, order, columns, cnd, null);
	}
	
	@At
    @Ok("json")
    @AdaptBy(type = UploadAdaptor.class, args = {"ioc:fileUpload"})
    @RequiresPermissions("customer.manager.blacklist.import")
    public Object importDo(@Param("Filedata") TempFile tf)
            throws IOException {
        try {
            String name = tf.getSubmittedFileName().substring(tf.getSubmittedFileName().indexOf(".")).toLowerCase();
            String p = Globals.AppRoot;
            String f = Globals.AppUploadPath + "/execl/blackList/" + R.UU32() + name;
            File file = new File(p + f);
            Files.createFileIfNoExists(file);
            Files.write(file, tf.getInputStream());
            List<String[]> list = PoiExcelUtils.readXSSFExcel(p + f, 0, 3);
            for(String[] str:list){
            	CustomerBlackList customerBlackList = new CustomerBlackList();
            	customerBlackList.setUserNo(str[0]);
            	customerBlackList.setSrc(str[1]);
            	customerBlackList.setRemark(str[2]);
            	customerBlackListService.insert(customerBlackList);
            }
            return Result.success("system.success");
        } catch (Exception e) {
            log.debug("plugin install error", e);
            return Result.error("system.error");
        }
    }
}