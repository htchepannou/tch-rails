/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.core.api;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import junit.framework.TestCase;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 *
 * @author herve
 */
public class RedirectResourceTest
    extends TestCase
{
    public RedirectResourceTest (String testName)
    {
        super (testName);
    }

    public void testOutputRedirectToURL ()
        throws Exception
    {
        HttpServletRequest req = new MockHttpServletRequest ();
        Map params = new HashMap ();
        params.put ("keyword", "ray");
        Resource r = new RedirectResource ("http://www.google.ca", params, req);
        MockHttpServletResponse resp = new MockHttpServletResponse ();
        r.output (resp);

        assertEquals ("http://www.google.ca?keyword=ray", resp.getRedirectedUrl ());
    }

    public void testOutputRedirectToController ()
        throws Exception
    {
        HttpServletRequest req = new MockHttpServletRequest ();
        Map params = new HashMap ();
        params.put ("keyword", "ray");
        Resource r = new RedirectResource ("/test", params, req);
        MockHttpServletResponse resp = new MockHttpServletResponse ();
        r.output (resp);
        assertEquals (req.getContextPath () + "/test?keyword=ray", resp.getRedirectedUrl ());
    }
}
