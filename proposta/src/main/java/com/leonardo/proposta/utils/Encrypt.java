package com.leonardo.proposta.utils;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;


public class Encrypt {


    public static String encrypt(String documento) {
        TextEncryptor encryptor = Encryptors.text("senha", "D807566B27F78645");
        if (documento == null || documento.equals("")) {
            return "";
        }

        return encryptor.encrypt(documento);

    }

    public static String decrypt(String documento) {
        TextEncryptor encryptor = Encryptors.text("senha", "D807566B27F78645");
        if (documento == null || documento.equals("")) {
            return "";
        }
        return encryptor.decrypt(documento);
    }
}



