/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.api;

import java.util.List;
import java.util.Map;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author herve
 */
public interface HttpRequestWrapper
{
    public void init (ActionController controller);

    public Map<String, Object> getParameters ();
    
    public List<FileItem> getFiles ();
}
