package com.example.spark.domain;

import java.io.Serializable;
import java.time.Instant;

public class Country implements Serializable{

    private Long id;

    private String name;

    private String code;

    private Boolean active;

    private String created_by;

    private Instant created_date;

    private String last_modified_by;

    private Instant last_modified_date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public Instant getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Instant created_date) {
        this.created_date = created_date;
    }

    public String getLast_modified_by() {
        return last_modified_by;
    }

    public void setLast_modified_by(String last_modified_by) {
        this.last_modified_by = last_modified_by;
    }

    public Instant getLast_modified_date() {
        return last_modified_date;
    }

    public void setLast_modified_date(Instant last_modified_date) {
        this.last_modified_date = last_modified_date;
    }
}
