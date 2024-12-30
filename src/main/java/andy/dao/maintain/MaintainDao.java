package andy.dao.maintain;

import java.util.List;

import andy.javabean.maintain.Maintain;

public interface MaintainDao {

	Maintain getLast();
	Maintain selectById(Integer id);
	int insert(Maintain maintain);
	int deleteById(Integer id);
	int update(Maintain maintain);
	List<Maintain> selectAll();
}
