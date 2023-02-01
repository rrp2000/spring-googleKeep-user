package com.google.userKeep.userKeep.model;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
@Document(collection = "folder")
public class FolderModel {

    @Id
    private String id;

    @NotBlank(message = "Enter a valid userId")
    private String userId;

    @NotBlank(message = "Enter a valid folder name")
    private String name;

    private List<String> notes;
}
