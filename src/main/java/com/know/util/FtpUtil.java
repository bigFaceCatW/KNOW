package com.know.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.MalformedURLException;
/**
 * Demo class
 * 
 * @author test
 * @date 2018/10/31
 */
public class FtpUtil {
	
		private static final Logger LOGGER = Logger.getLogger(FtpUtil.class);
	
        //ftp服务器地址
        public static final String hostname = com.know.util.ConfigUtil.getValue("ftpHostIp");
        //ftp服务器端口号
        public static final Integer port = Integer.parseInt(com.know.util.ConfigUtil.getValue("ftpPort")) ;
        //ftp登录账号
        public static final String username = com.know.util.ConfigUtil.getValue("userName");
        //ftp登录密码
        public static final String password = com.know.util.ConfigUtil.getValue("password");
        //ftp上传地址
        public static final String uploadPath = com.know.util.ConfigUtil.getValue("uploadPath");
        
        private static FTPClient ftpClient = null;
        
        
        /**
         * 功能描述：初始化ftp服务器
         *
         */
        public static void initFtpClient() {
            ftpClient = new FTPClient();
            ftpClient.setControlEncoding("utf-8");
            try {
            	LOGGER.info("connecting...ftp服务器:"+hostname+":"+port);
                ftpClient.connect(hostname, port);//连接ftp服务器
                ftpClient.login(username, password);//登录ftp服务器
                int replyCode = ftpClient.getReplyCode();//是否成功登录服务器
                if(!FTPReply.isPositiveCompletion(replyCode)){
                	LOGGER.info("connect failed...ftp服务器:"+hostname+":"+port);
                }
                LOGGER.info("connect successfu...ftp服务器:"+hostname+":"+port);
            }catch (MalformedURLException e) {
               //e.printStackTrace();
               LOGGER.info(e.getMessage());
            }catch (IOException e) {
               //e.printStackTrace();
            	 LOGGER.info(e.getMessage());
            }
        }

        /**
        * 上传文件
        * @param pathname ftp服务保存地址
        * @param fileName 上传到ftp的文件名
        *  @param originfilename 待上传文件的名称（绝对地址） * 
        * @return
        */
        public static boolean uploadFile(String pathname, String fileName,String originfilename){
            boolean flag = false;
            InputStream inputStream = null;
            try{
                LOGGER.info("开始上传文件");
                inputStream = new FileInputStream(new File(originfilename));
                initFtpClient();
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.setControlEncoding("GBK");
                changeUploadWay(pathname);
                ftpClient.enterLocalPassiveMode();
                ftpClient.storeFile(fileName, inputStream);
                inputStream.close();
                ftpClient.logout();
                flag = true;
                LOGGER.info("上传文件成功");
            }catch (Exception e) {
                LOGGER.info("上传文件失败");
              //  e.printStackTrace();
            }finally{
                if(ftpClient.isConnected()){
                    try{
                        ftpClient.disconnect();
                    }catch(IOException e){
                        //e.printStackTrace();
                        LOGGER.info(e.getMessage());
                    }
                }
                if(null != inputStream){
                    try {
                        inputStream.close();
                    }catch (IOException e) {
                        //e.printStackTrace();
                        LOGGER.info(e.getMessage());
                    }
                }
            }
            return flag;
        }
        /**
         * 上传（使用）
         * @param fis 源文件输入流
         * @param pathname ftp服务保存地址
	     * @param fileName 上传到ftp的文件名
         * @return
         */
        public static boolean upload(InputStream fis,String pathname, String fileName){
        	boolean flag = false;
        	try{
        		LOGGER.info("开始上传文件,ftp路径："+pathname);
        		initFtpClient();
        		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        		if(changeUploadWay(pathname)){
        			ftpClient.enterLocalPassiveMode();
        			ftpClient.storeFile(fileName, fis);
        			fis.close();
        			ftpClient.logout();
        			flag = true;
        			LOGGER.info("上传文件成功");
        		}else{
        			LOGGER.info("创建文件夹失败，不上传该文件！");
        		}
        	}catch (Exception e) {
        		LOGGER.info("上传文件失败");
        		//e.printStackTrace();
        	}finally{
        		if(ftpClient.isConnected()){
        			try{
        				ftpClient.disconnect();
        			}catch(IOException e){
        				//e.printStackTrace();
        				 LOGGER.info(e.getMessage());
        			}
        		}
        	}
        	return flag;
        }
        
        /**
         * 功能描述：上传方法进入路径，如果路径找不到，则创建
         * 包含：查看目录是否存在、不存在则创建该目录、转到该目录
         * @param remote FTP服务器文件目录 
         */
        public static boolean changeUploadWay(String remote) throws IOException {
            boolean success = true;
            String directory = remote + "/";
            // 如果远程目录不存在，则递归创建远程服务器目录
            if (!directory.equalsIgnoreCase("/") && !ftpClient.changeWorkingDirectory("/fileserver/share0"+remote)) {
                int start = 0;
                int end = 0;
                if (directory.startsWith("/")) {
                    start = 1;
                }else {
                    start = 0;
                }
                end = directory.indexOf("/", start);
                String paths = "";
                while (true) {
                    String subDirectory = directory.substring(start, end);
                    if (!ftpClient.changeWorkingDirectory(subDirectory)) {
                        if (ftpClient.makeDirectory(subDirectory)) {
                        	ftpClient.changeWorkingDirectory(subDirectory);
                        }else {
                        	success=false;
                            LOGGER.info("创建目录失败："+paths);
                            break;
                        }
                    }
                    paths = paths + "/" + subDirectory;
                    start = end + 1;
                    end = directory.indexOf("/", start);
                    // 检查所有目录是否创建完毕
                    if (end <= start) {
                        break;
                    }
                }
            }
            return success;
        }
        
        /** * 下载文件 * 
        * @param pathname FTP服务器文件目录 * 
        * @param filename 文件名称 * 
        * @param localpath 下载后的文件路径 * 
        * @return 
        */
        public static boolean downloadFile(String pathname, String filename, String localpath){
            boolean flag = false;
            OutputStream os=null;
            try {
                LOGGER.info("开始下载文件");
                initFtpClient();
                //切换FTP目录 
                if (pathname.startsWith("/")) {
                	pathname=pathname.substring(1);
            	}
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.setControlEncoding("UTF-8"); 
                ftpClient.enterLocalPassiveMode();
                ftpClient.changeWorkingDirectory(pathname);
                FTPFile[] ftpFiles = ftpClient.listFiles();
                boolean exist=false;
                for(FTPFile file : ftpFiles){
                    if(filename.equalsIgnoreCase(file.getName())){
                        File localFile = new File(localpath + "/" + file.getName());
                        os = new FileOutputStream(localFile);
                        ftpClient.retrieveFile(file.getName(), os);
                        exist=true;
                        os.close();
                    }
                }
                ftpClient.logout();
                flag = true;
                if(!exist){
                	LOGGER.info("该文件不存在");
                }else{
                	LOGGER.info("下载文件成功");
                }
            }catch (Exception e) {
                LOGGER.info("下载文件失败");
               // e.printStackTrace();
            }finally{
                if(ftpClient.isConnected()){
                    try{
                        ftpClient.disconnect();
                    }catch(IOException e){
                       // e.printStackTrace();
                    	 LOGGER.info(e.getMessage());
                    }
                }
                if(null != os){
                    try {
                        os.close();
                    }catch (IOException e) {
                       // e.printStackTrace();
                    	 LOGGER.info(e.getMessage());
                    }
                }
            }
            return flag;
        }
        
        /** * 下载文件 * 
         * @param os 输出流
         * @param fileName ftp带路径的文件名
         */
        public static boolean download(OutputStream os,String fileName){
        	boolean flag = false;
        	try {
        		LOGGER.info("开始下载文件");
        		initFtpClient();
        		//下载
        		if (fileName.startsWith("/")) {
        			fileName=fileName.substring(1);
            	}
        		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        		ftpClient.enterLocalPassiveMode();
				ftpClient.retrieveFile(fileName, os);
        		ftpClient.logout();
        		flag = true;
    			LOGGER.info("下载文件成功");
        	}catch (Exception e) {
        		LOGGER.info("下载文件失败");
        		//e.printStackTrace();
        	}finally{
        		if(ftpClient.isConnected()){
        			try{
        				ftpClient.disconnect();
        			}catch(IOException e){
        				//e.printStackTrace();
        				 LOGGER.info(e.getMessage());
        			}
        		}
        	}
        	return flag;
        }
        
        
     
        
        /** * 删除文件 * 
        * @param pathname FTP服务器保存目录 * 
        * @param filename 要删除的文件名称 * 
        * @return */ 
        public static boolean deleteFile(String pathname, String filename){
            boolean flag = false;
            try {
                LOGGER.info("开始删除文件");
                initFtpClient();
                //切换FTP目录 
                if (pathname.startsWith("/")) {
                	pathname=pathname.substring(1);
            	}
                ftpClient.changeWorkingDirectory(pathname);
                ftpClient.dele(filename);
                ftpClient.logout();
                flag = true;
                LOGGER.info("删除文件成功");
            }catch (Exception e) {
                LOGGER.info("删除文件失败");
                //e.printStackTrace();
            }finally {
                if(ftpClient.isConnected()){
                    try{
                        ftpClient.disconnect();
                    }catch(IOException e){
                       // e.printStackTrace();
                    	 LOGGER.info(e.getMessage());
                    }
                }
            }
            return flag;
        }
        
        public static void main(String[] args) {
        	//测试download新方法
//        	File localFile = new File("C:\\Users\\雨木花草\\Desktop"+"/20181109093136815.wav");
//        	OutputStream os=null;
//        	try {
//        		os = new FileOutputStream(localFile);
//				boolean flag=download(os, uploadPath+"/video/ahadmin/20181109093136815.wav");
//				LOGGER.info(flag);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}finally{
//				if(null != os){
//                    try {
//                        os.close();
//                    }catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//			}
        	
        	//测试原方法
//            ftp.uploadFile("ftpFile/data", "123.docx", "E://123.docx");
//			boolean flag=uploadFile(uploadPath, "lishiwei.mp4", "C:\\Users\\雨木花草\\Desktop\\lishiwei.mp4");
     //       boolean flag=downloadFile(uploadPath, "0949393456119.mp4", "C:\\Users\\小白呀\\Desktop");
//           boolean flag=deleteFile(uploadPath, "LA1.jpg");
//        	LOGGER.info(flag);
        	System.exit(0);
        }
}