/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.util;

import com.tchepannou.rails.core.api.I18n;
import java.util.Locale;

/**
 *
 * @author herve
 */
public class I18nThreadLocal
{
    //-- Static Attributes
    private static ThreadLocal<I18n> _i18n = new ThreadLocal<I18n> ();


    //-- Public method
    public static I18n getI18n (Locale locale)
    {
        I18n i18n = _i18n.get ();
        if (i18n == null)
        {
            i18n = new I18n (I18n.DEFAULT_BUNDLE_NAME, locale);
            _i18n.set (i18n);
        }
        return i18n;
    }

    public static I18n getI18n ()
    {
        return getI18n (Locale.getDefault ());
    }
//
//    public static void init (Locale user, Locale site)
//    {
//        I18n i18n = new I18n (I18n.DEFAULT_BUNDLE_NAME, user, site);
//        _i18n.set (i18n);
//    }
}
