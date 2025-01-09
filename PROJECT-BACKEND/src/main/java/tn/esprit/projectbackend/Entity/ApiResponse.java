package tn.esprit.projectbackend.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApiResponse {

    private String ticker;
    private int queryCount;
    private int resultsCount;
    private boolean adjusted;

    private List<Result> results;
    private String status;

    @JsonProperty("request_id")
    private String requestId;

    private int count;



    }

