package com.example.pruebasrecordatorios;

import android.content.Context;
import android.util.Log;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
/**no se usa**/
public class ClassCypher {
    public static byte[] cifrar(String texto) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] byteSecretKey = secretKey.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(byteSecretKey, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] mensajeEncriptado = cipher.doFinal(texto.getBytes());
        Log.e("ZZZ",new String(mensajeEncriptado));
        return mensajeEncriptado;
    }
    public static String desCifrar(byte[] textoCifrado) throws Exception{
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] byteSecretKey = secretKey.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(byteSecretKey, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);
        byte[] mensajeDesEncriptado = cipher.doFinal(textoCifrado);
        return new String(mensajeDesEncriptado);
    }
}
