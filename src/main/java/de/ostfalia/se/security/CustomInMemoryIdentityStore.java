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
public class CustomInMemoryIdentityStore implements IdentityStore {
    @Inject
    StaffService staffService;

    @Override
    public CredentialValidationResult validate(Credential credential) {

        UsernamePasswordCredential login = (UsernamePasswordCredential) credential;
        String role = staffService.groupsQuery( ((UsernamePasswordCredential) credential).getCaller() );
       System.out.println("Caller : " +  ((UsernamePasswordCredential) credential).getCaller() );
       System.out.println("role : " + role);

       System.out.println("Caller Password : " + ((UsernamePasswordCredential) credential).getPasswordAsString());

       String password = staffService.callerQuery(login.getCaller());
       System.out.println("Password from DB : " + password );

        if (login.getCaller().equals("admin@mail.com")
                && login.getPasswordAsString().equals("ADMIN1234")) {
            return new CredentialValidationResult("admin", new HashSet<>(Arrays.asList("ADMIN")));
        } else if (login.getCaller().equals("user@mail.com")
                && login.getPasswordAsString().equals("USER1234")) {
            return new CredentialValidationResult("user", new HashSet<>(Arrays.asList("USER")));
        } else {


            System.out.println("Caller : " +  ((UsernamePasswordCredential) credential).getCaller() );
            System.out.println("role : " + role);

            System.out.println("Caller Password : " + ((UsernamePasswordCredential) credential).getPasswordAsString());

            System.out.println("Password from DB : " + password );
            return new CredentialValidationResult("user", new HashSet<>(Arrays.asList("USER")));
        }

    }
}

