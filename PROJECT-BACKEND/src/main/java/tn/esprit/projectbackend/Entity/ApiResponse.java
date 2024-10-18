package tn.esprit.projectbackend.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ApiResponse {

    @JsonProperty("Meta Data")
    private MetaData metaData;

    @JsonProperty("Time Series (1min)")
    private Map<String, TimeSeriesData> timeSeriesData;

}
