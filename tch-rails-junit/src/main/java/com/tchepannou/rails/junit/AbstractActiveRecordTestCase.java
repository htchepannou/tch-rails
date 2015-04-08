/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.junit;


/**
 * Base class for all {@link ActiveRecord} test cases
 * @author herve
 */
public abstract class AbstractActiveRecordTestCase
    extends AbstractTestCase
{
    public AbstractActiveRecordTestCase()
    {

    }

    public AbstractActiveRecordTestCase(String name)
    {
        super (name);
    }
}
