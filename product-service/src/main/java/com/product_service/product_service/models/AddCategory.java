package com.product_service.product_service.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddCategory {

    private String categoryName;

    private String categoryDescription;

    private long createdBy;

    private long updatedBy;

    private boolean isDeleted;

    private boolean isActive;
}
