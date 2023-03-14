package com.cybersoft.osahaneat.service.imp;

import org.springframework.stereotype.Service;

@Service
public interface LoginServiceImp {
    boolean login(String username, String password);
}
