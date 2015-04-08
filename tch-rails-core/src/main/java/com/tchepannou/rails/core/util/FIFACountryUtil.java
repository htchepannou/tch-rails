/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author tcheer
 */
public class FIFACountryUtil
{
    //-- Static Attribute
    private static final FIFACountryUtil INSTANCE = new FIFACountryUtil ();
    private Map<String, String> __countries;
    private String[] __fifaCodeCountries;

    //-- Constructor
    private FIFACountryUtil ()
    {

    }

    //-- Public method
    public static FIFACountryUtil getInstance ()
    {
        return INSTANCE;
    }

    /**
     * Return an array of all the 2 letters ISO code ordered by the country iso2Country
     */
    public String[] getFIFACodeCoutries ()
    {
        if (__fifaCodeCountries == null)
        {
            synchronized (this)
            {
                loadCountries ();
            }
        }
        return __fifaCodeCountries;
    }

    /**
     * Returns the name of a country by its 2 letters ISO code
     */
    public String getCountryName (String fifaCodeCountry)
    {
        return getCountries ().get (fifaCodeCountry.toUpperCase ());
    }

    //-- Private method
    private Map<String, String> getCountries ()
    {
        if (__countries == null)
        {
            synchronized (this)
            {
                loadCountries ();
            }
        }
        return __countries;
    }

    private void loadCountries ()
    {
        try
        {
            InputStream in = getClass ().getClassLoader ().getResourceAsStream ("fifa_countries.properties");
            final Properties p = new Properties ();
            List<String> fifaCodeCountries = new ArrayList<String> ();
            p.load (in);

            /* Load the countries */
            __countries = new HashMap<String, String> ();
            for (Object key : p.keySet ())
            {
                String fifaCodeCountry = key.toString ();
                String value = p.getProperty (fifaCodeCountry);

                __countries.put (fifaCodeCountry, value);
                fifaCodeCountries.add (fifaCodeCountry);
            }

            /* Sort the iso codes */
            Comparator c = new Comparator () {

                public int compare (Object o1, Object o2)
                {
                    String n1 = __countries.get (o1.toString ());
                    String n2 = __countries.get (o2.toString ());
                    return n1.compareTo (n2);
                }
            };
            Collections.sort (fifaCodeCountries, c);
            __fifaCodeCountries = fifaCodeCountries.toArray (new String[] {});
        }
        catch (IOException e)
        {
            throw new RuntimeException ("Unable to load countries information", e);
        }
    }

}
