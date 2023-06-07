package gi2.spring.devoir1.controllers;

import gi2.spring.devoir1.exceptions.ContactNotFoundException;
import gi2.spring.devoir1.exceptions.GroupNotFoundException;
import gi2.spring.devoir1.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/groupes")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService service;

    @GetMapping
    public String getGroupsList(
            Model model,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "name", required = false, defaultValue = "") String name
    ) {
        if (page < 1)
            return "redirect:/groupes";

        model.addAttribute("page", service.getGroupsByName(name, page - 1));
        return "groups";
    }

    @GetMapping("/{id}")
    public String getGroupById(
            Model model,
            @PathVariable("id") Long groupId,
            @RequestParam(value = "search", required = false) String name
    ) {
        model.addAttribute("contacts", service.getAvailableContacts(name, groupId));
        model.addAttribute("group", service.getGroupById(groupId));
        return "group";
    }

    @PostMapping
    public RedirectView createGroup(
            RedirectAttributes attributes,
            @RequestParam("name") String groupName
    ) {
        if (groupName == null || groupName.isEmpty()) {
            attributes.addAttribute("error", "Nom du groupe est invalide");
        } else {
            service.createGroup(groupName);
            attributes.addAttribute("success", "Groupe crée avec succés");
        }
        return new RedirectView("/groupes");
    }

    @PostMapping("/{id}")
    public RedirectView updateGroupName(
            RedirectAttributes attributes,
            @PathVariable("id") Long groupId,
            @RequestParam("name") String groupName
    ) {
        if (groupName == null || groupName.isEmpty()) {
            attributes.addAttribute("error", "Nom du groupe est invalide");
        } else {
            service.updateGroupName(groupId, groupName);
            attributes.addAttribute("success", "Nom de groupe a été modifié avec succés");
        }
        return new RedirectView("/groupes/" + groupId);
    }

    @PostMapping("/{id}/contacts/add")
    public RedirectView addContactToGroup(
            RedirectAttributes attributes,
            @PathVariable("id") Long groupId,
            @RequestParam(value = "contacts", required = false) List<Long> contactsIds
    ) {
        if (contactsIds != null) {
            service.addContactsToGroup(groupId, contactsIds);
            attributes.addAttribute("success", "Contacts ajoutés avec succés");
        }
        return new RedirectView("/groupes/" + groupId);
    }

    @PostMapping("/{id}/contacts/remove")
    public RedirectView removeContactFromGroup(
            RedirectAttributes attributes,
            @PathVariable("id") Long groupId,
            @RequestParam(value = "contacts", required = false) List<Long> contactsIds
    ) {
        if (contactsIds != null) {
            service.removeContactsFromGroup(groupId, contactsIds);
            attributes.addAttribute("success", "Contacts retirés avec succés");
        }
        return new RedirectView("/groupes/" + groupId);
    }

    @PostMapping("/{id}/delete")
    public RedirectView deleteGroup(
            RedirectAttributes attributes,
            @PathVariable("id") Long groupId
    ) {
        service.deleteGroupById(groupId);
        attributes.addAttribute("success", "Groupe supprimé avec succés");
        return new RedirectView("/groupes");
    }

}
