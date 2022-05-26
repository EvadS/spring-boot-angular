package com.aws.application.domain;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Image {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "preview_image_path")
    private String previewImagePath;

    @Column(name = "image_file_name")
    private String imageFileName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    @JsonManagedReference
    private Set<ImageLabel> labels;
}