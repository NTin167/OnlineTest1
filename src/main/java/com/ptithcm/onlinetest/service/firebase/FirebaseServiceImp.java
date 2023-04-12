package com.ptithcm.onlinetest.service.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import com.ptithcm.onlinetest.config.FirebaseConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


@Service
public class FirebaseServiceImp implements FirebaseService{

    @Autowired
    FirebaseConfig properties;

    @EventListener
    public void init(ApplicationReadyEvent event) {
        ClassPathResource resource = new ClassPathResource("serviceAccountKey.json");
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
                    .setStorageBucket(properties.getBucketName())
                    .setDatabaseUrl("https://quiz-app-9e936-default-rtdb.firebaseio.com")
                    .build();
            FirebaseApp.initializeApp(options);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public String getImageUrl(String name) {
        return String.format(properties.imageUrl, name);
    }

    @Override
    public String save(MultipartFile file) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();

        String name = generateFileName(file.getName());

        bucket.create(name,file.getBytes(),file.getContentType());
        return properties.prefixImageUrl.concat(name).concat(properties.suffixImageUrl);
    }

    @Override
    public String saveAudio(MultipartFile fileAudio) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();

        String fileName = fileAudio.getOriginalFilename();
        String contentType = "audio/wav";
//        System.out.println(fileAudio.getContentType());
        String storagePath = "audio" + fileName+".wav";
        InputStream inputStream = fileAudio.getInputStream();
        bucket.create(fileName, inputStream, contentType);
        return properties.prefixImageUrl.concat(fileName).concat(properties.suffixImageUrl);
    }

    @Override
    public String save(BufferedImage bufferedImage, String originalFileName) throws IOException {
        byte[] bytes = getByteArrays(bufferedImage, getExtension(originalFileName));

        com.google.cloud.storage.Bucket bucket = StorageClient.getInstance().bucket();

        String name = generateFileName(originalFileName);

        bucket.create(name, bytes);

        return name;
    }

    @Override
    public void delete(String name) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();

        if (StringUtils.isEmpty(name)) {
            throw new IOException("invalid file name");
        }

        Blob blob = bucket.get(name);

        if (blob == null) {
            throw new IOException("file not found");
        }

        blob.delete();
    }
}
