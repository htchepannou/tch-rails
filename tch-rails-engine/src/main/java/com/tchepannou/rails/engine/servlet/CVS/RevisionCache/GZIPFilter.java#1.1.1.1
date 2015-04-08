/*
 * Copyright 2003 Jayson Falkner (jayson@jspinsider.com)
 * This code is from "Servlets and JavaServer pages; the J2EE Web Tier",
 * http://www.jspbook.com. You may freely use the code both commercially
 * and non-commercially. If you like the code, please pick up a copy of
 * the book and help support the authors, development of more free code,
 * and the JSP/Servlet/J2EE community.
 */
package com.tchepannou.rails.engine.servlet;

import com.tchepannou.rails.engine.util.ServletUtil;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * This filter encode servlet responses if the HTTP client supports <code>gzip</code> encoding.
 * 
 * @author herve
 */
public class GZIPFilter
    implements Filter
{
    //-- Static Attributes
    private static final Logger LOG = Logger.getLogger (GZIPFilter.class);

    //-- Filter overrides
    public void doFilter (ServletRequest req, ServletResponse res, FilterChain chain)
        throws IOException,
               ServletException
    {
        HttpServletRequest request = ( HttpServletRequest ) req;
        HttpServletResponse response = ( HttpServletResponse ) res;
        String encoding = request.getHeader ("accept-encoding");
        if ( encoding != null && encoding.indexOf ("gzip") != -1 )
        {
            if ( LOG.isTraceEnabled () )
            {
                LOG.trace ("doFilter(" + ServletUtil.toRequestURL (req) + ")");
            }

            GZIPResponseWrapper wrappedResponse = new GZIPResponseWrapper (response);
            try
            {
                chain.doFilter (request, wrappedResponse);
            }
            finally
            {
                wrappedResponse.finishResponse ();
            }
        }
        else
        {
            chain.doFilter (request, response);
        }
    }

    public void init (FilterConfig filterConfig)
    {
        LOG.info ("Initializing");
        LOG.info ("Initialized");
    }

    public void destroy ()
    {
        LOG.info ("Destroying");
        LOG.info ("Destroyed");
    }
}
