/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.api;

import com.tchepannou.rails.core.util.EntityManagerThreadLocal;
import com.tchepannou.rails.core.util.I18nThreadLocal;
import com.tchepannou.util.MimeUtil;
import com.tchepannou.util.StringUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;

/**
 *
 * @author herve
 */
public class MailController
{
    //-- Static attribute
    private static final Logger LOG = Logger.getLogger (MailController.class);
    
    //-- Attributes
    private String __name;
    private MailContext _context;
    private String _from;
    private List<String> _to = new ArrayList<String> ();
    private List<String> _cc = new ArrayList<String> ();
    private List<String> _bcc = new ArrayList<String> ();
    private List<File> _attachments = new ArrayList<File> ();
    private String _contentType = MimeUtil.TEXT;
    private String _subject;
    private String _body;
    private String _encoding = "utf-8";
    private Map<String, Object> _viewVariables = new HashMap<String, Object> ();


    //-- Public methods
    public void addTo (String to)
    {
        add(_to, to);
    }

    public void addCc (String cc)
    {
        add(_cc, cc);
    }

    public void addBcc (String bcc)
    {
        add(_bcc, bcc);
    }

    private void add(List<String> lst, String email)
    {
        if (!StringUtil.isEmpty (email) && StringUtil.isEmail (email) && !lst.contains (email))
        {
            lst.add(email);
        }
    }

    public void addAttachment (File attachment)
    {
        _attachments.add (attachment);
    }

    public boolean hasAttachments ()
    {
        return !_attachments.isEmpty ();
    }

    public boolean hasRecipients ()
    {
        return !_to.isEmpty () || !_cc.isEmpty () || !_bcc.isEmpty ();
    }

    public I18n getI18n ()
    {
        return I18nThreadLocal.getI18n ();
    }



    /**
     * Add a view variable
     *
     * @param name  Name of the variable
     * @param value Value of the variable
     */
    public void addViewVariable (String name, Object value)
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace ("addViewVaraible(" + name + "," + value + ")");
        }

        _viewVariables.put (name, value);
    }

    /**
     * Add a set of view variables
     */
    public void addViewVariables (Map<String, Object> vars)
    {
        if (vars != null)
        {
            _viewVariables.putAll (vars);
        }
    }

    public String getFrom ()
    {
        return _from;
    }

    public List<String> getTo ()
    {
        return _to;
    }

    public List<String> getBcc ()
    {
        return _bcc;
    }

    public List<String> getCc ()
    {
        return _cc;
    }

    public String getSubject ()
    {
        return _subject;
    }

    public String getBody ()
    {
        return _body;
    }

    public void setBody (String body)
    {
        _body = body;
    }

    public String getContentType ()
    {
        return _contentType;
    }

    public String getEncoding ()
    {
        return _encoding;
    }

    public MailContext getMailContext ()
    {
        return _context;
    }

    public void setMailContext (MailContext context)
    {
        _context = context;
    }

    public void setFrom (String from)
    {
        this._from = from;
    }

    public void setSubject (String subject)
    {
        this._subject = subject;
    }

    public void setEncoding (String encoding)
    {
        _encoding = encoding;
    }

    public void setContentType (String contentType)
    {
        _contentType = contentType;
    }

    public Map<String, Object> getViewVariables ()
    {
        return _viewVariables;
    }

    public Map<String, Object> getMailData ()
    {
        return _context.getData ();
    }
    
    public EntityManager getEntityManager ()
    {
        return EntityManagerThreadLocal.getEntityManager ();
    }

    /**
     * Returns the name of the controller
     */
    public String getName ()
    {
        if (__name == null)
        {
            __name = getName (getClass ());
        }
        return __name;
    }

    public List<File> getAttachments ()
    {
        return _attachments;
    }

    /**
     * Returns the computed name of a controller
     */
    public static String getName (Class<? extends MailController> clazz)
    {
        String fqcn = clazz.getName ();
        int i = fqcn.lastIndexOf ('.');
        String name = i>0 ? fqcn.substring (i+1) : fqcn;

        String xname = name.endsWith ("MailController")
            ? name.substring (0, name.length ()-14)
            : name;
        return  xname.substring (0, 1).toLowerCase () + xname.substring (1);
    }
}
