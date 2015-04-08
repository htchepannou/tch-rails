/*
 * Copyright 2003 Jayson Falkner (jayson@jspinsider.com)
 * This code is from "Servlets and JavaServer pages; the J2EE Web Tier",
 * http://www.jspbook.com. You may freely use the code both commercially
 * and non-commercially. If you like the code, please pick up a copy of
 * the book and help support the authors, development of more free code,
 * and the JSP/Servlet/J2EE community.
 */
package com.tchepannou.rails.engine.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * Zip stream
 *
 * @author herve
 */
public class GZIPResponseStream
    extends ServletOutputStream
{
    //-- Attributes
    protected ByteArrayOutputStream _baos = null;
    protected GZIPOutputStream _gzipstream = null;
    protected boolean _closed = false;
    protected HttpServletResponse _response = null;
    protected ServletOutputStream _output = null;

    //-- Constructor
    public GZIPResponseStream (HttpServletResponse response)
        throws IOException
    {
        super ();
        _closed = false;
        this._response = response;
        this._output = response.getOutputStream ();
        _baos = new ByteArrayOutputStream ();
        _gzipstream = new GZIPOutputStream (_baos);
    }

    //-- ServletOutputStream overrides
    @Override
    public void close ()
        throws IOException
    {
        if ( _closed )
        {
            throw new IOException ("This output stream has already been closed");
        }
        _gzipstream.finish ();

        byte[] bytes = _baos.toByteArray ();


        _response.addHeader ("Content-Length", Integer.toString (bytes.length));
        _response.addHeader ("Content-Encoding", "gzip");
        _output.write (bytes);
        _output.flush ();
        _output.close ();
        _closed = true;
    }

    @Override
    public void flush ()
        throws IOException
    {
        if ( _closed )
        {
            throw new IOException ("Cannot flush a closed output stream");
        }
        _gzipstream.flush ();
    }

    @Override
    public void write (int b)
        throws IOException
    {
        if ( _closed )
        {
            throw new IOException ("Cannot write to a closed output stream");
        }
        _gzipstream.write (( byte ) b);
    }

    @Override
    public void write (byte[] b)
        throws IOException
    {
        write (b, 0, b.length);
    }

    @Override
    public void write (byte[] b, int off, int len)
        throws IOException
    {
        //System.out.println("writing...");
        if ( _closed )
        {
            throw new IOException ("Cannot write to a closed output stream");
        }
        _gzipstream.write (b, off, len);
    }

    public boolean closed ()
    {
        return this._closed;
    }

    public void reset ()
    {
        //noop
    }
}
