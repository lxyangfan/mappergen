package cn.fr4nk.mybatis.mappergen;

import java.util.Collections;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import cn.fr4nk.mybatis.mappergen.service.ExportService;

@Component
public class ExportRunner implements CommandLineRunner  {

	private static Log log = LogFactory.getLog(ExportRunner.class);
	
	@Value("${mapper.package.name}")
	private String packageName;
	
	@Value("${mapper.tab.schema}")
	private String schema;
	
	@Value("${mapper.tab.names}")
	private String[] tabNames;

	@Value("${mapper.file.path}")
	private String filePath;
	
	@Autowired
	private ExportService exportService;
	
	@Override
	public void run(String... args) throws Exception {
		log.info("--------------------> HELLO EXPORT START <----------------------");
		log.info(packageName);
		log.info(schema);
		log.info(tabNames);
		exportService.exportToFile(filePath, packageName, schema, tabNames);
	}

}
