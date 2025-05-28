package pojo;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TagPojo {
    private int id;
    private String name;
    private String description;
    private int count;
    private String link;
    private String slug;
    private String taxonomy;
    private List<String> meta;

    public TagPojo(int id, String name, String description, int count, String link, String slug, String taxonomy, List<String> meta) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.count = count;
        this.link = link;
        this.slug = slug;
        this.taxonomy = taxonomy;
        this.meta = meta;
    }

    public TagPojo() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagPojo other = (TagPojo) o;
        return id == other.id &&
                name.equals(other.name) &&
                description.equals(other.description) &&
                count == other.count &&
                link.equals(other.link) &&
                slug.equals(other.slug) &&
                taxonomy.equals(other.taxonomy) &&
                meta.containsAll(other.meta);
    }
}
