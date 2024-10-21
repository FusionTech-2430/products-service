package co.allconnected.fussiontech.productsservice.services;

import co.allconnected.fussiontech.productsservice.utils.OperationException;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class FirebaseService {
    public String uploadImgProduct(String idBusiness, String idProduct, String extension, MultipartFile imageFile) throws IOException {
        InputStream inputStream = imageFile.getInputStream();
        Bucket bucket = StorageClient.getInstance().bucket();
        bucket.create("business_photos/"+idBusiness+"/products/"+idProduct, inputStream, "image/"+extension);
        return bucket.get("business_photos/"+idBusiness+"/products/"+idProduct)
                .signUrl(360, java.util.concurrent.TimeUnit.DAYS).toString();
    }
    public void deleteImgProduct(String idBusiness, String idProduct) {
        Bucket bucket = StorageClient.getInstance().bucket();
        Blob blob = bucket.get("business_photos/"+idBusiness+"/products/"+idProduct);

        if (blob != null) {
            blob.delete();
        } else {
            throw new OperationException(404, "Image file not found in the bucket");
        }
    }

}
