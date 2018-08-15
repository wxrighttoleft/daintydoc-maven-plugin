package org.xuanlisoft.daintydoc;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static List<String> scanSourceCode(File direction) {
        List<String> result = new ArrayList<String>();
        File[] files = direction.listFiles();
        for (File file : files) {
            if (file.isDirectory())
                result.addAll(scanSourceCode(file));
            else
                result.add(file.getPath());
        }
        return result;
    }

    public static void writeFile(String path, String content) {
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            throw new DaintyDocException(String.format("文件写入失败：%", path), e);
        }
    }
}
