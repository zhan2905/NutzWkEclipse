package cn.wizzer.modules.services.customer;

import java.util.List;

import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;

import cn.wizzer.common.base.Service;
import cn.wizzer.modules.models.customer.Customer_main_copy;

/**
 * @author memory
 * @time 2017-03-23 22:13:38
 * 
 */
@IocBean(args = {"refer:dao"})
public class CustomerMainCopyService extends Service<Customer_main_copy> {
		public CustomerMainCopyService(Dao dao) {
				super(dao);
		}
}