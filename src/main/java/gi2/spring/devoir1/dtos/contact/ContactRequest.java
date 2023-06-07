package gi2.spring.devoir1.dtos.contact;

import gi2.spring.devoir1.models.Contact;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ContactRequest {

    @NotBlank
    @NotEmpty(message = "Nom ne doit pas être vide")
    private String lastname;

    @NotBlank(message = "Prénom ne doit pas être vide")
    @NotEmpty(message = "Prénom ne doit pas être vide")
    private String firstname;

    @NotBlank(message = "Numéro personnel ne doit pas être vide")
    @NotEmpty(message = "Numéro personnel ne doit pas être vide")
    private String personnalNumber;

    @NotBlank(message = "Numéro professionnel ne doit pas être vide")
    @NotEmpty(message = "Numéro professionnel ne doit pas être vide")
    private String professionalNmuber;

    @NotBlank
    @NotEmpty(message = "Addresse ne doit pas être vide")
    private String addresse;

    @NotBlank
    @Email(message = "Email invalide")
    private String personnalEmail;

    @NotBlank
    @Email(message = "Email invalide")
    private String professionalEmail;

    @NotNull(message = "Genre ne doit pas être null")
    private Contact.Genre genre;

}
