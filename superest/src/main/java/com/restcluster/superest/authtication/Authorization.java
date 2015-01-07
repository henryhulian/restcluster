package com.restcluster.superest.authtication;

import java.util.Set;

public interface Authorization {

	boolean isUserAllowed(final String username, final Set<String> rolesSet);
   
}
