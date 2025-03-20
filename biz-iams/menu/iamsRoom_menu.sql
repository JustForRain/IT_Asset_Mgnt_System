-- 该脚本不要直接执行， 注意维护菜单的父节点ID 默认 父节点-1 

-- 菜单SQL
insert into sys_menu ( menu_id,parent_id, path, permission, menu_type, icon, del_flag, create_time, sort_order, update_time, name)
values (1742259697278, '-1', '/iams/iamsRoom/index', '', '0', 'icon-bangzhushouji', '0', null , '8', null , '机房管理');

-- 菜单对应按钮SQL
insert into sys_menu ( menu_id,parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742259697279,1742259697278, 'iams_iamsRoom_view', '1', null, '1',  '0', null, '0', null, '机房查看');

insert into sys_menu ( menu_id,parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742259697280,1742259697278, 'iams_iamsRoom_add', '1', null, '1',  '0', null, '1', null, '机房新增');

insert into sys_menu (menu_id, parent_id, permission, menu_type, path, icon,  del_flag, create_time, sort_order, update_time, name)
values (1742259697281,1742259697278, 'iams_iamsRoom_edit', '1', null, '1',  '0', null, '2', null, '机房修改');

insert into sys_menu (menu_id, parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742259697282,1742259697278, 'iams_iamsRoom_del', '1', null, '1',  '0', null, '3', null, '机房删除');

insert into sys_menu ( menu_id,parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742259697283,1742259697278, 'iams_iamsRoom_export', '1', null, '1',  '0', null, '3', null, '导入导出');