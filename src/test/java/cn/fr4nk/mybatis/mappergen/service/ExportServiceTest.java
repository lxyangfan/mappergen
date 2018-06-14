package cn.fr4nk.mybatis.mappergen.service;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.fr4nk.mybatis.mappergen.MappergenApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=MappergenApplication.class)
public class ExportServiceTest {
	
	@Autowired
	private ExportService exportService;
	
	@Test
	public void testExpFile() {
		String filePath = "C:/tmp/";
		String packageName = "ks.fintech";
		String schema = "INFO";
		String[] tabNames = new String[] {"T_WSDL_CLIENT"};
		try {
			exportService.exportToFile(filePath, packageName, schema, tabNames);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
