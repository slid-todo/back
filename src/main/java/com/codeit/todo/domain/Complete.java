package com.codeit.todo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Complete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int completeId;

    @Column(nullable = false)
    private LocalDate completedDate;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private String note;

    private String completeLink;
    private String completeFile;
    private String completePic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id", nullable = false)
    private Todo todo;

    @Builder
    public Complete(LocalDate completedDate, LocalDateTime createdAt, String note, String completeLink, String completeFile, String completePic, Todo todo) {
        this.completedDate = completedDate;
        this.createdAt = createdAt;
        this.note = note;
        this.completeLink = completeLink;
        this.completeFile = completeFile;
        this.completePic = completePic;
        this.todo = todo;
    }

    public void update(String completePicUrl, String completeFileUrl, String link, String note) {
        this.completePic = completePicUrl;
        this.completeFile = completeFileUrl;
        this.completeLink = link;
        this.note = note;
    }
}
