package ru.itis.bvb.mongo.template.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    String _id;
    String name;
    Integer age;
    ArrayList<String> hobby;
}
