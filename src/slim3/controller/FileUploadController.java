package slim3.controller;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slim3.controller.Navigation;
import org.slim3.datastore.Datastore;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.UploadOptions;

import slim3.model.reserve.ImgInGCS;
import util.StringUtil;
/**
 * ファイルをGCSにアップロードします。
 * @author uedadaiki
 *
 */
//TODO 共通化する。
public class FileUploadController extends AbstractController {
    private static final String BUCKET = "rese";

    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    
    public Navigation run() throws Exception {
        switch (request.getMethod()) {
        case "GET":
            String key = request.getParameter("key");
            if (StringUtil.isEmpty(key)) {
                respondUploadUrl();
            } else {
                downloadGcsFile(new BlobKey(key));
            }
            break;
        case "POST":
            receiveBlobServiceRedirect();
            break;
        default:
            throw new UnsupportedOperationException();
    }
    return null;
    }
    
    private void respondUploadUrl() throws Exception {
        System.out.println("responseUploadUrl");

        final long MEGA_BYTE = 1024 * 1024 * 1024;

//        final String defaultGcsBucketName =
//                AppIdentityServiceFactory.getAppIdentityService().getDefaultGcsBucketName();
//        System.out.println(defaultGcsBucketName);

        final UploadOptions options =
                UploadOptions.Builder.withGoogleStorageBucketName(BUCKET)
                    .maxUploadSizeBytes(100 * MEGA_BYTE);
        final String url =
                BlobstoreServiceFactory.getBlobstoreService().createUploadUrl("/tools/test",
                        options);
        System.out.println("URL : " + url);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().println("{\"url\":\"" + url + "\"}");
        response.flushBuffer();
    }

    private void downloadGcsFile(BlobKey blobKey) throws Exception {
        System.out.println("downloadGcsFile");

        BlobInfo blobInfo = new BlobInfoFactory().loadBlobInfo(blobKey);
        if (StringUtil.isEmpty(blobInfo.getContentType()) == false) {
            response.setContentType(blobInfo.getContentType());
        } else {
            response.setContentType("application/octet-stream");
        }
        response.setContentLength((int) blobInfo.getSize());
        blobstoreService.serve(blobKey, response);
    }

    private void receiveBlobServiceRedirect() throws Exception {
        System.out.println("receiveBlobServiceRedirect");

        final Map<String, List<BlobInfo>> infoListMap = blobstoreService.getBlobInfos(request);

        for (Entry<String, List<BlobInfo>> entry : infoListMap.entrySet()) {
            System.out.println(entry.getKey());

            for (BlobInfo info : entry.getValue()) {
                ImgInGCS content = new ImgInGCS(info);
                Datastore.put(content);
            }
        }
    }
    
}
