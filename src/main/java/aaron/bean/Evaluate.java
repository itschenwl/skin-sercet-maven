package aaron.bean;

import java.sql.Timestamp;

public class Evaluate {

    // 評論 EVALUATE
    private int evalNo;
    private String userNo;
    private String prodNo;
    private String evalTitle;
    private String evalContent;
    private int evalLevel;
    private Timestamp updateDate;

    // 檢舉 EVWH
    private int evwhNo;
    private int procNo;
    private String whiContent;
    private Timestamp whiDate;
    private Timestamp repDate;
    private String repContent;

    // 會資 USER_NUMB
    private String nickName;


    // 產列 PROD
    private String prodName;

    
    
    public int getEvalNo() {
        return evalNo;
    }

    public void setEvalNo(int evalNo) {
        this.evalNo = evalNo;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getProdNo() {
        return prodNo;
    }

    public void setProdNo(String prodNo) {
        this.prodNo = prodNo;
    }

    public String getEvalTitle() {
        return evalTitle;
    }

    public void setEvalTitle(String evalTitle) {
        this.evalTitle = evalTitle;
    }

    public String getEvalContent() {
        return evalContent;
    }

    public void setEvalContent(String evalContent) {
        this.evalContent = evalContent;
    }

    public int getEvalLevel() {
        return evalLevel;
    }

    public void setEvalLevel(int evalLevel) {
        this.evalLevel = evalLevel;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public int getEvwhNo() {
        return evwhNo;
    }

    public void setEvwhNo(int evwhNo) {
        this.evwhNo = evwhNo;
    }

    public int getProcNo() {
        return procNo;
    }

    public void setProcNo(int procNo) {
        this.procNo = procNo;
    }

    public String getWhiContent() {
        return whiContent;
    }

    public void setWhiContent(String whiContent) {
        this.whiContent = whiContent;
    }

    public Timestamp getWhiDate() {
        return whiDate;
    }

    public void setWhiDate(Timestamp whiDate) {
        this.whiDate = whiDate;
    }

    public Timestamp getRepDate() {
        return repDate;
    }

    public void setRepDate(Timestamp repDate) {
        this.repDate = repDate;
    }

    public String getRepContent() {
        return repContent;
    }

    public void setRepContent(String repContent) {
        this.repContent = repContent;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    @Override
    public String toString() {
        return "Evaluate{" +
               "evalNo=" + evalNo +
               ", userNo='" + userNo + '\'' +
               ", prodNo='" + prodNo + '\'' +
               ", evalTitle='" + evalTitle + '\'' +
               ", evalContent='" + evalContent + '\'' +
               ", evalLevel=" + evalLevel +
               ", updateDate=" + updateDate +
               '}';
    }
}
