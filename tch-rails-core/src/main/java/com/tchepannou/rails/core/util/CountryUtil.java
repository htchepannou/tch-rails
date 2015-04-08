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
 * This is a utility class for providing information about countries.
 * This class overcome limitation of {@link java.util.Locale}.
 *
 * @author herve
 */
public class CountryUtil
{
    //-- Static Attribute
    private static final CountryUtil INSTANCE = new CountryUtil ();
    private Map<String, String> __countries;
    private String[] __iso2Countries;

    //-- Constructor
    private CountryUtil ()
    {

    }

    //-- Public method
    public static CountryUtil getInstance ()
    {
        return INSTANCE;
    }

    /**
     * Return an array of all the 2 letters ISO code ordered by the country iso2Country
     */
    public String[] getISO2Coutries ()
    {
        if (__iso2Countries == null)
        {
            synchronized (this)
            {
                loadCountries ();
            }
        }
        return __iso2Countries;
    }

    /**
     * Returns the name of a country by its 2 letters ISO code
     */
    public String getCountryName (String iso2Code)
    {
        return getCountries ().get (iso2Code.toUpperCase ());
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
            InputStream in = getClass ().getClassLoader ().getResourceAsStream ("countries.properties");
            final Properties p = new Properties ();
            List<String> iso2Coutries = new ArrayList<String> ();
            p.load (in);

            /* Load the countries */
            __countries = new HashMap<String, String> ();
            for (Object key : p.keySet ())
            {
                String iso2Country = key.toString ();
                String value = p.getProperty (iso2Country);

                __countries.put (iso2Country, value);
                iso2Coutries.add (iso2Country);
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
            Collections.sort (iso2Coutries, c);
            __iso2Countries = iso2Coutries.toArray (new String[] {});
        }
        catch (IOException e)
        {
            throw new RuntimeException ("Unable to load countries information", e);
        }
    }
}
