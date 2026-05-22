package org.example.healthcare.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.healthcare.enums.UserRoles;

@Data
@Entity
@Table(name = "admins")
public class Admin extends User {

    public Admin() {
        super();
        this.setRole(UserRoles.ADMIN);
    }
}
