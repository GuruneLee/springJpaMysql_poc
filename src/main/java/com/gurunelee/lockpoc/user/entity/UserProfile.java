package com.gurunelee.lockpoc.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NaturalId;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "usr_user")
@DynamicUpdate
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_key")
    private Long userKey = 0L;

    @NaturalId
    @Column(name = "email", nullable = false, length = 255, unique = true)
    private String email;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "introduce", length = 4000)
    private String introduce;

    public UserProfile(String email, String name, String introduce) {
        this.email = email;
        this.name = name;
        this.introduce = introduce;
    }

    public void update(String name, String introduce) {
        this.name = name;
        this.introduce = introduce;
    }
}
