/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.service;

import com.tchepannou.rails.core.api.ServiceContext;
import com.tchepannou.rails.core.api.User;
import com.tchepannou.rails.core.api.UserService;
import com.tchepannou.rails.core.exception.InitializationException;
import com.tchepannou.util.StringUtil;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * Implementation of {@link UserService} that loads the options from the
 * file <code>/WEB-INF/rails-user.properties</code>
 *
 * @author herve
 */
public class PropertiesUserService
    extends AbstractService
    implements UserService
{
    //-- Static Attribute
    private static final Logger LOG = Logger.getLogger (PropertiesUserService.class);

    //-- Attributes
    private Properties _properties = new Properties ();

    //-- UserService override
    public User findUser (long id)
    {
        String name = _properties.getProperty ("user." + id + ".name");
        if (name != null)
        {
            return new UserImpl (id);
        }
        else
        {
            return null;
        }
    }

    //-- Service overrides
    @Override
    public void init (ServiceContext context)
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace("init(" + context + ")");
        }
        super.init (context);

        try
        {
            _properties = loadConfigurationAsProperties ("rails-user.properties");
        }
        catch (FileNotFoundException e)
        {
            LOG.warn ("User configuration not available");
        }
        catch (IOException e)
        {
            throw new InitializationException ("Initialization error", e);
        }
    }

    @Override
    public void destroy ()
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace("destroy()");
        }
        super.destroy ();

        _properties.clear ();
    }

    public String encryptPassword (String password)
    {
        return password;
    }

    public boolean passwordMatches (String clearPassword, String encrpytedPassword)
    {
        return clearPassword != null
            ? clearPassword.equals (encrpytedPassword)
            : encrpytedPassword == null;
    }


    //-- Implementation Method
    /**
     * Implementation of {@link User}
     */
    private class UserImpl
        implements User
    {
        //-- Attribute
        private long _id;

        //-- Constructor
        public UserImpl (long id)
        {
            _id = id;
        }

        //-- User overrides
        public long getId ()
        {
            return _id;
        }

        public String getName ()
        {
            return getAttribute ("name");
        }

        public String getDisplayName ()
        {
            return getAttribute ("displayName");
        }

        public String getEmail ()
        {
            return getAttribute ("email");
        }

        public String getAttribute (String name)
        {
            return _properties.getProperty ("user." + _id + "." + name);
        }

        public Locale getLocale ()
        {
            String locale = getAttribute ("locale");
            for (Locale loc : Locale.getAvailableLocales ())
            {
                if (loc.toString ().equals(locale))
                {
                    return loc;
                }
            }
            return null;
        }

        public boolean hasPermission (String permission)
        {
            String permissions = getAttribute ("permissions");
            if (StringUtil.isEmpty (permissions))
            {
                return false;
            }
            else
            {
                String[] array = permissions.split (",");
                for (String s : array)
                {
                    if (permission.equals (s.trim ()))
                    {
                        return true;
                    }
                }
                return false;
            }
        }
    }
}
