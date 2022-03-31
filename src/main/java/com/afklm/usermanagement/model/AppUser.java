package com.afklm.usermanagement.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    @Schema(hidden = true)
    private Long userId;

    @NotNull
    @Column(name = "user_name")
    @Schema(description = "username for the user to be created",
            defaultValue = "Bilel", required = true)
    private String username;

    @NotNull
    @Column(name = "birth_date")
    @Schema(description = "date of birth, user should be adult",
            defaultValue = "1993-02-27", required = true)
    private LocalDate birthdate;

    @Transient
    @XmlTransient
    @Schema(hidden = true)
    private Integer age;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 20)
    @Column(name = "residence_country")
    @Schema(description = "country of residence, user should be resident in France",
            defaultValue = "FRANCE", required = true)
    private Country country;

    @Column(name = "phone_number")
    @Schema(description = "phone number", defaultValue = "+33 000 000 000", required = false)
    private String phoneNumber;

    @Column(name = "gender")
    @Schema(description = "gender", defaultValue = "M", required = false)
    private String gender;

    public Integer getAge() {
        return Period.between(this.birthdate, LocalDate.now()).getYears();
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public AppUser(String username, LocalDate birthdate, Country country, String phoneNumber, String gender) {
        this.username = username;
        this.birthdate = birthdate;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public AppUser(String username, LocalDate birthdate, Country country) {
        this.username = username;
        this.birthdate = birthdate;
        this.country = country;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", birthdate=" + birthdate +
                ", country='" + country + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}