/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.sample.action;

import com.tchepannou.rails.core.annotation.Action;
import com.tchepannou.rails.core.annotation.WebTemplate;
import com.tchepannou.rails.core.api.ActionController;
import com.tchepannou.util.IOUtil;
import com.tchepannou.util.MimeUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.apache.log4j.Logger;

/**
 *
 * @author herve
 */
@Action
public class RenderActionController
    extends ActionController
{
    private static final Logger LOG = Logger.getLogger (RenderActionController.class);

    @WebTemplate (name="default")
    public void view()
    {
    }

    @WebTemplate (name="default")
    public void text()
    {
        renderText ("this is an example of text");
        expiresIn (5);
    }

    public void pdf ()
    {
        InputStream in = getClass ().getResourceAsStream ("/sample.pdf");
        renderStream (in, MimeUtil.PDF);
    }

    public void file ()
        throws IOException
    {
        InputStream in = getClass ().getResourceAsStream ("/sample.pdf");
        File out = File.createTempFile ("sample", ".pdf");
        IOUtil.copy (in, out);
        System.out.println (">>>> file=" + out.getName ());
        renderFile (out);
    }

    public void google ()
    {
        LOG.debug ("google()");
        redirectTo ("http://www.google.ca");
    }
}
