package gi2.spring.devoir1.handlers;

import gi2.spring.devoir1.exceptions.ContactNotFoundException;
import gi2.spring.devoir1.exceptions.GroupNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ContactNotFoundException.class)
    public String handleContactNotFound(ContactNotFoundException exception, Model model) {
        model.addAttribute("error", exception.getMessage());
        return "error";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(GroupNotFoundException.class)
    public String handleContactNotFound(GroupNotFoundException exception, Model model) {
        model.addAttribute("error", exception.getMessage());
        return "error";
    }

}
