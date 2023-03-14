package com.cybersoft.osahaneat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {
    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private int port;

    // taoj connect kết nối redis
    @Bean
    public LettuceConnectionFactory redisConnection(){
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(host, port);
        configuration.setHostName(host);
        configuration.setPort(port);
        /*configuration.setDatabase(0); chỉ định database trong redis
        configuration.setUsername("");  thông tin tài khoản đăng nhập redis
        configuration.setPassword(""); mật khẩu ứng tài khoản    */
        return new LettuceConnectionFactory(configuration);
    }

    // tạo template để thực hiện thêm xóa s
    @Bean
    @Primary
    public RedisTemplate<Object, Object> redisTemplate(LettuceConnectionFactory redisConnection){
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        System.out.println("check port: " + redisConnection.getHostName());
        redisTemplate.setConnectionFactory(redisConnection);

        return redisTemplate;
    }


}
