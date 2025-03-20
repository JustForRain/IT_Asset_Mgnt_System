-- 该脚本不要直接执行， 注意维护菜单的父节点ID 默认 父节点-1 

-- 菜单SQL
insert into sys_menu ( menu_id,parent_id, path, permission, menu_type, icon, del_flag, create_time, sort_order, update_time, name)
values (1742281323153, '-1', '/iams/iamsShelf/index', '', '0', 'icon-bangzhushouji', '0', null , '8', null , '上架管理');

-- 菜单对应按钮SQL
insert into sys_menu ( menu_id,parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742281323154,1742281323153, 'iams_iamsShelf_view', '1', null, '1',  '0', null, '0', null, '上架查看');

insert into sys_menu ( menu_id,parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742281323155,1742281323153, 'iams_iamsShelf_add', '1', null, '1',  '0', null, '1', null, '上架新增');

insert into sys_menu (menu_id, parent_id, permission, menu_type, path, icon,  del_flag, create_time, sort_order, update_time, name)
values (1742281323156,1742281323153, 'iams_iamsShelf_edit', '1', null, '1',  '0', null, '2', null, '上架修改');

insert into sys_menu (menu_id, parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742281323157,1742281323153, 'iams_iamsShelf_del', '1', null, '1',  '0', null, '3', null, '上架删除');

insert into sys_menu ( menu_id,parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742281323158,1742281323153, 'iams_iamsShelf_export', '1', null, '1',  '0', null, '3', null, '导入导出');