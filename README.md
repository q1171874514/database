# authority
RBAC权限管理

## 1.OutTablesField:查询外表字段的注解
### 用法：
      使用在dto上
### 依赖条件：
      1.service要继承OutFieldService基础类
      2.dao层要继承OutFieldDao
### 字段说明：
      1.outTableName：外表的表名称（必填）
      2.foreignKey：主表外键（与外表对应的字段，默认id）
      3.outKey：外表对应的建（与foreignKey对应的建）
      4.outField：外表字段（需要获得的字段，查询的字段，覆盖）
      5.outTableWhere：查询条件（默认：outKey = $(foreignKey) ,建议不改，因为还未完善）
## 2.ValidatorUtils：数据校验类
### 用法：
      使用在Controller层的方法中，和dto的注解配合
### dto注解参考：
![image](https://user-images.githubusercontent.com/33956313/109384125-b279a700-7925-11eb-8dab-03c22dc84e3c.png)
https://blog.csdn.net/qq_33655674/article/details/81509615

## 3.@TableField：自动填充处理
      1.配置：在common的handler中的FieldMetaObjectHandler进行配置
      2.使用：主要在entity的字段中，在修改或保存时填充
