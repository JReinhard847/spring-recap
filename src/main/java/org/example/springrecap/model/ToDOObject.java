package org.example.springrecap.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.With;

@RequiredArgsConstructor
@Data
@AllArgsConstructor
public class ToDOObject {
    @With
    String description;
    ToDOStatus status;
    String id;
}

