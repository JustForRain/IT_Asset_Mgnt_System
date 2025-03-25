-- 该脚本不要直接执行， 注意维护菜单的父节点ID 默认 父节点-1 

-- 菜单SQL
insert into sys_menu ( menu_id,parent_id, path, permission, menu_type, icon, del_flag, create_time, sort_order, update_time, name)
values (1742889621238, '-1', '/iams/iamsAccount/index', '', '0', 'icon-bangzhushouji', '0', null , '8', null , '账户管理');

-- 菜单对应按钮SQL
insert into sys_menu ( menu_id,parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742889621239,1742889621238, 'iams_iamsAccount_view', '1', null, '1',  '0', null, '0', null, '账户查看');

insert into sys_menu ( menu_id,parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742889621240,1742889621238, 'iams_iamsAccount_add', '1', null, '1',  '0', null, '1', null, '账户新增');

insert into sys_menu (menu_id, parent_id, permission, menu_type, path, icon,  del_flag, create_time, sort_order, update_time, name)
values (1742889621241,1742889621238, 'iams_iamsAccount_edit', '1', null, '1',  '0', null, '2', null, '账户修改');

insert into sys_menu (menu_id, parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742889621242,1742889621238, 'iams_iamsAccount_del', '1', null, '1',  '0', null, '3', null, '账户删除');

insert into sys_menu ( menu_id,parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742889621243,1742889621238, 'iams_iamsAccount_export', '1', null, '1',  '0', null, '3', null, '导入导出');