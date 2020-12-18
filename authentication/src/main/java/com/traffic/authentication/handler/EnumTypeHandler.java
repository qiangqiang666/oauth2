package com.traffic.authentication.handler;

import com.traffic.authentication.enums.CodeBaseEnum;
import com.traffic.authentication.enums.ErrorCodeEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * ibatis 枚举类型 绑定用
 *
 * @param <E>
 */
@MappedTypes(value = {ErrorCodeEnum.class})
public class EnumTypeHandler<E extends Enum<?> & CodeBaseEnum> extends BaseTypeHandler<CodeBaseEnum> {
    private Class<E> clazz;

    public EnumTypeHandler(Class<E> enumType) {
        if (enumType == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }

        this.clazz = enumType;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, CodeBaseEnum parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setInt(i, parameter.code());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return CodeBaseEnum.codeOf(clazz, rs.getInt(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return CodeBaseEnum.codeOf(clazz, rs.getInt(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return CodeBaseEnum.codeOf(clazz, cs.getInt(columnIndex));
    }
}