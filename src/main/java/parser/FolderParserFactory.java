package parser;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObjectSummary;

import java.util.List;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 文件夹解析器工厂
 * 用来存放 OSSClient 和线程池实例
 * 使用前需要设置 OSSClient
 * 设置后直接调用 parse 方法
 */
public class FolderParserFactory {
    // OSS 客户端，用来读取远程目录和文件信息
    private static OSSClient ossClient;
    // 文件夹解析器的线程池
    private static ThreadPoolExecutor folderParserThreadPool = new ThreadPoolExecutor(
            4,8,5, TimeUnit.MINUTES,
            new LinkedBlockingQueue<>());

    public static void setOssClient(OSSClient ossClient) {
        FolderParserFactory.ossClient = ossClient;
    }

    public static Folder parse(String bucket, String prefix) throws Exception {
        if (ossClient == null) {
            throw new Exception("do not have OSSClient instance");
        }
        // 列举文件，如果不设置KeyPrefix，则列举存储空间下所有的文件。KeyPrefix，则列举包含指定前缀的文件。
        List<OSSObjectSummary> sums = ossClient.listObjects(bucket,prefix).getObjectSummaries();
        // 转换为流，然后变为 List<String>
        List<String> strings = sums.stream().map(OSSObjectSummary::getKey).collect(Collectors.toList());
        // 新建 FutureTask
        FutureTask<Folder> newTask = new FutureTask<>(new FolderParser(strings));
        // 把任务加入线程池处理
        folderParserThreadPool.execute(newTask);
        return newTask.get();
    }
}
