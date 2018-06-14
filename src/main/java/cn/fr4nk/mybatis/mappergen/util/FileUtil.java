package cn.fr4nk.mybatis.mappergen.util;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

@Component
@SuppressWarnings("deprecation")
public class FileUtil {

	@Autowired
	private ResourceLoader resourceLoader;

	public String readFile(String fileName) throws IOException {
		Resource resource = resourceLoader.getResource("classpath:" + fileName);
		File dbAsFile = resource.getFile();
		return Files.toString(dbAsFile, Charsets.UTF_8);
	}

	public void writeFile(String content, String filePath) throws IOException {
		File out = new File(filePath);
		Files.write(content, out, Charsets.UTF_8);
	}

}
