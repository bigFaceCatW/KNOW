//package com.usi.cmos.config.util;
///**
// * sftp工具类
// * @author lmwang
// * 创建时间：2015-4-27 下午12:49:50
// */
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
//import org.apache.log4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.jcraft.jsch.Channel;
//import com.jcraft.jsch.ChannelSftp;
//import com.jcraft.jsch.JSch;
//import com.jcraft.jsch.JSchException;
//import com.jcraft.jsch.Session;
//import com.jcraft.jsch.SftpException;
//import com.usi.cmos.app.service.LoginService;
//  
//public class SFTPUtils {  
//	
//	private static final Logger LOGGER = Logger.getLogger(SFTPUtils.class);
//
//	//ftp主机ip
//	private static final String FTP_HOST_IP = ConfigUtil.getValue("ftpHostIp");
//	//ftp端口
//	private static final int FTP_PORT = Integer.parseInt(ConfigUtil.getValue("ftpPort"));
//	//ftp用户名
//	private static final String USER_NAME = ConfigUtil.getValue("userName");
//	//ftp密码
//	private static final String PASSWORD = ConfigUtil.getValue("password");
//	//超时时间（单位毫秒）
//	private static final int TIME_OUT = 5000;
//    /** 
//     * 连接sftp服务器 
//     * @return 
//     */  
//    public static ChannelSftp getSftpClient() {  
//        ChannelSftp sftp = new ChannelSftp();  
//        try {  
//            JSch jsch = new JSch();  
//            Session sshSession = jsch.getSession(USER_NAME, FTP_HOST_IP, FTP_PORT);  
//            sshSession.setPassword(PASSWORD);  
//            Properties sshConfig = new Properties();  
//            sshConfig.put("StrictHostKeyChecking", "no");  
//            sshSession.setConfig(sshConfig);  
//            sshSession.setTimeout(TIME_OUT);
//            sshSession.connect();  
//            Channel channel = sshSession.openChannel("sftp");  
//            channel.connect();  
//            sftp = (ChannelSftp) channel;  
//            LOGGER.mybatis("Connected to " + FTP_HOST_IP + ".");
//        } catch (Exception e) {  
//        	LOGGER.mybatis(e.getMessage());
//           // e.printStackTrace() ;  
//        }  
//        return sftp;  
//    }  
//    
//    
//    /** 
//     * 连接sftp服务器 
//     * @return 
//     */  
//    public static ChannelSftp getSftpClient(String userName,String ftpHoseIp,int ftpPort,String passwd) {  
//        ChannelSftp sftp = new ChannelSftp();  
//        try {  
//            JSch jsch = new JSch();  
//            Session sshSession = jsch.getSession(userName, ftpHoseIp, ftpPort);  
//            sshSession.setPassword(passwd);  
//            Properties sshConfig = new Properties();  
//            sshConfig.put("StrictHostKeyChecking", "no");  
//            sshSession.setConfig(sshConfig);  
//            sshSession.setTimeout(5000);
//            sshSession.connect();  
//            Channel channel = sshSession.openChannel("sftp");  
//            channel.connect();  
//            sftp = (ChannelSftp) channel;  
//            LOGGER.mybatis("Connected to " + ftpHoseIp + ".");
//        } catch (Exception e) {  
//        	LOGGER.mybatis(e.getMessage());
//           // e.printStackTrace() ;  
//        }  
//        return sftp;  
//    }  
//    
//    
//    /** 
//     * 创建目录
//     * @param dir 目录名称
//     * @param sftp 
//     */  
//    private static void createDir(String dir, ChannelSftp sftp){  
//        try{
//        	//文件服务器是linux，写死了/
//        	String [] dirArr = dir.split("/");
//        	for(String tmpDir:dirArr) {
//        		try {
//        			if(CommonUtil.isValue(tmpDir)){
//        				sftp.cd(tmpDir);
//        			}
//        		}catch(SftpException e) {
//        			LOGGER.mybatis("目录不存在，需要创建，异常就不打印了。。。");
//        			//创建一个目录并cd到那个目录下面，为了下一次
//        			sftp.mkdir(tmpDir);
//        			sftp.cd(tmpDir);
//        		}
//        	}
//		} catch (SftpException e) {
//			LOGGER.mybatis("创建目录失败了。。。");
//			//e.printStackTrace();
//		}  
//          
//    }
//    /**
//     * 上传
//     * @param fis 源文件输入流
//     * @param dir 路径
//     * @param newFileName 新文件名
//     * @return
//     */
//	public static Map<String,Object> upload(InputStream fis,String dir,String newFileName) {
//		//获取sftp客户端连接
//		ChannelSftp sftp = getSftpClient();
//		boolean flag = true;
//		Map<String,Object> map = new HashMap<String,Object>();
//		try {
//			map.put("homePath", sftp.getHome());
//			//切换到家目录
//			//sftp.cd(sftp.getHome());
//			sftp.cd("/");
//			//因为创建目录的时候，已经切换到对应的目录了，所以不创建目录后不再需要cd
//			createDir(dir,sftp);
//			sftp.put(fis,newFileName);
//		} catch (Exception e) {
//			LOGGER.mybatis("sftp文件上传失败。。。");
//			flag = false;
//			//e.printStackTrace();
//		} finally {
//			try {
//				Session sftpSession = sftp.getSession();
//				//关闭连接
//				if(sftp.isConnected()) {
//					sftp.disconnect();
//				}
//				//关闭session
//				if(sftpSession!=null) {
//					sftpSession.disconnect();
//				}
//			} catch (JSchException e) {
//				LOGGER.mybatis(e.getMessage());
//				//e.printStackTrace();
//			}
//		}
//		map.put("result", flag);
//		return map;
//	}
//  
//	/**
//	 * 下载
//	 * @param os 输出流
//	 * @param fileName 带路径的文件名
//	 * @return
//	 */
//	public static boolean download(OutputStream os,String fileName) {
//		//获取sftp客户端连接
//		ChannelSftp sftp = getSftpClient();
//		boolean flag = true;
//		try {
//			//切换到家目录
//			sftp.cd(sftp.getHome());
//			sftp.get(fileName, os);
//		} catch (Exception e) {
//			LOGGER.mybatis("sftp文件下载失败。。。");
//			flag = false;
//			//e.printStackTrace();
//		}finally {
//			try {
//				Session sftpSession = sftp.getSession();
//				//关闭连接
//				if(sftp.isConnected()) {
//					sftp.disconnect();
//				}
//				//关闭session
//				if(sftpSession!=null) {
//					sftpSession.disconnect();
//				}
//			} catch (JSchException e) {
//				LOGGER.mybatis(e.getMessage());
//				//e.printStackTrace();
//			}
//		}
//		return flag;
//	}  
//	
//	public static boolean download(OutputStream os,String fileName,ChannelSftp sftp) {
//		//获取sftp客户端连接
// 		boolean flag = true;
//		try {
//			//切换到家目录
//			sftp.cd(sftp.getHome());
//			sftp.get(fileName, os);
//		} catch (Exception e) {
//			LOGGER.mybatis("sftp文件下载失败。。。");
//			flag = false;
//			//e.printStackTrace();
//		}finally {
//			try {
//				Session sftpSession = sftp.getSession();
//				//关闭连接
//				if(sftp.isConnected()) {
//					sftp.disconnect();
//				}
//				//关闭session
//				if(sftpSession!=null) {
//					sftpSession.disconnect();
//				}
//			} catch (JSchException e) {
//				LOGGER.mybatis(e.getMessage());
//				//e.printStackTrace();
//			}
//		}
//		return flag;
//	}  
//	/**
//	 * 删除文件
//	 * @param fileName 文件名含路径（相对路径）
//	 * @return 
//	 */
//	public static boolean deleteFile(String fileName) {
//		//获取sftp客户端连接
//		ChannelSftp sftp = getSftpClient();
//		boolean flag = true;
//		try {
//			//切换到家目录
//			sftp.cd(sftp.getHome());
//			sftp.rm(fileName);  
//		} catch (Exception e) {
//			LOGGER.mybatis("sftp文件删除失败。。。");
//			flag = false;
//			//e.printStackTrace();
//		}finally {
//			try {
//				Session sftpSession = sftp.getSession();
//				//关闭连接
//				if(sftp.isConnected()) {
//					sftp.disconnect();
//				}
//				//关闭session
//				if(sftpSession!=null) {
//					sftpSession.disconnect();
//				}
//			} catch (JSchException e) {
//				LOGGER.mybatis(e.getMessage());
//				//e.printStackTrace();
//			}
//
//		}
//		return flag;
//	} 
//  
//}  
