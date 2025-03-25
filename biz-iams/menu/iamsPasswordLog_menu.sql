-- 该脚本不要直接执行， 注意维护菜单的父节点ID 默认 父节点-1 

-- 菜单SQL
insert into sys_menu ( menu_id,parent_id, path, permission, menu_type, icon, del_flag, create_time, sort_order, update_time, name)
values (1742895303294, '-1', '/iams/iamsPasswordLog/index', '', '0', 'icon-bangzhushouji', '0', null , '8', null , '密码修改记录管理');

-- 菜单对应按钮SQL
insert into sys_menu ( menu_id,parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742895303295,1742895303294, 'iams_iamsPasswordLog_view', '1', null, '1',  '0', null, '0', null, '密码修改记录查看');

insert into sys_menu ( menu_id,parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742895303296,1742895303294, 'iams_iamsPasswordLog_add', '1', null, '1',  '0', null, '1', null, '密码修改记录新增');

insert into sys_menu (menu_id, parent_id, permission, menu_type, path, icon,  del_flag, create_time, sort_order, update_time, name)
values (1742895303297,1742895303294, 'iams_iamsPasswordLog_edit', '1', null, '1',  '0', null, '2', null, '密码修改记录修改');

insert into sys_menu (menu_id, parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742895303298,1742895303294, 'iams_iamsPasswordLog_del', '1', null, '1',  '0', null, '3', null, '密码修改记录删除');

insert into sys_menu ( menu_id,parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742895303299,1742895303294, 'iams_iamsPasswordLog_export', '1', null, '1',  '0', null, '3', null, '导入导出');