package cn.wizzer.modules.services.customer;

import cn.wizzer.common.base.Service;
import cn.wizzer.modules.models.customer.CustomerFilterSegment;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.IocBean;

/**
 * @author memory
 * @time 2017-04-11 22:32:41
 * 
 */
@IocBean(args = {"refer:dao"})
public class CustomerFilterSegmentService extends Service<CustomerFilterSegment> {
		public CustomerFilterSegmentService(Dao dao) {
				super(dao);
		}
}