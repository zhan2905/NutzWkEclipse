/*getBaseCode*/
SELECT b.name name,b.code code from sys_dict a 
inner join sys_dict b on a.id = b.parentId 
where b.delFlag = 0 and a.code = @parentCode