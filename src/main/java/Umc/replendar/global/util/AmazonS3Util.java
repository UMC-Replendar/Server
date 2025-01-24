package Umc.replendar.global.util;

import Umc.replendar.user.entity.ProfileImage;
import Umc.replendar.user.entity.User;
import Umc.replendar.user.repository.ProfileImageRepository;
import Umc.replendar.user.repository.UserRepository;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Util {

    private final AmazonS3 amazonS3Client;
    private final AmazonS3 amazonS3;
    private final ProfileImageRepository profileImageRepository;
    private final UserRepository userRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.path.profile}")
    private String profilePath;

    //프로필 이미지 업로드
    //db에 있는걸 먼저 찾고 s3를 삭제한 후 디비 데이터를 삭제해주시면 됩니다!
    @Transactional
    public String profileImageUpload(MultipartFile multipartFile, Long userId) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        ProfileImage beforeProfileImage = profileImageRepository.findByUser(user);

        //이미지가 이미 있으면 삭제하기
        if (beforeProfileImage != null) {
            String previousKey = profilePath + "/" + beforeProfileImage.getUuid() + "_" + beforeProfileImage.getOriginalFilename();
            amazonS3.deleteObject(bucket, previousKey); // S3에서 이전 이미지 삭제
        }
        //uuid 생성
        String uuid = UUID.randomUUID().toString(); //uuid 생성(랜덤값)

        beforeProfileImage.setUuid(uuid);
        beforeProfileImage.setOriginalFilename(multipartFile.getOriginalFilename());
        beforeProfileImage.setContentType(multipartFile.getContentType());
        beforeProfileImage.setFileSize(multipartFile.getSize());
        profileImageRepository.save(beforeProfileImage);

        String key = profilePath + "/" + uuid + "_" + multipartFile.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        // S3에 파일 업로드
        amazonS3.putObject(bucket, key, multipartFile.getInputStream(), metadata);

        // 업로드된 파일의 URL 반환
        return amazonS3.getUrl(bucket, key).toString();
    }

    //프로필 이미지 url 가져오기
    public String getProfilePath(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        ProfileImage profileImage = profileImageRepository.findByUser(user);
        if (profileImage == null) {
            return null;
        }
        return amazonS3.getUrl(bucket, profilePath + "/" + profileImage.getUuid() + "_" + profileImage.getOriginalFilename()).toString();
    }

//    // MultipartFile 을 전달받아 File 로 전환한 후 S3에 업로드
    public String upload(MultipartFile multipartFile, String path, ProfileImage uuid) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
        try {
            return upload(uploadFile, path, uuid);
        } finally {
            removeNewFile(uploadFile);  // 로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)
        }
    }

    private String upload(File uploadFile, String path, ProfileImage profileImage) {
        String fileName = generateKeyName(path, profileImage);
        return putS3(uploadFile, fileName);      // 업로드된 파일의 S3 URL 주소 반환
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.exists() && !targetFile.delete()) {
            log.error("파일이 삭제되지 못했습니다: {}", targetFile.getAbsolutePath());
            throw new RuntimeException("파일 삭제 실패: " + targetFile.getAbsolutePath());
        }
    }

    public void deleteFile(String targetFileName) {
        targetFileName = targetFileName.substring(46);
        log.info("targetFileUrl {} : ", targetFileName);
        amazonS3Client.deleteObject(bucket, targetFileName);
    }


    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("java.io.tmpdir") + "/" + Objects.requireNonNull(file.getOriginalFilename()));
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            } catch (IOException e) {
                log.error("파일 변환 중 오류 발생: {}", e.getMessage());
                throw e;
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    public String generateKeyName(String path, ProfileImage profileImage) {
        return profilePath + '/' + profileImage.getUuid();
    }
}
