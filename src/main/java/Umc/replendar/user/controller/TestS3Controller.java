package Umc.replendar.user.controller;

import Umc.replendar.apiPayload.ApiResponse;
import Umc.replendar.common.AmazonConfig;
import Umc.replendar.common.AmazonS3Manager;
import Umc.replendar.global.util.AmazonS3Util;
import Umc.replendar.user.dto.req.res.TestRes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/s3")
@RequiredArgsConstructor
public class TestS3Controller {
    private final AmazonS3Util s3Util;
    private final AmazonS3Util amazonS3Util;

    @PostMapping(value = "/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<List<String>> createProfileImage(@PathVariable("id") Long id, @RequestPart(required = false) MultipartFile[] files) throws IOException {
        List<String> a = new ArrayList<>();

        for (MultipartFile file : files) {
            a.add(amazonS3Util.profileImageUpload(file, id));
        }

        return ApiResponse.onSuccess(a);
    }
}
