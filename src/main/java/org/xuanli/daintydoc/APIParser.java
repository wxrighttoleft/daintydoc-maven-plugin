package org.xuanli.daintydoc;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.Tag;

import java.util.ArrayList;
import java.util.List;

public class APIParser {

    private ClassDoc[] allClass;

    public APIParser(ClassDoc[] classes) {
        allClass = classes;
    }

    public List<DocPage> start() {
        List<DocPage> pageList = new ArrayList<DocPage>();
        for (ClassDoc classDoc : allClass) {
            if (classDoc.tags("@apidoc-name").length > 0 ) {
                DocPage docpage = new DocPage();
                docpage.setFileName(classDoc.toString());
                for (Tag tag : classDoc.tags()) {
                    if (tag.name().substring(1).startsWith("apidoc-name")) {
                        docpage.setName(tag.text());
                    }
                    if (tag.name().substring(1).startsWith("apidoc-desc")) {
                        docpage.setDesc(tag.text());
                    }
                }
                docpage.setApInfos(apiParse(classDoc.methods()));
                pageList.add(docpage);
            }
        }
        return pageList;
    }

    private List<APInfo> apiParse(MethodDoc[] methodDocs) {
        List<APInfo> apInfos = new ArrayList<APInfo>();
        for (MethodDoc methodDoc : methodDocs) {
            if (methodDoc.tags("@apidoc-url").length > 0) {
                APInfo apInfo = new APInfo();
                for (Tag tag : methodDoc.tags()) {
                    if (tag.name().substring(1).startsWith("apidoc-url")) {
                        apInfo.setUrl(tag.text());
                    }
                    if (tag.name().substring(1).startsWith("apidoc-name")) {
                        apInfo.setName(tag.text());
                    }
                    if (tag.name().substring(1).startsWith("apidoc-desc")) {
                        apInfo.setDesc(tag.text());
                    }
                    if (tag.name().substring(1).startsWith("apidoc-param")) {
                        if (apInfo.getParameters() == null)
                            apInfo.setParameters(new ArrayList<APIParameter>());
                        apInfo.getParameters().add(paramParse(tag.text()));
                    }
                }
                apInfos.add(apInfo);
            }
        }
        return apInfos;
    }

    private APIParameter paramParse(String paramstr) {
        String[] paramDesc = paramstr.split("|");
        APIParameter parameter = new APIParameter();
        parameter.setName(paramDesc[0]);
        parameter.setType(paramDesc[1]);
        if (paramDesc.length >= 3) {
            parameter.setRequired(Boolean.valueOf(paramDesc[2]));
        }
        if (paramDesc.length == 4) {
            parameter.setDesc(paramDesc[3]);
        }
        return parameter;
    }


}
