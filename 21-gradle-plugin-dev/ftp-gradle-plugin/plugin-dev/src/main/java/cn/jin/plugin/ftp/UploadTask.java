package cn.jin.plugin.ftp;

import lombok.Getter;
import lombok.Setter;
import org.gradle.api.DefaultTask;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

/**
 * �Զ����ϴ�����
 */
@Getter
@Setter
public class UploadTask extends DefaultTask {

    /** ��������IP��ַ **/
    @Input
    private String host;

    /** �˿ں� **/
    @Input
    private int port;

    /** �û��� **/
    @Input
    private String username;

    /** ���� **/
    @Input
    private String password;

    /** ������Ŀ��Ŀ¼ **/
    @Input
    private String remoteDir;

    /** �����ļ�·�� **/
    @Input
    private String filePath;

    /**
     * ִ���ϴ�����
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
