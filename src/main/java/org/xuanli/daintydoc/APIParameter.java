package org.xuanli.daintydoc;

/**
 * 接口参数信息
 */
public class APIParameter {
    /**
     * 接口参数名
     */
    private String name;
    /**
     * 接口参数数据类型
     */
    private String type;
    /**
     * 接口参数描述
     */
    private String desc;
    /**
     * 接口参数是否必填
     */
    private boolean required;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
