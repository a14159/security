package io.contek.invoker.security;

import javax.annotation.concurrent.Immutable;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.UTF_8;

@Immutable
public enum SecretKeyAlgorithm {
  HMAC_MD5("HmacMD5"),
  HMAC_SHA256("HmacSHA256"),
  HMAC_SHA384("HmacSHA384"),
  HMAC_SHA512("HmacSHA512"),
  ;

  private final String algorithmName;
  private final Mac mac;

  SecretKeyAlgorithm(String algorithmName) {
    this.algorithmName = algorithmName;
      try {
        mac = Mac.getInstance(this.algorithmName);
      } catch (NoSuchAlgorithmException e) {
        throw new IllegalStateException(e);
      }
  }

  public String getAlgorithmName() {
    return algorithmName;
  }

  public Mac setupMac(String secret) {
    return setupMac(secret.getBytes(UTF_8));
  }

  public Mac setupMac(byte[] secret) {
    try {
      Key spec = new SecretKeySpec(secret, algorithmName);
      mac.init(spec);
      return mac;
    } catch (InvalidKeyException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
