-- 该脚本不要直接执行， 注意维护菜单的父节点ID 默认 父节点-1 

-- 菜单SQL
insert into sys_menu ( menu_id,parent_id, path, permission, menu_type, icon, del_flag, create_time, sort_order, update_time, name)
values (1742261370114, '-1', '/iams/iamsCabinet/index', '', '0', 'icon-bangzhushouji', '0', null , '8', null , '机柜管理');

-- 菜单对应按钮SQL
insert into sys_menu ( menu_id,parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742261370115,1742261370114, 'iams_iamsCabinet_view', '1', null, '1',  '0', null, '0', null, '机柜查看');

insert into sys_menu ( menu_id,parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742261370116,1742261370114, 'iams_iamsCabinet_add', '1', null, '1',  '0', null, '1', null, '机柜新增');

insert into sys_menu (menu_id, parent_id, permission, menu_type, path, icon,  del_flag, create_time, sort_order, update_time, name)
values (1742261370117,1742261370114, 'iams_iamsCabinet_edit', '1', null, '1',  '0', null, '2', null, '机柜修改');

insert into sys_menu (menu_id, parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742261370118,1742261370114, 'iams_iamsCabinet_del', '1', null, '1',  '0', null, '3', null, '机柜删除');

insert into sys_menu ( menu_id,parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742261370119,1742261370114, 'iams_iamsCabinet_export', '1', null, '1',  '0', null, '3', null, '导入导出');