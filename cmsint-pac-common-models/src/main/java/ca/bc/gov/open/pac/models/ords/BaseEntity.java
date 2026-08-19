package ca.bc.gov.open.pac.models.ords;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseEntity implements Serializable {

    @JsonProperty("status_message")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof BaseEntity)) return false;
        BaseEntity other = (BaseEntity) o;
        return other.canEqual(this) && Objects.equals(this.getStatus(), other.getStatus());
    }

    protected boolean canEqual(Object other) {
        return other instanceof BaseEntity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatus());
    }
}
