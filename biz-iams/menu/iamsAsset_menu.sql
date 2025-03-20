-- 该脚本不要直接执行， 注意维护菜单的父节点ID 默认 父节点-1 

-- 菜单SQL
insert into sys_menu ( menu_id,parent_id, path, permission, menu_type, icon, del_flag, create_time, sort_order, update_time, name)
values (1742201108103, '-1', '/iams/iamsAsset/index', '', '0', 'icon-bangzhushouji', '0', null , '8', null , '资产管理');

-- 菜单对应按钮SQL
insert into sys_menu ( menu_id,parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742201108104,1742201108103, 'iams_iamsAsset_view', '1', null, '1',  '0', null, '0', null, '资产查看');

insert into sys_menu ( menu_id,parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742201108105,1742201108103, 'iams_iamsAsset_add', '1', null, '1',  '0', null, '1', null, '资产新增');

insert into sys_menu (menu_id, parent_id, permission, menu_type, path, icon,  del_flag, create_time, sort_order, update_time, name)
values (1742201108106,1742201108103, 'iams_iamsAsset_edit', '1', null, '1',  '0', null, '2', null, '资产修改');

insert into sys_menu (menu_id, parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742201108107,1742201108103, 'iams_iamsAsset_del', '1', null, '1',  '0', null, '3', null, '资产删除');

insert into sys_menu ( menu_id,parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742201108108,1742201108103, 'iams_iamsAsset_export', '1', null, '1',  '0', null, '3', null, '导入导出');