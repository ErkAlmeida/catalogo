package com.catalogo.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.apache.commons.io.FilenameUtils;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


@Service
public class S3Service {
    private static Logger LOG = LoggerFactory.getLogger(S3Service.class);

    @Autowired
    private AmazonS3 s3client;

    @Value("${s3.bucket}")
    private String bucketName;

    public URL uploadFile(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = FilenameUtils.getExtension(originalFilename);
            String fileName = Instant.now().toDate().getTime() + "." + extension;
            InputStream inputStream = file.getInputStream();
            String contentType = file.getContentType();
            return uploadFile(inputStream,fileName,contentType);
        }
        catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    private URL uploadFile(InputStream inputStream, String fileName, String contentType) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        LOG.info("inicio do upload");
        s3client.putObject(bucketName,fileName,inputStream,metadata);
        LOG.info("fim do upload");
        return s3client.getUrl(bucketName,fileName);
    }
}
