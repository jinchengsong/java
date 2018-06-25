package me.jincs.tool.ibatis.domain;

/**
 * Created by jinchengsong on 2018/6/25.
 */
public class ColumnDomain {
    private String columnName;
    private String dataType;
    private int dataLength;
    private String nullable;
    private String comments;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName.toLowerCase();
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = changeDbTypeToJavaType(dataType);
    }

    public int getDataLength() {
        return dataLength;
    }

    public void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    private String changeDbTypeToJavaType(String dbType){

        return DbType.getJavaType(dbType);
    }

    enum DbType{
        VARCHAR2("VARCHAR2","VARCHAR"),DATE("DATE","TIMESTAMP"),NUMBER("NUMBER","INTEGER");
        private String javaType;
        private String dbType;

        public String getDbType() {
            return dbType;
        }

        public void setDbType(String dbType) {
            this.dbType = dbType;
        }

        public String getJavaType() {
            return javaType;
        }

        public void setJavaType(String javaType) {
            this.javaType = javaType;
        }

        private DbType(String dbType,String javaType) {
            this.javaType = javaType;
            this.dbType = dbType;
        }
        // 普通方法
        public static String getJavaType(String dbType) {
            for (DbType c : DbType.values()) {
                if (c.getDbType().equalsIgnoreCase(dbType) ) {
                    return c.javaType;
                }
            }
            return null;
        }
    }

}

