/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.junit;

import com.tchepannou.rails.core.api.ActionController;
import com.tchepannou.rails.core.api.HttpRequestWrapper;
import com.tchepannou.rails.core.api.HttpRequestWrapperProvider;
import com.tchepannou.rails.core.api.User;
import com.tchepannou.util.IOUtil;
import com.tchepannou.util.MimeUtil;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.fileupload.FileItem;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 *
 * @author herve
 */
public abstract class AbstractActionControllerTestCase
    extends AbstractControllerTestCase
{
    //-- Attribute
    private MockHttpServletRequest _request;
    private MockHttpServletResponse _response;
    private User _user;

    //-- Constructor
    public AbstractActionControllerTestCase ()
    {
    }

    public AbstractActionControllerTestCase (String name)
    {
        super (name);
    }

    //-- Test
    @Override
    protected void setUp ()
        throws Exception
    {
        super.setUp ();

        _request = new MockHttpServletRequest (getServletContext ());
        _response = new MockHttpServletResponse ();
    }

    //-- Protected methods
    protected void login (User user)
    {
        _user = user;
        getRequest ().getSession ().setAttribute (ActionController.SESSION_USER_ID, user.getId ());
    }

    protected User getUser ()
    {
        return _user;
    }

    public ActionController service (String path)
        throws IOException
    {
        return service (path, null, null);
    }

    public ActionController service (String path, File file)
        throws IOException
    {
        return service (path, null, file);
    }

    public ActionController service (String path, Map params)
        throws IOException
    {
        return service (path, params, null);
    }

    public ActionController service (String path, final Map params, final File file)
        throws IOException
    {
        _request.setPathInfo (path);
        _response = new MockHttpServletResponse (); // IMPORTANT: create a new instance per request

        ActionController._requestWrapperProvider = new HttpRequestWrapperProvider ()
        {
            public HttpRequestWrapper getInstance (ActionController controller)
            {
                return new HttpRequestWrapperImpl (params, file);
            }
        };

        return getEngine ().getActionContainer ().service (_request, _response);
    }

    protected MockHttpServletRequest getRequest ()
    {
        return _request;
    }

    protected MockHttpServletResponse getResponse ()
    {
        return _response;
    }

    protected boolean isRequestSuccessful ()
    {
        return _response.getStatus () == 200;
    }

    protected String getResponseContextAsString ()
        throws IOException
    {
        return _response.getContentAsString ();
    }

    //-- Inner classes
    /**
     * Request wrapper
     */
    private static class HttpRequestWrapperImpl
        implements HttpRequestWrapper
    {
        //--- Attribute
        private Map _parameters;
        private List<FileItem> _files;

        //-- Attributes
        public HttpRequestWrapperImpl (Map parameters, File file)
        {
            _parameters = parameters;
            _files = new ArrayList<FileItem> ();

            if (file != null)
            {
                FileItem fi = createFileItem (file);
                _files.add (fi);
            }
        }

        //--- Attributes
        public void init (ActionController controller)
        {
        }

        public Map<String, Object> getParameters ()
        {
            return _parameters;
        }

        public List<FileItem> getFiles ()
        {
            return _files;
        }

        private FileItem createFileItem (final File f)
        {
            return new FileItemImpl (f);
        }
    }

    //-- Inner class
    /**
     *
     */
    private static class FileItemImpl
        implements FileItem
    {
        private File _file;

        public FileItemImpl (File file)
        {
            _file = file;
        }

        //-- FileItem override
        public InputStream getInputStream ()
            throws IOException
        {
            return new ByteArrayInputStream (get ());
        }

        public String getContentType ()
        {
            return MimeUtil.getInstance ().getMimeTypeByFile (_file.getAbsolutePath ());
        }

        public String getName ()
        {
            return _file.getName ();
        }

        public boolean isInMemory ()
        {
            return false;
        }

        public long getSize ()
        {
            return _file.length ();
        }

        public byte[] get ()
        {
            ByteArrayOutputStream out = new ByteArrayOutputStream ();
            try
            {
                IOUtil.copy (_file, out);
                return out.toByteArray ();
            }
            catch ( IOException e )
            {
                throw new RuntimeException (e);
            }
        }

        public String getString (String encoding)
            throws UnsupportedEncodingException
        {
            return new String (get (), encoding);
        }

        public String getString ()
        {
            try
            {
                return getString ("UTF-8");
            }
            catch ( IOException e )
            {
                throw new RuntimeException ();
            }
        }

        public void write (File file)
            throws Exception
        {
            IOUtil.copy (_file, file);
        }

        public void delete ()
        {
            throw new UnsupportedOperationException ("Not supported yet.");
        }

        public String getFieldName ()
        {
            return "file";
        }

        public void setFieldName (String name)
        {
        }

        public boolean isFormField ()
        {
            return false;
        }

        public void setFormField (boolean state)
        {
        }

        public OutputStream getOutputStream ()
            throws IOException
        {
            throw new UnsupportedOperationException ("Not supported yet.");
        }
    }
}
