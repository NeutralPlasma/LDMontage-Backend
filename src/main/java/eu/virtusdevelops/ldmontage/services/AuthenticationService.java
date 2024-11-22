package eu.virtusdevelops.ldmontage.services;

import eu.virtusdevelops.ldmontage.domain.auth.LoginData;
import eu.virtusdevelops.ldmontage.domain.user.User;
import eu.virtusdevelops.ldmontage.requests.LoginRequest;
import eu.virtusdevelops.ldmontage.requests.RegisterRequest;

public interface AuthenticationService {

    /**
     * Preforms a login
     * @param request request information to preform login with
     * @return LoginData (containing token for future requests)
     */
    LoginData login(LoginRequest request);

    /**
     * Tries to register new user
     * @param request register request
     * @return the new registered user
     */
    User register(RegisterRequest request);

}
