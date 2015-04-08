/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.api;

/**
 *
 * @author herve
 */
public interface MessageInterceptor
    extends Interceptor
{
    //-- Public methods
    /**
     * This method is called prior accessing a controller
     *
     * @param controller controller
     */
    public int before (MessageController controller);

    /**
     * This method is called after accessing a controller
     *
     * @param controller controller
     */
    public void after (MessageController controller, Throwable cause);
}
