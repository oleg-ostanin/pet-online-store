package com.githubolegostanin.petonlinestore.db.repository;

import com.githubolegostanin.petonlinestore.db.entity.Tenant;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;


public interface TenantRepository extends R2dbcRepository<Tenant, Long> {

    @Query("SELECT * FROM tenants where name like :name")
    public Flux<Tenant> findByName(String name);
}

