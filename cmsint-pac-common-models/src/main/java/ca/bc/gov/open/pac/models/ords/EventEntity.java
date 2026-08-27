package ca.bc.gov.open.pac.models.ords;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class EventEntity extends BaseEntity {
    @JsonProperty("clientNumber")
    private String clientNumber;

    @JsonProperty("eventSeqNum")
    private String eventSeqNum;

    @JsonProperty("eventTypeCode")
    private String eventTypeCode;
}
