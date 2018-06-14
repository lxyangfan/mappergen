package cn.fr4nk.mybatis.mappergen.service;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.CaseFormat;

import cn.fr4nk.mybatis.mappergen.util.DbUtil;
import cn.fr4nk.mybatis.mappergen.util.FileUtil;

@Service
public class MapperServiceImpl  implements MapperService {

	private static Log log = LogFactory.getLog(MapperServiceImpl.class);
	
	@Autowired
	private DbUtil dbUtil; 
	
	@Autowired
	private FileUtil fileUtil;

	@Override
	public String generateMapperClazz(String packageName, String schema, String tabName) throws IOException {
		String fullTab = schema+"."+tabName;
		String mapperTpl = null;
		try {
			mapperTpl = fileUtil.readFile("mapper.tpl");
		}catch(IOException e) {
			log.error("模板文件 mapper.tpl 读取失败");
			throw e;
		}
		
		String insertSql = dbUtil.getInsertSql(fullTab);
		String deleteSql = dbUtil.getDeleteSql(fullTab);
		String updateSql = dbUtil.getUpdateSql(fullTab);
		String selectSql = dbUtil.getSelectSql(fullTab);
		
		String clazzName = getDomainClazzName(tabName);

		log.info(insertSql);
		log.info(deleteSql);
		log.info(updateSql);
		log.info(selectSql);
		log.info(clazzName);
		
		String[] args = new String[] {
				packageName,
				packageName,
				clazzName,
				clazzName,
				insertSql,
				clazzName,
				selectSql,
				clazzName,
				selectSql,
				clazzName,
				updateSql,
				clazzName,
				deleteSql,
				clazzName
				};
		return String.format(mapperTpl, args);
	}

	@Override
	public String getDomainClazzName(String tabName) {
		return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tabName.toUpperCase());
	}

	@Override
	public String generateDomainClazz(String packageName, String schema, String tabName) throws IOException {
		String domainTpl = null;
		try {
			domainTpl = fileUtil.readFile("domain.tpl");
		}catch(IOException e) {
			log.error("模板文件 domain.tpl 读取失败");
			throw e;
		}
		String clazzName = getDomainClazzName(tabName);
		String baseStr = String.format(domainTpl, packageName, clazzName);
		
		List<String> camelFields = dbUtil.getCamelFields(schema+"."+tabName);
		final StringBuilder sb = new StringBuilder(baseStr);
		camelFields.forEach( (field) -> {
			sb.append(String.format("\tprivate String %s;\n", field));
		});
		sb.append("}");
		return sb.toString();
	}

}
