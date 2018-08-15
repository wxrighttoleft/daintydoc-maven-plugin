package org.xuanlisoft.daintydoc;

public enum TagEnum {
    NAME("@apidoc-name"),
    DESC("@apidoc-desc"),
    URL("@apidoc-url"),
    PARAM("@apidoc-param"),
    SUCCESS("@apidoc-success"),
    ERROR("@apidoc-error"),
    RETURN_PARAM("@apidoc-param-r"),
    OTHER("other");

    private String tag;

    TagEnum(String tag){
        this.tag = tag;
    }

    public static TagEnum getTag(String tag) {
        for (TagEnum ret : TagEnum.values()) {
            if (ret.tag.equals(tag))
                return ret;
        }
        return OTHER;
    }
}
