package com.test.config;


import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * redis配置类
 * 
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
	/*@Bean
	@SuppressWarnings("all")
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(factory);
		//FastJsonRedisSerializer fastjsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		// key采用String的序列化方式
		template.setKeySerializer(stringRedisSerializer);
		// hash的key也采用String的序列化方式
		template.setHashKeySerializer(stringRedisSerializer);
		// value序列化方式采用fastjson
		template.setValueSerializer(jackson2JsonRedisSerializer);
		// hash的value序列化方式采用fastjson
		template.setHashValueSerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();
		return template;
	}*/
	
	/*@Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				// TODO Auto-generated method stub
				StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
			}
        };
    }*/

	//在SpringBoot 1.0中使用RedisTemplate即可实例化一个RedisCacheManager
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
    	//在SpringBoot 2.0中删除了这个构造器
    	// 通过RedisCacheManager的静态方法create：
    	// 这样产生的cacheManager只是使用Spring提供的默认配置
    	RedisCacheManager cacheManager = RedisCacheManager.create(factory);
        //设置缓存过期时间	RedisTemplate redisTemplate
    	//Springboot 2.* 之后，该方法无法使用
    	/*RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
        cacheManager.setDefaultExpiration(60);//秒*/
		return cacheManager;
    }
    
    //通过Spring提供的RedisCacheConfiguration类，构造一个自己的redis配置类，
    //从该配置类中可以设置一些初始化的缓存命名空间、及对应的默认过期时间等属性，
    //再利用RedisCacheManager中的builder.build()的方式生成cacheManager：
	// ***测试 设置时间无效
    /*@Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
    	// 生成一个默认配置，通过config对象即可对缓存进行自定义配置
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        
        // 设置缓存的默认过期时间，也是使用Duration设置
        config = config.entryTtl(Duration.ofMinutes(1))
                .disableCachingNullValues();     // 不缓存空值

        // 设置一个初始化的缓存空间set集合
        Set<String> cacheNames =  new HashSet<>();
        cacheNames.add("my-redis-cache1");
        cacheNames.add("my-redis-cache2");

        // 对每个缓存空间应用不同的配置
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put("my-redis-cache1", config);
        configMap.put("my-redis-cache2", config.entryTtl(Duration.ofSeconds(120)));
        
		// 使用自定义的缓存配置初始化一个cacheManager
        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
                .initialCacheNames(cacheNames)  // 注意这两句的调用顺序，一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
                .withInitialCacheConfigurations(configMap)
                .build();
        RedisCacheManager cacheManager = RedisCacheManager.builder(RedisCacheWriter.lockingRedisCacheWriter
                (factory)).cacheDefaults(config).withInitialCacheConfigurations(java.util.Collections.singletonMap
                ("test", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(120L))
                        .disableCachingNullValues())).transactionAware().build();
        return cacheManager;
    }*/
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        /*Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);*/
        FastJsonRedisSerializer fastjsonRedisSerializer = new FastJsonRedisSerializer(Object.class);
        template.setValueSerializer(fastjsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
