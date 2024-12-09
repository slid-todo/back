package com.codeit.todo.service.storage.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.codeit.todo.common.exception.ApplicationException;
import com.codeit.todo.common.exception.payload.ErrorStatus;
import com.codeit.todo.service.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements StorageService {
    private final AmazonS3 amazonS3;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    /**
     * @param fileEncodedBase64 Base64로 인코딩된 파일
     * @param fileName          파일명
     * @return uploadUrl        S3 업로드 주소
     */
    @Override
    public String uploadFile(String fileEncodedBase64, String fileName) {
        if (Objects.isNull(fileName) || fileName.isEmpty()) {
            throw new ApplicationException(
                    ErrorStatus.toErrorStatus("파일명이 첨부되지 않았습니다.", 400)
            );
        }

        try {
            String base64 = fileEncodedBase64.split(",")[1];
            byte[] decodedBytes = Base64.getDecoder().decode(base64);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(decodedBytes.length);
            metadata.setContentType(determineType(fileName));

            String uploadFileName = UUID.randomUUID() + "_" + fileName;
            ByteArrayInputStream input = new ByteArrayInputStream(decodedBytes);

            amazonS3.putObject(bucket, uploadFileName, input, metadata);

            return getPublicUrl(uploadFileName);
        } catch (IllegalArgumentException e) {
            throw new ApplicationException(
                    ErrorStatus.toErrorStatus("파일 디코딩에 실패했습니다.", 400)
            );
        } catch (Exception e) {
            throw new ApplicationException(
                    ErrorStatus.toErrorStatus("S3 업로드 중 오류가 발생했습니다: " + e.getMessage(), 500)
            );
        }
    }

    /**
     *
     * @param fileName 파일명
     * @return extension 확장자
     */
    private String determineType(String fileName) {
        if (fileName.contains(".")) {
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

            return switch (extension) {
                case "png" -> "png";
                case "jpeg", "jpg" -> "jpeg";
                default -> "application/octet-stream";
            };
        }

        throw new ApplicationException(
                ErrorStatus.toErrorStatus("유효하지 않은 파일명입니다.", 400)
        );
    }

    public String getPublicUrl(String uploadFileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, amazonS3.getRegionName(), uploadFileName);
    }
}
