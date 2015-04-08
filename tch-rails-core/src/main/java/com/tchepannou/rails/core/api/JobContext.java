/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.api;

/**
 *
 * @author herve
 */
public interface JobContext
{
    public ContainerContext getContainerContext ();

    public boolean isRollback ();

    public void setRollback (boolean rollback);
}
