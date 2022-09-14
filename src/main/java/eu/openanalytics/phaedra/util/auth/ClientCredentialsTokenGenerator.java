package eu.openanalytics.phaedra.util.auth;

import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

public class ClientCredentialsTokenGenerator {

	private String clientRegId;
	
	private AuthorizedClientServiceOAuth2AuthorizedClientManager clientManager;

    public ClientCredentialsTokenGenerator(String clientRegId, ClientRegistrationRepository clientRegistrationRepository) {
    	this.clientRegId = clientRegId;

		OAuth2AuthorizedClientService authorizedClientService = new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
		
		OAuth2AuthorizedClientProvider authorizedClientProvider =
				OAuth2AuthorizedClientProviderBuilder.builder()
				.clientCredentials()
				.build();
		
		clientManager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientService);
		clientManager.setAuthorizedClientProvider(authorizedClientProvider);
	}
			
	public OAuth2AccessToken obtainToken() {
		OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
				.withClientRegistrationId(clientRegId)
				.principal("Phaedra2Client")
				.build();
		
		return clientManager.authorize(authorizeRequest).getAccessToken();
	}
}
