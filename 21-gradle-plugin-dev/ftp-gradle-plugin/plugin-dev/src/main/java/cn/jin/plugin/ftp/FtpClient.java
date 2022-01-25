package cn.jin.plugin.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * commons-net��FTPClient�İ�װ��
 *
 * @author hujin
 * @version 2022/1/25
 */
public class FtpClient {

    /**
     * ��������IP��ַ
     */
    private String host;

    /**
     * �˿ں�
     */
    private int port;

    /**
     * �û���
     */
    private String user;

    /**
     * ����
     */
    private String password;

    /**
     * FTP�ͻ���
     */
    private FTPClient ftpClient;

    public FtpClient(String host, int port, String user, String password) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.ftpClient = new FTPClient();
    }

    /**
     * �򿪲���¼FTP������
     *
     * @throws IOException ��������쳣
     */
    public void open() throws IOException {
        try {
            ftpClient.connect(host, port);
        } catch (IOException e) {
            throw new IOException("��������˿ڴ���");
        }
        int reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpClient.disconnect();
        }
        // ��¼
        if (!ftpClient.login(user, password)) {
            throw new IOException("�û������������");
        }
    }

    /**
     * �ǳ����ر�FTP������
     *
     * @throws IOException ��������쳣
     */
    public void close() throws IOException {
        // �ǳ�
        ftpClient.logout();
        if (ftpClient != null && ftpClient.isConnected()) {
            ftpClient.disconnect();
        }
    }

    /**
     * ���ط������ļ�������
     *
     * @param source      �������ļ�·��
     * @param destination �����ļ�·��
     * @throws IOException ��������쳣
     */
    public boolean downloadFile(String source, String destination) throws IOException {
        FileOutputStream out = new FileOutputStream(destination);
        return ftpClient.retrieveFile(source, out);
    }

    /**
     * �ϴ��ļ���������
     *
     * @param file �����ļ�����
     * @param path �������ļ�·��
     * @throws IOException ��������쳣
     */
    public boolean putFileToPath(File file, String path) throws IOException {
        FileInputStream in = new FileInputStream(file);
        return ftpClient.storeFile(path, in);
    }

    /**
     * ����FTP���Ӳ���
     *
     * @throws IOException ��������쳣
     */
    public void setProperty() throws IOException {
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
    }
}
