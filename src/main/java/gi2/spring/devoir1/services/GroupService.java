package gi2.spring.devoir1.services;

import gi2.spring.devoir1.dtos.contact.ContactDTO;
import gi2.spring.devoir1.dtos.contact.ContactMapper;
import gi2.spring.devoir1.dtos.group.GroupDTO;
import gi2.spring.devoir1.dtos.group.GroupMapper;
import gi2.spring.devoir1.exceptions.GroupNotFoundException;
import gi2.spring.devoir1.models.Contact;
import gi2.spring.devoir1.models.Group;
import gi2.spring.devoir1.repositories.ContactRepository;
import gi2.spring.devoir1.repositories.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    private final GroupRepository repository;
    private final GroupMapper mapper;

    public Page<GroupDTO> getAllGroups(int page) {
        return repository.findAll(getPageable(page)).map(mapper::map);
    }

    public Page<GroupDTO> getGroupsByName(String name, int page) {
        return repository.findByNameContainingIgnoreCase(name, getPageable(page))
                .map(mapper::map);
    }

    public GroupDTO getGroupById(Long groupId) {
        return mapper.mapWithContacts(findGroupById(groupId));
    }

    public void createGroup(String groupName) {
        Group group = Group.builder()
                .name(groupName)
                .createdAt(new Date())
                .build();
        repository.save(group);
    }

    public void createGroupByContactFullname(Contact contact) {
        String fullname = contact.getLastname() + " " + contact.getFirstname();
        Optional<Group> groupOptional = repository.findByNameIgnoreCase(fullname.toLowerCase());

        if (groupOptional.isEmpty()) {
            Group group = Group.builder()
                    .name(fullname)
                    .contacts(List.of(contact))
                    .createdAt(new Date())
                    .build();
            repository.save(group);
        } else {
            groupOptional.get().getContacts().add(contact);
            repository.save(groupOptional.get());
        }
    }

    public List<ContactDTO> getAvailableContacts(String name, Long groupId) {
        Group group = findGroupById(groupId);
        List<Contact> contacts;
        if (name == null || name.isEmpty())
            contacts = contactRepository.findAll(Sort.by(Sort.Order.asc("firstname"), Sort.Order.asc("lastname")));
        else {
            contacts = contactRepository.searchByName(name, Sort.by(Sort.Order.asc("firstname"), Sort.Order.asc("lastname")));
        }

        contacts.removeAll(group.getContacts());

        return contactMapper.map(contacts);
    }

    public void updateGroupName(Long groupId, String name) {
        Group group = findGroupById(groupId);
        group.setName(name);
        group.setUpdatedAt(new Date());
        repository.save(group);
    }

    public void addContactsToGroup(Long groupId, List<Long> contactsIds) {
        Group group = findGroupById(groupId);
        List<Contact> contacts = contactRepository.findAllById(contactsIds);
        contacts.removeAll(group.getContacts()); // Retirer les duplications
        group.getContacts().addAll(contacts);
        repository.save(group);
    }

    public void removeContactsFromGroup(Long groupId, List<Long> contactsIds) {
        Group group = findGroupById(groupId);
        List<Contact> contacts = contactRepository.findAllById(contactsIds);
        group.getContacts().removeAll(contacts);
        repository.save(group);
    }

    public void deleteGroupById(Long groupId) {
        Group group = findGroupById(groupId);
        group.getContacts().forEach(c -> c.getGroups().remove(group)); // Supprimer les associations entre Le groupe et ses contacts
        contactRepository.saveAll(group.getContacts());
        repository.delete(group);
    }

    private Group findGroupById(Long groupId) {
        return repository.findById(groupId)
                .orElseThrow(() -> new GroupNotFoundException(groupId));
    }

    private Pageable getPageable(int page) {
        return PageRequest.of(page, 10, Sort.by(Sort.Order.asc("name")));
    }
}
