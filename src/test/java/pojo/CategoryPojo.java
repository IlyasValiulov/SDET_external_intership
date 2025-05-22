package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryPojo {
    private int id;

    private String name;

    private String description;

    private String taxonomy;

    private String slug;

    public CategoryPojo(int id, String name, String description, String taxonomy, String slug) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.taxonomy = taxonomy;
        this.slug = slug;
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
                slug.equals(other.slug);
    }
}
