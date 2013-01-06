/*
 * $Id: PdfDictionary.java,v 1.27 2002/07/09 11:28:22 blowagie Exp $
 * $Name:  $
 *
 * Copyright 1999, 2000, 2001, 2002 Bruno Lowagie
 *
 *
 * The Original Code is 'iText, a free JAVA-PDF library'.
 *
 * The Initial Developer of the Original Code is Bruno Lowagie. Portions created by
 * the Initial Developer are Copyright (C) 1999, 2000, 2001, 2002 by Bruno Lowagie.
 * All Rights Reserved.
 * Co-Developer of the code is Paulo Soares. Portions created by the Co-Developer
 * are Copyright (C) 2000, 2001, 2002 by Paulo Soares. All Rights Reserved.
 *
 * Contributor(s): all the names of the contributors are added in the source code
 * where applicable.
 *
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 * 
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301, USA.
 *
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 * 
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301, USA.
 *
 *
 * If you didn't download this code from the following link, you should check if
 * you aren't using an obsolete version:
 * http://www.lowagie.com/iText/
 */

package com.lowagie.text.pdf;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Set;

/**
 * <CODE>PdfDictionary</CODE> is the Pdf dictionary object.
 * <P>
 * A dictionary is an associative table containing pairs of objects. The first element
 * of each pair is called the <I>key</I> and the second element is called the <I>value</I>.
 * Unlike dictionaries in the PostScript language, a key must be a <CODE>PdfName</CODE>.
 * A value can be any kind of <CODE>PdfObject</CODE>, including a dictionary. A dictionary is
 * generally used to collect and tie together the attributes of a complex object, with each
 * key-value pair specifying the name and value of an attribute.<BR>
 * A dictionary is represented by two left angle brackets (<<), followed by a sequence of
 * key-value pairs, followed by two right angle brackets (>>).<BR>
 * This object is described in the 'Portable Document Format Reference Manual version 1.3'
 * section 4.7 (page 40-41).
 * <P>
 *
 * @see		PdfObject
 * @see		PdfName
 * @see		BadPdfFormatException
 */

public class PdfDictionary extends PdfObject {
    
    // static membervariables (types of dictionary's)
    
/** This is a possible type of dictionary */
    public static final PdfName FONT = PdfName.FONT;
    
/** This is a possible type of dictionary */
    public static final PdfName OUTLINES = PdfName.OUTLINES;
    
/** This is a possible type of dictionary */
    public static final PdfName PAGE = PdfName.PAGE;
    
/** This is a possible type of dictionary */
    public static final PdfName PAGES = PdfName.PAGES;
    
/** This is a possible type of dictionary */
    public static final PdfName CATALOG = PdfName.CATALOG;
    
    // membervariables
    
/** This is the type of this dictionary */
    private PdfName dictionaryType = null;
    
/** This is the hashmap that contains all the values and keys of the dictionary */
    protected HashMap hashMap;
    
    // constructors
    
/**
 * Constructs an empty <CODE>PdfDictionary</CODE>-object.
 */
    
    public PdfDictionary() {
        super(DICTIONARY);
        hashMap = new HashMap();
    }
    
/**
 * Constructs a <CODE>PdfDictionary</CODE>-object of a certain type.
 *
 * @param		type	a <CODE>PdfName</CODE>
 */
    
    public PdfDictionary(PdfName type) {
        this();
        dictionaryType = type;
        put(PdfName.TYPE, dictionaryType);
    }
    
    // methods overriding some methods in PdfObject
    
/**
 * Returns the PDF representation of this <CODE>PdfDictionary</CODE>.
 *
 * @return		an array of <CODE>byte</CODE>
 */
    
    public void toPdf(PdfWriter writer, OutputStream os) throws IOException {
        os.write('\n'); // ssteward
        os.write('<');
        os.write('<');

        // loop over all the object-pairs in the HashMap
        PdfName key;
        PdfObject value;
        //int type = 0; // ssteward
        for (Iterator i = hashMap.keySet().iterator(); i.hasNext(); ) {
            os.write('\n');
            key = (PdfName) i.next();
            value = (PdfObject) hashMap.get(key);
            key.toPdf(writer, os);
	    // ssteward: I liked the old syntax formatting
            //type = value.type();
            //if (type != PdfObject.ARRAY && type != PdfObject.DICTIONARY && type != PdfObject.NAME && type != PdfObject.STRING)
            os.write(' ');
            value.toPdf(writer, os);
        }
        os.write('\n'); // ssteward
        os.write('>');
        os.write('>');
    }
    
    // methods concerning the HashMap member value
    
/**
 * Adds a <CODE>PdfObject</CODE> and its key to the <CODE>PdfDictionary</CODE>.
 *
 * @param		key		key of the entry (a <CODE>PdfName</CODE>)
 * @param		value	value of the entry (a <CODE>PdfObject</CODE>)
 * @return		the previous </CODE>PdfObject</CODE> corresponding with the <VAR>key</VAR>
 */
    
    public void put(PdfName key, PdfObject value) {
        hashMap.put(key, value);
    }
    
/**
 * Adds a <CODE>PdfObject</CODE> and its key to the <CODE>PdfDictionary</CODE>.
 * If the value is null it does nothing.
 *
 * @param		key		key of the entry (a <CODE>PdfName</CODE>)
 * @param		value	value of the entry (a <CODE>PdfObject</CODE>)
 * @return		the previous </CODE>PdfObject</CODE> corresponding with the <VAR>key</VAR>
 */
    public void putEx(PdfName key, PdfObject value) {
        if (value == null)
            return;
        hashMap.put(key, value);
    }
    
/**
 * Adds a <CODE>PdfObject</CODE> and its key to the <CODE>PdfDictionary</CODE>.
 * If the value is null the key is deleted.
 *
 * @param		key		key of the entry (a <CODE>PdfName</CODE>)
 * @param		value	value of the entry (a <CODE>PdfObject</CODE>)
 * @return		the previous </CODE>PdfObject</CODE> corresponding with the <VAR>key</VAR>
 */
    public void putDel(PdfName key, PdfObject value) {
        if (value == null) {
            hashMap.remove(key);
            return;
        }
        hashMap.put(key, value);
    }
    
/**
 * Removes a <CODE>PdfObject</CODE> and its key from the <CODE>PdfDictionary</CODE>.
 *
 * @param		key		key of the entry (a <CODE>PdfName</CODE>)
 * @return		the previous </CODE>PdfObject</CODE> corresponding with the <VAR>key</VAR>
 */
    
    public void remove(PdfName key) {
        hashMap.remove(key);
    }
    
/**
 * Gets a <CODE>PdfObject</CODE> with a certain key from the <CODE>PdfDictionary</CODE>.
 *
 * @param		key		key of the entry (a <CODE>PdfName</CODE>)
 * @return		the previous </CODE>PdfObject</CODE> corresponding with the <VAR>key</VAR>
 */
    
    public PdfObject get(PdfName key) {
        return (PdfObject) hashMap.get(key);
    }
    
    // methods concerning the type of Dictionary
    
/**
 * Checks if a <CODE>PdfDictionary</CODE> is of a certain type.
 *
 * @param		type	a type of dictionary
 * @return		<CODE>true</CODE> of <CODE>false</CODE>
 *
 * @deprecated
 */
    
    public boolean isDictionaryType(PdfName type) {
        return dictionaryType.compareTo(type) == 0;
    }
    
/**
 *  Checks if a <CODE>Dictionary</CODE> is of the type FONT.
 *
 * @return		<CODE>true</CODE> if it is, <CODE>false</CODE> if it isn't.
 */
    
    public boolean isFont() {
        return dictionaryType.compareTo(FONT) == 0;
    }
    
/**
 *  Checks if a <CODE>Dictionary</CODE> is of the type PAGE.
 *
 * @return		<CODE>true</CODE> if it is, <CODE>false</CODE> if it isn't.
 */
    
    public boolean isPage() {
        return dictionaryType.compareTo(PAGE) == 0;
    }
    
/**
 *  Checks if a <CODE>Dictionary</CODE> is of the type PAGES.
 *
 * @return		<CODE>true</CODE> if it is, <CODE>false</CODE> if it isn't.
 */
    
    public boolean isPages() {
        return dictionaryType.compareTo(PAGES) == 0;
    }
    
/**
 *  Checks if a <CODE>Dictionary</CODE> is of the type CATALOG.
 *
 * @return		<CODE>true</CODE> if it is, <CODE>false</CODE> if it isn't.
 */
    
    public boolean isCatalog() {
        return dictionaryType.compareTo(CATALOG) == 0;
    }
    
/**
 *  Checks if a <CODE>Dictionary</CODE> is of the type OUTLINES.
 *
 * @return		<CODE>true</CODE> if it is, <CODE>false</CODE> if it isn't.
 */
    
    public boolean isOutlineTree() {
        return dictionaryType.compareTo(OUTLINES) == 0;
    }
    
    public void merge(PdfDictionary other) {
        hashMap.putAll(other.hashMap);
    }
    
    public void mergeDifferent(PdfDictionary other) {
        for (Iterator i = other.hashMap.keySet().iterator(); i.hasNext();) {
            Object key = i.next();
            if (!hashMap.containsKey(key)) {
                hashMap.put(key, other.hashMap.get(key));
            }
        }
    }
    
    public Set getKeys() {
        return hashMap.keySet();
    }

    public void putAll(PdfDictionary dic) {
        hashMap.putAll(dic.hashMap);
    }
    
    public int size() {
        return hashMap.size();
    }
    
    public boolean contains(PdfName key) {
        return hashMap.containsKey(key);
    }
}