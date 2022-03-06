package pe.com.egonzalesd.msspsupportexchangerate.business;

import io.reactivex.rxjava3.core.Maybe;
import pe.com.egonzalesd.msspsupportexchangerate.expose.response.UserResponse;

public interface UserService {
    Maybe<UserResponse> findUser(String username,
                                 String pwd);
}
