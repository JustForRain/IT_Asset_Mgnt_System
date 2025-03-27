package com.pig4cloud.pig.biz.iams.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.biz.iams.dto.ChangePasswordDto;
import com.pig4cloud.pig.biz.iams.dto.IamsAccountDto;
import com.pig4cloud.pig.biz.iams.entity.*;
import com.pig4cloud.pig.biz.iams.service.*;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 账户
 *
 * @author pig
 * @date 2025-03-25 16:00:21
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/iamsAccount")
@Tag(description = "iamsAccount", name = "账户管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class IamsAccountController {

	private final IamsAccountService iamsAccountService;
	private final IamsShelfService iamsShelfService;
	private final IamsAssetService iamsAssetService;
	private final IamsPasswordLogService iamsPasswordLogService;
	private final IamsAssetAccountService iamsAssetAccountService;

	/**
	 * 分页查询
	 *
	 * @param page        分页对象
	 * @param iamsAccount 账户
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('iams_iamsAccount_view')")
	public R getIamsAccountPage(@ParameterObject Page page, @ParameterObject IamsAccountEntity iamsAccount) {
		LambdaQueryWrapper<IamsAccountEntity> wrapper = Wrappers.lambdaQuery();
		Page resultPage = iamsAccountService.page(page, wrapper);
		List list = resultPage.getRecords().stream().map(iamsAccountEntity -> {
			IamsAccountDto iamsAccountDto = BeanUtil.copyProperties(iamsAccountEntity, IamsAccountDto.class);
			if (!iamsAccountDto.getSingle()) {
				List<IamsAssetAccountEntity> iamsAssetAccountEntities = iamsAssetAccountService.listByAccountId(iamsAccountDto.getId());
				List<IamsShelfEntity> iamsShelfEntityList = iamsShelfService.listByAssetIds(iamsAssetAccountEntities.stream().mapToLong(IamsAssetAccountEntity::getAssetId).boxed().toList());
				StringBuffer stringBuffer = new StringBuffer();
				iamsShelfEntityList.forEach(iamsShelfEntity -> {
					stringBuffer.append(iamsShelfEntity.getRole());
					stringBuffer.append("\n");
				});
				// 新增删除逻辑
				if (!stringBuffer.isEmpty()) {
					stringBuffer.setLength(stringBuffer.length() - 1);
				}
				iamsAccountDto.setRole(stringBuffer.toString());
			} else {
				IamsShelfEntity shelfEntity = iamsShelfService.getByAssetId(iamsAccountDto.getAssetId());
				iamsAccountDto.setRole(shelfEntity.getRole());
			}
			return iamsAccountDto;
		}).toList();
		resultPage.setRecords(list);
		return R.ok(resultPage);
	}


	/**
	 * 通过id查询账户
	 *
	 * @param id id
	 * @return R
	 */
	@Operation(summary = "通过id查询", description = "通过id查询")
	@GetMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('iams_iamsAccount_view')")
	public R getById(@PathVariable("id") Long id) {
		IamsAccountEntity accountEntity = iamsAccountService.getById(id);
		IamsAccountDto iamsAccountDto = BeanUtil.copyProperties(accountEntity, IamsAccountDto.class);
		IamsAssetEntity assetEntity = iamsAssetService.getById(accountEntity.getAssetId());
		if (accountEntity.getSingle()) {
			iamsAccountDto.setSn(assetEntity.getSn());
		} else {
			List<IamsAssetAccountEntity> iamsAssetAccountEntities = iamsAssetAccountService.listByAccountId(accountEntity.getId());
			List<IamsAssetEntity> iamsAssetEntities = iamsAssetService.listByIds(iamsAssetAccountEntities.stream().map(IamsAssetAccountEntity::getAssetId).toList());
			iamsAccountDto.setSns(iamsAssetEntities.stream().map(IamsAssetEntity::getSn).toList());
		}
		return R.ok(iamsAccountDto);
	}

	/**
	 * 新增账户
	 *
	 * @param iamsAccount 账户
	 * @return R
	 */
	@Operation(summary = "新增账户", description = "新增账户")
	@SysLog("新增账户")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('iams_iamsAccount_add')")
	public R save(@RequestBody IamsAccountDto iamsAccount) {
		if (iamsAccount.getSingle()) {
			//单设备情况
			String sn = iamsAccount.getSn();
			IamsAssetEntity assetEntity = iamsAssetService.getBySn(sn);
			iamsAccount.setAssetId(assetEntity.getId());
			iamsAccountService.save(iamsAccount);
//			IamsAssetAccountEntity iamsAssetAccountEntity = IamsAssetAccountEntity.builder().assetId(assetEntity.getId()).accountId(iamsAccount.getId()).build();
//			iamsAssetAccountService.save(iamsAssetAccountEntity);
		} else {
			//多设备情况
			iamsAccountService.save(iamsAccount);
			iamsAccount.getSns().forEach(sn -> {
				IamsAssetEntity iamsAssetEntity = iamsAssetService.getBySn(sn);
				IamsAssetAccountEntity assetAccountEntity = IamsAssetAccountEntity.builder().accountId(iamsAccount.getId()).assetId(iamsAssetEntity.getId()).build();
				iamsAssetAccountService.save(assetAccountEntity);
			});
		}
		return R.ok();
	}

	/**
	 * 修改账户
	 *
	 * @param iamsAccount 账户
	 * @return R
	 */
	@Operation(summary = "修改账户", description = "修改账户")
	@SysLog("修改账户")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('iams_iamsAccount_edit')")
	public R updateById(@RequestBody IamsAccountDto iamsAccount) {
		if (iamsAccount.getSingle()) {
			//通过iamsAccount.SN在asset表中查找ASSET ID
			IamsAssetEntity iamsAssetEntity = iamsAssetService.getBySn(iamsAccount.getSn());
			//通过ACCOUNT ID在account表查找ASSET ID
			IamsAccountEntity iamsAccountEntity = iamsAccountService.getById(iamsAccount.getId());
			if (!iamsAssetEntity.getId().equals(iamsAccountEntity.getAssetId())) {
				//判断两者资产ID不相等时
				iamsAccount.setAssetId(iamsAssetEntity.getId());
			}
			iamsAccountService.updateById(iamsAccount);
		} else if (iamsAccount.getSns().size() > 1) {
			iamsAccountService.updateById(iamsAccount);
			//删除原有的记录
			iamsAssetAccountService.removeByAccountId(iamsAccount.getId());
			//插入传入的记录
			List<String> sns = iamsAccount.getSns();
			List<IamsAssetEntity> iamsAssetEntities = iamsAssetService.listBySns(sns);
			List<IamsAssetAccountEntity> saveBatchList = new ArrayList<>();
			iamsAssetEntities.stream().mapToLong(IamsAssetEntity::getId).forEach(assetId -> {
				IamsAssetAccountEntity assetAccountEntity = IamsAssetAccountEntity.builder().assetId(assetId).accountId(iamsAccount.getId()).build();
				saveBatchList.add(assetAccountEntity);
			});
			iamsAssetAccountService.saveBatch(saveBatchList);
		}
		return R.ok();
	}

	/**
	 * 通过id删除账户
	 *
	 * @param ids id列表
	 * @return R
	 */
	@Operation(summary = "通过id删除账户", description = "通过id删除账户")
	@SysLog("通过id删除账户")
	@DeleteMapping
	@PreAuthorize("@pms.hasPermission('iams_iamsAccount_del')")
	public R removeById(@RequestBody Long[] ids) {
		return R.ok(iamsAccountService.removeBatchByIds(CollUtil.toList(ids)));
	}


	/**
	 * 导出excel 表格
	 *
	 * @param iamsAccount 查询条件
	 * @param ids         导出指定ID
	 * @return excel 文件流
	 */
	@ResponseExcel
	@GetMapping("/export")
	@PreAuthorize("@pms.hasPermission('iams_iamsAccount_export')")
	public List<IamsAccountEntity> export(IamsAccountEntity iamsAccount, Long[] ids) {
		return iamsAccountService.list(Wrappers.lambdaQuery(iamsAccount).in(ArrayUtil.isNotEmpty(ids), IamsAccountEntity::getId, ids));
	}

	/**
	 * 修改密码
	 *
	 * @param changePasswordDto 修改密码
	 * @return R
	 */
	@Operation(summary = "修改密码", description = "修改密码")
	@SysLog("修改账户")
	@PutMapping("/changePassword")
	@PreAuthorize("@pms.hasPermission('iams_iamsAccount_edit')")
	public R changePasswordById(@RequestBody ChangePasswordDto changePasswordDto) {
		// 更新account表
		Long accountId = changePasswordDto.getId();
		IamsAccountEntity accountEntity = iamsAccountService.getById(accountId);
//		if (accountEntity.getSingle()) {
			//单设备改密码
			accountEntity.setPassword(changePasswordDto.getNewPassword());
			iamsAccountService.updateById(accountEntity);
			//增加iams_password_log 记录
			IamsPasswordLogEntity passwordLogEntity = new IamsPasswordLogEntity();
			passwordLogEntity.setAccountId(accountId);
			passwordLogEntity.setAssetId(accountEntity.getAssetId());
			passwordLogEntity.setOldPassword(changePasswordDto.getOldPassword());
			passwordLogEntity.setNewPassword(changePasswordDto.getNewPassword());
			if(!accountEntity.getSingle()){
				//多设备情况下，资产ID的处理，保存两条密码修改记录
				List<IamsAssetAccountEntity> iamsAssetAccountEntities = iamsAssetAccountService.listByAccountId(accountId);
				iamsAssetAccountEntities.forEach(iamsAssetAccountEntity -> {
					passwordLogEntity.setAssetId(iamsAssetAccountEntity.getAssetId());
					iamsPasswordLogService.save(passwordLogEntity);
					passwordLogEntity.setId(null);
				});
			}else {
				iamsPasswordLogService.save(passwordLogEntity);
			}
//		} else {
//			//todo 多设备更新密码失败
//			//多设备改密码
//			iamsAccountService.list(Wrappers.lambdaQuery(new IamsAccountEntity()).eq(IamsAccountEntity::getVsn, accountEntity.getVsn())).forEach(iamsAccountEntity -> {
//				iamsAccountEntity.setPassword(changePasswordDto.getNewPassword());
//				iamsAccountService.updateById(iamsAccountEntity);
//				//增加iams_password_log 记录
//				IamsPasswordLogEntity passwordLogEntity = new IamsPasswordLogEntity();
//				passwordLogEntity.setAccountId(accountId);
//				passwordLogEntity.setAssetId(iamsAccountEntity.getAssetId());
//				passwordLogEntity.setOldPassword(changePasswordDto.getOldPassword());
//				passwordLogEntity.setNewPassword(changePasswordDto.getNewPassword());
//				iamsPasswordLogService.save(passwordLogEntity);
//			});
//		}
		return R.ok();
	}
}