package com.jt.sharemapapi.utils;


import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Arrays;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class ApiUtils {
    private static final String TAG = ApiUtils.class.getSimpleName();

    public static X509TrustManager getX509TrustManager(Context context, InputStream inputStream) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            TraceLog.d( "X.509 CertificateFactory init.");

//            InputStream inputStream = GetCaStream(context);
            keyStore.setCertificateEntry("ca", certificateFactory.generateCertificate(inputStream));
            inputStream.close();

            TraceLog.d( "Certificate init.");

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            TraceLog.d( "Certificate add into TrustManagerFactory.");

            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            return (X509TrustManager) trustManagers[0];
        } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
            e.printStackTrace();
            TraceLog.d( "X509TrustManager is null.", e);
            return null;
        }
    }

    public  static InputStream getCaStream(Context context){

        InputStream inputStream = null;
        try {
            inputStream = context.getResources().getAssets().open("test.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }


        return inputStream;
    }


    public static String getUUID() {
        return  java.util.UUID.randomUUID().toString();
    }

    public static SSLSocketFactory getSSLSocketFactory(TrustManager trustManager) {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
            TraceLog.d( "TLSv1.2 SSLContext init.");
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
            TraceLog.d( "TLSv1.2 SSLContext is null.", e);
            return null;
        }
    }

    public static String packageSentStringToSign(String env, String version, String key, String method,
                                                 String accept, String contentLanguage, String contentMD5,
                                                 String contentType, String timestamp) {

        return (env == null ? "" : env) + "\n" +
                (version == null ? "" : version) + "\n" +
                (key == null ? "" : key) + "\n" +
                (method == null ? "" : method) + "\n" +
                (accept == null ? "" : accept) + "\n" +
                (contentLanguage == null ? "" : contentLanguage) + "\n" +
                (contentMD5 == null ? "" : contentMD5) + "\n" +
                (contentType == null ? "" : contentType) + "\n" +
                (timestamp == null ? "" : timestamp) + "\n";
    }

    public static String packageReceivedStringToSign(String env, String version, String key,
                                                     String contentMD5, String timestamp) {

        return (env == null ? "" : env) + "\n" +
                (version == null ? "" : version) + "\n" +
                (key == null ? "" : key) + "\n" +
                (contentMD5 == null ? "" : contentMD5) + "\n" +
                (timestamp == null ? "" : timestamp) + "\n";
    }
}
