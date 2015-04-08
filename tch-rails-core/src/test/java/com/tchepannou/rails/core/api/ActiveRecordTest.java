/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.api;

import com.tchepannou.util.DateUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;

/**
 *
 * @author herve
 */
public class ActiveRecordTest 
    extends TestCase
{
    //-- Public
    public void testPopulate ()
    {
        Record r = new Record ();
        Map properties = new HashMap ();
        properties.put ("id", "1");
        properties.put ("age", "10");
        properties.put ("name", "Ray Sponsible");
        properties.put ("birthDate", DateUtil.createDate(1973, 12, 27));
        properties.put ("active", "true");

        r.populate (properties);

        assertEquals ("id", 1, r.getId ());
        assertEquals ("age", 10, r.getAge ());
        assertEquals ("name", "Ray Sponsible", r.getName ());
        assertEquals ("birthDate", DateUtil.createDate(1973, 12, 27), r.getBirthDate ());
        assertTrue ("active", r.isActive());
    }

    public void testProperties()
    {
        Record r = new Record ();
        Map properties = new HashMap ();
        properties.put ("id", "1");
        properties.put ("age", "10");
        properties.put ("name", "Ray Sponsible");
        properties.put ("birthDate", DateUtil.createDate(1973, 12, 27));
        properties.put ("active", "true");

        r.populate (properties);
        Map p = r.toProperties ();

        assertEquals ("id", new Long(1), p.get("id"));
        assertEquals ("age", new Integer(10), p.get("age"));
        assertEquals ("name", "Ray Sponsible", p.get("name"));
        assertEquals ("birthDate", DateUtil.createDate(1973, 12, 27), p.get("birthDate"));
        assertTrue ("active", (Boolean)p.get("active"));
    }

    //-- Inner classes
    public static class Record
        extends ActiveRecord
    {
        private long _id;
        private int _age;
        private String _name;
        private Date _birthDate;
        private boolean _active;

        public long getId ()
        {
            return _id;
        }

        public void setId (long id)
        {
            this._id = id;
        }

        public int getAge ()
        {
            return _age;
        }

        public void setAge (int age)
        {
            this._age = age;
        }

        public String getName ()
        {
            return _name;
        }

        public void setName (String name)
        {
            this._name = name;
        }

        public Date getBirthDate ()
        {
            return _birthDate;
        }

        public void setBirthDate (Date birthDate)
        {
            this._birthDate = birthDate;
        }

        public boolean isActive ()
        {
            return _active;
        }

        public void setActive (boolean active)
        {
            this._active = active;
        }
    }
}
