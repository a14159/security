package io.contek.invoker.security;

import removing.dependencies.GuavaBaseEncoding;

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public abstract class BaseCredentialFactory implements ICredentialFactory {

  private final SecretKeyAlgorithm algorithm;
  private final GuavaBaseEncoding encoding;

  protected BaseCredentialFactory(SecretKeyAlgorithm algorithm, GuavaBaseEncoding encoding) {
    this.algorithm = algorithm;
    this.encoding = encoding;
  }

  public final ICredential create(ApiKey apiKey) {
    return new SimpleCredential(apiKey, algorithm, encoding);
  }
}
