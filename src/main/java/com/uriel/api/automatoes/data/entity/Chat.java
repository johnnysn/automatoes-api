package com.uriel.api.automatoes.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id"})
public class Chat {

    @Id
    private String id;

    @Column(nullable = false)
    private LocalDateTime start;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Message> messages;

}
