package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.projections.SaleSummaryProjection;

public class SaleSummaryDTO {

    private String name;
    private Double total;

    public SaleSummaryDTO() {
    }

    public SaleSummaryDTO(String name, Double amount) {
        this.name = name;
        this.total = amount;
    }

    public SaleSummaryDTO(SaleSummaryProjection projection) {
        name = projection.getSellerName();
        total = projection.getTotal();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return total;
    }

    public void setAmount(Double total) {
        this.total = total;
    }
}
