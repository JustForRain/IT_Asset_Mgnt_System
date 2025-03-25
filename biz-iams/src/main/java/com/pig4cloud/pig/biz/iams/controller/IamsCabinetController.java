package com.pig4cloud.pig.biz.iams.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.biz.iams.dto.IamsCabinetDto;
import com.pig4cloud.pig.biz.iams.entity.IamsCabinetEntity;
import com.pig4cloud.pig.biz.iams.entity.IamsModuleEntity;
import com.pig4cloud.pig.biz.iams.entity.IamsShelfEntity;
import com.pig4cloud.pig.biz.iams.service.*;
import com.pig4cloud.pig.biz.iams.vo.SelectOption;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 机柜
 *
 * @author pig
 * @date 2025-03-18 09:29:30
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/iamsCabinet")
@Tag(description = "iamsCabinet", name = "机柜管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class IamsCabinetController {

	private final IamsCabinetService iamsCabinetService;
	private final IamsModuleService iamsModuleService;
	private final IamsShelfService iamsShelfService;
	private final IamsCabinetDetailService iamsCabinetDetailService;

	/**
	 * 分页查询
	 *
	 * @param page        分页对象
	 * @param iamsCabinet 机柜
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('iams_iamsCabinet_view')")
	public R getIamsCabinetPage(@ParameterObject Page page, @ParameterObject IamsCabinetEntity iamsCabinet) {
		LambdaQueryWrapper<IamsCabinetEntity> wrapper = Wrappers.lambdaQuery();
		Page searchResult = iamsCabinetService.page(page, wrapper);
		List records = searchResult.getRecords();
		List list = records.stream().map(cabinet -> {
			IamsCabinetDto iamsCabinetDto = BeanUtil.copyProperties(cabinet, IamsCabinetDto.class);
			IamsModuleEntity iamsModule = iamsModuleService.getById(iamsCabinetDto.getModuleId());
			iamsCabinetDto.setModuleName(iamsModule.getName());
			return iamsCabinetDto;
		}).toList();
		searchResult.setRecords(list);
		return R.ok(searchResult);
	}


	/**
	 * 通过id查询机柜
	 *
	 * @param id id
	 * @return R
	 */
	@Operation(summary = "通过id查询", description = "通过id查询")
	@GetMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('iams_iamsCabinet_view')")
	public R getById(@PathVariable("id") Long id) {
		return R.ok(iamsCabinetService.getById(id));
	}

	/**
	 * 新增机柜
	 *
	 * @param iamsCabinet 机柜
	 * @return R
	 */
	@Operation(summary = "新增机柜", description = "新增机柜")
	@SysLog("新增机柜")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('iams_iamsCabinet_add')")
	public R save(@RequestBody IamsCabinetDto iamsCabinet) {
		if (StrUtil.isEmptyIfStr(iamsCabinet.getName())) {
			iamsCabinet.setName(StrUtil.format("{}行{}列", iamsCabinet.getRowNum(), iamsCabinet.getColumnNum()));
		}
		return R.ok(iamsCabinetService.save(iamsCabinet));
	}

	/**
	 * 修改机柜
	 *
	 * @param iamsCabinet 机柜
	 * @return R
	 */
	@Operation(summary = "修改机柜", description = "修改机柜")
	@SysLog("修改机柜")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('iams_iamsCabinet_edit')")
	public R updateById(@RequestBody IamsCabinetEntity iamsCabinet) {
		return R.ok(iamsCabinetService.updateById(iamsCabinet));
	}

	/**
	 * 通过id删除机柜
	 *
	 * @param ids id列表
	 * @return R
	 */
	@Operation(summary = "通过id删除机柜", description = "通过id删除机柜")
	@SysLog("通过id删除机柜")
	@DeleteMapping
	@PreAuthorize("@pms.hasPermission('iams_iamsCabinet_del')")
	public R removeById(@RequestBody Long[] ids) {
		return R.ok(iamsCabinetService.removeBatchByIds(CollUtil.toList(ids)));
	}


	/**
	 * 导出excel 表格
	 *
	 * @param iamsCabinet 查询条件
	 * @param ids         导出指定ID
	 * @return excel 文件流
	 */
	@ResponseExcel
	@GetMapping("/export")
	@PreAuthorize("@pms.hasPermission('iams_iamsCabinet_export')")
	public List<IamsCabinetEntity> export(IamsCabinetEntity iamsCabinet, Long[] ids) {
		return iamsCabinetService.list(Wrappers.lambdaQuery(iamsCabinet).in(ArrayUtil.isNotEmpty(ids), IamsCabinetEntity::getId, ids));
	}

	/**
	 * 通过id查询机柜所有U的列表，并标记已使用以及未使用
	 * @param id
	 * @return
	 */
	@GetMapping("/unit/{id}")
	@PreAuthorize("@pms.hasPermission('iams_iamsCabinet_view')")
	public R getUnit(@PathVariable("id") Long id) {
		IamsCabinetEntity iamsCabinetEntity = iamsCabinetService.getById(id);
		Integer size = iamsCabinetEntity.getSize();
		Map<String, Object> map = new HashMap<>();
		List<SelectOption> selectOptions = new ArrayList<>();
		for (int i = 1; i <= size; i++) {
			SelectOption selectOption = new SelectOption();
			selectOption.setKey(String.valueOf(i));
			selectOption.setLabel(i + "U");
			selectOptions.add(selectOption);
		}
		map.put("unit", selectOptions);
		if (iamsCabinetEntity.getSortOrder()) {
			Collections.reverse(selectOptions);
		}
		//key label
		//获取已使用位置
		List<IamsShelfEntity> iamsShelfs = iamsShelfService.getByCabinetId(id);
		if (CollUtil.isNotEmpty(iamsShelfs)) {
			iamsShelfs.stream().forEach(iamsShelf -> {
				for (int i = iamsShelf.getUnitStart(); i <= iamsShelf.getUnitEnd(); i++) {
					int finalI = i;
					selectOptions.forEach(selectOption -> {
						if (selectOption.getLabel().equals(finalI + "U")) {
							selectOption.setDisabled(true);
						}
					});
				}
			});
		}
		return R.ok(map);
	}

	/**
	 * 通过id查询机柜设备上架详情
	 * @param id
	 * @return
	 */
	@GetMapping("/unit/detail/{id}")
	@PreAuthorize("@pms.hasPermission('iams_iamsCabinet_view')")
	public R getUnitDetail(@PathVariable("id") Long id) {
		return R.ok(iamsCabinetDetailService.getUnitDetail(id));
	}
}