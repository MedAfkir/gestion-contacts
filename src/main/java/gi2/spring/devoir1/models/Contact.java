package gi2.spring.devoir1.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "contacts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lastname;

    private String firstname;

    private String personnalNumber;

    private String professionalNmuber;

    private String addresse;

    private String personnalEmail;

    private String professionalEmail;

    private Genre genre;

    @ManyToMany(mappedBy = "contacts")
    private List<Group> groups;

    private Date createdAt;

    private Date updatedAt;

    public enum Genre {
        MALE("Homme"),
        FEMALE("Femme");

        private final String name;

        public String getName() {
            return name;
        }

        Genre(String name) {
            this.name = name;
        }
    }

}
