package com.william.model.enum_;

public enum ScanType {
	
	GETASSISTANTROLE("01"), GETMAGAGERROLE("02"), CONSUMENOTIF("03"), PAYNOTIF("04"), SETTLEMENTNOTIF("05");

    // 定义私有变量
    private String value;

    // 构造函数，枚举类型只能为私有
    private ScanType(String _value) {

        this.value = _value;

    }
    
   
    public static ScanType valueOf1(String value) {    
        switch (value) {  
        case "01":  
            return GETASSISTANTROLE;  
        case "02":  
            return GETMAGAGERROLE;  
        case "03":  
            return CONSUMENOTIF; 
        case "04":  
            return PAYNOTIF; 
        case "05":  
            return SETTLEMENTNOTIF; 
        default:  
            return null;  
        }  
    }  
    
    public String value() {  
        return this.value;  
    }  

}
