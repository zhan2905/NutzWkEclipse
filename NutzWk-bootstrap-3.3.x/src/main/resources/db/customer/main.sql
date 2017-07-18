/*customer:getMainCount*/
select 1 from customer_main a $condition
/*customer:getMainList*/
select  a.id,
		a.customer_no customerNo,
		 a.broadband_account broadbandAccount,
		 b.service_name serviceName,
   		 F_GET_CODE_NAME(a.customer_state,8) customerState,
		 F_GET_SERVICE_NAME(a.service_id,a.service_state) serviceState,
		 a.free_days freeDays,
		 a.call_time callTime ,
		 a.function_start_date functionStartDate,
         a.charge_start_date chargeStartDate,
		 F_JUDGE_BLACK_LIST(a.customer_no) blackList,
		 F_JUDGE_WHITE_LIST(a.customer_no) whiteList,
		 F_JUDGE_FILTER_SEGMENT(a.customer_no) shielding,
		 F_GET_CITY_NAME(1,a.city_code) cityCode,
		 c.loginname opBy,
		 a.opAt
from customer_main a
inner join service_main b on a.service_id = b.id and b.delFlag = 0
inner join sys_user c on c.id = a.opBy and c.delFlag = 0
$condition