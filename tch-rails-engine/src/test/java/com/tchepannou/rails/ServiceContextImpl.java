/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails;

import com.tchepannou.rails.core.api.ContainerContext;
import com.tchepannou.rails.core.api.ServiceContext;


/**
 *
 * @author herve
 */
public class ServiceContextImpl 
    implements ServiceContext
{
    private ContainerContext _cc;
    
    public ServiceContextImpl(ContainerContext cc)
    {
        _cc = cc;
    }

    public ContainerContext getContainerContext ()
    {
        return _cc;
    }

}
