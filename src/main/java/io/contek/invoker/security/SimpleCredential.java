package io.contek.invoker.security;

import is.fm.util.Encoder;

import javax.annotation.concurrent.Immutable;
import javax.crypto.Mac;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@Immutable
public final class SimpleCredential implements ICredential {

  private final ApiKey apiKey;
  private final SecretKeyAlgorithm algorithm;
  private final Encoder encoding;
  private final Mac mac;

  SimpleCredential(ApiKey apiKey, SecretKeyAlgorithm algorithm, Encoder encoding) {
    this.apiKey = apiKey;
    this.algorithm = algorithm;
    this.encoding = encoding;
    mac = algorithm.setupMac(apiKey.getSecret());
  }

  @Override
  public boolean isAnonymous() {
    return false;
  }

  @Override
  public String getApiKeyId() {
    return apiKey.getId();
  }

  @Override
  public SecretKeyAlgorithm getAlgorithm() {
    return algorithm;
  }

  @Override
  public Map<String, String> getProperties() {
    return apiKey.getProperties();
  }

  @Override
  public String sign(String payload) {
    return encoding.encode(mac.doFinal(payload.getBytes(UTF_8)));
  }
}
