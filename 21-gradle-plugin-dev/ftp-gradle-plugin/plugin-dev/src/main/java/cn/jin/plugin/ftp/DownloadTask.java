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
 * 自定义下载任务
 */
@Getter
@Setter
public class DownloadTask extends DefaultTask {

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
     * 服务器文件路径
     **/
    @Input
    private String src;

    /**
     * 本地文件路径
     **/
    @Input
    private String dest;

    /**
     * 执行下载任务
     */
    @TaskAction
    public void download() {
        Logger logger = getProject().getLogger();
        FtpClient ftpClient = new FtpClient(host, port, username, password);
        try {
            ftpClient.open();
            ftpClient.setProperty();
            if (ftpClient.downloadFile(src, dest)) {
                logger.warn("下载完毕");
            } else {
                logger.warn("下载失败");
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
