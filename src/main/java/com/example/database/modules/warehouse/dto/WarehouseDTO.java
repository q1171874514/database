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
import java.util.List;


/**
 * 仓库
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-20
 */
@Data
@ApiModel(value = "仓库")
public class WarehouseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	@Null(message="{id.null}", groups = AddGroup.class)
	@NotNull(message="{id.require}", groups = UpdateGroup.class)
	private Long id;

	@ApiModelProperty(value = "名称")
	@NotNull(message = "仓库名称不能空", groups = AddGroup.class)
	private String name;

	@ApiModelProperty(value = "容量大小{B)")
	@NotNull(message = "仓库大小不能空", groups = AddGroup.class)
	private Long size;

	@ApiModelProperty(value = "绑定的用户名")
	private List<String> userNameList;

	@ApiModelProperty(value = "状态（0在使用，1无人用）")
	@Null(message = "无法修改状态", groups = AddGroup.class)
	@Null(message = "无法修改状态", groups = UpdateGroup.class)
	private Integer state;

	@ApiModelProperty(value = "创建者")
	private Long creator;

	@ApiModelProperty(value = "创建者用户名")
	private String creatorUsername;

	@ApiModelProperty(value = "创建时间")
	private Date createDate;

	@ApiModelProperty(value = "更新者")
	private Long updater;

	@ApiModelProperty(value = "创建者用户名")
	private String updaterUsername;

	@ApiModelProperty(value = "更新时间")
	private Date updateDate;
}