package com.example.spring.models;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_robbery")
public class UnityModel extends RepresentationModel<UnityModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID idRobbery;
    private String name;
    private String ip;

    public UUID getIdRobbery() {
        return idRobbery;
    }

    public void setIdRobbery(UUID idRobbery) {
        this.idRobbery = idRobbery;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
