package de.ostfalia.se.security;

import de.ostfalia.se.boundary.StaffService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

import java.util.Arrays;
import java.util.HashSet;


@ApplicationScoped
public class CustomIdentityStore implements IdentityStore {

    @Inject
    StaffService staffService;

    @Override
    public CredentialValidationResult validate(Credential credential) {

        UsernamePasswordCredential login = (UsernamePasswordCredential) credential;
        String matchedPasswordFromDatabase =  staffService.callerQuery(login.getCaller());
        String userGroupOfMatchedStaff = staffService.groupsQuery(login.getCaller());

        String[] splitPasswort = matchedPasswordFromDatabase.split(" ");
        String matchedPassword = splitPasswort[1];

        if(matchedPasswordFromDatabase != null && userGroupOfMatchedStaff != null){
            if (matchedPassword.contains(login.getPasswordAsString())) {
                return new CredentialValidationResult(
                        login.getCaller().concat(" (").concat(userGroupOfMatchedStaff).concat(") "),
                        new HashSet<>(Arrays.asList(userGroupOfMatchedStaff)));
            }
        }
            return CredentialValidationResult.NOT_VALIDATED_RESULT;

    }
}


