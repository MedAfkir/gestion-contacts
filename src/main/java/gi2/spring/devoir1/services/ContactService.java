package gi2.spring.devoir1.services;

import gi2.spring.devoir1.dtos.contact.ContactMapper;
import gi2.spring.devoir1.dtos.contact.ContactDTO;
import gi2.spring.devoir1.dtos.contact.ContactRequest;
import gi2.spring.devoir1.dtos.group.GroupDTO;
import gi2.spring.devoir1.dtos.group.GroupMapper;
import gi2.spring.devoir1.exceptions.ContactNotFoundException;
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

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository repository;
    private final ContactMapper mapper;

    private final GroupRepository groupRepository;
    private final GroupService groupService;
    private final GroupMapper groupMapper;

    public Page<ContactDTO> getAllContacts(String name, String tel, boolean isPro, int pageIndex) {
        Page<Contact> page;
        if (name == null || name.isEmpty()) {
            if (tel == null || tel.isEmpty())
                return getAllContacts(pageIndex);
            else if (isPro)
                page = repository.searchByProTel(tel, getPageable(pageIndex));
            else
                page = repository.searchByPersonnalTel(tel, getPageable(pageIndex));
        } else {
            if (tel == null || tel.isEmpty())
                page = repository.searchByName(name, getPageable(pageIndex));
            else if (isPro)
                page = repository.searchByNameAndProTel(name, tel, getPageable(pageIndex));
            else
                page = repository.searchByNameAndPersonnalTel(name, tel, getPageable(pageIndex));
        }
        return page.map(mapper::map);
    }

    public Page<ContactDTO> getAllContacts(int pageIndex) {
        return repository.findAll(getPageable(pageIndex)).map(mapper::map);
    }

    public ContactDTO getContactById(Long contactId) {
        Contact contact = findContactById(contactId);
        return mapper.mapWithDetails(contact);
    }

    public void updateContactById(Long contactId, ContactRequest request) {
        Contact contact = findContactById(contactId);
        mapper.updateContact(request, contact);
        repository.save(contact);
    }

    public List<GroupDTO> getContactGroups(Long contactId) {
        Contact contact = findContactById(contactId);
        return groupMapper.mapWithContacts(contact.getGroups());
    }

    public void createContact(ContactRequest request) {
        Contact savedContact = repository.save(mapper.createContact(request));

        // Créer des groupes automatiquement constitués des contacts de même nom.
        groupService.createGroupByContactFullname(savedContact);
    }

    public void deleteContactById(Long contactId) {
        Contact contact = findContactById(contactId);
        contact.getGroups().forEach(g -> g.getContacts().remove(contact));
        groupRepository.saveAll(contact.getGroups());
        repository.delete(contact);
    }

    public void removeGroupsFromContact(Long contactId, List<Long> groupsIds) {
        Contact contact = findContactById(contactId);
        List<Group> groups = groupRepository.findAllById(groupsIds);
        groups.forEach(g -> g.getContacts().remove(contact));
        repository.save(contact);
    }

    private Contact findContactById(Long contactId) {
        return repository.findById(contactId)
                .orElseThrow(() -> new ContactNotFoundException(contactId));
    }

    private Pageable getPageable(int page) {
        return PageRequest.of(
                page,
                10,
                Sort.by(
                        Sort.Order.asc("lastname"),
                        Sort.Order.asc("firstname")
                )
        );
    }
}
