package com.example.project_guide.ui.topicItem;

public class Chapter {
    private String title;
    private int icon;
    private String description;

    public Chapter(String title, int icon, String description) {
        this.title = title;
        this.icon = icon;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
