package com.superest.resources;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;

import com.superest.service.ServiceFatory;
import com.superest.session.Session;
import com.superest.util.CookieUtil;
import com.superest.util.IpUtil;
import com.superest.util.TokenUtil;

/**
 * This interceptor verify the access permissions for a user
 * based on username and passowrd provided in request
 * */
@Provider
public class SecurityInterceptor implements javax.ws.rs.container.ContainerRequestFilter
{
	
	@Context
	private HttpServletRequest request;
	
    private static final ServerResponse ACCESS_NO_TOKEN = new ServerResponse("No user login", 401, new Headers<Object>());
    private static final ServerResponse ACCESS_NO_USER_LOGIN = new ServerResponse("No user login", 401, new Headers<Object>());;
    private static final ServerResponse ACCESS_IP_FORBIDDEN = new ServerResponse("Ip error", 403, new Headers<Object>());;
    private static final ServerResponse ACCESS_ROLE_FORBIDDEN = new ServerResponse("No role", 403, new Headers<Object>());
    
     
    @Override
    public void filter(ContainerRequestContext requestContext)
    {
        ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
        Method method = methodInvoker.getMethod();
       
        //Access allowed for all
        if( ! method.isAnnotationPresent(PermitAll.class))
        {
            //Access denied for all
            if(method.isAnnotationPresent(DenyAll.class))
            {
                requestContext.abortWith(ACCESS_ROLE_FORBIDDEN);
                return;
            }
            
            //detect token
            // if cann't find token return
            String token = CookieUtil.getCookie(request, TokenUtil.TOKEN_COOKIE_NMAE);
            if( token == null ){
            	 requestContext.abortWith(ACCESS_NO_TOKEN);
                 return;
            }
            
            //find session information
            //if cann't find session return
            Session session = ServiceFatory.getSessionService().getSession(token);
            if( session == null ){
            	 requestContext.abortWith(ACCESS_NO_USER_LOGIN);
                 return;
            }
            
            //check ip
            if( !IpUtil.getIp(request).contains(session.getSessionIp())){
            	 requestContext.abortWith(ACCESS_IP_FORBIDDEN);
                 return;
            }
             
           
            //Verify user access
            if(method.isAnnotationPresent(RolesAllowed.class))
            {
                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
                Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
                 
                //Is user valid?
                if( ! isUserAllowed( session.get("USERNAME"), rolesSet))
                {
                    requestContext.abortWith(ACCESS_ROLE_FORBIDDEN);
                    return;
                }
            }
        }
    }
    private boolean isUserAllowed(final String username, final Set<String> rolesSet)
    {
        boolean isAllowed = false;
         
        String userRole = "ADMIN";
         
        //Step 2. Verify user role
        if(rolesSet.contains(userRole))
        {
            isAllowed = true;
        }
        return isAllowed;
    }
     
}


