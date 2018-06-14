package cn.fr4nk.mybatis.mappergen.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.common.base.CaseFormat;

@Service
public class DbUtil {
	
	private static Log log = LogFactory.getLog(DbUtil.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<String> getTableFields(String tabName) throws IllegalArgumentException {
		if (StringUtils.isEmpty(tabName)) {
			throw new IllegalArgumentException("表名为空！");
		}
		List<String> res = new ArrayList<String>();
		String sql = "select * from " + tabName + " where rownum <= 0";
		try {
			SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
			SqlRowSetMetaData metaData = rowSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				res.add(metaData.getColumnName(i).toUpperCase());
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(String.format("查询数据库表[%s]结构异常：  %s", tabName, e));
		}
		return res;
	}
	
	/**
	 * 获得 Camel 格式的field定义
	 * @param tabName
	 * @return
	 * @throws IllegalArgumentException
	 */
	public List<String> getCamelFields(String tabName) throws IllegalArgumentException {
		List<String> fields = getTableFields(tabName);
		List<String> camelFields = fields.stream()
				.map(r -> CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, r))
				.collect(Collectors.toList());
		return camelFields;
	}

	public String getSelectSql(String tabName) throws IllegalArgumentException {
		List<String> fields = getTableFields(tabName);
		List<String> camelFields = fields.stream()
				.map(r -> String.format("%s as %s", r, CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, r)))
				.collect(Collectors.toList());
		String retStr = "Select";
		int size = camelFields.size();
		for (int i=0; i<size; i++) {
			if (i != size-1) {
				retStr = String.format("%s %s, ", retStr, camelFields.get(i));
			} else {
				retStr = String.format("%s %s from %s", retStr, camelFields.get(i), tabName);
			}
		}
		return retStr;
	}
	

	/**
	 * 获取 insert 语句， insert into tableName(col_name1, col_name2) values (#{colName1}, #{colName2})
	 * @param tabName
	 * @return insert SQL
	 * @throws IllegalArgumentException
	 */
	public String getInsertSql(String tabName) throws IllegalArgumentException {
		List<String> fields = getTableFields(tabName);
		Map<String, Object> camelFieldsMap = fields.stream()
				.collect(Collectors.toMap(k -> k,
						v -> CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, v),
						(old, newV) -> newV ));
		final StringBuilder colStr = new StringBuilder("");
		final StringBuilder camelStr  = new StringBuilder("");
		camelFieldsMap.forEach((k, v) -> {
			colStr.append(String.format(", %s", k));
			camelStr.append(String.format(", #{%s}", v));
		});
		String fcolStr = colStr.toString().substring(1);
		String fcamelStr = camelStr.toString().substring(1);
		return String.format("insert into %s (%s) values (%s)", tabName, fcolStr, fcamelStr);
	}
	
	

	/**
	 * 获取 update 语句， UPDATE table SET col_name1 = #{colName1} where col_name1 = #{colName1} and col_name2 = #{colName2}
	 * @param tabName
	 * @return
	 * @throws IllegalArgumentException
	 */
	public String getUpdateSql(String tabName) throws IllegalArgumentException {
		List<String> fields = getTableFields(tabName);
		List<String> camelFields = fields.stream()
				.map( r -> String.format("%s = #{%s}", r, CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, r)))
				.collect(Collectors.toList());
		final StringBuilder fieldsClause = new StringBuilder("");
		final StringBuilder whereClause = new StringBuilder("");
		camelFields.forEach( r -> {
			fieldsClause.append(String.format(", %s", r));
			whereClause.append(String.format("and %s ", r));
		});
		String ffieldsClause = fieldsClause.toString().substring(1);
		String fwhereClause = whereClause.toString().substring(3);
		return String.format("UPDATE %s set %s where %s", tabName, ffieldsClause, fwhereClause);
	}
	

	public String getDeleteSql(String tabName) throws IllegalArgumentException {
		List<String> fields = getTableFields(tabName);
		List<String> camelFields = fields.stream()
				.map( r -> String.format("%s = #{%s}", r, CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, r)))
				.collect(Collectors.toList());
		final StringBuilder whereClause = new StringBuilder("");
		camelFields.forEach( r -> {
			whereClause.append(String.format("and %s ", r));
		});
		String fwhereClause = whereClause.toString().substring(3);
		return String.format("delete from %s where %s", tabName, fwhereClause);
	}

}
