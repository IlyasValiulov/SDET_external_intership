package pojo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NavigationPojo {
    private int id;
    private String date;
    private String date_gmt;
    private String modified;
    private String modified_gmt;
    private String guid;
    private String slug;
    private String status;
    private String type;
    private String password;
    private String title;
    private String content;

    public NavigationPojo(int id, String date, String date_gmt, String modified, String modified_gmt, String guid,
                          String slug, String status, String type, String password, String title, String content) {
        this.id = id;
        this.date = date;
        this.date_gmt = date_gmt;
        this.modified = modified;
        this.modified_gmt = modified_gmt;
        this.guid = guid;
        this.slug = slug;
        this.status = status;
        this.type = type;
        this.password = password;
        this.title = title;
        this.content = content;
    }

    public NavigationPojo() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NavigationPojo other = (NavigationPojo) o;
        return id == other.id &&
                date.equals(other.date) &&
                date_gmt.equals(other.date_gmt) &&
                modified.equals(other.modified) &&
                modified_gmt.equals(other.modified_gmt) &&
                guid.equals(other.guid) &&
                slug.equals(other.slug) &&
                status.equals(other.status) &&
                type.equals(other.type) &&
                password.equals(other.password) &&
                title.equals(other.title) &&
                content.equals(other.content);
    }
}
