package cn.jin.plugin.ftp;

import lombok.Getter;
import lombok.Setter;
import org.gradle.api.DefaultTask;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

/**
 * 自定义上传任务
 */
@Getter
@Setter
public class UploadTask extends DefaultTask {

    /** 主机名或IP地址 **/
    @Input
    private String host;

    /** 端口号 **/
    @Input
    private int port;

    /** 用户名 **/
    @Input
    private String username;

    /** 密码 **/
    @Input
    private String password;

    /** 服务器目标目录 **/
    @Input
    private String remoteDir;

    /** 本地文件路径 **/
    @Input
    private String filePath;

    /**
     * 执行上传任务
     */
    @TaskAction
    public void upload() {
        Logger logger = getProject().getLogger();
        UploadClient client = UploadClient.getInstance();
        client.uploadFile(host, port, username, password, remoteDir, filePath);
        String uploadedPath = remoteDir + "/" + new File(filePath).getName();
        logger.warn("Upload Complete: " + uploadedPath);
    }
}
