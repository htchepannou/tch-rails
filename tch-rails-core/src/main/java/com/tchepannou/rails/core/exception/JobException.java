/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.exception;

/**
 * This exception is thrown when the rendering fails
 *
 * @author herve
 */
public class JobException
    extends Exception
{
    public JobException (String message, Throwable cause)
    {
        super (message, cause);
    }
}
