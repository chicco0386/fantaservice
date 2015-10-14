package it.zeze.fanta.service;

import it.zeze.ftp.FtpUtil;

import java.io.IOException;
import java.net.SocketException;

import javax.ws.rs.Consumes;
import javax.ws.rs.Encoded;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/ftp")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class Ftp {

	@GET
	@Path("/test/{url:.*}/{user:.}/{pass:.}")
	public String doFtp(@PathParam("url") String url, @PathParam("user") String user, @PathParam("pass") String pass) throws SocketException, IOException {
		return FtpUtil.testFtp(url, user, pass);

	}

	@GET
	@Path("/getFileContent/{remotePath:.*}/{url:.*}/{user:.}/{pass:.}")
	public String getFileContent(@PathParam("remotePath") String remoteFilePath, @PathParam("url") String url, @PathParam("user") String user, @PathParam("pass") String pass) throws SocketException, IOException {
		return FtpUtil.getFileContent(remoteFilePath, url, user, pass);

	}

	@GET
	@Path("/downloadFile/{remotePath:.*}-{localPath:.*}/{url:.*}/{user:.}/{pass:.}")
	@Encoded
	public String downloadFile(@PathParam("remotePath") String remotePath, @PathParam("localPath") String localPath, @PathParam("url") String url, @PathParam("user") String user, @PathParam("pass") String pass) throws SocketException, IOException {
		return FtpUtil.saveFile("/" + remotePath, "/" + localPath, url, user, pass);

	}

	@GET
	@Path(value = "/downloadFile/{remotePath:.*}/{url:.*}/{user:.}/{pass:.}")
	public String downloadFile(@PathParam("remotePath") String remotePath, @PathParam("url") String url, @PathParam("user") String user, @PathParam("pass") String pass) throws SocketException, IOException {
		return FtpUtil.saveFile("/" + remotePath, "/tmp", url, user, pass);

	}

	@GET
	@Path(value = "/saveAllFilesDir/{remotePath:.*}-{localPath:.*}/{url:.*}/{user:.}/{pass:.}")
	public String saveAllFilesDir(@PathParam("remotePath") String remotePath, @PathParam("localPath") String localPath, @PathParam("url") String url, @PathParam("user") String user, @PathParam("pass") String pass) throws SocketException, IOException {
		return FtpUtil.saveAllFilesDir("/" + remotePath, "/" + localPath, url, user, pass);

	}
}
