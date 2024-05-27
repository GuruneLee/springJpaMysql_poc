package com.gurunelee.lockpoc.user;

import com.gurunelee.lockpoc.user.entity.UserProfile;
import com.gurunelee.lockpoc.user.entity.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;

    public UserProfile getUserProfile(Long userKey) {
        return userProfileRepository.findById(userKey).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public Long create(String email, String name, String introduce) {
        UserProfile userProfile = new UserProfile(email, name, introduce);

        return userProfileRepository.save(userProfile).getUserKey();
    }

    @Transactional
    public void update(Long userKey, String name, String introduce) {
        UserProfile userProfile = getUserProfile(userKey);
        userProfile.update(name, introduce);
    }
}
