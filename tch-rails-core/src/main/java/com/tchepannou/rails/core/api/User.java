/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.api;

import java.util.Locale;

/**
 * This interface defines a user of the site
 *
 * @author herve
 */
public interface User
{
    public long getId ();
    
    public String getName ();

    public String getDisplayName ();

    public String getEmail ();
    
    public String getAttribute (String name);

    public Locale getLocale ();

    public boolean hasPermission (String permission);
}
