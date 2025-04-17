package io.contek.invoker.security;

import removing.dependencies.Encoder;

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public abstract class BaseCredentialFactory implements ICredentialFactory {

  private final SecretKeyAlgorithm algorithm;
  private final Encoder encoding;

  protected BaseCredentialFactory(SecretKeyAlgorithm algorithm, Encoder encoding) {
    this.algorithm = algorithm;
    this.encoding = encoding;
  }

  public final ICredential create(ApiKey apiKey) {
    return new SimpleCredential(apiKey, algorithm, encoding);
  }
}
