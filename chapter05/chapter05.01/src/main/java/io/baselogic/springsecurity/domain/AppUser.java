package io.baselogic.springsecurity.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.security.Principal;
import java.util.Set;

/**
 * {@link AppUser} is this applications notion of a user. It is good to use your own objects to interact with a
 * user especially in large applications. This ensures that as you evolve your security requirements (update Spring
 * Security, leverage new Spring Security modules, or even swap out security implementations) you can do so easily.
 *
 * @author mickknutson
 * @since chapter01.00 created
 * @since chapter05.01 Updated for JPA
 */
// JPA Annotations:
@Entity
@Table(name = "app_users")

// Lombok Annotations:
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUser implements Principal, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonManagedReference
    private Set<Role> roles;

    private static final long serialVersionUID = 8433999509932007961L;

    /**
     * Gets the full name in a formatted fashion. Note in a real application a formatter may be more appropriate, but in
     * this application simplicity is more important.
     *
     * @return AppUser email as their name.
     */
    @JsonIgnore
    public String getName() {
        return getEmail();
    }

} // The End...
