package com.britr.simpleRestaurant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "device_mst")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceMaster {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "device_name", length = 45)
    private String deviceName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

}