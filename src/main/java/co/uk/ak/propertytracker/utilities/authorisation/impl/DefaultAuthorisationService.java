package co.uk.ak.propertytracker.utilities.authorisation.impl;

import co.uk.ak.propertytracker.utilities.authorisation.AuthorisationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DefaultAuthorisationService implements AuthorisationService {

	@Value("${authorisation}")
	private String authorisation;

	@Override
	public boolean isAuthorised(String authorisation) {
		return StringUtils.equals(this.authorisation, authorisation);
	}
}
