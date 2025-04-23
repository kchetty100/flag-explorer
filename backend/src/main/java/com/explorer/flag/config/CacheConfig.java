package com.explorer.flag.config;

import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

  @Value("${app.cache.countries.ttl-minutes}")
  private int cacheTtlMinutes;

  @Value("${app.cache.countries.max-size}")
  private int cacheMaxSize;

  @Bean
  public Caffeine<Object, Object> caffeineConfig() {
    return Caffeine.newBuilder()
        .expireAfterWrite(cacheTtlMinutes, TimeUnit.MINUTES)
        .maximumSize(cacheMaxSize);
  }

  @Bean
  public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
    CaffeineCacheManager manager = new CaffeineCacheManager("countries", "countryDetails");
    manager.setCaffeine(caffeine);
    return manager;
  }
}