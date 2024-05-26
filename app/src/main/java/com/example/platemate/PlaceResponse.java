package com.example.platemate;

import java.util.List;

public class PlaceResponse {
    private List<Post> documents;

    public List<Post> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Post> documents) {
        this.documents = documents;
    }
}
