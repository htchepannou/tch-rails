/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Defines a set of methods that {@link ActionController} uses to communicate with its container.
 * 
 * @author herve
 */
public interface ActionContext
{
    public ContainerContext getContainerContext ();

    public HttpServletRequest getRequest ();

    public HttpServletResponse getResponse ();

    /**
     * Call this method to rollback the current transaction
     */
    public void setRollback (boolean rollback);

    /**
     * Returns <code>true</code> if the current transaction should be rolled-back
     * @return
     */
    public boolean isRollback ();
}
