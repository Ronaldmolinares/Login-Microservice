package edu.uptc.swii.login_service.infrastructure.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import edu.uptc.swii.login_service.domain.model.Login;
import edu.uptc.swii.login_service.domain.ports.out.LoginRepositoryPort;
import edu.uptc.swii.login_service.infrastructure.entities.LoginEntity;

@Repository
public class LoginRepositoryAdapter implements LoginRepositoryPort {

    @Autowired
    private JpaLoginRepository jpaLoginRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<Login> findByUserId(Long userId) {
        return jpaLoginRepository.findByUserId(userId)
                .map(entity -> new Login(entity.getUserId(), entity.getPassword()));
    }

    @Override
    public void save(Login newLogin) {
        LoginEntity loginEntity = new LoginEntity();
        loginEntity.setUserId(newLogin.getUserId());
        loginEntity.setPassword(passwordEncoder.encode(newLogin.getPassword()));
        jpaLoginRepository.save(loginEntity);
    }
}
