package com.githubolegostanin.petonlinestore.db.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "tenants")
public class Tenant {
    @Id
    @Column("id")
    private Long id;

    @Column("name")
    private String name;
}
