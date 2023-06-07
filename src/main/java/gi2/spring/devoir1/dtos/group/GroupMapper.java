package gi2.spring.devoir1.dtos.group;

import gi2.spring.devoir1.dtos.contact.ContactDTO;
import gi2.spring.devoir1.models.Contact;
import gi2.spring.devoir1.models.Group;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GroupMapper {

    @Named("map")
    @Mapping(target = "contacts", ignore = true)
    GroupDTO map(Group group);

    @IterableMapping(qualifiedByName = "map")
    List<GroupDTO> map(List<Group> groups);

    @Named("mapWithDetails")
    @Mapping(target = "contacts", qualifiedByName = "mapContacts")
    GroupDTO mapWithContacts(Group group);

    @IterableMapping(qualifiedByName = "mapWithDetails")
    List<GroupDTO> mapWithContacts(List<Group> groups);

    @Named("mapContact")
    @Mapping(target = "groups", ignore = true)
    ContactDTO mapContact(Contact contact);

    @Named("mapContacts")
    @IterableMapping(qualifiedByName = "mapContact")
    List<ContactDTO> mapContact(List<Contact> contacts);

}
