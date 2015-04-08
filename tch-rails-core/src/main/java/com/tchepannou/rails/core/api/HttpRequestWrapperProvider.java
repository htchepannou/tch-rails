/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.api;

/**
 *
 * @author herve
 */
public interface HttpRequestWrapperProvider
{
    public HttpRequestWrapper getInstance (ActionController controller);
}
