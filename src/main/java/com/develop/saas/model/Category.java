package com.develop.saas.model;

import com.develop.saas.common.BaseEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Category extends BaseEntity {
    @Column(nullable = false, length = 100)
    private String name;

    @Builder.Default
    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private List<Script> scripts = new ArrayList<>();
}
