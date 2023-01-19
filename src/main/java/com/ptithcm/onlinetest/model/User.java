package com.ptithcm.onlinetest.model;

import com.ptithcm.onlinetest.model.audit.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
//@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class User extends DateAudit {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank
        @Size(max = 15)
        @Column(name = "username")
        private String username;

        @NaturalId
        @NotBlank
        @Size(max = 40)
        @Email
        @Column(name = "email")
        private String email;

        @NotBlank
        @Size(max = 100)
        @Column(name = "password")
        private String password;

        @Column(name = "enabled")
        private  boolean enabled;

        @Column(name = "firstname")
        private String firstName;

        @Column(name = "lastname")
        private String lastName;

        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "user_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id")
        )
        private Set<Role> roles = new HashSet<>();

        public User() {
                this.enabled = false;
        }
}
