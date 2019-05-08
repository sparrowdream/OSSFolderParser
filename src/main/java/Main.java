import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import parser.FolderParserFactory;

public class Main {
    private static String accessId = ""; // 请填写您的AccessKeyId。
    private static String accessKey = ""; // 请填写您的AccessKeySecret。
    private static String endpoint = ""; // 请填写您的 endpoint。
    private static String bucket = ""; // 请填写您的 bucketname 。
    private static String host = "https://" + bucket + "." + endpoint; // host的格式为 bucketname.endpoint

    public static void main(String[] args) {
        OSSClient ossClient = null;
        try {
            ossClient = new OSSClient(endpoint, (new DefaultCredentialProvider(accessId, accessKey)), null);

            FolderParserFactory.setOssClient(ossClient);
            System.out.println(FolderParserFactory.parse(bucket,""));

            // 关闭OSSClient。
            ossClient.shutdown();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
