package cn.jin.plugin.ftp;

import lombok.Getter;
import lombok.Setter;
import org.gradle.api.DefaultTask;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;

/**
 * 自定义上传任务
 */
@Getter
@Setter
public class UploadTask extends DefaultTask {

    /**
     * 主机名或IP地址
     **/
    @Input
    private String host;

    /**
     * 端口号
     **/
    @Input
    private int port;

    /**
     * 用户名
     **/
    @Input
    private String username;

    /**
     * 密码
     **/
    @Input
    private String password;

    /**
     * 服务器目标目录
     **/
    @Input
    private String remoteDir;

    /**
     * 本地文件路径
     **/
    @Input
    private String filePath;

    /**
     * 执行上传任务
     */
    @TaskAction
    public void upload() {
        Logger logger = getProject().getLogger();
        FtpClient ftpClient = new FtpClient(host, port, username, password);
        try {
            ftpClient.open();
            ftpClient.setProperty();
            File file = new File(filePath);
            String name = file.getName();
            if (!file.exists()) {
                logger.warn("文件{}不存在", name);
            }
            String path = remoteDir + "/" + name;
            if (ftpClient.putFileToPath(file, path)) {
                logger.warn("上传完毕: {}", path);
            } else {
                logger.warn("上传失败");
            }
        } catch (IOException e) {
            logger.warn(e.getMessage());
        } finally {
            try {
                ftpClient.close();
            } catch (IOException e) {
                logger.warn(e.getMessage());
            }
        }
    }
}
