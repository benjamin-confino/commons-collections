/*
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons//collections/src/test/org/apache/commons/collections/Attic/TestListIteratorWrapper.java,v 1.1 2002/04/09 16:40:58 morgand Exp $
 * $Revision: 1.1 $
 * $Date: 2002/04/09 16:40:58 $
 *
 * ====================================================================
 *
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999-2001 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.apache.commons.collections;

import junit.framework.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Tests the ListIteratorWrapper to insure that it simulates
 * a ListIterator correctly.
 *
 * @author Morgan Delagrange
 * @version $Id: TestListIteratorWrapper.java,v 1.1 2002/04/09 16:40:58 morgand Exp $
 */
public class TestListIteratorWrapper extends TestIterator {

    protected String[] testArray = {
        "One", "Two", "Three", "Four", "Five", "Six"
    };

    protected List list1 = null;

    public static Test suite() {
        return new TestSuite(TestListIteratorWrapper.class);
    }

    public TestListIteratorWrapper(String testName) {
        super(testName);
    }

    public void setUp() {
        list1 = new ArrayList();
        list1.add("One");
        list1.add("Two");
        list1.add("Three");
        list1.add("Four");
        list1.add("Five");
        list1.add("Six");
    }

    public Iterator makeEmptyIterator() {
        ArrayList list = new ArrayList();
        return new ListIteratorWrapper(list.iterator());
    }

    public Iterator makeFullIterator() {
        Iterator i = list1.iterator();

        return new ListIteratorWrapper(i);
    }

    /**
     * Return a new, empty {@link Object} to used for testing.
     */
    public Object makeObject() {
        return makeFullIterator();
    }

    public void testIterator() {
        ListIterator iter = (ListIterator) makeFullIterator();
        for ( int i = 0; i < testArray.length; i++ ) {
            Object testValue = testArray[i];            
            Object iterValue = iter.next();

            assertEquals( "Iteration value is correct", testValue, iterValue );
        }

        assertTrue("Iterator should now be empty", ! iter.hasNext() );

        try {
            Object testValue = iter.next();
        } catch (Exception e) {
            assertTrue("NoSuchElementException must be thrown", 
                       e.getClass().equals((new NoSuchElementException()).getClass()));
        }

        // now, read it backwards
        for (int i = testArray.length - 1; i > -1; --i) {
            Object testValue = testArray[i];
            Object iterValue = iter.previous();

            assertEquals( "Iteration value is correct", testValue, iterValue );
        }

        try {
            Object testValue = iter.previous();
        } catch (Exception e) {
            assertTrue("NoSuchElementException must be thrown", 
                       e.getClass().equals((new NoSuchElementException()).getClass()));
        }

        // now, read it forwards again
        for ( int i = 0; i < testArray.length; i++ ) {
            Object testValue = testArray[i];            
            Object iterValue = iter.next();

            assertEquals( "Iteration value is correct", testValue, iterValue );
        }

    }

    public void testRemove() {
        Iterator iter = (Iterator) makeFullIterator();

        try {
            iter.remove();
            fail("FilterIterator does not support the remove() method");
        } catch (UnsupportedOperationException e) {

        }

    }

}

