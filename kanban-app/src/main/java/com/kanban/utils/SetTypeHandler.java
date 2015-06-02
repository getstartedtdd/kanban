package com.kanban.utils;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by L.x on 15-6-3.
 */
@MappedTypes({Set.class, HashSet.class})
@MappedJdbcTypes({JdbcType.VARCHAR, JdbcType.NVARCHAR})
public class SetTypeHandler implements TypeHandler<Set<String>> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Set parameter, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public Set<String> getResult(ResultSet rs, String columnName) throws SQLException {
        return wrap(rs.getString(columnName));
    }

    @Override
    public Set<String> getResult(ResultSet rs, int columnIndex) throws SQLException {
        return wrap(rs.getString(columnIndex));
    }

    @Override
    public Set<String> getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return wrap(cs.getString(columnIndex));
    }

    private Set<String> wrap(String items) {
        if (items == null) {
            return new HashSet<String>();
        }
        return new HashSet<String>(Arrays.asList(items.split(",")));
    }
}
