package ru.itis.bvb.mongo.template.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Text {
    private String _id;
    private String title;
    private String author;
    private boolean isImportant;
    private String text;
}
