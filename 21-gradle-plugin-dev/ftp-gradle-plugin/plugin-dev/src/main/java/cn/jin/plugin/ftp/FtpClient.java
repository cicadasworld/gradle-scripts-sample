package cn.jin.plugin.ftp;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * commons-net的FTPClient的包装类
 *
 * @author hujin
 * @version 2022/1/25
 */
public class FtpClient {

    /**
     * 主机名或IP地址
     */
    private String host;

    /**
     * 端口号
     */
    private int port;

    /**
     * 用户名
     */
    private String user;

    /**
     * 密码
     */
    private String password;

    /**
     * FTP客户端
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
     * 打开并登录FTP服务器
     *
     * @throws IOException 输入输出异常
     */
    public void open() throws IOException {
        try {
            ftpClient.connect(host, port);
        } catch (IOException e) {
            throw new IOException("主机名或端口错误");
        }
        int reply = ftpClient.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftpClient.disconnect();
        }
        // 登录
        if (!ftpClient.login(user, password)) {
            throw new IOException("用户名或密码错误");
        }
    }

    /**
     * 登出并关闭FTP服务器
     *
     * @throws IOException 输入输出异常
     */
    public void close() throws IOException {
        // 登出
        ftpClient.logout();
        if (ftpClient != null && ftpClient.isConnected()) {
            ftpClient.disconnect();
        }
    }

    /**
     * 下载服务器文件到本地
     *
     * @param source      服务器文件路径
     * @param destination 本地文件路径
     * @throws IOException 输入输出异常
     */
    public boolean downloadFile(String source, String destination) throws IOException {
        FileOutputStream out = new FileOutputStream(destination);
        return ftpClient.retrieveFile(source, out);
    }

    /**
     * 上传文件到服务器
     *
     * @param file 本地文件对象
     * @param path 服务器文件路径
     * @throws IOException 输入输出异常
     */
    public boolean putFileToPath(File file, String path) throws IOException {
        FileInputStream in = new FileInputStream(file);
        return ftpClient.storeFile(path, in);
    }

    /**
     * 设置FTP连接参数
     *
     * @throws IOException 输入输出异常
     */
    public void setProperty() throws IOException {
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
    }
}
