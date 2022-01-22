package cn.jin.plugin.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ConnectException;

/**
 * 上传客户端实现
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
     * 上传文件
     * @param host 主机名或IP地址
     * @param port 端口号
     * @param username 用户名
     * @param password 密码
     * @param remoteDir 服务器目标目录
     * @param filePath 本地文件路径
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
     * 连接FTP服务器
     * @param ftpClient FTP客户端
     * @param host 主机名
     * @param port 端口
     * @param username 用户名
     * @param password 密码
     * @throws IOException 输入输出异常
     */
    private void connect(FTPClient ftpClient, String host, int port, String username, String password) throws IOException {
        // 连接服务器
        ftpClient.connect(host, port);
        int replyCode = ftpClient.getReplyCode();
        // 是否连接成功
        if (!FTPReply.isPositiveCompletion(replyCode)) {
            throw new ConnectException("FTP服务器 " + host + " 拒绝连接");
        }
        // 登录
        if (!ftpClient.login(username, password)) {
            throw new ConnectException("用户名或密码错误");
        }
    }

    /**
     * 设置FTP服务器参数
     * @param ftpClient FTP客户端
     * @throws IOException 输入输出异常
     */
    private void setProperty(FTPClient ftpClient) throws IOException {
        ftpClient.enterLocalPassiveMode();
        // 二进制传输，默认为ASCII
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
    }

    /**
     * 上传某个文件
     * @param ftpClient FTP客户端
     * @param remoteDir 服务器目标目录
     * @param filePath 本地文件路径
     * @throws IOException 输入输出异常
     */
    private void upload(FTPClient ftpClient, String remoteDir, String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        ftpClient.changeWorkingDirectory(remoteDir);
        File file = new File(filePath);
        ftpClient.storeFile(file.getName(), fis);
    }

    /**
     * 登出FTP服务器
     * @param ftpClient FTP客户端
     * @throws IOException 输入输出异常
     */
    private void logout(FTPClient ftpClient) throws IOException {
        ftpClient.noop();
        ftpClient.logout();
    }
}
