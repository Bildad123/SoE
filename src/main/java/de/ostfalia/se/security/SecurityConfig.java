package de.ostfalia.se.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;



@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "java:jboss/datasources/bikes11",
        callerQuery = "SELECT phone FROM staffs WHERE email = ?",
        groupsQuery = "SELECT \n" +
                "CASE \n" +
                "WHEN manager_id = null THEN 'ADMIN'\n" +
                "WHEN manager_id = 1 THEN 'USER1'\n" +
                "ELSE 'USER2'\n" +
                "END AS result\n" +
                "FROM staffs WHERE email = ?",
        priority = 10
)
@ApplicationScoped
public class SecurityConfig {

}
