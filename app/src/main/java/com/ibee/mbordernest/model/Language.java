package com.ibee.mbordernest.model;

public class Language {
    String name = "";
    String description = "";
    Boolean expand = false;

    public Language(String name, String description, Boolean expand) {
        this.name = name;
        this.description = description;
        this.expand = expand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getExpand() {
        return expand;
    }

    public void setExpand(Boolean expand) {
        this.expand = expand;
    }
}
