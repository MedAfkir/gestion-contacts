package gi2.spring.devoir1.controllers;

import gi2.spring.devoir1.dtos.contact.ContactDTO;
import gi2.spring.devoir1.dtos.contact.ContactMapper;
import gi2.spring.devoir1.dtos.contact.ContactRequest;
import gi2.spring.devoir1.models.Contact;
import gi2.spring.devoir1.services.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService service;
    private final ContactMapper mapper;

    @GetMapping
    public String getContactsListPage(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "tel", required = false) String tel,
            @RequestParam(value = "is-pro", required = false) boolean isPro,
            @RequestParam(value = "page", required = false, defaultValue = "1") int pageIndex,
            Model model
    ) {
        if (pageIndex < 1) return "redirect:/contacts";
        model.addAttribute("genres", Contact.Genre.values());
        model.addAttribute("request", new ContactRequest());
        model.addAttribute("page", service.getAllContacts(search, tel, isPro, pageIndex - 1));

        return "contacts";
    }

    @GetMapping("/{id}")
    public String getContactInfosPage(
            @PathVariable("id") Long contactId,
            Model model
    ) {
        ContactDTO contact = service.getContactById(contactId);
        model.addAttribute("updateRequest", mapper.map(contact));
        model.addAttribute("genres", Contact.Genre.values());
        model.addAttribute("contact", contact);
        return "contact";
    }

    @PostMapping
    public RedirectView createContact(
            RedirectAttributes attributes,
            @ModelAttribute("request") @Valid ContactRequest request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            attributes.addAttribute("error", bindingResult.getFieldErrors().get(0).getDefaultMessage());
        } else {
            service.createContact(request);
            attributes.addAttribute("success", "Contact crée avec succés");
        }
        return new RedirectView("/contacts");
    }

    @PostMapping("/{id}")
    public RedirectView updateContactById(
            RedirectAttributes attributes,
            @ModelAttribute("updateRequest") ContactRequest request,
            @PathVariable("id") Long contactId,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            attributes.addAttribute("error", bindingResult.getFieldErrors().get(0).getDefaultMessage());
        } else {
            service.updateContactById(contactId, request);
            attributes.addAttribute("success", "Contact modifié avec succés");
        }
        return new RedirectView("/contacts/" + contactId);
    }

    @PostMapping("/{id}/groupes/remove")
    public RedirectView removeGroupsFromContact(
            RedirectAttributes attributes,
            @PathVariable("id") Long contactId,
            @RequestParam(value = "groups", required = false) List<Long> groupsIds
    ) {
        if (groupsIds != null) {
            service.removeGroupsFromContact(contactId, groupsIds);
            attributes.addAttribute("success", "Groupes retirés avec succés");
        }
        return new RedirectView("/contacts/" + contactId);
    }

    @PostMapping("/{id}/delete")
    public RedirectView deleteContactById(
            RedirectAttributes attributes,
            @PathVariable("id") Long contactId
    ) {
        service.deleteContactById(contactId);
        attributes.addAttribute("success", "Contact supprimé avec succés");
        return new RedirectView("/contacts");
    }

}
