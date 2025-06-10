package edu.uptc.swii.login_service.application.services;

import edu.uptc.swii.login_service.domain.model.Login;
import edu.uptc.swii.login_service.domain.ports.in.LoginUseCase;
import edu.uptc.swii.login_service.domain.ports.out.LoginRepositoryPort;
import edu.uptc.swii.login_service.infrastructure.config.JwtUtil;
import jakarta.transaction.Transactional;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LoginService implements LoginUseCase {

    private final LoginRepositoryPort loginRepositoryPort;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public LoginService(LoginRepositoryPort loginRepositoryPort, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.loginRepositoryPort = loginRepositoryPort;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(Long userId, String password) {
        Optional<Login> loginOptional = loginRepositoryPort.findByUserId(userId);

        if (loginOptional.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Login login = loginOptional.get();

        if (!passwordEncoder.matches(password, login.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // Generas el JWT
        return jwtUtil.generateToken(userId);
    }

    @Override
    public void register(Long userId, String password) {
        Optional<Login> existing = loginRepositoryPort.findByUserId(userId);
        if (existing.isPresent()) {
            throw new IllegalArgumentException("El usuario ya existe");
        }

        Login newLogin = new Login(userId, password);
        loginRepositoryPort.save(newLogin);
    }
}