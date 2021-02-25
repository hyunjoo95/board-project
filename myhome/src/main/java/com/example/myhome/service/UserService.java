package com.example.myhome.service;


import com.example.myhome.model.Role;
import com.example.myhome.model.User;
import com.example.myhome.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user){
        String enCodePassword = passwordEncoder.encode((user.getPassword())); // 비밀번호 암호화
        user.setPassword(enCodePassword);
        user.setUsername(user.getUsername());
        user.setEnabled(true); // 활성화여부

        Role role = new Role();
        role.setId(1L); // 권한을 넣어줘야하는데 우선 하드코딩으로 넣어줌..

        user.getRoles().add(role);
        return userRepository.save(user);
    }

}
