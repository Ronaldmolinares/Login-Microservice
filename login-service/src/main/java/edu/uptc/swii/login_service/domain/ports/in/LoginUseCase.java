package edu.uptc.swii.login_service.domain.ports.in;

public interface LoginUseCase {
    String login(Long userId, String password);
    void register(Long userId, String password);
}
