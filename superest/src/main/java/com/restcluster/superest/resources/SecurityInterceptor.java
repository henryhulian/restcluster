package com.restcluster.superest.resources;

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
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import com.restcluster.superest.domain.Session;
import com.restcluster.superest.server.SuperRestServerContextSingleton;
import com.restcluster.superest.session.SessionFatory;
import com.restcluster.superest.util.CookieUtil;
import com.restcluster.superest.util.IpUtil;
import com.restcluster.superest.util.TokenUtil;

/**
 * This interceptor verify the access permissions for a user
 * based on username and passowrd provided in request
 * */
@Provider
public class SecurityInterceptor extends AbstractResources implements javax.ws.rs.container.ContainerRequestFilter
{
	
	@Context
	private HttpServletRequest request;
	
    private static final ServerResponse ACCESS_NO_TOKEN = new ServerResponse("No token", 401, new Headers<Object>());
    private static final ServerResponse ACCESS_NO_USER_LOGIN = new ServerResponse("No user login", 401, new Headers<Object>());;
    private static final ServerResponse ACCESS_IP_FORBIDDEN = new ServerResponse("Ip error", 403, new Headers<Object>());;
    private static final ServerResponse ACCESS_ROLE_FORBIDDEN = new ServerResponse("No role", 403, new Headers<Object>());
    
     
    @Override
    public void filter(ContainerRequestContext requestContext)
    {
        ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
        Method method = methodInvoker.getMethod();
        
        SuperRestServerContextSingleton context = SuperRestServerContextSingleton.getInstance();
        request.setAttribute(SuperRestServerContextSingleton.SERVER_CONTEXT,context);
       
        //Access allowed for all
        if( ! method.isAnnotationPresent(PermitAll.class) && !request.getRequestURI().contains("/api-docs"))
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
            
            
            try( Transaction transaction = super.getDatabase().beginTx()){
            	
            	//find session information
                //if cann't find session return
                Node session = SessionFatory.getInstance().getSession(token);
                if( session == null ){
                	 requestContext.abortWith(ACCESS_NO_USER_LOGIN);
                     return;
                }
            	
              //check ip
	            if( session.getProperty(Session.SESSION_IP)!=null && !IpUtil.getIp(request).contains((String)session.getProperty(Session.SESSION_IP))){
	            	 requestContext.abortWith(ACCESS_IP_FORBIDDEN);
	                 return;
	            }
	             
	           
	            //Verify user access
	            if(method.isAnnotationPresent(RolesAllowed.class))
	            {
	                RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
	                Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
	                 
	                //Is user valid?
	                if( !context.getAuthenticationService().getAuthorization().isUserAllowed( (String)session.getProperty(Session.USER_NAME), rolesSet) )
	                {
	                    requestContext.abortWith(ACCESS_ROLE_FORBIDDEN);
	                    return;
	                }
	            }
	            
	            
            }
        }
    }
    
     
}


