package es.goda87.marvelcharacterdisplay.data;

import es.goda87.marvelcharacterdisplay.data.characters.model.Character;

/**
 * Created by goda87 on 20.01.18.
 */

public class DataModel {
    private int id;
    private String name, description;
    private String imagePath, imageExtension;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageExtension() {
        return imageExtension;
    }

    public void setImageExtension(String imageExtension) {
        this.imageExtension = imageExtension;
    }

    public String thumbnailPath() {
        String imgUrl = getImagePath();
        if (getImageExtension() != null) {
            imgUrl += "/portrait_medium." + getImageExtension();
        }
        return imgUrl;
    }

    public String bigPicturePath() {
        String imgUrl = getImagePath();
        if (getImageExtension() != null) {
            imgUrl += "/portrait_uncanny." + getImageExtension();
        }
        return imgUrl;
    }

    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", imageExtension='" + imageExtension + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataModel dataModel = (DataModel) o;

        return id == dataModel.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
