// SPDX-License-Identifier: AGPL-3.0-or-later
package nanoseeds.springboot.vo.dao;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "github_id", nullable = false, unique = true)
    private Long github_id;
    @Column(name = "github_node_id", nullable = false, unique = true, length = 32)
    private String github_node_id;
    @Column(name = "login", nullable = false, unique = true, length = 32)
    private String login;
    @Column(name = "username", nullable = false, unique = true, length = 32)
    private String username;
    @Column(name = "company", length = 32)
    private String company;
    @Column(name = "blog", length = 64)
    private String blog;
    @Column(name = "location", length = 64)
    private String location;
    @Column(name = "email", length = 64)
    private String email;
    @Column(name = "bio", length = 128)
    private String bio;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        final User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
