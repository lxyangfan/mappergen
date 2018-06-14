package %s.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import %s.domain.%s;

@Mapper
public interface %sMapper {
	
	@Insert("%s")
	int insert(%s item);
	
	@Select("%s")
	%s findByName(@Param("name") String name);
	
	@Select("%s")
	%s findById(@Param("id") String id);
	
	@Update("%s")
	int updateById(%s item);
	
	@Delete("%s")
	int deleteById(%s item);

}
