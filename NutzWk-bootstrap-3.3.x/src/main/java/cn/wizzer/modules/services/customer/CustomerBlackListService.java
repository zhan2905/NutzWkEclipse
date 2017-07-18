package cn.wizzer.modules.services.customer;

import cn.wizzer.common.base.Service;
import cn.wizzer.modules.models.customer.CustomerBlackList;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;

/**
 * @author memory
 * @time 2017-04-11 22:31:55
 * 
 */
@IocBean(args = {"refer:dao"})
public class CustomerBlackListService extends Service<CustomerBlackList> {
		public CustomerBlackListService(Dao dao) {
				super(dao);
		}
}