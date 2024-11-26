package com.develop.saas.model;

import com.develop.saas.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user_social_media_accounts")
public class UserSocialMediaAccount extends BaseEntity {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialMediaType socialMediaType;

    @Column(nullable = false, length = 1024)
    private String link;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
