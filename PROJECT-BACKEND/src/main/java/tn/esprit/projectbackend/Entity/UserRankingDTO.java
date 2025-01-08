package tn.esprit.projectbackend.Entity;

public class UserRankingDTO {
    private Long userId;
    private String name;
    private Double profit;



    public UserRankingDTO(String name, Long userId, Double profit) {
        this.userId = userId;
        this.name = name;
        this.profit = profit;
    }

    // Getters and setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }
}

