package eu.virtusdevelops.ldmontage.requests;

import eu.virtusdevelops.ldmontage.validation.FieldMatch;
import eu.virtusdevelops.ldmontage.validation.PhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;

@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match."),
})
public record RegisterRequest(

        @Email
        @NotEmpty(message = "Email can not be empty.")
        String email,

        @NotEmpty(message = "Password can not be empty.")
        String password,

        @NotEmpty(message = "Firstname can not be empty.")
        String firstName,

        @NotEmpty(message = "Lastname can not be empty.")
        String lastName,


        Date birthDate,

        @NotEmpty(message = "Phone number can not be empty.")
        @PhoneNumber()
        String phoneNumber,

        @NotEmpty(message = "Confirmation password can not be empty.")
        String confirmPassword,

        @NotEmpty(message = "Invite token can not be empty.")
        String inviteToken


) {
}
