package cn.fr4nk.mybatis.mappergen.service;

import java.io.IOException;

public interface ExportService {
	
	void exportToFile(String filePath, String packageName, String schema, String[] tabNames) throws IOException;

}
