package com.example.bank_account_assessment.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public abstract class BaseMapper {
    @Autowired
    private ApplicationContext applicationContext;
    private final Map<Class<?>, Object> mapperCache = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    protected <T> T getMapper(Class<T> clazz) {
        return (T) mapperCache.computeIfAbsent(clazz, applicationContext::getBean);
    }

    protected BankAccountMapper getBankAccountMapper() {
        return getMapper(BankAccountMapper.class);
    }

}
