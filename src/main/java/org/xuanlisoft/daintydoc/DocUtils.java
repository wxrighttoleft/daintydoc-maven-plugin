package org.xuanlisoft.daintydoc;

import org.codehaus.plexus.util.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class DocUtils {
    private String outputDirection;

    public DocUtils(String outputDirection) {
        this.outputDirection = outputDirection;
    }

    public void createIndexPage() throws URISyntaxException, IOException {
        // 获取文件列表
        File outDir = new File(outputDirection);
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        Document document = getMode("index_mode.html");
        File[] files = outDir.listFiles();
        if (files != null && files.length > 0) {
            document.select(".api-index ul li").remove();
            for (File file : files) {
                if ( file.isFile() ) {
                    String name = Jsoup.parse(file, "UTF-8").select("title").text();
                    document.select(".api-index ul").append(String.format("<li><a href=\"%s\" target=\"win\">%s</a></li>", file.getName(), name));
                }
            }
            document.select("iframe").attr("src",files[0].getName());
            FileUtils.writeFile(String.format("%s/index.html", outDir.getPath()), document.toString());
        }
    }

    public void createDocument(DocPage page) throws URISyntaxException, IOException {
        Document document = getMode("api_mode.html");
        document.select("h4").html(page.getName());
        document.select("title").html(page.getName());
        document.select("#doc-desc").html(String.format("<p>%s</p>", page.getDesc()));
        Elements apiPanelMode = document.select(".api-panel");
        // api列表
        for (APInfo apInfo : page.getApInfos()) {
            Elements apiPanel = apiPanelMode.clone();
            apiPanel.select(".api-title").html(apInfo.getName());
            apiPanel.select(".api-url").html(apInfo.getUrl());
            if ( StringUtils.isBlank(apInfo.getDesc()) ) {
                apiPanel.select(".api-desc").remove();
            } else {
                apiPanel.select(".api-desc").html(apInfo.getDesc());
            }
            // api 参数列表
            if (apInfo.getParameters() == null || apInfo.getParameters().size() == 0) {
                apiPanel.select("table.api-param").remove();
            } else {
                for (APIParameter param : apInfo.getParameters()) {
                    apiPanel.select("table.api-param tbody").append(String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>",
                            param.getName(),
                            param.getType(),
                            param.isRequired() ? "是" : "否",
                            param.getDesc()));
                }
            }
            // 返回参数列表
            if ( apInfo.getReturnParams() == null || apInfo.getReturnParams().size() == 0 ) {
                apiPanel.select("table.api-return-param").remove();
            } else {
                for (APIParameter param : apInfo.getReturnParams()) {
                    apiPanel.select("table.api-return-param tbody").append(String.format("<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>",
                            param.getName(),
                            param.getType(),
                            param.isRequired() ? "是" : "否",
                            param.getDesc()));
                }
            }

            // 错误返回结果
            if ( StringUtils.isBlank(apInfo.getError()) ) {
                apiPanel.select(".api-result-error").prev().remove();
                apiPanel.select(".api-result-error").remove();
            } else {
                apiPanel.select(".api-result-error").html(apInfo.getError());
            }

            // 正确返回结果
            if ( StringUtils.isBlank(apInfo.getSuccess()) ) {
                apiPanel.select(".api-result-ok").prev().remove();
                apiPanel.select(".api-result-ok").remove();
            } else {
                apiPanel.select(".api-result-ok").html(apInfo.getSuccess());
            }

            document.select(".contains").append(apiPanel.toString());
        }
        apiPanelMode.remove();
        FileUtils.writeFile(String.format("%s/%s.html", outputDirection, page.getFileName().substring(page.getFileName().lastIndexOf(".") + 1)), document.toString());
    }

    /**
     * 获取模板文档
     * @return
     */
    public Document getMode(String mode) throws IOException, URISyntaxException {
        URL url = this.getClass().getResource("/" + mode);
        Document document = Jsoup.parse(url.openStream(), "UTF-8", url.toURI().toString());
        Document.OutputSettings settings = new Document.OutputSettings().prettyPrint(false);
        document.outputSettings(settings);
        return document.clone();
    }
}
