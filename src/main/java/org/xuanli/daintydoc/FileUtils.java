package org.xuanli.daintydoc;

import java.io.File;
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
}
