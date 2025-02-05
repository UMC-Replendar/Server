package Umc.replendar.user.controller;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.common.security.JwtTokenProvider;
import Umc.replendar.global.util.AmazonS3Util;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/s3")
@RequiredArgsConstructor
public class S3Controller {

    private final AmazonS3Util amazonS3Util;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<List<String>> createProfileImage(@PathVariable("id") Long id, @RequestPart(required = false) MultipartFile[] files) throws IOException {
        List<String> uploadedUrls = new ArrayList<>();

        for (MultipartFile file : files) {
            uploadedUrls.add(amazonS3Util.profileImageUpload(file, id));
        }

        return ApiResponse.onSuccess(uploadedUrls);
    }
    @Operation(summary = "프로필 이미지 변경 API", description = "프로필 이미지를 변경 합니다.")
    @PostMapping(value = "/update-profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<String> updateProfileImage(@RequestPart("profileImage") MultipartFile profileImage) throws IOException {
        Long userId = jwtTokenProvider.getUserIdFromToken();

        String imageUrl = amazonS3Util.profileImageUpload(profileImage, userId);

        return ApiResponse.onSuccess(imageUrl);
    }
}
