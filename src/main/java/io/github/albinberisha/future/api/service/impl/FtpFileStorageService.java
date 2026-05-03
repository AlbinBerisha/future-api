package io.github.albinberisha.future.api.service.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import io.github.albinberisha.future.api.service.FileStorageService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author Albin Berisha <albin199915@gmail.com>
 *
 */
@Service
@Validated
@Qualifier("ftpFileStorageService")
@Profile("local")
public class FtpFileStorageService implements FileStorageService {
    @Value("${ftp.host}")
    private String host;
    @Value("${ftp.port}")
    private int port;
    @Value("${ftp.username}")
    private String username;
    @Value("${ftp.password}")
    private String password;

    @Override
    public String upload(@NotBlank String path, @NotNull MultipartFile file) throws IOException {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(host, port);
            ftpClient.login(username, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ensureDirectories(ftpClient, path);
            try (InputStream inputStream = file.getInputStream()) {
                boolean stored = ftpClient.storeFile(path, inputStream);
                if (!stored) {
                    throw new IOException("FTP store failed: " + ftpClient.getReplyCode());
                }
            }
            return path;
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ignored) {
            }
        }
    }

    private void ensureDirectories(FTPClient ftpClient, String path) throws IOException {
        int lastSlash = path.lastIndexOf('/');
        if (lastSlash <= 0) {
            return;
        }
        String dirPath = path.substring(0, lastSlash);
        String[] dirs = dirPath.split("/");
        for (String dir : dirs) {
            if (!dir.isEmpty()) {
                ftpClient.makeDirectory(dir);
                ftpClient.changeWorkingDirectory(dir);
            }
        }
        ftpClient.changeWorkingDirectory("/");
    }

	@Override
	public InputStream download(String remoteFile) throws IOException {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(host, port);
            ftpClient.login(username, password);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            return ftpClient.retrieveFileStream(remoteFile);
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ignored) {
            }
        }
	}
}
