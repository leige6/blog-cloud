package com.baidu.ueditor.upload;

import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class BinaryUploader
{

  private static String serverPath;

  private static String imagePath;

  @Value("${server.upload-path}")
  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }
  @Value("${server.context-path}")
  public void setServerPath(String serverPath) {
    this.serverPath = serverPath;
  }

  private static final Logger logger = LoggerFactory.getLogger(BinaryUploader.class);

  public static final State save(HttpServletRequest request, Map<String, Object> conf) {

    if (!ServletFileUpload.isMultipartContent(request)) {
      return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
    }

    try {
      String savePath ="upload/ueditor/";
      //上传附件目录
      Path path = Paths.get(savePath);
      if(!Files.isDirectory(path)){
        try {
          Files.createDirectories(path);
        } catch (IOException e) {
          logger.error("百度上传附件创建上传文件夹错误： {}", e.getMessage());
        }
      }
      //重新命名文件名字
      String newFileName = "", fileExt = "", fileName = "";
     // MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
      MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart(request);
      MultipartFile multipartFile = multipartRequest.getFile(conf.get("fieldName").toString());
      if(multipartFile==null){
        return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
      }
      String originFileName = multipartFile.getOriginalFilename();
      String suffix = FileType.getSuffixByFilename(originFileName);
      originFileName = originFileName.substring(0,
              originFileName.length() - suffix.length());
      newFileName=generateName();
      savePath = savePath + newFileName+suffix;
      long maxSize = ((Long) conf.get("maxSize")).longValue();

      if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
        return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
      }
      String physicalPath = imagePath+ savePath;

      //InputStream is = fileStream.openStream();
      InputStream is = multipartFile.getInputStream();
      State storageState = StorageManager.saveFileByInputStream(is,
              physicalPath, maxSize);
      is.close();

      if (storageState.isSuccess()) {
        storageState.putInfo("url", serverPath+PathFormat.format(savePath));
        storageState.putInfo("type", suffix);
        storageState.putInfo("original", originFileName + suffix);
      }

      return storageState;

    } catch (IOException e) {
    }
    return new BaseState(false, AppInfo.IO_ERROR);
  }

  private static boolean validType(String type, String[] allowTypes) {
    List<String> list = Arrays.asList(allowTypes);
    return list.contains(type);
  }

  public static String generateName() {
    DateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    String uuid = format.format(new Date().getTime()) + new Double(Math.random() * 100000).intValue();
    while (uuid.length() < 22) {
      uuid = uuid + "0";
    }
    uuid = uuid.substring(2);
    return uuid;
  }
}