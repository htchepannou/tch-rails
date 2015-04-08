/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.api;

import javax.mail.MessagingException;

/**
 * This service is used for sending email using SMTP protocol
 *
 * @author herve
 */
public interface MailService
    extends Service
{
    /**
     * Send a email
     *
     * @param controller
     * 
     * @throws MessagingException
     */
    public void send (MailController controller)
        throws MessagingException;
}
