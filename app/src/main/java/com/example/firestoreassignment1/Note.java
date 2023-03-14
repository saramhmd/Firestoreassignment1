package com.example.firestoreassignment1;

class Note {
    String id;
    String note;
    private  Note(){}
    Note(String id, String note) {
        this.id = id;
        this.note = note;

    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return note;
    }

}
