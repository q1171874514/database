package com.example.database.modules.sys.dto;

import com.example.database.common.validator.group.AddGroup;
import com.example.database.common.validator.group.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;


/**
 * 表字段设置
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-08
 */
@Data
@ApiModel(value = "字段设置")
public class SysSetUpDataDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	@Null(message="{id.null}", groups = AddGroup.class)
	@NotNull(message="{id.require}", groups = UpdateGroup.class)
	private Long id;

	@ApiModelProperty(value = "设置表名ID")
	@NotNull(message="外键不能空", groups = AddGroup.class)
	@Null(message="外键不可修改", groups = UpdateGroup.class)
	private Long setUpTypeId;

	@ApiModelProperty(value = "字段标签")
	private String label;

	@ApiModelProperty(value = "字段名")
	@NotNull(message="字段名不能空", groups = AddGroup.class)
	@Null(message="字段名不可修改", groups = UpdateGroup.class)
	private String fieldName;

	@ApiModelProperty(value = "字段值")
	private String fieldValue;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "保存类型")
	private Integer saveType;

	@ApiModelProperty(value = "更新类型")
	private Integer updateType;

	@ApiModelProperty(value = "排序")
	private Integer sort;

	@ApiModelProperty(value = "状态（0可用，1不可用）")
	private Integer state;

	@ApiModelProperty(value = "创建者")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long creator;

	@ApiModelProperty(value = "更新者")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Long updater;

	@ApiModelProperty(value = "创建时间")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Date createDate;

	@ApiModelProperty(value = "更新时间")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Date updateDate;
}