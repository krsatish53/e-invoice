package com.invoice.einvoice.zatca;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;


public class ECDSAUtil {
   private static final String SIGNALGORITHMS = "SHA256withECDSA";
   private static final String ALGORITHM = "EC";
   private static final String SECP256K1 = "secp256k1";

   public static void main(String[] args) throws Exception {
      KeyPair keyPair1 = getKeyPair();
      PublicKey publicKey1 = keyPair1.getPublic();
      PrivateKey privateKey1 = keyPair1.getPrivate();
      String publicKey = HexUtil.encodeHexString(publicKey1.getEncoded());
      String privateKey = HexUtil.encodeHexString(privateKey1.getEncoded());
      PrivateKey privateKey2 = getPrivateKey(privateKey);
      PublicKey publicKey2 = getPublicKey(publicKey);
      String data = "New Oriental Future";
   }

   public static byte[] signECDSA(PrivateKey privateKey, String data) {
      try {
         Signature signature = Signature.getInstance("SHA256withECDSA");
         signature.initSign(privateKey);
         signature.update(data.getBytes());
         byte[] sign = signature.sign();
         return sign;
      } catch (Exception var4) {
         return null;
      }
   }

   public static boolean verifyECDSA(PublicKey publicKey, String signed, String data) {
      try {
         Signature signature = Signature.getInstance("SHA256withECDSA");
         signature.initVerify(publicKey);
         signature.update(data.getBytes());
         byte[] hex = Base64.getDecoder().decode(signed);
         boolean bool = signature.verify(hex);
         return bool;
      } catch (Exception var6) {
         return false;
      }
   }

   public static PrivateKey getPrivateKey(String key) throws Exception {
      byte[] bytes = HexUtil.decode(key);
      PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
      KeyFactory keyFactory = KeyFactory.getInstance("EC");
      return keyFactory.generatePrivate(keySpec);
   }

   public static PublicKey getPublicKey(String key) throws Exception {
      byte[] bytes = HexUtil.decode(key);
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
      KeyFactory keyFactory = KeyFactory.getInstance("EC");
      return keyFactory.generatePublic(keySpec);
   }

   public static KeyPair getKeyPair() throws Exception {
      new ECGenParameterSpec("secp256k1");
      KeyPairGenerator kf = KeyPairGenerator.getInstance("EC");
      KeyPair keyPair = kf.generateKeyPair();
      return keyPair;
   }

   public static byte[] extractR(String digitalSignature) throws Exception {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(Base64.getDecoder().decode(digitalSignature.getBytes(StandardCharsets.UTF_8)));
      return Arrays.copyOfRange(hash, 0, 32);
   }

   public static byte[] extractS(String digitalSignature) throws Exception {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(Base64.getDecoder().decode(digitalSignature.getBytes(StandardCharsets.UTF_8)));
      return Arrays.copyOfRange(hash, 32, 64);
   }
}
