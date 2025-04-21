package com.oneteam.oauth;

import com.oneteam.domain.dept.DeptEntity;
import com.oneteam.domain.user.UserEntity;
import com.oneteam.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthClientService {

    @Value("${logout.redirect-url}")
    private String redirectUrl;

    private final UserRepository userRepository;
    private String msg = "Client not Found Exception: ";

    public RegisteredClient findById(String id) {
        UserEntity oAuthClient = userRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new IllegalArgumentException(msg + id));
        return loadClientByResult(oAuthClient);
    }

    public RegisteredClient findByClientId(String clientId) {
        UserEntity oAuthClient = userRepository.findByEmailAndUseYn(clientId, 'Y')
                .orElseThrow(() -> new IllegalArgumentException(msg + clientId));
        return loadClientByResult(oAuthClient);
    }

    private RegisteredClient loadClientByResult(UserEntity user) {
        if(user != null) {
            return RegisteredClient
                    .withId(user.getNo().toString())
                    .clientId(user.getEmail())
                    .clientName(user.getName())
                    .clientSecret(user.getPassword())
                    //            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                    .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                    //            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    //            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                    .redirectUri(redirectUrl)
                    .postLogoutRedirectUri(redirectUrl)
                    .scopes(scope -> {
                        for (DeptEntity dept : user.getDepts()) {
                            scope.add(dept.getName());
                        }
                        //          scope.add(OidcScopes.OPENID);
                        //          scope.add(OidcScopes.PROFILE);
                        //          scope.add("read");
                        //          scope.add("write");
                    })
                    .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
                    .build();
        }
        return null;
    }

}
