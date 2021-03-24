package com.example.database.modules.warehouse.dto;

import com.example.database.common.validator.group.AddGroup;
import com.example.database.common.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;


/**
 * 文件夹
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-20
 */
@Data
@ApiModel(value = "文件夹")
public class FolderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	@Null(message="{id.null}", groups = AddGroup.class)
	@NotNull(message="{id.require}", groups = UpdateGroup.class)
	private Long id;

	@ApiModelProperty(value = "上级id")
	private Long pid;

	@ApiModelProperty(value = "仓库id")
	@NotNull(message="仓库id不能空", groups = AddGroup.class)
	private Long warehouseId;

	@ApiModelProperty(value = "文件夹名称")
	@NotNull(message="文件名不能空", groups = AddGroup.class)
	private String name;

	@ApiModelProperty(value = "容量大小")
	private Long size;

	@ApiModelProperty(value = "状态（0可用，1回收站，2不可用）")
	@Null(message = "无法修改状态", groups = AddGroup.class)
	@Null(message = "无法修改状态", groups = UpdateGroup.class)
	private Integer state;

	@ApiModelProperty(value = "创建者")
	private Long creator;

	@ApiModelProperty(value = "创建时间")
	private Date createDate;

	@ApiModelProperty(value = "更新者")
	private Long updater;

	@ApiModelProperty(value = "更新时间")
	private Date updateDate;


}