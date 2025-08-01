package com.final_project.shopper.shopper.payloads;

import lombok.Data;

import java.util.List;

@Data
public class PaginationPayload<TModel> {
    private List<TModel> models;
    private Integer currentPage;
    private Integer totalPage;
}
