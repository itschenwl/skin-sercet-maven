package aaron.dao;

import java.util.List;
import aaron.bean.Product;

public interface ProductListDao {
    List<Product> selectAll();
}