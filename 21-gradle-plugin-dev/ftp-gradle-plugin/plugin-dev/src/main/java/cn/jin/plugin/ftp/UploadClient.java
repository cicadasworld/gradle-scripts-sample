package cn.jin.plugin.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ConnectException;

/**
 * �ϴ��ͻ���ʵ��
 */
public class UploadClient {

    private UploadClient() {
    }

    public static UploadClient getInstance() {
        return UploadClientHolder.instance;
    }

    public static class UploadClientHolder {
        private static final UploadClient instance = new UploadClient();
    }

    /**
     * �ϴ��ļ�
     * @param host ��������IP��ַ
     * @param port �˿ں�
     * @param username �û���
     * @param password ����
     * @param remoteDir ������Ŀ��Ŀ¼
     * @param filePath �����ļ�·��
     */
    public void uploadFile(
            String host,
            int port,
            String username,
            String password,
            String remoteDir,
            String filePath) {
        FTPClient ftpClient = null;
        try {
            ftpClient = new FTPClient();
            connect(ftpClient, host, port, username, password);
            setProperty(ftpClient);
            upload(ftpClient, remoteDir, filePath);
            logout(ftpClient);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient != null && ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    /**
     * ����FTP������
     * @param ftpClient FTP�ͻ���
     * @param host ������
     * @param port �˿�
     * @param username �û���
     * @param password ����
     * @throws IOException ��������쳣
     */
    private void connect(FTPClient ftpClient, String host, int port, String username, String password) throws IOException {
        // ���ӷ�����
        ftpClient.connect(host, port);
        int replyCode = ftpClient.getReplyCode();
        // �Ƿ����ӳɹ�
        if (!FTPReply.isPositiveCompletion(replyCode)) {
            throw new ConnectException("FTP������ " + host + " �ܾ�����");
        }
        // ��¼
        if (!ftpClient.login(username, password)) {
            throw new ConnectException("�û������������");
        }
    }

    /**
     * ����FTP����������
     * @param ftpClient FTP�ͻ���
     * @throws IOException ��������쳣
     */
    private void setProperty(FTPClient ftpClient) throws IOException {
        ftpClient.enterLocalPassiveMode();
        // �����ƴ��䣬Ĭ��ΪASCII
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
    }

    /**
     * �ϴ�ĳ���ļ�
     * @param ftpClient FTP�ͻ���
     * @param remoteDir ������Ŀ��Ŀ¼
     * @param filePath �����ļ�·��
     * @throws IOException ��������쳣
     */
    private void upload(FTPClient ftpClient, String remoteDir, String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        ftpClient.changeWorkingDirectory(remoteDir);
        File file = new File(filePath);
        ftpClient.storeFile(file.getName(), fis);
    }

    /**
     * �ǳ�FTP������
     * @param ftpClient FTP�ͻ���
     * @throws IOException ��������쳣
     */
    private void logout(FTPClient ftpClient) throws IOException {
        ftpClient.noop();
        ftpClient.logout();
    }
}
