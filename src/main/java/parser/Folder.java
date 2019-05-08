package parser;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 文件夹
 */
public class Folder {
    // 上级目录
    private Folder parentFolder;
    // 路径全称
    private String currentPath;
    // 文件夹名称
    private String folderName;
    // 当前路径下的文件名称
    private Set<String> files = new HashSet<>();
    // 当前文件夹下的文件夹
    private Set<Folder> folders = new HashSet<>();

    Folder(Folder parentFolder, String folderName) {
        this.parentFolder = parentFolder;
        this.folderName = folderName;
        this.currentPath = (parentFolder!=null?this.parentFolder.getCurrentPath():"") + folderName;
    }

    /**
     * 检查当前文件夹是否存在该文件
     * @param file 文件名
     * @return 是否存在
     */
    public boolean hasFile(String file) {
        return files.contains(file);
    }

    /**
     * 添加问题件
     * @param file 文件名
     */
    public void addFile(String file) {
        if (!hasFile(file)) {
            files.add(file);
        }
    }

    /**
     * 检查是否存在该文件夹
     * @param path 文件夹名
     * @return 是否存在
     */
    public boolean hasFolder(String path) {
        for (Folder f : folders) {
            if (f.getFolderName().equals(path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取指定文件夹
     * @param path 文件夹名
     * @return 文件夹类
     */
    public Folder getFolder(String path) {
        for (Folder f : folders) {
            if (f.getFolderName().equals(path)) {
                return f;
            }
        }
        return null;
    }

    /**
     * 在当前文件夹下新增文件夹
     * @param folderName 文件夹名
     * @return 新增文件夹
     */
    public Folder addFolder(String folderName) {
        // 设置新增文件夹父文件夹为自己
        Folder folder = new Folder(this,folderName);
        folders.add(folder);
        return folder;
    }

    public String getFolderName() {
        return folderName;
    }

    public Folder getParentFolder() {
        return parentFolder;
    }

    public String getCurrentPath() {
        return currentPath;
    }

    public Set<String> getFiles() {
        return files;
    }

    public Set<Folder> getFolders() {
        return folders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Folder)) return false;
        Folder folder = (Folder) o;
        return Objects.equals(folderName, folder.folderName) &&
                Objects.equals(files, folder.files) &&
                Objects.equals(folders, folder.folders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(folderName, files, folders);
    }

    @Override
    public String toString() {
        return toTreeString(0);
    }

    /**
     * 递归调用显示文件夹树状目录
     * @param height 当前层数
     * @return 打印信息
     */
    private String toTreeString(int height) {
        // 临时变量，保存需要输出的内容
        StringBuilder sb = new StringBuilder(this.getFolderName()+"\n");
        // 保存每行前需要多少空格（根据目录所在深度决定）
        StringBuilder spaceBuilder = new StringBuilder();
        spaceBuilder.append(" ".repeat(Math.max(0, height * 4)));
        String spaces = spaceBuilder.toString();
        // 每个文件夹都先打印所持有文件
        files.forEach((file) -> sb.append(spaces).append(" |- ").append(file).append('\n'));
        // 之后递归打印所持有文件夹
        folders.forEach((folder) -> sb.append(spaces).append(" |- ")
                .append(folder.toTreeString(height+1)));
        return sb.toString();
    }
}
