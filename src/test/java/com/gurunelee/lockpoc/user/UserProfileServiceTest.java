package com.gurunelee.lockpoc.user;

import com.gurunelee.lockpoc.user.entity.UserProfile;
import com.gurunelee.lockpoc.user.entity.UserProfileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserProfileServiceTest {
    @Autowired
    UserProfileService userProfileService;

    @Autowired
    UserProfileRepository userProfileRepository;

    ExecutorService executor = Executors.newFixedThreadPool(100);

    @AfterEach
    void tearDown() {
        System.out.println("tearDown");
        userProfileRepository.deleteAll();
    }

    @Test
    void create() {
        Long userKey = userProfileService.create(
                "dlckdgk4858@gmail.com",
                "gurunelee",
                "hello"
        );

        assertEquals(userProfileService.getUserProfile(userKey).getEmail(), "dlckdgk4858@gmail.com");
    }

    @Test
    @Description("@DynamicUpdate 없으면 update 쿼리가 모든 컬럼을 업데이트한다.")
    void updateWithDynamicUpdate() {
        // given
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Long userKey = userProfileService.create(
                "dlckdgk4858@gmail.com",
                "gurunelee",
                "hello"
        );

        // when
        UserProfile select1 = userProfileService.getUserProfile(userKey);
        UserProfile select2 = userProfileService.getUserProfile(userKey);

        executor.execute(() -> {
            select1.setName("gurunelee_mod1");
            userProfileRepository.save(select1);
            countDownLatch.countDown();
        });

        executor.execute(() -> {
            select2.setIntroduce("hello_mod2");
            userProfileRepository.save(select2);
            countDownLatch.countDown();
        });

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }

        // then
        UserProfile userProfile = userProfileService.getUserProfile(userKey);
        assertAll(
                () -> assertEquals("gurunelee_mod1", userProfile.getName()),
                () -> assertEquals("hello_mod2", userProfile.getIntroduce())
        );
    }
}