package eu.virtusdevelops.ldmontage.domain.auth;

import eu.virtusdevelops.ldmontage.domain.token.SessionToken;
import eu.virtusdevelops.ldmontage.domain.user.User;

public record LoginData(
        User user,
        SessionToken sessionToken
) {
}
