package com.aws.application.models.specification;

import lombok.Data;

@Data
public class JoinColumnProps {
    private String joinColumnName;
    private SearchFilter searchFilter;
}