<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<!DOCTYPE sqlMap PUBLIC "-//iBATIS.com//DTD SQL Map 2.0//EN" "http://www.ibatis.com/dtd/sql-map-2.dtd">

<sqlMap namespace="${tableName}">
	<typeAlias alias="${domainAlias}" type="${package}.${domainName}"/>
<!--TA+BEGIN-->
	<!-- 查询一批数据的查询 -->
	<select id="getList" parameterClass="map" resultClass="${domainAlias}">
		SELECT
            <#list columns as col>
            a.${col.columnName} as ${col.columnName}<#sep>,</#sep>    <!-- ${col.comments} -->
            </#list>
		  FROM ${tableName} a
		 WHERE 1=1
		 <#list columns as col>
		<isNotEmpty prepend="AND" property="${col.columnName}">   <!-- ${col.comments} -->
		       a.${col.columnName} = #${col.columnName}:${col.dataType}#
		</isNotEmpty>
		</#list>
	</select>

	<!-- 通过主键进行查询 -->
	<select id="get" parameterClass="map" resultClass="${domainAlias}">
		SELECT
            <#list columns as col>
              a.${col.columnName} as ${col.columnName}<#sep>,</#sep>    <!-- ${col.comments} -->
            </#list>
		  FROM ${tableName} a
		 WHERE 1 = 1
         <#list pkCols as pk>
         <![CDATA[	   AND a.${pk.columnName} = #${pk.columnName}#    ]]>
         </#list>
	</select>

	<!-- 插入单条数据 -->
	<insert id="insert" parameterClass="${domainAlias}">
		INSERT INTO ${tableName} (
            <#list columns as col>
              ${col.columnName}<#sep>,</#sep>    <!-- ${col.comments} -->
            </#list>
		 )
		VALUES (
            <#list columns as col>
              #${col.columnName}:${col.dataType}#<#sep>,</#sep>    <!-- ${col.comments} -->
            </#list>
        )
	</insert>

	<!-- 通过主键修改数据库记录，domain的每一个字段都要设值，如果是null，则数据库中也是null -->
	<update id="update" parameterClass="map">
		UPDATE ${tableName} SET
            <#list columns as col>
              ${col.columnName}=#${col.columnName}:${col.dataType}#<#sep>,</#sep>    <!-- ${col.comments} -->
            </#list>
		 WHERE 1 = 1
         <#list pkCols as col>
         <![CDATA[	   AND ${col.columnName} = #${col.columnName}#    ]]>
         </#list>
	</update>

	<!-- 通过DTO更新单条数据,主键不能为空，只修改你设定值得字段，并且值不为null或"" -->
	<update id="updateNotEmpty" parameterClass="map">
		UPDATE ${tableName}
		 <dynamic prepend="SET">
            <#list columns as col>
            <isNotEmpty prepend="," property="${col.columnName}">   <!-- ${col.comments}   -->
               ${col.columnName} = #${col.columnName}:${col.dataType}#
            </isNotEmpty>
            </#list>
		 </dynamic>
		 WHERE 1 = 1
         <#list pkCols as col>
         <![CDATA[	   AND ${col.columnName} = #${col.columnName}#    ]]>
         </#list>
	</update>

	<!-- 通过DTO更新单条数据，主键不能为空，只修改你设定值得字段，值可以为null或"" -->
	<update id="updateAvailable" parameterClass="map">
		UPDATE ${tableName}
		 <dynamic prepend="SET">
            <#list columns as col>
            <isPropertyAvailable prepend="," property="${col.columnName}">   <!-- ${col.comments}    -->
               ${col.columnName} = #${col.columnName}:${col.dataType}#
            </isPropertyAvailable>
            </#list>

		 </dynamic>
		 WHERE 1 = 1
         <#list pkCols as col>
         <![CDATA[	   AND ${col.columnName} = #${col.columnName}#    ]]>
         </#list>
    </update>

	<!--通过主键删除单条数据-->
	<delete id="delete" parameterClass="map">
		DELETE  FROM  ${tableName}
		 WHERE 1 = 1
         <#list pkCols as col>
         <![CDATA[	   AND ${col.columnName} = #${col.columnName}#    ]]>
         </#list>
    </delete>

</sqlMap>
