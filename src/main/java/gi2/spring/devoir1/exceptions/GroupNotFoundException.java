package gi2.spring.devoir1.exceptions;

import lombok.Getter;

@Getter
public class GroupNotFoundException extends RuntimeException {

    private final Object identifier;

    public GroupNotFoundException(Object identifier, String message) {
        super(message);
        this.identifier = identifier;
    }

    public GroupNotFoundException(Object identifier) {
        this.identifier = identifier;
    }

}
