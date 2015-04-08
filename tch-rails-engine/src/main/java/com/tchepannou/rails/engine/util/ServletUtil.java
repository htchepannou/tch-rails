/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.engine.util;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author herve
 */
public class ServletUtil
{
    public static String toRequestURL (ServletRequest req)
    {
        if (req instanceof HttpServletRequest)
        {
            return toRequestURL ((HttpServletRequest)req);
        }
        else
        {
            return req.getScheme () + "://" + req.getServerName () + ":" + req.getServerPort ();
        }
    }

    public static String toRequestURL (HttpServletRequest req)
    {
        StringBuffer sb = new StringBuffer ();
        sb.append (req.getRequestURL ());

        String qs = req.getQueryString ();
        if (qs != null)
        {
            sb.append ("?").append (qs);
        }
        return sb.toString ();
    }
}
