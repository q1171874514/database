package com.example.database.modules.warehouse.dto;

import com.example.database.common.validator.group.AddGroup;
import com.example.database.common.validator.group.UpdateGroup;
import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.Date;


/**
 * 文件
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.0.0 2021-03-20
 */
@Data
@ApiModel(value = "文件")
public class FileDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	@Null(message="{id.null}", groups = AddGroup.class)
	@NotNull(message="{id.require}", groups = UpdateGroup.class)
	private Long id;

	@ApiModelProperty(value = "上传id")
	@Null(message="文件上传id不可改", groups = UpdateGroup.class)
	private Long sysOssId;

	@ApiModelProperty(value = "文件夹")
	@NotNull(message="文件夹id不能为空", groups = AddGroup.class)
	private Long folderId;

	@ApiModelProperty(value = "上传的文件")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private MultipartFile file;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "大小")
	@Null(message="文件大小不可改", groups = UpdateGroup.class)
	private Long size;

	@ApiModelProperty(value = "URL地址")
	@Null(message="文件路径不可改", groups = UpdateGroup.class)
	private String url;

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