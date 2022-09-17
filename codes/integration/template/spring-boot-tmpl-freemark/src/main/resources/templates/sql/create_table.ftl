CREATE TABLE `${table.database}`.`${table.tableName}` (
<#list table.columns as item>
  `${item.columnName}` ${item.columnType}<#if item?index != table.columns?size - 1>,</#if>
</#list>
);
