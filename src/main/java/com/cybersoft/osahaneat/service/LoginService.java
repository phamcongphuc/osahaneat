package com.cybersoft.osahaneat.service;

import com.cybersoft.osahaneat.entity.Users;
import com.cybersoft.osahaneat.repository.UserRepository;
import com.cybersoft.osahaneat.service.imp.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService implements LoginServiceImp {
    @Autowired
    UserRepository userRepository;

    @Override
    public boolean login(String username, String password) {

        List<Users> list = userRepository.findByEmailAndPassword(username, password);

        return list.size() > 0;
    }
}
