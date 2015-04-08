/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.engine.interceptor;

import com.tchepannou.rails.ContainerContextImpl;
import com.tchepannou.rails.engine.container.ActionControllerWrapper;
import com.tchepannou.rails.core.annotation.RequireUser;
import com.tchepannou.rails.core.api.ActionController;
import com.tchepannou.rails.core.api.ContainerContext;
import com.tchepannou.rails.core.api.ServiceContext;
import com.tchepannou.rails.core.api.User;
import com.tchepannou.rails.core.api.UserService;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import junit.framework.TestCase;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;

/**
 *
 * @author herve
 */
public class ActionRequireInterceptorTest
    extends TestCase
{
    private ActionControllerWrapper _wrapper;
    private MockServletContext _sc;
    private MockHttpServletRequest _request;
    private MockHttpServletResponse _response;
    private List _permissions;

    public ActionRequireInterceptorTest (String name)
    {
        super (name);
    }


    @Override
    protected void setUp ()
        throws Exception
    {
        _wrapper = new ActionControllerWrapper ();
        _wrapper.init (createContainerContext ());

        _sc = new MockServletContext ();
        _request = new MockHttpServletRequest (_sc);
        _response = new MockHttpServletResponse ();
    }

    @Override
    protected void tearDown ()
        throws Exception
    {
        super.tearDown ();

        _wrapper.destroy ();
    }

    public void testSecuredClass ()
        throws Exception
    {
        /* login */
        login();

        /* hello */
        _wrapper.setControllerClass (SecuredClassActionController.class);
        _wrapper.service ("/controller/hello", _request, _response);

        /* result */
        assertNull ("redirectUrl", _response.getRedirectedUrl ());
        assertEquals ("content", "hello", _response.getContentAsString ());
    }

    public void testSecuredClassNoLogin ()
        throws Exception
    {
        /* hello */
        _wrapper.setControllerClass (SecuredClassActionController.class);
        _wrapper.service ("/controller/hello", _request, _response);

        /* result */
        assertTrue ("redirectUrl", _response.getRedirectedUrl ().contains ("/login"));
    }

    public void testSecuredMethod ()
        throws Exception
    {
        /* login */
        login();

        /* hello */
        _wrapper.setControllerClass (SecuredMethodActionController.class);
        _wrapper.service ("/controller/hello", _request, _response);

        /* result */
        assertNull ("redirectUrl", _response.getRedirectedUrl ());
        assertEquals ("content", "hello", _response.getContentAsString ());
    }

    public void testSecuredMethodNoLogin ()
        throws Exception
    {
        /* hello */
        _wrapper.setControllerClass (SecuredMethodActionController.class);
        _wrapper.service ("/controller/hello", _request, _response);

        /* result */
        assertTrue ("redirectUrl", _response.getRedirectedUrl ().contains ("/login"));
    }

    public void testPermissionClassNoPermission ()
        throws Exception
    {
        login (Collections.singletonList ("xx"));

        /* hello */
        _wrapper.setControllerClass (PermissionClassActionController.class);
        _wrapper.service ("/controller/hello", _request, _response);

        /* result */
        assertEquals ("http error", 401, _response.getStatus ());
    }

    public void testPermissionClassWithPermission ()
        throws Exception
    {
        login (Collections.singletonList ("p1"));

        /* hello */
        _wrapper.setControllerClass (PermissionClassActionController.class);
        _wrapper.service ("/controller/hello", _request, _response);

        /* result */
        assertEquals ("http error", 200, _response.getStatus ());
    }


    public void testPermissionMethodNoPermission ()
        throws Exception
    {
        login (Collections.singletonList ("xx"));

        /* hello */
        _wrapper.setControllerClass (PermissionMethodActionController.class);
        _wrapper.service ("/controller/hello", _request, _response);

        /* result */
        assertEquals ("http error", 401, _response.getStatus ());
    }

    public void testPermissionMethodWithPermission ()
        throws Exception
    {
        login (Collections.singletonList ("p1"));

        /* hello */
        _wrapper.setControllerClass (PermissionMethodActionController.class);
        _wrapper.service ("/controller/hello", _request, _response);

        /* result */
        assertEquals ("http error", 200, _response.getStatus ());
    }


    //-- private
    private void login ()
    {
        _request.getSession ().setAttribute (ActionController.SESSION_USER_ID, 1L);
    }
    private void login (List permissions)
    {
        login ();
        _permissions = permissions;
    }

    private ContainerContext createContainerContext ()
    {
        ContainerContextImpl cc = new ContainerContextImpl ();
        cc.registerActionInterceptor (new ActionRequireUserInterceptor ());
        cc.register (UserService.class, createUserService ());
        return cc;
    }

    private UserService createUserService ()
    {
        return new UserService () {

            public User findUser (long id)
            {
                return createUser (id);
            }

            public void init (ServiceContext context)
            {
            }

            public void destroy ()
            {
            }

            public String encryptPassword (String password)
            {
                throw new UnsupportedOperationException ("Not supported yet.");
            }

            public boolean passwordMatches (String clearPassword, String encrpytedPassword)
            {
                throw new UnsupportedOperationException ("Not supported yet.");
            }
        };
    }

    private User createUser (final long id)
    {
        return new User () {

            public long getId ()
            {
                return id;
            }

            public String getName ()
            {
                throw new UnsupportedOperationException ("Not supported yet.");
            }

            public String getDisplayName ()
            {
                throw new UnsupportedOperationException ("Not supported yet.");
            }

            public String getEmail ()
            {
                throw new UnsupportedOperationException ("Not supported yet.");
            }

            public String getAttribute (String name)
            {
                throw new UnsupportedOperationException ("Not supported yet.");
            }

            public Locale getLocale ()
            {
                throw new UnsupportedOperationException ("Not supported yet.");
            }

            public boolean hasPermission (String permission)
            {
                return _permissions != null && _permissions.contains(permission);
            }
        };
    }


    //-- InnerClass
    @RequireUser
    public static class SecuredClassActionController
        extends ActionController
    {
        public void hello()
        {
            renderText ("hello");
        }
    }

    public static class SecuredMethodActionController
        extends ActionController
    {
        @RequireUser
        public void hello()
        {
            renderText ("hello");
        }
    }

    @RequireUser(permissions={"p1"})
    public static class PermissionClassActionController
        extends ActionController
    {
        public void hello()
        {
            renderText ("hello");
        }
    }


    public static class PermissionMethodActionController
        extends ActionController
    {
        @RequireUser (permissions={"p1"})
        public void hello()
        {
            renderText ("hello");
        }
    }
}
