package com.traffic.server.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Random;
import javax.imageio.ImageIO;

import com.traffic.server.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class OSSClientUtil {
    public static final Logger logger = LoggerFactory.getLogger(OSSClientUtil.class);
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String filedir;
    private OSSClient ossClient;

    public OSSClientUtil(String endpoint, String accessKeyId, String accessKeySecret, String bucketName, String filedir) {
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.bucketName = bucketName;
        this.filedir = filedir;
        this.ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    public void init() {
        this.ossClient = new OSSClient(this.endpoint, this.accessKeyId, this.accessKeySecret);
    }

    public void destory() {
        this.ossClient.shutdown();
    }

    public void uploadImg2Oss(String url) throws ApiException {
        File fileOnServer = new File(url);

        try {
            FileInputStream fin = new FileInputStream(fileOnServer);
            String[] split = url.split("/");
            this.uploadFile2OSS(fin, split[split.length - 1]);
        } catch (FileNotFoundException var5) {
            throw new ApiException(500, "图片/文件上传失败");
        } catch (ApiException var6) {
            throw var6;
        }
    }

    public String uploadImg2Oss(MultipartFile file) throws ApiException {
        if (file.getSize() > 10485760L) {
            throw new ApiException(500, "上传图片/文件大小不能超过10M");
        } else {
            String originalFilename = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
            Random random = new Random();
            String name = (long)random.nextInt(10000) + System.currentTimeMillis() + "." + originalFilename;

            try {
                InputStream inputStream = file.getInputStream();
                String contentType = getcontentType(originalFilename);
                if (contentType.equalsIgnoreCase("image/jpeg")) {
                    BufferedImage bufImg = ImageIO.read((InputStream)inputStream);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    BufferedImage newBufferedImage = new BufferedImage(bufImg.getWidth(), bufImg.getHeight(), 1);
                    newBufferedImage.createGraphics().drawImage(bufImg, 0, 0, Color.WHITE, (ImageObserver)null);
                    ImageIO.write(newBufferedImage, "jpg", bos);
                    inputStream = new ByteArrayInputStream(bos.toByteArray());
                }

                this.uploadFile2OSS((InputStream)inputStream, name);
                return name;
            } catch (ApiException var10) {
                throw var10;
            } catch (Exception var11) {
                throw new ApiException(500, "图片/文件上传失败");
            }
        }
    }

    public String getImgUrl(String fileUrl) {
        System.out.println(fileUrl);
        if (!StringUtils.isEmpty(fileUrl)) {
            String[] split = fileUrl.split("/");
            return this.getUrl(this.filedir + split[split.length - 1]);
        } else {
            return null;
        }
    }

    public String uploadFile2OSS(InputStream instream, String fileName) {
        String ret = "";

        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength((long)instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf(".") + 1)));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            PutObjectResult putResult = this.ossClient.putObject(this.bucketName, this.filedir + fileName, instream, objectMetadata);
            ret = putResult.getETag();
        } catch (IOException var15) {
            logger.error(var15.getMessage(), var15);
        } catch (ApiException var16) {
            throw var16;
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException var14) {
                var14.printStackTrace();
            }

        }

        return ret;
    }

    public static String getcontentType(String filenameExtension) {
        if (filenameExtension.equalsIgnoreCase("bmp")) {
            return "image/bmp";
        } else if (filenameExtension.equalsIgnoreCase("gif")) {
            return "image/gif";
        } else if (!filenameExtension.equalsIgnoreCase("jpeg") && !filenameExtension.equalsIgnoreCase("jpg") && !filenameExtension.equalsIgnoreCase("png")) {
            if (filenameExtension.equalsIgnoreCase("html")) {
                return "text/html";
            } else if (filenameExtension.equalsIgnoreCase("txt")) {
                return "text/plain";
            } else if (filenameExtension.equalsIgnoreCase("vsd")) {
                return "application/vnd.visio";
            } else if (!filenameExtension.equalsIgnoreCase("pptx") && !filenameExtension.equalsIgnoreCase("ppt")) {
                if (!filenameExtension.equalsIgnoreCase("docx") && !filenameExtension.equalsIgnoreCase("doc")) {
                    if (filenameExtension.equalsIgnoreCase("xml")) {
                        return "text/xml";
                    } else if (!filenameExtension.equalsIgnoreCase("xls") && !filenameExtension.equalsIgnoreCase("xlsx")) {
                        throw new ApiException(500, "不支持此类型");
                    } else {
                        return "application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
                    }
                } else {
                    return "application/msword";
                }
            } else {
                return "application/vnd.ms-powerpoint";
            }
        } else {
            return "image/jpeg";
        }
    }

    public String getUrl(String key) {
        Date expiration = new Date(System.currentTimeMillis() + 315360000000L);
        URL url = this.ossClient.generatePresignedUrl(this.bucketName, key, expiration);
        return url != null ? url.toString() : null;
    }
}
