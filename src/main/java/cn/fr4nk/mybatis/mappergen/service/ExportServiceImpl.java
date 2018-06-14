package cn.fr4nk.mybatis.mappergen.service;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.fr4nk.mybatis.mappergen.util.FileUtil;
import lombok.NonNull;

@Service
public class ExportServiceImpl implements ExportService {
	
	private static Log log = LogFactory.getLog(ExportServiceImpl.class);
	
	@Autowired
	private MapperService mapperSerivce;
	
	@Autowired
	private FileUtil fileUtil;

	@Override
	public void exportToFile(@NonNull String filePath, @NonNull String packageName, @NonNull String schema, @NonNull String[] tabNames)
			throws IOException
	{
		
		for (String tabName: tabNames) {
			String mapperStr = mapperSerivce.generateMapperClazz(packageName, schema, tabName);
			String domainStr = mapperSerivce.generateDomainClazz(packageName, schema, tabName);
			String domainName = mapperSerivce.getDomainClazzName(tabName);
			
			fileUtil.writeFile(mapperStr, filePath+"/mapper/"+domainName+"Mapper.java");
			fileUtil.writeFile(domainStr, filePath+"/domain/"+domainName+".java");
		}
		
	}


}
