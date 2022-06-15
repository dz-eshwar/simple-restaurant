package com.britr.simpleRestaurant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "table_mst")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableMaster {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "table_name", length = 45)
    private String tableName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}