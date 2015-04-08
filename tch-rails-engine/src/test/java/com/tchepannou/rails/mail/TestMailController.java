/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.mail;

import com.tchepannou.rails.core.api.MailController;

/**
 *
 * @author herve
 */
public class TestMailController
    extends MailController
{
    public void hello ()
    {
        addViewVariables (getMailContext ().getData ());
    }
}
