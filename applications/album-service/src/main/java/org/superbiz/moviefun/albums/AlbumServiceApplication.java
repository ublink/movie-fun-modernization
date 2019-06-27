package org.superbiz.moviefun.albums;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.superbiz.moviefun.blobstore.BlobStore;
import org.superbiz.moviefun.blobstore.S3Store;
import org.superbiz.moviefun.support.ServiceCredentials;

@SpringBootApplication
public class AlbumServiceApplication {
    private static final Logger logger = LoggerFactory.getLogger(AlbumServiceApplication.class);

    public static void main(String... args) {
        ApplicationContext applicationContext = SpringApplication.run(AlbumServiceApplication.class, args);
//
//        Environment env = applicationContext.getEnvironment();
//        logger.info("Environment: {}", env.getProperty("vcap.services") );

    }

//    @Component
//    @Order(1)
//    public class ShowEnvRunner implements CommandLineRunner {
//        @Override
//        public void run(String... args) throws Exception {
//            logger.info("VCAP_SERVICES: {}", System.getenv() );
//        }
//    }
//
//
//    @Component
//    @Order(2)
//    public class SecondShowEnvRunner implements CommandLineRunner {
//        @Override
//        public void run(String... args) throws Exception {
//            logger.info("VCAP_SERVICES: {}", System.getenv() );
//        }
//    }


    @Bean
    ServiceCredentials serviceCredentials(@Value("${vcap.services}") String vcapServices) {
        return new ServiceCredentials(vcapServices);
    }

    @Bean
    public BlobStore blobStore(
            ServiceCredentials serviceCredentials,
            @Value("${vcap.services.photo-storage.credentials.endpoint:#{null}}") String endpoint
    ) {
        String photoStorageAccessKeyId = serviceCredentials.getCredential("photo-storage", "user-provided", "access_key_id");
        String photoStorageSecretKey = serviceCredentials.getCredential("photo-storage", "user-provided", "secret_access_key");
        String photoStorageBucket = serviceCredentials.getCredential("photo-storage", "user-provided", "bucket");

        logger.info("S3 Credentials are AccessKeyId = {}, SecretKey = {}, Bucket = {}",
                photoStorageAccessKeyId,
                photoStorageSecretKey,
                photoStorageBucket);

        AWSCredentials credentials = new BasicAWSCredentials(photoStorageAccessKeyId, photoStorageSecretKey);
        AmazonS3Client s3Client = new AmazonS3Client(credentials);

        if (endpoint != null) {
            s3Client.setEndpoint(endpoint);
        }

        return new S3Store(s3Client, photoStorageBucket);
    }
}