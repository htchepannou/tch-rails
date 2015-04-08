/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.core.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * This controller handle all static files
 * 
 * @author herve
 */
public abstract class AbstractStaticActionController
    extends ActionController
{
    //-- Static Attribute
    private static final Logger LOG = Logger.getLogger (AbstractStaticActionController.class);
    private static final List<ActionInterceptor> INTERCEPTORS = new ArrayList<ActionInterceptor> ();


    //-- Public method
    /**
     * This is the method called for rendering the request file
     *
     * @return Requested file
     */
    public void render ()
        throws IOException
    {
        if ( LOG.isTraceEnabled () )
        {
            LOG.trace ("render ()");
        }

        File file = getFile ();
        if (LOG.isDebugEnabled ())
        {
            LOG.debug (getRequest ().getPathInfo () + "->" + file.getAbsolutePath ());
        }
        if ( file != null && file.exists () && !file.isDirectory () )
        {
            renderFile (file);
            setLastModificationDate (file.lastModified ());
        }
        else
        {
            if (LOG.isDebugEnabled ())
            {
                LOG.debug ("File not found: " + file.getAbsolutePath ());
            }
            sendError (404, "File not found: " + file.getAbsolutePath ());
        }
    }

    /**
     * Returns the file to output
     */
    protected File getFile ()
    {
        String path = getRequest ().getPathInfo ();
        String xpath = getActionContext ().getContainerContext ().getServletContext ().getRealPath (path);
        return xpath != null ? new File (xpath) : null;
    }

    //-- ActionController override
    @Override
    public String convertPathToAction (String p)
    {
        return "render";
    }

    @Override
    public List<ActionInterceptor> getActionInterceptors ()
    {
        return INTERCEPTORS;
    }
}
