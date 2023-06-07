package gi2.spring.devoir1.dtos.contact;

import gi2.spring.devoir1.dtos.group.GroupDTO;
import gi2.spring.devoir1.models.Contact;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ContactDTO {

    private Long id;

    private String lastname;

    private String firstname;

    private String personnalNumber;

    private String professionalNmuber;

    private String addresse;

    private String personnalEmail;

    private String professionalEmail;

    private Contact.Genre genre;

    private List<GroupDTO> groups;

    private Date createdAt;

    private Date updatedAt;

}
