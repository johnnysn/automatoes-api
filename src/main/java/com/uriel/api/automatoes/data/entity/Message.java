package com.uriel.api.automatoes.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id"})
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(length = 4000)
    private String content;

    private Role role;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatId")
    private Chat chat;

    @RequiredArgsConstructor
    public enum Role {
        USER("user"),
        ASSISTANT("assistant"),
        SYSTEM("system"),
        FUNCTION("function");

        @Getter
        private final String value;
    }

}
