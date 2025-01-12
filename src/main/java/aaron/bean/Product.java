package aaron.bean; 

import java.sql.Timestamp;
import java.util.List;

public class Product {
    private String prodNo;
    private String prodName;
    private String prodIntro;
	private Integer price;
    private Integer brandNo;
    private String brandName;
    private String brandDesc;
    private Integer typeNo;
    private String typeName;
    private Integer prodState;
    private Integer prodCount;
    private Integer funtNo;
    private String funtName;
    private Integer ingrNo;
    private String ingrName;
    private String ingrDesc;
    private Integer piNo;
    private String piName;
    private byte[] piData;
    private String piUrl;
    private String prodImg;
    private Timestamp piUpdate;
    private Boolean isCollection;
    
    public String getProdImg() {
		return prodImg;
	}

	public void setProdImg(String prodImg) {
		this.prodImg = prodImg;
	}
    
    // 多筆功能相關屬性
    private List<Integer> funtNos;
    private List<String> funtNames;

    
    // 多筆成分相關屬性
    private List<Integer> ingrNos;
    private List<String> ingrNames;
    private List<String> ingrDescs;
    private List<String> allergy;

//    private List<Byte> allergies;
    
    //評價總計 & 評價平均
	private int evalCount;
    private double avgScore;
  
    
    


	public String getProdIntro() {
		return prodIntro;
	}

	public void setProdIntro(String prodIntro) {
		this.prodIntro = prodIntro;
	}
    
        
    public List<String> getAllergy() {
		return allergy;
	}

	public void setAllergy(List<String> allergy) {
		this.allergy = allergy;
	}

	public List<Integer> getIngrNos() {
		return ingrNos;
	}

	public void setIngrNos(List<Integer> ingrNos) {
		this.ingrNos = ingrNos;
	}

	public List<String> getIngrNames() {
		return ingrNames;
	}

	public void setIngrNames(List<String> ingrNames) {
		this.ingrNames = ingrNames;
	}

	public List<String> getIngrDescs() {
		return ingrDescs;
	}

	public void setIngrDescs(List<String> ingrDescs) {
		this.ingrDescs = ingrDescs;
	}

//	public List<Byte> getAllergies() {
//		return allergies;
//	}
//
//	public void setAllergies(List<Byte> allergies) {
//		this.allergies = allergies;
//	}

	public void setAvgScore(double avgScore) {
		this.avgScore = avgScore;
	}

	public List<Integer> getFuntNos() {
		return funtNos;
	}

	public void setFuntNos(List<Integer> funtNos) {
		this.funtNos = funtNos;
	}

	public List<String> getFuntNames() {
		return funtNames;
	}

	public void setFuntNames(List<String> funtNames) {
		this.funtNames = funtNames;
	}

	public String getProdNo() {
        return prodNo;
    }

    public void setProdNo(String prodNo) {
        this.prodNo = prodNo;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getBrandNo() {
        return brandNo;
    }

    public void setBrandNo(Integer brandNo) {
        this.brandNo = brandNo;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandDesc() {
        return brandDesc;
    }

    public void setBrandDesc(String brandDesc) {
        this.brandDesc = brandDesc;
    }

    public Integer getTypeNo() {
        return typeNo;
    }

    public void setTypeNo(Integer typeNo) {
        this.typeNo = typeNo;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getProdState() {
        return prodState;
    }

    public void setProdState(Integer prodState) {
        this.prodState = prodState;
    }

    public Integer getProdCount() {
        return prodCount;
    }

    public void setProdCount(Integer prodCount) { 
        this.prodCount = prodCount;
    }

    public Integer getFuntNo() {
        return funtNo;
    }

    public void setFuntNo(Integer funtNo) {
        this.funtNo = funtNo;
    }

    public String getFuntName() {
        return funtName;
    }

    public void setFuntName(String funtName) {
        this.funtName = funtName;
    }

    public Integer getIngrNo() {
        return ingrNo;
    }

    public void setIngrNo(Integer ingrNo) {
        this.ingrNo = ingrNo;
    }

    public String getIngrName() {
        return ingrName;
    }

    public void setIngrName(String ingrName) {
        this.ingrName = ingrName;
    }

    public String getIngrDesc() {
        return ingrDesc;
    }

    public void setIngrDesc(String ingrDesc) {
        this.ingrDesc = ingrDesc;
    }


    public Integer getPiNo() {
        return piNo;
    }

    public void setPiNo(Integer piNo) {
        this.piNo = piNo;
    }

    public String getPiName() {
        return piName;
    }

    public void setPiName(String piName) {
        this.piName = piName;
    }

    public byte[] getPiData() {
        return piData;
    }

    public void setPiData(byte[] piData) {
        this.piData = piData;
    }

    public String getPiUrl() {
        return piUrl;
    }

    public void setPiUrl(String piUrl) {
        this.piUrl = piUrl;
    }

    public Timestamp getPiUpdate() {
        return piUpdate;
    }

    public void setPiUpdate(Timestamp piUpdate) {
        this.piUpdate = piUpdate;
    }
    
    public int getEvalCount() {
		return evalCount;
	}

	public void setEvalCount(int evalCount) {
		this.evalCount = evalCount;
	}

	public double getAvgScore() {
		return avgScore;
	}

	public void setAvgScore(int avgScore) {
		this.avgScore = avgScore;
	}

	public Boolean getIsCollection() {
		return isCollection;
	}

	public void setIsCollection(Boolean isCollection) {
		this.isCollection = isCollection;
	}
}


//package aaron.bean;
//
//import java.sql.Timestamp;
//import java.util.List;
//
//public class Product {
//    private String prodNo;
//    private String prodName;
//    private Integer price;
//    private Integer brandNo;
//    private String brandName;
//    private String brandDesc;
//    private Integer typeNo;
//    private String typeName;
//    private Integer prodState;
//    private Integer prodCount;
//

//
//    private Integer piNo;
//    private String piName;
//    private byte[] piData;
//    private String piUrl;
//    private Timestamp piUpdate;
//
//    // Getters and Setters
//    public String getProdNo() {
//        return prodNo;
//    }
//
//    public void setProdNo(String prodNo) {
//        this.prodNo = prodNo;
//    }
//
//    public String getProdName() {
//        return prodName;
//    }
//
//    public void setProdName(String prodName) {
//        this.prodName = prodName;
//    }
//
//    public Integer getPrice() {
//        return price;
//    }
//
//    public void setPrice(Integer price) {
//        this.price = price;
//    }
//
//    public Integer getBrandNo() {
//        return brandNo;
//    }
//
//    public void setBrandNo(Integer brandNo) {
//        this.brandNo = brandNo;
//    }
//
//    public String getBrandName() {
//        return brandName;
//    }
//
//    public void setBrandName(String brandName) {
//        this.brandName = brandName;
//    }
//
//    public String getBrandDesc() {
//        return brandDesc;
//    }
//
//    public void setBrandDesc(String brandDesc) {
//        this.brandDesc = brandDesc;
//    }
//
//    public Integer getTypeNo() {
//        return typeNo;
//    }
//
//    public void setTypeNo(Integer typeNo) {
//        this.typeNo = typeNo;
//    }
//
//    public String getTypeName() {
//        return typeName;
//    }
//
//    public void setTypeName(String typeName) {
//        this.typeName = typeName;
//    }
//
//    public Integer getProdState() {
//        return prodState;
//    }
//
//    public void setProdState(Integer prodState) {
//        this.prodState = prodState;
//    }
//
//    public Integer getProdCount() {
//        return prodCount;
//    }
//
//    public void setProdCount(Integer prodCount) {
//        this.prodCount = prodCount;
//    }
//
//    public List<Integer> getFuntNos() {
//        return funtNos;
//    }
//
//    public void setFuntNos(List<Integer> funtNos) {
//        this.funtNos = funtNos;
//    }
//
//    public List<String> getFuntNames() {
//        return funtNames;
//    }
//
//    public void setFuntNames(List<String> funtNames) {
//        this.funtNames = funtNames;
//    }
//
//    public List<Integer> getIngrNos() {
//        return ingrNos;
//    }
//
//    public void setIngrNos(List<Integer> ingrNos) {
//        this.ingrNos = ingrNos;
//    }
//
//    public List<String> getIngrNames() {
//        return ingrNames;
//    }
//
//    public void setIngrNames(List<String> ingrNames) {
//        this.ingrNames = ingrNames;
//    }
//
//    public List<String> getIngrDescs() {
//        return ingrDescs;
//    }
//
//    public void setIngrDescs(List<String> ingrDescs) {
//        this.ingrDescs = ingrDescs;
//    }
//
//    public List<Byte> getAllergies() {
//        return allergies;
//    }
//
//    public void setAllergies(List<Byte> allergies) {
//        this.allergies = allergies;
//    }
//
//    public Integer getPiNo() {
//        return piNo;
//    }
//
//    public void setPiNo(Integer piNo) {
//        this.piNo = piNo;
//    }
//
//    public String getPiName() {
//        return piName;
//    }
//
//    public void setPiName(String piName) {
//        this.piName = piName;
//    }
//
//    public byte[] getPiData() {
//        return piData;
//    }
//
//    public void setPiData(byte[] piData) {
//        this.piData = piData;
//    }
//
//    public String getPiUrl() {
//        return piUrl;
//    }
//
//    public void setPiUrl(String piUrl) {
//        this.piUrl = piUrl;
//    }
//
//    public Timestamp getPiUpdate() {
//        return piUpdate;
//    }
//
//    public void setPiUpdate(Timestamp piUpdate) {
//        this.piUpdate = piUpdate;
//    }
//}