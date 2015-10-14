package it.zeze.ftp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FtpUtil {

	public static String testFtp(String url, String user, String pass) throws SocketException, IOException {
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(url);
			ftpClient.enterLocalPassiveMode();
			String toReturn = "";
			toReturn += "\n Login: " + ftpClient.login(user, pass);
			toReturn += "\n Connected: " + ftpClient.isConnected();
			toReturn += "\n WorkingDir: " + ftpClient.printWorkingDirectory();
			toReturn += "\n Spostato nella ROOT: " + ftpClient.changeToParentDirectory();
			toReturn += "\n WorkingDir: " + ftpClient.printWorkingDirectory();
			toReturn += "\n ListDir: " + ftpClient.listDirectories().length;
			FTPFile[] listFile = ftpClient.listFiles();
			toReturn += "\n ListFile " + listFile.length;
			return toReturn;
		} finally {
			ftpClient.disconnect();
		}
	}

	public static String getFileContent(String pathFile, String url, String user, String pass) throws SocketException, IOException {
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(url);
			ftpClient.enterLocalPassiveMode();
			String toReturn = "";
			toReturn += "\n Login: " + ftpClient.login(user, pass);
			toReturn += "\n Connected: " + ftpClient.isConnected();
			FTPFile[] listFile = ftpClient.listFiles();
			toReturn += "\n ListFile " + listFile.length;
			for (FTPFile currentFile : listFile) {
				if (currentFile.isFile()) {
					OutputStream os = null;
					try {
						os = new ByteArrayOutputStream();
						toReturn += "\n Trovato il file: " + ftpClient.retrieveFile("/" + pathFile, os);
						toReturn += "\n" + os.toString();
					} finally {
						IOUtils.closeQuietly(os);
					}
				}
			}
			return toReturn;
		} finally {
			ftpClient.disconnect();
		}
	}

	public static String saveFile(String fileAbsolutePath, String dirDownloadPath, String url, String user, String pass) throws SocketException, IOException {
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(url);
			ftpClient.enterLocalPassiveMode();
			String toReturn = "";
			toReturn += "\n Login: " + ftpClient.login(user, pass);
			toReturn += "\n Connected: " + ftpClient.isConnected();
			FTPFile[] listFile = ftpClient.listFiles();
			toReturn += "\n ListFile " + listFile.length;
			File downloadDir = new File(dirDownloadPath);
			downloadDir.mkdirs();
			String nomeFile = StringUtils.substringAfterLast(fileAbsolutePath, "/");
			File downloadFile = new File(dirDownloadPath + "/" + nomeFile);
			toReturn += "\n File [" + fileAbsolutePath + "] scaricato in [" + downloadFile.getAbsolutePath() + "]" + ftpClient.retrieveFile(fileAbsolutePath, new FileOutputStream(downloadFile));
			return toReturn;
		} finally {
			ftpClient.disconnect();
		}
	}

	public static String saveAllFilesDir(String dirRemoteAbsolutePath, String dirLocalDownloadPath ,String url, String user, String pass) throws SocketException, IOException {
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(url);
			ftpClient.enterLocalPassiveMode();
			String toReturn = "";
			toReturn += "\n Login: " + ftpClient.login(user, pass);
			toReturn += "\n Connected: " + ftpClient.isConnected();
			File downloadDir = new File(dirLocalDownloadPath);
			downloadDir.mkdirs();
			if (ftpClient.changeWorkingDirectory(dirRemoteAbsolutePath)) {
				toReturn += "\nImpostata working dir [" + dirRemoteAbsolutePath + "]";
				FTPFile[] listFile = ftpClient.listFiles();
				String remotePath = "";
				String localPath = "";
				for (FTPFile currentFile : listFile) {
					if (currentFile.isFile()) {
						remotePath = dirRemoteAbsolutePath + "/" + currentFile.getName();
						localPath = dirLocalDownloadPath + "/" + currentFile.getName();
						boolean result = ftpClient.retrieveFile(remotePath, new FileOutputStream(localPath));
						toReturn += "\n File [" + remotePath + "] scaricato in [" + localPath + "]" + result;
					}
				}
			}
			return toReturn;
		} finally {
			ftpClient.disconnect();
		}
	}
}
