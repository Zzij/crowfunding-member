package com.zz.crow.util;

import com.zz.crow.constant.CrowdConstant;
import com.zz.crow.response.ResultEntity;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

public class CrowUtil {

    /**
     * 模拟验证码发送
     * @param phone  接收手机号
     * @param code   验证码
     * @return
     */

    private static Random random = new Random();

    public static ResultEntity<String> sendMessage(String phone){

        StringBuilder sb = new StringBuilder(6);

        for (int i = 0; i <  6; i++){
            sb.append(random.nextInt(10));
        }

        return ResultEntity.successWithData(sb.toString());
    }

    public static String md5(String source) {
        if (source == null || source.length() == 0) {
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_NULL);
        }

        String algorithm = "md5";

        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            byte[] digest = messageDigest.digest(source.getBytes());
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum, digest);
            //16进制将数组转为字符串
            int radix = 16;
            String encoder = bigInteger.toString(radix);
            return encoder;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultEntity<String> uploadPicture(String path, InputStream inputStream, String filename) {
        String uuid = UUID.randomUUID().toString();

        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }

        StringBuilder sb = new StringBuilder();
        sb.append(path).append(File.separator)
                .append(uuid).append(filename);
        File pictureFile = new File(sb.toString());

        byte[] bytes = new byte[1024];
        try (FileOutputStream fileOutputStream = new FileOutputStream(pictureFile);){
            while(inputStream.read(bytes) != -1){
                fileOutputStream.write(bytes);
            }
            return ResultEntity.successWithData(sb.toString());
        }catch (Exception e){
            System.out.println(e);
            return ResultEntity.failed(e.getMessage());
        }



    }
}
