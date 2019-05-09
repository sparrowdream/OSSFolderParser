package parser;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * 文件夹解析器
 * 输入：文件名和目录名的字符串列表
 * 输出：Folder 文件目录
 */
public class FolderParser implements Callable<Folder> {
    private List<String> paths;

    FolderParser(List<String> paths) {
        this.paths = paths;
    }

    @Override
    public Folder call() {
        return parser(this.paths);
    }

    /**
     * 静态解析函数
     * @param paths 路径s + 文件s
     * @return 文件夹
     */
    private static Folder parser(List<String> paths) {
        // 初始化根文件夹
        Folder rootFolder = new Folder(null,"/");
        // 声明游标文件夹，初始值为根文件夹
        Folder cursorFolder = rootFolder;
        // 临时变量，记录当前游标文件夹前缀字符串的长度，方便后续文件名的截取
        int beginIndex = 0;
        for (String path : paths) {
            // 收集目录前缀，用以跳转文件夹
            StringBuilder prefixCollector = new StringBuilder();
            // 最后一个字符是 / ，说明是目录
            if (path.charAt(path.length() - 1) == '/') {
                // 重置游标到根文件夹
                cursorFolder = rootFolder;
                for (char ch : path.toCharArray()) {
                    prefixCollector.append(ch);
                    // 解析目录字符串遇到分隔符
                    if (ch == '/') {
                        // 判断是否有该文件夹
                        if (cursorFolder.hasFolder(prefixCollector.toString())) {
                            // 如果有则获取该文件夹，并跳转
                            cursorFolder = cursorFolder.getFolder(prefixCollector.toString());
                        } else {
                            // 如果没有该文件夹就新增文件夹，并跳转
                            cursorFolder = cursorFolder.addFolder(prefixCollector.toString());
                        }
                        // 清零前缀字符串
                        prefixCollector.setLength(0);
                    }
                }
                // 记录目录长度，下一系列该路径下的文件直接截取
                beginIndex = path.length();
            } else {
                // 如果不是文件夹，因为文件夹必在文件前，所以肯定在文件夹中，所以直接新增文件
                cursorFolder.addFile(path.substring(beginIndex));
            }
        }
        return rootFolder;
    }
}
