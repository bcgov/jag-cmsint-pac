package ca.bc.gov.open.pac.models.ords;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewerEventEntity extends BaseEntity {
    @JsonProperty("hasNewerEvent")
    private boolean hasNewerEvent;

    public boolean hasNewerEvent() {
        return hasNewerEvent;
    }

    public void setHasNewerEvent(boolean hasNewerEvent) {
        this.hasNewerEvent = hasNewerEvent;
    }
}
