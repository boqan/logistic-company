package com.LogisticsCompany.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client extends IdGenerator {

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Email(message="Please provide a valid email address")
    private String email;

}
