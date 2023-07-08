package com.example.jpa.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "file_model")
public class FileModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fileId;
    private String fileName;
    private String path;
    private String ext;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
