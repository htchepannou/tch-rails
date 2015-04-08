/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails;

import com.tchepannou.util.IOUtil;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author herve
 */
public class ObjectFactory
{
//    public static Container createContainer ()
//    {
//        MockServletContext sc = new MockServletContext ();
//        sc.setContextPath ("/");
//
//        Container obj = new Container ();
//        obj.setServletContext (sc);
//        obj.registerBasePackage ("com.tchepannou.rails.container");
//
//        obj.registerActionInterceptor (new LoginInterceptor ());
//        obj.registerActionInterceptor (new DebugInterceptor ());
//
//        return obj;
//    }

//    public static ContainerContext createContainerContext ()
//    {
//        final ServletContext sc = new MockServletContext ();
//
//        return new ContainerContext () {
//
//            public ServletContext getServletContext ()
//            {
//                return sc;
//            }
//
//            public EntityManagerFactory getEntityManagerFactory ()
//            {
//                throw new UnsupportedOperationException ("Not supported yet.");
//            }
//
//            public List<ActionInterceptor> getActionInterceptors ()
//            {
//                throw new UnsupportedOperationException ("Not supported yet.");
//            }
//
//            public Service findService (Class<? extends Service> type)
//            {
//                throw new UnsupportedOperationException ("Not supported yet.");
//            }
//        };
//    }
//
//    public static ActionContext createActionContext (final ContainerContext cc)
//    {
//        final MockHttpServletRequest req = new MockHttpServletRequest (cc.getServletContext ());
//        final MockHttpServletResponse resp = new MockHttpServletResponse ();
//
//        return new ActionContext () {
//
//            public ContainerContext getContainerContext ()
//            {
//                return cc;
//            }
//
//            public HttpServletRequest getRequest ()
//            {
//                return req;
//            }
//
//            public HttpServletResponse getResponse ()
//            {
//                return resp;
//            }
//
//            public void setRollback (boolean rollback)
//            {
//
//            }
//
//            public boolean isRollback ()
//            {
//                return false;
//            }
//        };
//    }
//
    public static File createTXTFile (String content)
        throws IOException
    {
        File f = File.createTempFile ("file", ".txt");
        IOUtil.copy (new ByteArrayInputStream (content.getBytes ()), f);
        return f;
    }

    public static InputStream createInputStream (String content)
    {
        return new ByteArrayInputStream (content.getBytes ());
    }
}
