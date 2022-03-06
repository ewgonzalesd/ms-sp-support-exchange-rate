package pe.com.egonzalesd.msspsupportexchangerate.business.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import pe.com.egonzalesd.msspsupportexchangerate.business.UserService;
import pe.com.egonzalesd.msspsupportexchangerate.business.exception.BusinessException;
import pe.com.egonzalesd.msspsupportexchangerate.expose.response.UserResponse;
import pe.com.egonzalesd.msspsupportexchangerate.model.UserModel;
import pe.com.egonzalesd.msspsupportexchangerate.repository.UserRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public Maybe<UserResponse> findUser(String username, String pwd) {

        List<UserModel> list = userRepository.findUserModelByUsername(username);

        return Flowable.fromIterable(list)
                .firstElement()
                .filter(f -> f.getPwd().equals(pwd))
                .map(o -> UserResponse.builder()
                        .username(o.getUsername())
                        .token(generateToken(o))
                        .build())
                .switchIfEmpty(Maybe.error(new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR,
                        UUID.randomUUID().toString(),"RETO","Usuario no existe o clave incorrecta")));
    }

    private String generateToken(UserModel userModel) {
        String secretKey = "egonzalesd";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("authorities",grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        claimsMap.put("typeDoc",userModel.getTypeDoc());
        claimsMap.put("numDoc",userModel.getNumDoc());

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(userModel.getUsername())
                .addClaims(claimsMap)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}
