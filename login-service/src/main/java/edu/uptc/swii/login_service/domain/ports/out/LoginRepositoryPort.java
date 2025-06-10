package edu.uptc.swii.login_service.domain.ports.out;

import java.util.Optional;

import edu.uptc.swii.login_service.domain.model.Login;

public interface LoginRepositoryPort {
        Optional<Login> findByUserId(Long userId);
        void save(Login newLogin);
}
