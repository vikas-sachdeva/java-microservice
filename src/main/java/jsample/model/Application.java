package jsample.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Document(value = "application")
public class Application {

    @Id
    private String id;

    @NotBlank
    private String name;

    @NotNull
    private Boolean running;

    public Application() {
        super();
    }

    public Application(String id, String name, boolean running) {
        this.id = id;
        this.name = name;
        this.running = running;
    }


    public Application(String id, String name,
                       Boolean running) {
        this.id = id;
        this.name = name;
        this.running = running;
    }

    public String getId() {
        return id;
    }

    public Application setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Application setName(String name) {
        this.name = name;
        return this;
    }

    public Boolean getRunning() {
        return running;
    }

    public Application setRunning(Boolean running) {
        this.running = running;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Application that = (Application) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(name, that.name)
                .append(running, that.running)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(running)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("running", running)
                .toString();
    }
}