package gi2.spring.devoir1.dtos.group;

import gi2.spring.devoir1.dtos.contact.ContactDTO;
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
public class GroupDTO {

    private Long id;

    private String name;

    private List<ContactDTO> contacts;

    private Date createdAt;

    private Date updatedAt;

}
