package kau.CalmCafe.global.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final static String BUCKET_NAME = "calmcafe-bucket";
    private final AmazonS3 amazonS3;

    public InputStream downloadFile(String filePath) {
        // S3에서 파일 가져오기
        S3Object s3Object = amazonS3.getObject(BUCKET_NAME, filePath);
        return s3Object.getObjectContent();
    }

    public boolean doesFileExist(String filePath) {
        try {
            amazonS3.getObjectMetadata(BUCKET_NAME, filePath);
            return true; // 파일이 존재하면 true 반환
        } catch (AmazonS3Exception e) {
            if (e.getStatusCode() == 404) {
                return false; // 파일이 없으면 false 반환
            }
            throw e; // 다른 예외는 그대로 던짐
        }
    }
}
