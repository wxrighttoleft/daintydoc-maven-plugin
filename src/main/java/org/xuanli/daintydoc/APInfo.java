package org.xuanli.daintydoc;

import java.util.List;

/**
 * API信息
 */
public class APInfo {
    /**
     * API URL 地址
     */
    private String url;
    /**
     * 接口名称
     */
    private String name;
    /**
     * 接口描述
     */
    private String desc;
    /**
     * 接口参数列表
     */
    private List<APIParameter> parameters;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<APIParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<APIParameter> parameters) {
        this.parameters = parameters;
    }
}
