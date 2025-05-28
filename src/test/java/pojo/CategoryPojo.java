package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryPojo {
    private int id;

    private String name;

    private String description;

    private String taxonomy;

    private String slug;

    private int count;

    private String link;

    private int parent;

    private List<String> meta;

    public CategoryPojo(int id, String name, String description, String taxonomy, String slug, int count, String link, int parent, List<String> meta) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.taxonomy = taxonomy;
        this.slug = slug;
        this.count = count;
        this.link = link;
        this.parent = parent;
        this.meta = meta;
    }

    public CategoryPojo() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryPojo other = (CategoryPojo) o;
        return id == other.id &&
                name.equals(other.name) &&
                description.equals(other.description) &&
                taxonomy.equals(other.taxonomy) &&
                slug.equals(other.slug) &&
                count == other.count &&
                link.equals(other.link) &&
                parent == other.parent &&
                meta.containsAll(other.meta);
    }
}
