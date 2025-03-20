-- 该脚本不要直接执行， 注意维护菜单的父节点ID 默认 父节点-1 

-- 菜单SQL
insert into sys_menu ( menu_id,parent_id, path, permission, menu_type, icon, del_flag, create_time, sort_order, update_time, name)
values (1742199951933, '-1', '/pig/iamsContract/index', '', '0', 'icon-bangzhushouji', '0', null , '8', null , '合同管理');

-- 菜单对应按钮SQL
insert into sys_menu ( menu_id,parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742199951934,1742199951933, 'pig_iamsContract_view', '1', null, '1',  '0', null, '0', null, '合同查看');

insert into sys_menu ( menu_id,parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742199951935,1742199951933, 'pig_iamsContract_add', '1', null, '1',  '0', null, '1', null, '合同新增');

insert into sys_menu (menu_id, parent_id, permission, menu_type, path, icon,  del_flag, create_time, sort_order, update_time, name)
values (1742199951936,1742199951933, 'pig_iamsContract_edit', '1', null, '1',  '0', null, '2', null, '合同修改');

insert into sys_menu (menu_id, parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742199951937,1742199951933, 'pig_iamsContract_del', '1', null, '1',  '0', null, '3', null, '合同删除');

insert into sys_menu ( menu_id,parent_id, permission, menu_type, path, icon, del_flag, create_time, sort_order, update_time, name)
values (1742199951938,1742199951933, 'pig_iamsContract_export', '1', null, '1',  '0', null, '3', null, '导入导出');