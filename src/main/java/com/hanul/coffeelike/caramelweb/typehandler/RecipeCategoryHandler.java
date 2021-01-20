package com.hanul.coffeelike.caramelweb.typehandler;

import com.hanul.coffeelike.caramelweb.data.RecipeCategory;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecipeCategoryHandler extends BaseTypeHandler<RecipeCategory>{
	@Override public void setNonNullParameter(PreparedStatement ps, int i, RecipeCategory parameter, JdbcType jdbcType) throws SQLException{
		ps.setString(i, parameter.toString());
	}
	@Override public RecipeCategory getNullableResult(ResultSet rs, String columnName) throws SQLException{
		return RecipeCategory.fromString(rs.getString(columnName));
	}
	@Override public RecipeCategory getNullableResult(ResultSet rs, int columnIndex) throws SQLException{
		return RecipeCategory.fromString(rs.getString(columnIndex));
	}
	@Override public RecipeCategory getNullableResult(CallableStatement cs, int columnIndex) throws SQLException{
		return RecipeCategory.fromString(cs.getString(columnIndex));
	}
}
