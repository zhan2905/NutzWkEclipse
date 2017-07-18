package cn.wizzer.modules.services.customer;

import cn.wizzer.common.base.Service;
import cn.wizzer.modules.models.customer.CustomerWhiteList;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;

/**
 * @author memory
 * @time 2017-04-11 22:32:49
 * 
 */
@IocBean(args = {"refer:dao"})
public class CustomerWhiteListService extends Service<CustomerWhiteList> {
		public CustomerWhiteListService(Dao dao) {
				super(dao);
		}
}