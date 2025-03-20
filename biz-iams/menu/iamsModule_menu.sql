-- 该脚本不要直接执行， 注意维护菜单的父节点ID 默认 父节点-1 

-- 菜单SQL
insert into sys_menu ( menu_id,parent_id, path, permission, menu_type, icon, del_flag, create_time, sort_order, update_time, name)
values (1742260236449, '-1', '/iams/iamsModule/index', '', '0', 'icon-bangzhushouji', '0', null , '8', null , '微模块管理');

-- 菜单对应按钮SQL
insert into sys_menu ( menu_id,parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742260236450,1742260236449, 'iams_iamsModule_view', '1', null, '1',  '0', null, '0', null, '微模块查看');

insert into sys_menu ( menu_id,parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742260236451,1742260236449, 'iams_iamsModule_add', '1', null, '1',  '0', null, '1', null, '微模块新增');

insert into sys_menu (menu_id, parent_id, permission, menu_type, path, icon,  del_flag, create_time, sort_order, update_time, name)
values (1742260236452,1742260236449, 'iams_iamsModule_edit', '1', null, '1',  '0', null, '2', null, '微模块修改');

insert into sys_menu (menu_id, parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742260236453,1742260236449, 'iams_iamsModule_del', '1', null, '1',  '0', null, '3', null, '微模块删除');

insert into sys_menu ( menu_id,parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742260236454,1742260236449, 'iams_iamsModule_export', '1', null, '1',  '0', null, '3', null, '导入导出');