package gi2.spring.devoir1.dtos.contact;

import gi2.spring.devoir1.dtos.group.GroupDTO;
import gi2.spring.devoir1.models.Contact;
import gi2.spring.devoir1.models.Group;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContactMapper {

    ContactRequest map(ContactDTO contactDTO);

    @Named("ignoreGroups")
    @Mapping(target = "groups", ignore = true)
    ContactDTO map(Contact contact);

    @IterableMapping(qualifiedByName = "ignoreGroups")
    List<ContactDTO> map(List<Contact> contancts);

    @Named("map")
    @Mapping(target = "groups", qualifiedByName = "mapGroups")
    ContactDTO mapWithDetails(Contact contact);

    @IterableMapping(qualifiedByName = "map")
    List<ContactDTO> mapWithDetails(List<Contact> contancts);

    @Mapping(target = "createdAt", expression = "java(new java.util.Date())")
    Contact createContact(ContactRequest request);

    @Mapping(target = "updatedAt", expression = "java(new java.util.Date())")
    void updateContact(ContactRequest request, @MappingTarget Contact contact);

    @Named("mapGroup")
    @Mapping(target = "contacts", ignore = true)
    GroupDTO mapGroup(Group group);

    @Named("mapGroups")
    @IterableMapping(qualifiedByName = "mapGroup")
    List<GroupDTO> mapGroup(List<Group> groups);

}
