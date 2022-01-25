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
 * �Զ�����������
 */
@Getter
@Setter
public class DownloadTask extends DefaultTask {

    /**
     * ��������IP��ַ
     **/
    @Input
    private String host;

    /**
     * �˿ں�
     **/
    @Input
    private int port;

    /**
     * �û���
     **/
    @Input
    private String username;

    /**
     * ����
     **/
    @Input
    private String password;

    /**
     * �������ļ�·��
     **/
    @Input
    private String src;

    /**
     * �����ļ�·��
     **/
    @Input
    private String dest;

    /**
     * ִ����������
     */
    @TaskAction
    public void download() {
        Logger logger = getProject().getLogger();
        FtpClient ftpClient = new FtpClient(host, port, username, password);
        try {
            ftpClient.open();
            ftpClient.setProperty();
            if (ftpClient.downloadFile(src, dest)) {
                logger.warn("�������");
            } else {
                logger.warn("����ʧ��");
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
