package com.codeit.todo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Complete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int completeId;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private String note;

    private String completeLink;
    private String completePic;
    @Column(nullable = false)
    private String completeStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id", nullable = false)
    private Todo todo;

    @Builder
    public Complete(LocalDate startDate, LocalDateTime createdAt, String note, String completeLink, String completeFile, String completePic, String completeStatus, Todo todo) {
        this.startDate = startDate;
        this.createdAt = createdAt;
        this.note = note;
        this.completeLink = completeLink;
        this.completePic = completePic;
        this.completeStatus = completeStatus;
        this.todo = todo;
    }

    public void update(String completePicUrl, String link, String note) {
        this.completePic = completePicUrl;
        this.completeLink = link;
        this.note = note;
        if (Objects.nonNull(completePicUrl) && !completePicUrl.isEmpty()) {
            this.completeStatus = "인증";
        }
    }
}
