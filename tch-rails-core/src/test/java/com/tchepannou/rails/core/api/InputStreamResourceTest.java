/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.api;

import com.tchepannou.rails.core.api.InputStreamResource;
import com.tchepannou.rails.ObjectFactory;
import com.tchepannou.rails.core.api.Resource;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import junit.framework.TestCase;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 *
 * @author herve
 */
public class InputStreamResourceTest extends TestCase {
    
    public InputStreamResourceTest(String testName) {
        super(testName);
    }

    public void testOutput ()
        throws Exception
    {
        InputStream in = ObjectFactory.createInputStream ("test");
        InputStreamResource r = new InputStreamResource (in, "text/plain");
        MockHttpServletResponse resp = new MockHttpServletResponse ();
        r.output (resp);

        assertEquals ("test", resp.getContentAsString ());
    }

}
