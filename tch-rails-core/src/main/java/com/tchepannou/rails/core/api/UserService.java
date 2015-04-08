/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.api;


/**
 * This is the service that returns user's information
 * 
 * @author herve
 */
public interface UserService 
    extends Service
{
    /**
     * Returns a user by its ID or <code>null</code> if not found
     *
     * @param id ID of the user
     */
    public User findUser (long id);

    /**
     * Encrypt a password and returnes it
     */
    public String encryptPassword (String password);

    /**
     * Test if a clear password matches an encripted password
     */
    public boolean passwordMatches (String clearPassword, String encrpytedPassword);
}
