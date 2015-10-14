package it.zeze.fanta.service.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

@Path("/util")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class Utils {
	@GET
	@Path("/variables")
	public String getVariable() throws SocketException, IOException {
		String toReturn = "";

		toReturn += "\njava.io.tmpdir: " + System.getProperty("java.io.tmpdir");

		File tmpFolder = new File("/tmp");
		if (tmpFolder.exists()) {
			toReturn += "\nCanRead: " + tmpFolder.canRead();
			toReturn += "\nCanWrite: " + tmpFolder.canWrite();
			File prova = new File("/tmp/prova");
			toReturn += "\nCreata: " + prova.mkdir();

			String[] listFile1 = tmpFolder.list();
			toReturn += "\nFileList1" + listFile1.length;
			for (String currentFile : listFile1) {
				toReturn += "\n - " + currentFile;
			}
		}
		return toReturn;
	}

	@GET
	@Path("/listFiles/{rootDir:.*}")
	public String listFiles(@PathParam("rootDir") String rootDir) throws SocketException, IOException {
		String toReturn = "";

		File tmpFolder = new File("/" + rootDir);
		if (tmpFolder.exists()) {
			toReturn += "\nCanRead: " + tmpFolder.canRead();
			toReturn += "\nCanWrite: " + tmpFolder.canWrite();

			File[] listFile1 = tmpFolder.listFiles();
			toReturn += "\nFileList: " + listFile1.length;
			for (File currentFile : listFile1) {
				if (currentFile.isDirectory()) {
					toReturn += "\n - " + currentFile;
				} else {
					toReturn += "\n -- " + currentFile;
				}
			}
		}
		return toReturn;
	}

	@GET
	@Path("/cleanDir/{rootDir:.*}")
	public String cleanDir(@PathParam("rootDir") String rootDir) throws SocketException, IOException {
		String toReturn = "";

		File tmpFolder = new File("/" + rootDir);
		if (tmpFolder.exists()) {
			toReturn += "\nCanRead: " + tmpFolder.canRead();
			toReturn += "\nCanWrite: " + tmpFolder.canWrite();

			FileUtils.cleanDirectory(tmpFolder);
		}
		return toReturn;
	}

	@GET
	@Path("/removeDir/{rootDir:.*}")
	public String removeDir(@PathParam("rootDir") String rootDir) throws SocketException, IOException {
		String toReturn = "";

		File tmpFolder = new File("/" + rootDir);
		if (tmpFolder.exists()) {
			toReturn += "\nCanRead: " + tmpFolder.canRead();
			toReturn += "\nCanWrite: " + tmpFolder.canWrite();

			FileUtils.cleanDirectory(tmpFolder);
			FileUtils.deleteDirectory(tmpFolder);
		}
		return toReturn;
	}

	@GET
	@Path("/getFileContent/{filePath:.*}")
	public String getFileContent(@PathParam("filePath") String filePath) throws SocketException, IOException {
		String toReturn = "";

		File tmpFile = new File("/" + filePath);
		if (tmpFile.exists()) {
			InputStream is = null;
			OutputStream os = null;
			try {
				is = new FileInputStream(tmpFile);
				os = new ByteArrayOutputStream();
				IOUtils.copy(is, os);
				toReturn += "\n" + os.toString();
			} finally {
				IOUtils.closeQuietly(is);
				IOUtils.closeQuietly(os);
			}
		}
		return toReturn;
	}
}
