package mobi.speakin.sdk.example;

import mobi.speakin.sdk.voiceprint_cloud.SecApiClient;
import mobi.speakin.sdk.voiceprint_cloud.SecStorageApi;
import mobi.speakin.sdk.voiceprint_cloud.SecVoiceprintApi;
import mobi.speakin.sdk.voiceprint_cloud.gen.model.*;
import static org.junit.Assert.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Example {

    private static String uploadFile(String fileName, SecStorageApi storageApi, String bucket) throws Exception {
        String filePath = Example.class.getClassLoader().getResource(fileName).getFile();
        File file = new File(filePath);

        CallUploadResponse resp = storageApi.upload(bucket, "wav", 0L, System.currentTimeMillis(), file);
        if(resp.getHasError()){
            throw new RuntimeException(resp.getErrorId());
        }
        return resp.getData().getKey();
    }


    public static void main(String[] args) throws Exception {
        // 加载api客户端 替换你的key
        SecApiClient apiClient = new SecApiClient(
                "your app access key",   // app access key
                "your app secret key",   // app secret key
                "your bucket access key",// bucket access key
                "your bucket secret key" // bucket secret key
        );
        
        SecStorageApi storageApi = new SecStorageApi(apiClient);
        SecVoiceprintApi api = new SecVoiceprintApi(apiClient);
        String[] userNames = {"customer_1", "customer_2"};
        // 替换成你的app name和bucket name
        String appName = "app-test";
        String bucketName = "bucket-test";


        // 上传所有注册音频文件，并把文件key储起来
        String[][] usersFiles = {
                {
                        "./audio-data/u1/01_16k.wav",
                        "./audio-data/u1/02_16k.wav",
                        "./audio-data/u1/03_16k.wav",
                },
                {
                        "./audio-data/u2/01_16k.wav",
                        "./audio-data/u2/02_16k.wav",
                        "./audio-data/u2/03_16k.wav",
                },
        };
        ArrayList<ArrayList<String> > usersFilesKey = new ArrayList<>();
        for (String[] files : usersFiles){
            ArrayList<String> keys = new ArrayList<>();
            for (String fileName: files){
                keys.add(uploadFile(fileName, storageApi, bucketName));
            }
            usersFilesKey.add(keys);
        }

        // 注册所有用户
        for(int i = 0;i < userNames.length;i++) {
            VoiceprintRegisterRequest registerReq = new VoiceprintRegisterRequest();
            registerReq.setAppName(appName);
            registerReq.setUrls(usersFilesKey.get(i));
            registerReq.setUnionID(userNames[i]);
            registerReq.setTimestamp(System.currentTimeMillis());
            registerReq.setSamplingRate("16k");
            registerReq.setReplace(true);
            RespVoiceprintRegisterResponse registerRsp = api.register(registerReq);
            if(registerRsp.getHasError()){
                throw new RuntimeException(registerRsp.getErrorId());
            }
        }

        // 查询用户是否存在
        for (String userName : userNames) {
            VoiceprintQueryRequest queryReq = new VoiceprintQueryRequest();
            queryReq.setAppName(appName);
            queryReq.setUnionID(userName);
            queryReq.setTimestamp(System.currentTimeMillis());
            RespVoiceprintQueryResponse queryRsp = api.query(queryReq);
            if (queryRsp.getHasError()) {
                throw new RuntimeException(queryRsp.getErrorId());
            }
            System.out.printf("user %s register: %s\n", userName, queryRsp.getData().getIsRegister());
        }

        // 验证用户音频
        String[] checkFiles = {
                "./audio-data/u1/04_16k.wav",
                "./audio-data/u2/04_16k.wav",
        };
        String[] checkFilesKeys = {
                uploadFile(checkFiles[0], storageApi, bucketName),
                uploadFile(checkFiles[1], storageApi, bucketName),
        };
        for(String name : userNames) {
            for(int i = 0;i < checkFilesKeys.length;i++) {
                VoiceprintVerifyRequest verifyReq = new VoiceprintVerifyRequest();
                verifyReq.setAppName(appName);
                verifyReq.setUnionID(name);
                verifyReq.setTimestamp(System.currentTimeMillis());
                verifyReq.setUrl(checkFilesKeys[i]);
                verifyReq.setSamplingRate("16k");
                RespVoiceprintVerifyResponse verifyRsp = api.verify(verifyReq);
                if(verifyRsp.getHasError()){
                    throw new RuntimeException(verifyRsp.getErrorId());
                }
                System.out.printf("user %s verify file %s score %s\n", name, checkFiles[i], verifyRsp.getData().getScore());
            }
        }

        // 1比n 验证
        for(int i = 0;i < checkFilesKeys.length;i++) {
            Voiceprint1tonVerifyRequest verifyMultipleReq = new Voiceprint1tonVerifyRequest();
            verifyMultipleReq.setAppName(appName);
            verifyMultipleReq.setUnionIDs(Arrays.asList(userNames));
            verifyMultipleReq.setTimestamp(System.currentTimeMillis());
            verifyMultipleReq.setUrl(checkFilesKeys[i]);
            verifyMultipleReq.setSamplingRate("16k");
            RespVoiceprint1tonVerifyResponse verifyMultipleRsp = api.verify1ton(verifyMultipleReq);
            if(verifyMultipleRsp.getHasError()){
                throw new RuntimeException(verifyMultipleRsp.getErrorId());
            }
            System.out.printf("verify file %s match user %s score: %s\n", checkFiles[i], verifyMultipleRsp.getData().getUnionID(), verifyMultipleRsp.getData().getScore());
        }
        // 下载上传的音频文件
        {
            File file = storageApi.download(bucketName, checkFilesKeys[0]);
            System.out.printf("file at %s",file.toURL().toString());
        }

    }
}
