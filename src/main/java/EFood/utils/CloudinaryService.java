package EFood.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import EFood.config.CloudinaryConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Configuration
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public CloudinaryService(CloudinaryConfig cloudinary) {
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudinary.getCloudName(),
                "api_key", cloudinary.getApiKey(),
                "api_secret", cloudinary.getApiSecret()));
    }

    public String uploadFile(MultipartFile file) {
        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return (String) uploadResult.get("secure_url");
        } catch (IOException e) {
            throw new RuntimeException("image upload failed");
        }
    }
}