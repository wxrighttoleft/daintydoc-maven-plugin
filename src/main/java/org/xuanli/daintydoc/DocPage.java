package org.xuanli.daintydoc;

import java.util.List;

/**
 * API文档信息
 */
public class DocPage {
    /**
     * 文档模块名
     */
    private String name;
    /**
     * 文档文件名
     */
    private String fileName;
    /**
     * 文档描述
     */
    private String desc;
    /**
     * API接口列表
     */
    private List<APInfo> apInfos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<APInfo> getApInfos() {
        return apInfos;
    }

    public void setApInfos(List<APInfo> apInfos) {
        this.apInfos = apInfos;
    }
}
