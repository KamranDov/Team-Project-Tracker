//package com.crocusoft.teamprojecttracker.service;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.redisson.api.RBucket;
//import org.redisson.api.RedissonClient;
//import org.springframework.stereotype.Service;
//
//import java.time.Duration;
//import java.time.temporal.ChronoUnit;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class RedisService {
//
//    private final RedissonClient redissonClient;
//
//    public <T> void set(String key, T value, long seconds){
//        RBucket<T> testKey = redissonClient.getBucket(key);
//        testKey.set(value, Duration.of(seconds, ChronoUnit.SECONDS));
//    }
//    private <T> T get (String key){
//        RBucket<T> testKey2 = redissonClient.getBucket(key);
//        return testKey2.get();
//    }
//}
