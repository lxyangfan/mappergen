package cn.fr4nk.mybatis.mappergen.util;

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
public class FileUtilTest {
	
	private static Log log = LogFactory.getLog(FileUtilTest.class);
	
	@Autowired
	private FileUtil fileUtil;
	
	@Test
	public void testReadFile() {
		String fileName = "mapper.tpl";
		String content = null;
		try {
			content = fileUtil.readFile(fileName);
		} catch (IOException e) {
			log.error(e);
		}
		Assertions.assertThat(content != null);
		log.info(content);
	}
	
	@Test
	public void testWriteFile() {
		String filePath = "C:/tmp/mapper.tpl";
		String content = "hello size";
		try {
			fileUtil.writeFile(content, filePath);
		} catch (IOException e) {
			log.error(e);
		}
	}

}
