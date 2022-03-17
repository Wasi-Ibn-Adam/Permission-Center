package com.wasitech.permissioncenter.java.com.wasitech.assist.testing;
/// 0309 8784229, 0344-4719618

import android.annotation.SuppressLint;
import android.media.MediaCodec;
import android.os.Environment;
import android.util.Base64;

import com.wasitech.basics.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt {

    private SecretKeySpec getKey() throws Exception{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String pswd="Assist";// ProcessApp.getCurUser().getUid();
        byte[] bytes = pswd.getBytes(StandardCharsets.UTF_8);
        digest.digest(bytes, 0, bytes.length);
        byte []key=digest.digest();
        return new SecretKeySpec(key,"AES");
    }
    public String encryptText(String text) throws Exception{
        SecretKeySpec spec= getKey();
        @SuppressLint("GetInstance") Cipher c=Cipher.getInstance("AES");
        c.init(Cipher.ENCRYPT_MODE,spec);
        byte[]b=c.doFinal(text.getBytes());
        return Base64.encodeToString(b,Base64.DEFAULT);
    }
    public String decryptText(String text) throws Exception{
        SecretKeySpec spec= getKey();
        @SuppressLint("GetInstance") Cipher c=Cipher.getInstance("AES");
        c.init(Cipher.DECRYPT_MODE,spec);
        byte[]b=c.doFinal(text.getBytes());
        return Base64.encodeToString(b,Base64.DEFAULT);
    }
    public File encrypt(File file){
        File outputFile= Storage.CreateDataFile(Storage.APP,"db save",".aes");
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, getKey());

            FileInputStream inputStream = new FileInputStream(file);
            byte[] inputBytes = new byte[(int) file.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return file;
    }
    public File decrypt(String path){
        File in= new File(path);
        File out= Storage.CreateDataFile(Storage.APP,"db unsafe",".txt");
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, getKey());

            FileInputStream inputStream = new FileInputStream(in);
            byte[] inputBytes = new byte[(int) in.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(out);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return out;
    }


    private static void doCryptoInAES(int cipherMode, String key, File inputFile, File outputFile){
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(cipherMode, secretKey);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void doCryptoInBlowFish(int cipherMode,String KEY,File inputFile,File outputFile) {
        String ALGORITHM = "Blowfish";
        String MODE = "Blowfish/CBC/PKCS5Padding";
        String IV = "!a3edr45";

        try {
            Key secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(MODE);
            cipher.init(cipherMode, secretKey, new IvParameterSpec(IV.getBytes()));

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();

        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }
    public void encryptFileinAES(){
        final File saveFile = new File(Environment.getExternalStorageDirectory(), "intruder.jpg");
        doCryptoInAES(Cipher.ENCRYPT_MODE,"ak$#54%^RtF%g^Hf",saveFile,saveFile);
        doCryptoInAES(Cipher.DECRYPT_MODE,"ak$#54%^RtF%g^Hf",saveFile,saveFile);
    }
    public static void encrypt(File in,File out){
        doCryptoInAES(Cipher.ENCRYPT_MODE,"KlMnOpQrStUvWxYz",in,out);
    }
    public static void decrypt(File in,File out){
        doCryptoInAES(Cipher.DECRYPT_MODE,"KlMnOpQrStUvWxYz",in,out);
    }

    public void encryptFileinBlowfish() throws MediaCodec.CryptoException {
        final File saveFile = new File(Environment.getExternalStorageDirectory(), "intruder.jpg");
        doCryptoInBlowFish(Cipher.ENCRYPT_MODE,"SAMPLEKEY",saveFile,saveFile);
        doCryptoInBlowFish(Cipher.DECRYPT_MODE,"SAMPLEKEY",saveFile,saveFile);
    }


}