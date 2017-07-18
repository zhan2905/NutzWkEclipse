package cn.wizzer.common.base;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.FieldFilter;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Entity;
import org.nutz.dao.entity.Record;
import org.nutz.dao.impl.FileSqlManager;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.sql.SqlCallback;
import org.nutz.dao.util.Daos;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.random.R;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.TempFile;
import org.nutz.service.EntityService;

import cn.wizzer.common.page.DataTableColumn;
import cn.wizzer.common.page.DataTableOrder;
import cn.wizzer.common.page.OffsetPager;
import cn.wizzer.common.page.Pagination;
import cn.wizzer.common.util.PoiExcelUtils;
import cn.wizzer.common.util.StringUtil;
import cn.wizzer.common.util.Underline2Camel;
import cn.wizzer.modules.models.customer.Customer_main_copy;
import cn.wizzer.modules.models.sys.Sys_dict;
import cn.wizzer.modules.models.view.code.Base_code;

/**
 * Created by wizzer on 2016/6/21.
 */
public class Service<T> extends EntityService<T> {
    protected final static int DEFAULT_PAGE_NUMBER = 10;
    protected final static JsonFormat jsonFormat = new JsonFormat().setIgnoreNull(false);
    private static final Log log = Logs.get();
    public Service() {
        super();
    }

    public Service(Dao dao) {
        super(dao);
    }

    /**
     * 统计符合条件的对象表条数
     *
     * @param cnd
     * @return
     */
    public int count(Condition cnd) {
        return this.dao().count(this.getEntityClass(), cnd);
    }

    /**
     * 统计对象表条数
     *
     * @return
     */
    public int count() {
        return this.dao().count(this.getEntityClass());
    }

    /**
     * 统计符合条件的记录条数
     *
     * @param tableName
     * @param cnd
     * @return
     */
    public int count(String tableName, Condition cnd) {
        return this.dao().count(tableName, cnd);
    }

    /**
     * 统计表记录条数
     *
     * @param tableName
     * @return
     */
    public int count(String tableName) {
        return this.dao().count(tableName);
    }

    public T fetch(long id) {
        return this.dao().fetch(this.getEntityClass(), id);
    }

    public T fetch(String name) {
        return this.dao().fetch(this.getEntityClass(), name);
    }

    public T fetchLinks(T t, String name) {
        return this.dao().fetchLinks(t, name);
    }

    public T fetchLinks(T t, String name, Condition cnd) {
        return this.dao().fetchLinks(t, name, cnd);
    }

    public int delete(String name) {
        return this.dao().delete(this.getEntityClass(), name);
    }

    public T insert(T t) {
        return this.dao().insert(t);
    }
    
    public T insert(T t, boolean ignoreNull, boolean ignoreZero, boolean ignoreBlankStr){
    	return this.dao().insert(t, ignoreNull, ignoreZero, ignoreBlankStr);
    }

    public void insert(String tableName, Chain chain) {
        this.dao().insert(tableName, chain);
    }

    public T fastInsert(T t) {
        return this.dao().fastInsert(t);
    }

    public int update(Object obj) {
        return this.dao().update(obj);
    }

    /**
     * 忽略值为null的字段
     *
     * @param obj
     * @return
     */
    public int updateIgnoreNull(Object obj) {
        return this.dao().updateIgnoreNull(obj);
    }

    /**
     * 部分更新实体表
     *
     * @param chain
     * @param cnd
     * @return
     */
    public int update(Chain chain, Condition cnd) {
        return this.dao().update(this.getEntityClass(), chain, cnd);
    }

    /**
     * 部分更新表
     *
     * @param tableName
     * @param chain
     * @param cnd
     * @return
     */
    public int update(String tableName, Chain chain, Condition cnd) {
        return this.dao().update(tableName, chain, cnd);
    }

    public int delete(long id) {
        return this.dao().delete(this.getEntityClass(), id);
    }

    public int delete(int id) {
        return this.dao().delete(this.getEntityClass(), id);
    }

    public int getMaxId() {
        return this.dao().getMaxId(this.getEntityClass());
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    public void delete(Integer[] ids) {
        this.dao().clear(getEntityClass(), Cnd.where("id", "in", ids));
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    public void delete(Long[] ids) {
        this.dao().clear(getEntityClass(), Cnd.where("id", "in", ids));
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    public void delete(String[] ids) {
        this.dao().clear(getEntityClass(), Cnd.where("id", "in", ids));
    }

    /**
     * 伪删除
     *
     * @param id
     * @return
     */
    public int vDelete(String id) {
        return this.dao().update(this.getEntityClass(), Chain.make("delTag", true), Cnd.where("id", "=", id));
    }

    /**
     * 批量伪删除
     *
     * @param ids
     * @return
     */
    public int vDelete(String[] ids) {
        return this.dao().update(this.getEntityClass(), Chain.make("delTag", true), Cnd.where("id", "in", ids));
    }

    /**
     * 通过LONG主键获取部分字段值
     *
     * @param fieldName
     * @param id
     * @return
     */
    public T getField(String fieldName, long id) {
        return Daos.ext(this.dao(), FieldFilter.create(getEntityClass(), fieldName))
                .fetch(getEntityClass(), id);
    }

    /**
     * 通过INT主键获取部分字段值
     *
     * @param fieldName
     * @param id
     * @return
     */
    public T getField(String fieldName, int id) {
        return Daos.ext(this.dao(), FieldFilter.create(getEntityClass(), fieldName))
                .fetch(getEntityClass(), id);
    }


    /**
     * 通过NAME主键获取部分字段值
     *
     * @param fieldName 支持通配符 ^(a|b)$
     * @param name
     * @return
     */
    public T getField(String fieldName, String name) {
        return Daos.ext(this.dao(), FieldFilter.create(getEntityClass(), fieldName))
                .fetch(getEntityClass(), name);
    }

    /**
     * 通过NAME主键获取部分字段值
     *
     * @param fieldName 支持通配符 ^(a|b)$
     * @param cnd
     * @return
     */
    public T getField(String fieldName, Condition cnd) {
        return Daos.ext(this.dao(), FieldFilter.create(getEntityClass(), fieldName))
                .fetch(getEntityClass(), cnd);
    }

    /**
     * 查询获取部分字段
     *
     * @param fieldName 支持通配符 ^(a|b)$
     * @param cnd
     * @return
     */
    public List<T> query(String fieldName, Condition cnd) {
        return Daos.ext(this.dao(), FieldFilter.create(getEntityClass(), fieldName))
                .query(getEntityClass(), cnd);
    }

    /**
     * 计算子节点ID
     *
     * @param tableName
     * @param cloName
     * @param value
     * @return
     */
    public String getSubPath(String tableName, String cloName, String value) {
        final String val = Strings.sNull(value);
        Sql sql = Sqls.create("select " + cloName + " from " + tableName
                + " where " + cloName + " like '" + val + "____' order by "
                + cloName + " desc");
        sql.setCallback(new SqlCallback() {
            public Object invoke(Connection conn, ResultSet rs, Sql sql)
                    throws SQLException {
                String rsvalue = val + "0001";
                if (rs != null && rs.next()) {
                    rsvalue = rs.getString(1);
                    int newvalue = NumberUtils.toInt(rsvalue
                            .substring(rsvalue.length() - 4)) + 1;
                    rsvalue = rsvalue.substring(0, rsvalue.length() - 4)
                            + new java.text.DecimalFormat("0000")
                            .format(newvalue);
                }
                return rsvalue;
            }
        });
        this.dao().execute(sql);
        return sql.getString();

    }

    /**
     * 自定义SQL统计
     *
     * @param sql
     * @return
     */
    public int count(Sql sql) {
        sql.setCallback(new SqlCallback() {
            public Object invoke(Connection conn, ResultSet rs, Sql sql)
                    throws SQLException {
                int rsvalue = 0;
                if (rs != null && rs.next()) {
                    rsvalue = rs.getInt(1);
                }
                return rsvalue;
            }
        });
        this.dao().execute(sql);
        return sql.getInt();
    }

    /**
     * 自定义SQL返回Record记录集，Record是个MAP但不区分大小写
     * 别返回Map对象，因为MySql和Oracle中字段名有大小写之分
     *
     * @param sql
     * @return
     */
    public List<Record> list(Sql sql) {
        sql.setCallback(Sqls.callback.records());
        this.dao().execute(sql);
        return sql.getList(Record.class);

    }

    /**
     * 自定义sql获取map key-value
     *
     * @param sql
     * @return
     */
    public Map<?, ?> getMap(Sql sql) {
        sql.setCallback(new SqlCallback() {
            @Override
            public Object invoke(Connection conn, ResultSet rs, Sql sql)
                    throws SQLException {
                Map<String, String> map = new HashMap<>();
                while (rs.next()) {
                    map.put(Strings.sNull(rs.getString(1)), Strings.sNull(rs.getString(2)));
                }
                return map;
            }
        });
        this.dao().execute(sql);
        return sql.getObject(Map.class);
    }

    /**
     * 分页查询
     *
     * @param pageNumber
     * @param cnd
     * @return
     */
    public Pagination listPage(Integer pageNumber, Condition cnd) {
        return listPage(pageNumber, DEFAULT_PAGE_NUMBER, cnd);
    }

    /**
     * 分页查询
     *
     * @param pageNumber
     * @param sql
     * @return
     */
    public Pagination listPage(Integer pageNumber, Sql sql) {
        return listPage(pageNumber, DEFAULT_PAGE_NUMBER, sql);
    }

    /**
     * 分页查询
     *
     * @param pageNumber
     * @param tableName
     * @param cnd
     * @return
     */
    public Pagination listPage(Integer pageNumber, String tableName, Condition cnd) {
        return listPage(pageNumber, DEFAULT_PAGE_NUMBER, tableName, cnd);
    }

    /**
     * 分页查询(cnd)
     *
     * @param pageNumber
     * @param pageSize
     * @param cnd
     * @return
     */
    public Pagination listPage(Integer pageNumber, int pageSize, Condition cnd) {
        pageNumber = getPageNumber(pageNumber);
        pageSize = getPageSize(pageSize);
        Pager pager = this.dao().createPager(pageNumber, pageSize);
        List<T> list = this.dao().query(getEntityClass(), cnd, pager);
        pager.setRecordCount(this.dao().count(getEntityClass(), cnd));
        return new Pagination(pageNumber, pageSize, pager.getRecordCount(), list);
    }

    /**
     * 分页查询,获取部分字段(cnd)
     *
     * @param pageNumber
     * @param pageSize
     * @param cnd
     * @param fieldName  支持通配符 ^(a|b)$
     * @return
     */
    public Pagination listPage(Integer pageNumber, int pageSize, Condition cnd, String fieldName) {
        pageNumber = getPageNumber(pageNumber);
        pageSize = getPageSize(pageSize);
        Pager pager = this.dao().createPager(pageNumber, pageSize);
        List<T> list = Daos.ext(this.dao(), FieldFilter.create(getEntityClass(), fieldName)).query(getEntityClass(), cnd);
        pager.setRecordCount(this.dao().count(getEntityClass(), cnd));
        return new Pagination(pageNumber, pageSize, pager.getRecordCount(), list);
    }

    /**
     * 分页查询(tabelName)
     *
     * @param pageNumber
     * @param pageSize
     * @param tableName
     * @param cnd
     * @return
     */
    public Pagination listPage(Integer pageNumber, int pageSize, String tableName, Condition cnd) {
        pageNumber = getPageNumber(pageNumber);
        pageSize = getPageSize(pageSize);
        Pager pager = this.dao().createPager(pageNumber, pageSize);
        List<Record> list = this.dao().query(tableName, cnd, pager);
        pager.setRecordCount(this.dao().count(tableName, cnd));
        return new Pagination(pageNumber, pageSize, pager.getRecordCount(), list);
    }

    /**
     * 分页查询(sql)
     *
     * @param pageNumber
     * @param pageSize
     * @param sql
     * @return
     */
    public Pagination listPage(Integer pageNumber, int pageSize, Sql sql) {
        pageNumber = getPageNumber(pageNumber);
        pageSize = getPageSize(pageSize);
        Pager pager = this.dao().createPager(pageNumber, pageSize);
        pager.setRecordCount((int) Daos.queryCount(this.dao(), sql.toString()));// 记录数需手动设置
        sql.setPager(pager);
        sql.setCallback(Sqls.callback.records());
        dao().execute(sql);
        return new Pagination(pageNumber, pageSize, pager.getRecordCount(), sql.getList(Record.class));
    }

    /**
     * 默认页码
     *
     * @param pageNumber
     * @return
     */
    protected int getPageNumber(Integer pageNumber) {
        return Lang.isEmpty(pageNumber) ? 1 : pageNumber;
    }

    /**
     * 默认页大小
     *
     * @param pageSize
     * @return
     */
    protected int getPageSize(int pageSize) {
        return pageSize == 0 ? DEFAULT_PAGE_NUMBER : pageSize;
    }

    /**
     * DataTable Page
     *
     * @param length   页大小
     * @param start    start
     * @param draw     draw
     * @param orders   排序
     * @param columns  字段
     * @param cnd      查询条件
     * @param linkname 关联查询
     * @return
     */
    public NutMap data(int length, int start, int draw, List<DataTableOrder> orders, List<DataTableColumn> columns, Cnd cnd, String linkname) {
        NutMap re = new NutMap();
//        for(int i=0;i<columns.size();i++){
//        	columns.get(i).setData(Underline2Camel.camel2Underline(columns.get(i).getData()));
//        }
        if (orders != null && orders.size() > 0) {
            for (DataTableOrder order : orders) {
                DataTableColumn col = columns.get(order.getColumn());
                cnd.orderBy(Sqls.escapeSqlFieldValue(col.getData()).toString(), order.getDir());
            }
        }
        Pager pager = new OffsetPager(start, length);
        re.put("recordsFiltered", this.dao().count(getEntityClass(), cnd));
        List<?> list = this.dao().query(getEntityClass(), cnd, pager);
        if (!Strings.isBlank(linkname)) {
            this.dao().fetchLinks(list, linkname);
        }
        re.put("data", list);
        re.put("draw", draw);
        re.put("recordsTotal", length);
        return re;
    }

	/**
     * DataTable Page
     *
     * @param length   页大小
     * @param start    start
     * @param draw     draw
     * @param orders   排序
     * @param columns  字段
     * @param cnd      查询条件
     * @param linkName 关联查询
     * @param subCnd   关联查询条件
     * @return
     */
    public NutMap data(int length, int start, int draw, List<DataTableOrder> orders, List<DataTableColumn> columns, Cnd cnd, String linkName, Cnd subCnd) {
        NutMap re = new NutMap();
        if (orders != null && orders.size() > 0) {
            for (DataTableOrder order : orders) {
                DataTableColumn col = columns.get(order.getColumn());
                cnd.orderBy(Sqls.escapeSqlFieldValue(col.getData()).toString(), order.getDir());
            }
        }
        Pager pager = new OffsetPager(start, length);
        re.put("recordsFiltered", this.dao().count(this.getEntityClass(), cnd));
        List<?> list = this.dao().query(this.getEntityClass(), cnd, pager);
        if (!Strings.isBlank(linkName)) {
            if (subCnd != null)
                this.dao().fetchLinks(list, linkName, subCnd);
            else
                this.dao().fetchLinks(list, linkName);
        }
        re.put("data", list);
        re.put("draw", draw);
        re.put("recordsTotal", length);
        return re;
    }

    /**
     * DataTable Page SQL
     *
     * @param length   页大小
     * @param start    start
     * @param draw     draw
     * @param countSql 查询条件
     * @param orderSql 排序语句
     * @return
     */
    public NutMap data(int length, int start, int draw, Sql countSql, Sql orderSql) {
        NutMap re = new NutMap();
        Pager pager = new OffsetPager(start, length);
        pager.setRecordCount((int) Daos.queryCount(this.dao(), countSql.toString()));// 记录数需手动设置
        orderSql.setPager(pager);
        orderSql.setCallback(Sqls.callback.records());
        this.dao().execute(orderSql);
        re.put("recordsFiltered", pager.getRecordCount());
        re.put("data", orderSql.getList(Record.class));
        re.put("draw", draw);
        re.put("recordsTotal", length);
        return re;
    }
    /**
     * 
     * @param length
     * @param start
     * @param draw
     * @param countSql 获取总数的SQL标识
     * @param orderSql 获取结果集的SQL标识
     * @param paramsMaps  需要设置的参数对应值
     * @param SingelParamsMaps  此map只作用于setCondition之外的参数设置
     * @return
     */
    
    public NutMap data(int length, int start, int draw, String countSql, String orderSql,
    		NutMap paramsMaps,String sqlFilePath,NutMap SingelParamsMaps,Cnd cnd) {
    	FileSqlManager fm_cms = new FileSqlManager(sqlFilePath);
    	Sql count= Sqls.create(fm_cms.get(countSql));
    	Sql order= Sqls.create(fm_cms.get(orderSql));
    	cnd = queryCondition(paramsMaps,cnd);
    	count.setCondition(cnd);
    	order.setCondition(cnd);
    	return data(length,start,draw,count,order);
    }
    /**
     * 获取字典数据列表
     * @param codeKind
     * @return
     */
    public List<Sys_dict> getDict(String codeKind){
    	FileSqlManager fm_cms = new FileSqlManager("db/base.sql");
    	Sql sql = Sqls.create(fm_cms.get("getBaseCode"));
    	sql.params().set("parentCode", codeKind);
    	sql.setCallback(Sqls.callback.entities());
        Entity<Sys_dict> entity = this.dao().getEntity(Sys_dict.class);
        sql.setEntity(entity);
        this.dao().execute(sql);
        return sql.getList(Sys_dict.class);
    }
    /**
     * 获取字典name值
     * @param codeKind
     * @return
     */
    public Sys_dict getDictName(String codeKind,String baseCode){
    	FileSqlManager fm_cms = new FileSqlManager("db/base.sql");
    	Sql sql = Sqls.create(fm_cms.get("getBaseCodeName"));
    	sql.params().set("parentCode", codeKind);
    	sql.params().set("childCode", baseCode);
    	sql.setCallback(Sqls.callback.entity());
    	Entity<Sys_dict> entity = this.dao().getEntity(Sys_dict.class);
    	sql.setEntity(entity);
    	this.dao().execute(sql);
    	return sql.getObject(Sys_dict.class);
    }
    /**
     * @param length	页大小	
     * @param start		从第几个记录开始
     * @param draw		页码
     * @param orders	排序
     * @param columns	字段
     * @param cnd		查询条件
     * @param linkname	关联查询
     * @param params	传入占位符参数
     * @param SqlKey	查询语句标识
     * @param SqlCountKey	查询总数标识
     * @return
     */
    public NutMap dataIndex(int length, int start, int draw, List<DataTableOrder> orders, List<DataTableColumn> columns,
    		Cnd cnd,String linkname,Map<String,Object> params,String SqlKey,String SqlCountKey) {
        NutMap re = new NutMap();
        if (orders != null && orders.size() > 0) {
            for (DataTableOrder order : orders) {
                DataTableColumn col = columns.get(order.getColumn());
                cnd.orderBy(Sqls.escapeSqlFieldValue(col.getData()).toString(), order.getDir());
            }
        }
        //获取结果集个数
        FileSqlManager fileSqlManager = new FileSqlManager("db/");
        Sql sqlCount = Sqls.create(fileSqlManager.get(SqlCountKey));
        for(String key:params.keySet()){
        	sqlCount.params().set(key, params.get(key));
        }
        sqlCount.setCondition(cnd);
        sqlCount.setCallback(Sqls.callback.integer());
        this.dao().execute(sqlCount);
        re.put("recordsFiltered",sqlCount.getInt());
        //获取结果集
        StringBuffer sb = new StringBuffer(fileSqlManager.get(SqlKey));
        sb.append("LIMIT").append(start).append(Enum.COMMA).append(length);
        Sql sql = Sqls.create(sb.toString());
        for(String key:params.keySet()){
        	sql.params().set(key, params.get(key));
        }
        sql.setCondition(cnd);
    	sql.setCallback(Sqls.callback.entities());
        sql.setEntity(getEntity());
        this.dao().execute(sql);
        List<?> list = sql.getList(getEntityClass());
        if (!Strings.isBlank(linkname)) {
            this.dao().fetchLinks(list, linkname);
        }
        re.put("data", list);
        
        re.put("draw", draw);
        re.put("recordsTotal", length);
        return re;
    }
    
    
    /**
     * DataTable Page
     * 此方法主要是针对类中有字典数据的字段进行name值替换
     * @param length   页大小
     * @param start    start
     * @param draw     draw
     * @param orders   排序
     * @param columns  字段
     * @param cnd      查询条件
     * @param linkName 关联查询
     * @param subCnd   关联查询条件
     * @param keys     需要数据字典中 key-name值替换的map列表
     * @return
     */
    public NutMap dataCode(int length, int start, int draw, List<DataTableOrder> orders, List<DataTableColumn> columns, 
    		Cnd cnd, String linkName, Cnd subCnd,Map<String,String> keys) {
        NutMap re = new NutMap();
        if (orders != null && orders.size() > 0) {
            for (DataTableOrder order : orders) {
                DataTableColumn col = columns.get(order.getColumn());
                cnd.orderBy(Sqls.escapeSqlFieldValue(col.getData()).toString(), order.getDir());
            }
        }
        Pager pager = new OffsetPager(start, length);
        re.put("recordsFiltered", this.dao().count(this.getEntityClass(), cnd));
        List<T> list = this.dao().query(this.getEntityClass(), cnd, pager);
        if (!Strings.isBlank(linkName)) {
            if (subCnd != null)
                this.dao().fetchLinks(list, linkName, subCnd);
            else
                this.dao().fetchLinks(list, linkName);
        }
        for(int i=0;i<list.size();i++){
	        try {
	        	T entity = list.get(i);
	        	// 获取实体类的所有属性，返回Field数组
	        	Field[] fields = entity.getClass().getDeclaredFields();
	        	// 遍历所有属性
				for(Field field:fields){
					// 获取属性的名字
					String name = field.getName();
					// 将属性的首字符大写，方便构造get，set方法
					if(keys.containsKey(name)){
						String methodName = name.substring(0, 1).toUpperCase() + name.substring(1);
						String type = field.getGenericType().toString(); // 获取属性的类型
		                if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
		                    Method method = entity.getClass().getMethod("get" + methodName);
		                    String value = (String) method.invoke(entity); // 调用getter方法获取属性值
		                    if (!StringUtil.isEmpty(value)) {
		                    	method = entity.getClass().getMethod("set"+methodName,String.class);
		                    	Base_code baseCode = this.dao().fetch(Base_code.class,
		                    			Cnd.where("parentCode","=",keys.get(name)).and("childCode","=",value));
		                    	method.invoke(entity, baseCode == null ? value : baseCode.getChildName());
		                    }
		                }
					}
					list.set(i, entity);
				}
	        } catch (NoSuchMethodException e) {
	            e.printStackTrace();
	        } catch (SecurityException e) {
	            e.printStackTrace();
	        } catch (IllegalAccessException e) {
	            e.printStackTrace();
	        } catch (IllegalArgumentException e) {
	            e.printStackTrace();
	        } catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        re.put("data", list);
        re.put("draw", draw);
        re.put("recordsTotal", length);
        return re;
    }
    
    /**
     * 拼装条件Cnd
     * @param paramsMaps （key值 增加 equal和like  标识(标识分界符“_”) 
     * 					以判断是用等于"=" 还是用like） 默认如果无标识 则默认是等于
     * 					key必须与model类中的值相同
     * @param type  1 驼峰转下划线;   其他   则忽视
     * @return
     */
    public Cnd queryCondition(NutMap paramsMaps,Cnd cnd){
    	for(String key:paramsMaps.keySet()){
	    		if(key.contains("~")){
	    			String[] params = key.split("~");
//	    			if("1".equals(type))
//	    				params[0] = Underline2Camel.camel2Underline(params[0]);
	    			//拼装like条件语句
	    			if(StringUtil.LIKE.equals(params[0]))
	    				cnd.and(params[1],StringUtil.LIKE,
	    						StringUtil.transferLike(paramsMaps.get(key).toString()));
	    			//小于
	    			else if(StringUtil.LESSTHAN.equals(params[0]))
	    				cnd.and(params[1],StringUtil.LESSTHAN_SIGN,
	    						paramsMaps.get(key));
	    			//大于
	    			else if(StringUtil.GREATERTHAN.equals(params[0]))
	    				cnd.and(params[1],StringUtil.GREATERTHAN_SIGN,
	    						paramsMaps.get(key));
	    			//小于等于
	    			else if(StringUtil.LESSTHANEQUAL.equals(params[0]))
	    				cnd.and(params[1],StringUtil.LESSTHANEQUAL_SIGN,
	    						paramsMaps.get(key));
	    			//大于等于
	    			else if(StringUtil.GREATERTHANEQUAL.equals(params[0]))
	    				cnd.and(params[1],StringUtil.GREATERTHANEQUAL_SIGN,
	    						paramsMaps.get(key));
	    			//不等于
	    			else if(StringUtil.NOTEQUAL.equals(params[0]))
	    				cnd.and(params[1],StringUtil.NOTEQUAL_SIGN,
	    						paramsMaps.get(key));
	    			else
	    				cnd.and(params[1],StringUtil.EQUAL_SIGN,
	    						paramsMaps.get(key));
	    		}else{
	    			//默认拼装等于条件
	    			cnd.and(key,
	    						StringUtil.EQUAL_SIGN,paramsMaps.get(key));
	    		}
    		}
    	return cnd;
    }
    /**
     * 获取列表数据
     * @param paramsMaps
     * @return
     */
    public List<T> data(NutMap paramsMaps,Cnd cnd){
    	return this.dao().query(getEntityClass(), queryCondition(paramsMaps,cnd));
    }
    
    /**
     * 
     * @param tf 			文件对象
     * @param pathType		存放文件的分类文件夹
     * @param skipRows		忽略行数
     * @param columnCount   读取的列数
     * @return
     */
    public List<String[]> importDo(@Param("Filedata") TempFile tf,String pathType,int skipRows,int columnCount){
		try {
			String name = tf.getSubmittedFileName().substring(tf.getSubmittedFileName().indexOf(".")).toLowerCase();
			String p = Globals.AppRoot;
			String f = Globals.AppUploadPath + pathType + R.UU32() + name;
			File file = new File(p + f);
			Files.createFileIfNoExists(file);
			Files.write(file, tf.getInputStream());
			if(".xls".equals(name))
				return PoiExcelUtils.readExcel(p + f, skipRows,columnCount);
			else if(".xlsx".equals(name))
				return PoiExcelUtils.readXSSFExcel(p + f, skipRows,columnCount);
		} catch (Exception e) {
			log.info("import excel error", e);
			return null;
		}
		return null;
	}
}
