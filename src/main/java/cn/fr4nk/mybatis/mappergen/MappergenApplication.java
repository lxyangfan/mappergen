package cn.fr4nk.mybatis.mappergen;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MappergenApplication {
	
	private static Log log = LogFactory.getLog(MappergenApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MappergenApplication.class, args);
	}
	
}
