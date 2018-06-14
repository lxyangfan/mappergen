package cn.fr4nk.mybatis.mappergen.service;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.fr4nk.mybatis.mappergen.MappergenApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=MappergenApplication.class)
public class MapperServiceTest {

	private static Log log = LogFactory.getLog(MapperServiceTest.class);
	
	@Autowired
	private MapperService mapperService;
	
	public void testGenerateMapperClazz() {
		String ss = null;
		try {
			ss = mapperService.generateMapperClazz("cn.fr4nk", "INFO" , "T_WSDL_BENEFICIARY");
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info(ss);
		Assertions.assertThat(ss != null);
	}

	@Test
	public void testGenerateDomainClazz() {
		String ss = null;
		try {
			ss = mapperService.generateDomainClazz("cn.fr4nk", "INFO" , "T_WSDL_BENEFICIARY");
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info(ss);
		Assertions.assertThat(ss != null);
	}
	
	
	

}
