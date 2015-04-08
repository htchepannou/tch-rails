/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.api;

import com.tchepannou.rails.core.api.TextResource;
import junit.framework.TestCase;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 *
 * @author herve
 */
public class TextResourceTest extends TestCase {
    
    public TextResourceTest(String testName) {
        super(testName);
    }

    public void testOutput ()
        throws Exception
    {
        TextResource r = new TextResource ("test", "text/plain");
        MockHttpServletResponse resp = new MockHttpServletResponse ();
        r.output (resp);

        assertEquals ("test", resp.getContentAsString ());
    }

}
