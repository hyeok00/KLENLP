package com.ssafy.trycatch.user.domain;

import com.ssafy.trycatch.common.domain.Company;
import com.ssafy.trycatch.feed.domain.Read;
import com.ssafy.trycatch.gamification.domain.MyBadge;
import com.ssafy.trycatch.gamification.domain.MyChallenge;
import com.ssafy.trycatch.qna.domain.Answer;
import com.ssafy.trycatch.qna.domain.Question;
import com.ssafy.trycatch.roadmap.domain.Roadmap;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@DynamicInsert
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 50)
    @Column(name = "github_node_id", length = 50)
    private String githubNodeId;

    @Size(max = 50)
    @Column(name = "username", length = 50)
    private String username;

    @Size(max = 50)
    @Column(name = "git_address", length = 50)
    private String gitAddress;

    @Size(max = 50)
    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "activated")
    private Boolean activated;

    @Size(max = 50)
    @Column(name = "calendar_mail", length = 50)
    private String calendarMail;

    @Column(name = "confirmation_code")
    private Integer confirmationCode;

    @Size(max = 200)
    @Column(name = "introduction", length = 200)
    private String introduction;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @NotNull
    @Column(name = "points", nullable = false)
    private Integer points;

    @Size(max = 100)
    @Column(name = "image_src", length = 100)
    private String imageSrc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "follower")
    @ToString.Exclude
    @Builder.Default
    private Set<Follow> followers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "followee")
    @ToString.Exclude
    @Builder.Default
    private Set<Follow> followees = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @Builder.Default
    private Set<Answer> answers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @Builder.Default
    private Set<Subscription> subscriptions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @Builder.Default
    private Set<Question> questions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @Builder.Default
    private Set<Read> reads = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @Builder.Default
    private Set<MyBadge> myBadges = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @Builder.Default
    private Set<MyChallenge> myChallenges = new LinkedHashSet<>();

    @OneToOne(mappedBy = "user")
    @ToString.Exclude
    private Roadmap roadmaps;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @Builder.Default
    private Set<History> histories = new LinkedHashSet<>();

    @Builder
    public User(
            Long id, String githubNodeId, String username, String gitAddress, String email, Boolean activated,
            String calendarMail, Integer confirmationCode, String introduction, LocalDate createdAt,
            Integer points,
            String imageSrc, Company company
    ) {
        this.id = id;
        this.githubNodeId = githubNodeId;
        this.username = username;
        this.gitAddress = gitAddress;
        this.email = email;
        this.activated = activated;
        this.calendarMail = calendarMail;
        this.confirmationCode = confirmationCode;
        this.introduction = introduction;
        this.createdAt = createdAt;
        this.points = points;
        this.imageSrc = imageSrc;
        this.company = company;
    }
}
