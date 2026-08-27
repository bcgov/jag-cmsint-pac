package ca.bc.gov.open.pac.models.ords;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
public class NewerEventEntity extends BaseEntity {
    @JsonProperty("hasNewerEvent")
    private boolean hasNewerEvent;

    public boolean hasNewerEvent() {
        return hasNewerEvent;
    }
}
