/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.api;

import com.tchepannou.util.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.fileupload.FileItem;

/**
 * Default implementation of P{@link HttpRequestWrapper}
 * 
 * @author herve
 */
class HttpRequestWrapperSimple
    implements HttpRequestWrapper
{
    //-- Attribute
    private Map<String, Object> _parameters = new HashMap<String, Object> ();
    private List<FileItem> _files = new ArrayList<FileItem> ();

    
    public void init (ActionController controller)
    {
        Map map = controller.getRequest ().getParameterMap();
        for (Iterator it = map.entrySet().iterator() ; it.hasNext () ; )
        {
            Map.Entry entry = (Map.Entry)it.next();
            Object value = entry.getValue();
            if (value instanceof String[])
            {
                String[] array = StringUtil.trim((String[])value);
                if (array.length == 1)
                {
                    value = array[0];
                }
            }
            else if (value instanceof String)
            {
                value = StringUtil.trim ((String)value);
            }
            _parameters.put ((String)entry.getKey(), value);
        }
    }

    public Map<String, Object> getParameters ()
    {
        return _parameters;
    }

    public List<FileItem> getFiles ()
    {
        return _files;
    }


    //
}
