package com.github.stasmalykhin.botHH.service;

import com.github.stasmalykhin.botHH.entity.AppUser;
import com.github.stasmalykhin.botHH.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void saveUser(AppUser user) {
        userRepository.save(user);
    }

    public List<AppUser> findAllUser() {
        return userRepository.findAll();
    }

    public boolean checkIfUserIsNew(Long id) {
        Optional<AppUser> user = userRepository.findByTelegramUserId(id);
        return user.isEmpty();
    }

    public void updateDateOfPublicationAtUser(AppUser user, Date dateOfPublication) {
        user.setDateOfPublicationOfLastVacancy(dateOfPublication);
        userRepository.save(user);
    }
}
