package com.LogisticsCompany.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client extends IdGenerator {

    @Column(name = "name", nullable = false, length = 255)
    private String name;




//@OneToOne(fetch = FetchType.LAZY)
    //private Credentials credentials;
}
