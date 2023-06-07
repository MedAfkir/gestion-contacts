package gi2.spring.devoir1.exceptions;

import lombok.Getter;

@Getter
public class ContactNotFoundException extends RuntimeException {

    private final Object identifier;

    public ContactNotFoundException(Object identifier, String message) {
        super(message);
        this.identifier = identifier;
    }

    public ContactNotFoundException(Object identifier) {
        this.identifier = identifier;
    }

}
