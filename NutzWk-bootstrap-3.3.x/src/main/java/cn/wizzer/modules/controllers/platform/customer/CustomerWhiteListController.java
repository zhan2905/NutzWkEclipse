package cn.wizzer.modules.controllers.platform.customer;

import cn.wizzer.common.annotation.SLog;
import cn.wizzer.common.base.Globals;
import cn.wizzer.common.base.Result;
import cn.wizzer.common.filter.PrivateFilter;
import cn.wizzer.common.page.DataTableColumn;
import cn.wizzer.common.page.DataTableOrder;
import cn.wizzer.common.util.PoiExcelUtils;
import cn.wizzer.common.util.StringUtil;

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
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.wizzer.modules.models.customer.CustomerBlackList;
import cn.wizzer.modules.models.customer.CustomerWhiteList;
import cn.wizzer.modules.services.customer.CustomerWhiteListService;

/**
 * @author memory
 * @time 2017-04-11 22:32:49
 * 
 */
@IocBean
@At("/platform/customer/customerwhitelist")
@Filters({ @By(type = PrivateFilter.class)})
public class CustomerWhiteListController {
private static final Log log = Logs.get();
	@Inject
	CustomerWhiteListService customerWhiteListService;

	@At("")
	@Ok("beetl:/platform/customer/whitelist/index.html")
	@RequiresAuthentication
	public void index() {
		
	}
	
	@At
	@Ok("beetl:/platform/customer/whitelist/add.html")
	@RequiresAuthentication
	public void add() {
	
	}
	
	@At
	@Ok("json")
	@RequiresPermissions("customer.manager.whitelist.add")
	@SLog(tag = "Add", msg = "Add:customer_white_list")
	public Object addDo(@Param("..") CustomerWhiteList customerWhiteList, HttpServletRequest req) {
		try {
			customerWhiteListService.insert(customerWhiteList);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}
	
	@At("/edit/?")
	@Ok("beetl:/platform/customer/whitelist/edit.html")
	@RequiresAuthentication
	public Object edit(String id) {
		return customerWhiteListService.fetch(id);
	}
	
	@At
	@Ok("json")
	@RequiresPermissions("customer.manager.whitelist.edit")
	@SLog(tag = "Edit", msg = "Edit:customer_white_list")
	public Object editDo(@Param("..") CustomerWhiteList customerWhiteList, HttpServletRequest req) {
		try {
			customerWhiteListService.updateIgnoreNull(customerWhiteList);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}
	
	@At({"/delete","/delete/?"})
	@Ok("json")
	@RequiresPermissions("customer.manager.whitelist.delete")
	@SLog(tag = "Delete", msg = "Delete:customer_white_list")
	public Object delete(String id,@Param("ids") String[] ids, HttpServletRequest req) {
		try {
			if(ids!=null&&ids.length>0){
				customerWhiteListService.delete(ids);
			}else{
				customerWhiteListService.delete(id);
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
					   @Param("..") CustomerWhiteList customerWhiteList) {
		Cnd cnd = Cnd.NEW();
		if(!Strings.isEmpty(customerWhiteList.getUserNo()))
			cnd.and("userNo","like",StringUtil.transferLike(customerWhiteList.getUserNo()));
		return customerWhiteListService.data(length, start, draw, order, columns, cnd, null);
	}

	@At
    @Ok("json")
    @AdaptBy(type = UploadAdaptor.class, args = {"ioc:fileUpload"})
    @RequiresPermissions("customer.manager.whitelist.import")
    public Object importDo(@Param("Filedata") TempFile tf)
            throws IOException {
        try {
            String name = tf.getSubmittedFileName().substring(tf.getSubmittedFileName().indexOf(".")).toLowerCase();
            String p = Globals.AppRoot;
            String f = Globals.AppUploadPath + "/execl/whiteList/" + R.UU32() + name;
            File file = new File(p + f);
            Files.createFileIfNoExists(file);
            Files.write(file, tf.getInputStream());
            List<String[]> list = PoiExcelUtils.readXSSFExcel(p + f, 0, 3);
            for(String[] str:list){
            	CustomerWhiteList customerWhiteList = new CustomerWhiteList();
            	customerWhiteList.setUserNo(str[0]);
            	customerWhiteList.setSrc(str[1]);
            	customerWhiteList.setRemark(str[2]);
            	customerWhiteListService.insert(customerWhiteList);
            }
            return Result.success("system.success");
        } catch (Exception e) {
            log.debug("plugin install error", e);
            return Result.error("system.error");
        }
    }
}