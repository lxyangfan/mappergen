package cn.fr4nk.mybatis.mappergen.service;

import java.io.IOException;

public interface MapperService {
	
	String getDomainClazzName(String tabName);

	String generateMapperClazz(String packageName, String schema, String tabName) throws IOException;
	
	String generateDomainClazz(String packageName, String schema, String tabName) throws IOException;
}
