package aaron.dao;

import aaron.bean.Product;

public interface ProductDetailDao {

    //根據產品編號查詢產品資訊。
	// @param prodNo 產品編號
	// @return 對應的 AllProduct 物件，若找不到則回傳 null。
	
    Product selectByProdNo(String prodNo);
}