package io.contek.invoker.security;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Immutable
public final class ApiKey {

  private final String id;
  private final String secret;
  private final Map<String, String> properties;

  private ApiKey(String id, String secret, Map<String, String> properties) {
    this.id = id;
    this.secret = secret;
    this.properties = Collections.unmodifiableMap(properties);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getId() {
    return id;
  }

  public String getSecret() {
    return secret;
  }

  public Map<String, String> getProperties() {
    return properties;
  }

  @NotThreadSafe
  public static final class Builder {

    private String id;
    private String secret;
    private Map<String, String> properties = new HashMap<>();

    public Builder setId(String id) {
      this.id = id;
      return this;
    }

    public Builder setSecret(String secret) {
      this.secret = secret;
      return this;
    }

    public Builder setProperties(Map<String, String> properties) {
      this.properties = properties;
      return this;
    }

    public Builder addProperty(String key, String value) {
      properties.put(key, value);
      return this;
    }

    public ApiKey build() {
      if (id == null) {
        throw new IllegalArgumentException("No API-Key ID specified");
      }
      if (secret == null) {
        throw new IllegalArgumentException("No API-Key secret specified");
      }
      return new ApiKey(id, secret, properties);
    }

    private Builder() {}
  }
}
