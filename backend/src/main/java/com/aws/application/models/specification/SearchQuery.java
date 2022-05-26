package com.aws.application.models.specification;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class SearchQuery {
    private int pageNumber;
    private int pageSize;
    private SortOrder sortOrder;
    private List<SearchFilter> searchFilter;
    private List<JoinColumnProps> joinColumnProps;
}