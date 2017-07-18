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
import cn.wizzer.modules.models.customer.CustomerFilterSegment;
import cn.wizzer.modules.services.customer.CustomerFilterSegmentService;

/**
 * @author memory
 * @time 2017-04-11 22:32:46
 * 
 */
@IocBean
@At("/platform/customer/customerfiltersegment")
@Filters({ @By(type = PrivateFilter.class)})
public class CustomerFilterSegmentController {
private static final Log log = Logs.get();
	@Inject
	CustomerFilterSegmentService customerFilterSegmentService;

	@At("")
	@Ok("beetl:/platform/customer/filtersegment/index.html")
	@RequiresAuthentication
	public void index() {
		
	}
	
	@At
	@Ok("beetl:/platform/customer/filtersegment/add.html")
	@RequiresAuthentication
	public void add() {
	
	}
	
	@At
	@Ok("json")
	@RequiresPermissions("customer.manager.filtersegment.add")
	@SLog(tag = "Add", msg = "Add:customer_filter_segment")
	public Object addDo(@Param("..") CustomerFilterSegment customerFilterSegment, HttpServletRequest req) {
		try {
			customerFilterSegmentService.insert(customerFilterSegment);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}
	
	@At("/edit/?")
	@Ok("beetl:/platform/customer/filtersegment/edit.html")
	@RequiresAuthentication
	public Object edit(String id) {
		return customerFilterSegmentService.fetch(id);
	}
	
	@At
	@Ok("json")
	@RequiresPermissions("customer.manager.filtersegment.edit")
	@SLog(tag = "Edit", msg = "Edit:customer_filter_segment")
	public Object editDo(@Param("..") CustomerFilterSegment customerFilterSegment, HttpServletRequest req) {
		try {
			customerFilterSegmentService.updateIgnoreNull(customerFilterSegment);
			return Result.success("system.success");
		} catch (Exception e) {
			return Result.error("system.error");
		}
	}
	
	@At({"/delete","/delete/?"})
	@Ok("json")
	@RequiresPermissions("customer.manager.filtersegment.delete")
	@SLog(tag = "Delete", msg = "Delete:customer_filter_segment")
	public Object delete(String id,@Param("ids") String[] ids, HttpServletRequest req) {
		try {
			if(ids!=null&&ids.length>0){
				customerFilterSegmentService.delete(ids);
			}else{
				customerFilterSegmentService.delete(id);
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
					   @Param(".." ) CustomerFilterSegment customerFilterSegment) {
		Cnd cnd = Cnd.NEW();
		if(!Strings.isEmpty(customerFilterSegment.getNumberSegment()))
			cnd.and("numberSegment","like",StringUtil.transferLike(customerFilterSegment.getNumberSegment()));
		return customerFilterSegmentService.data(length, start, draw, order, columns, cnd, null);
	}

	@At
    @Ok("json")
    @AdaptBy(type = UploadAdaptor.class, args = {"ioc:fileUpload"})
    @RequiresPermissions("customer.manager.filtersegment.import")
    public Object importDo(@Param("Filedata") TempFile tf)
            throws IOException {
        try {
            String name = tf.getSubmittedFileName().substring(tf.getSubmittedFileName().indexOf(".")).toLowerCase();
            String p = Globals.AppRoot;
            String f = Globals.AppUploadPath + "/execl/filterSegment/" + R.UU32() + name;
            File file = new File(p + f);
            Files.createFileIfNoExists(file);
            Files.write(file, tf.getInputStream());
            List<String[]> list = PoiExcelUtils.readXSSFExcel(p + f, 0, 3);
            for(String[] str:list){
            	CustomerFilterSegment customerFilterSegment = new CustomerFilterSegment();
            	customerFilterSegment.setNumberSegment(str[0]);
            	customerFilterSegment.setSrc(str[1]);
            	customerFilterSegment.setRemark(str[2]);
            	customerFilterSegmentService.insert(customerFilterSegment);
            }
            return Result.success("system.success");
        } catch (Exception e) {
            log.debug("plugin install error", e);
            return Result.error("system.error");
        }
    }
}