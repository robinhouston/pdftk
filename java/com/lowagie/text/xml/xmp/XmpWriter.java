/*
 * $Id: XmpWriter.java,v 1.7 2005/09/08 07:50:15 blowagie Exp $
 * $Name:  $
 *
 * Copyright 2005 by Bruno Lowagie.
 *
 *
 * The Original Code is 'iText, a free JAVA-PDF library'.
 *
 * The Initial Developer of the Original Code is Bruno Lowagie. Portions created by
 * the Initial Developer are Copyright (C) 1999-2005 by Bruno Lowagie.
 * All Rights Reserved.
 * Co-Developer of the code is Paulo Soares. Portions created by the Co-Developer
 * are Copyright (C) 2000-2005 by Paulo Soares. All Rights Reserved.
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

package com.lowagie.text.xml.xmp;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;

import com.lowagie.text.pdf.PdfDate;
import com.lowagie.text.pdf.PdfDictionary;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfObject;
import com.lowagie.text.pdf.PdfString;

/**
 * With this class you can create an Xmp Stream that can be used for adding
 * Metadata to a PDF Dictionary. Remark that this class doesn't cover the
 * complete XMP specification. 
 */
public class XmpWriter {

	/** A possible charset for the XMP. */
	public static final String UTF8 = "UTF-8";
	/** A possible charset for the XMP. */
	public static final String UTF16 = "UTF-16";
	/** A possible charset for the XMP. */
	public static final String UTF16BE = "UTF-16BE";
	/** A possible charset for the XMP. */
	public static final String UTF16LE = "UTF-16LE";
	
	/** String used to fill the extra space. */
	public static final String EXTRASPACE = "                                                                                                   \n";
	
	/** You can add some extra space in the XMP packet; 1 unit in this variable represents 100 spaces and a newline. */
	protected int extraSpace;
	
	/** The writer to which you can write bytes for the XMP stream. */
	protected OutputStreamWriter writer;
	
	/** The about string that goes into the rdf:Description tags. */
	protected String about;
	
	/** The end attribute. */
	protected char end = 'w';
	
	/**
	 * Creates an XmpWriter. 
	 * @param os
	 * @param utfEncoding
	 * @param extraSpace
	 * @throws IOException
	 */
	public XmpWriter(OutputStream os, String utfEncoding, int extraSpace) throws IOException {
		this.extraSpace = extraSpace;
		writer = new OutputStreamWriter(os, utfEncoding);
		writer.write("<?xpacket begin='\uFEFF' id='W5M0MpCehiHzreSzNTczkc9d' ?>\n");
		writer.write("<x:xmpmeta xmlns:x='adobe:ns:meta/'>\n");
		writer.write("<rdf:RDF xmlns:rdf='http://www.w3.org/1999/02/22-rdf-syntax-ns#'>\n");
		about = "";
	}
	
	/**
	 * Creates an XmpWriter.
	 * @param os
	 * @throws IOException
	 */
	public XmpWriter(OutputStream os) throws IOException {
		this(os, UTF8, 20);
	}
	
	/** Sets the XMP to read-only */
	public void setReadOnly() {
		end = 'r';
	}
	
	/**
	 * @param about The about to set.
	 */
	public void setAbout(String about) {
		this.about = about;
	}
	
	/**
	 * Adds an rdf:Description.
	 * @param xmlns
	 * @param content
	 * @throws IOException
	 */
	public void addRdfDescription(String xmlns, String content) throws IOException {
		writer.write("<rdf:Description rdf:about='");
		writer.write(about);
		writer.write("' ");
		writer.write(xmlns);
		writer.write(">");
		writer.write(content);
		writer.write("</rdf:Description>\n");
	}
	
	/**
	 * Adds an rdf:Description.
	 * @param s
	 * @throws IOException
	 */
	public void addRdfDescription(XmpSchema s) throws IOException {
		writer.write("<rdf:Description rdf:about='");
		writer.write(about);
		writer.write("' ");
		writer.write(s.getXmlns());
		writer.write(">");
		writer.write(s.toString());
		writer.write("</rdf:Description>\n");
	}
	
	/**
	 * Flushes and closes the XmpWriter.
	 * @throws IOException
	 */
	public void close() throws IOException {
		writer.write("</rdf:RDF>");
		writer.write("</x:xmpmeta>\n");
		for (int i = 0; i < extraSpace; i++) {
			writer.write(EXTRASPACE);
		}
		writer.write("<?xpacket ends='" + end + "' ?>");
		writer.flush();
		writer.close();
	}
    
    /**
     * @param os
     * @param info
     * @throws IOException
     */
    public XmpWriter(OutputStream os, PdfDictionary info) throws IOException {
        this(os);
        if (info != null) {
        	DublinCoreSchema dc = new DublinCoreSchema();
        	PdfSchema p = new PdfSchema();
        	XmpBasicSchema basic = new XmpBasicSchema();
        	PdfName key;
        	PdfObject obj;
        	for (Iterator it = info.getKeys().iterator(); it.hasNext();) {
        		key = (PdfName)it.next();
        		obj = info.get(key);
        		if (obj == null)
        			continue;
        		if (PdfName.TITLE.equals(key)) {
        			dc.addTitle(((PdfString)obj).toUnicodeString());
        		}
        		if (PdfName.AUTHOR.equals(key)) {
        			dc.addAuthor(((PdfString)obj).toUnicodeString());
        		}
        		if (PdfName.SUBJECT.equals(key)) {
        			dc.addSubject(((PdfString)obj).toUnicodeString());
        		}
        		if (PdfName.KEYWORDS.equals(key)) {
        			p.addKeywords(((PdfString)obj).toUnicodeString());
        		}
        		if (PdfName.CREATOR.equals(key)) {
        			basic.addCreatorTool(((PdfString)obj).toUnicodeString());
        		}
        		if (PdfName.PRODUCER.equals(key)) {
        			p.addProducer(((PdfString)obj).toUnicodeString());
        		}
        		if (PdfName.CREATIONDATE.equals(key)) {
        			basic.addCreateDate(((PdfDate)obj).getW3CDate());
        		}
        		if (PdfName.MODDATE.equals(key)) {
        			basic.addModDate(((PdfDate)obj).getW3CDate());
        		}
        	}
        	if (dc.size() > 0) addRdfDescription(dc);
        	if (p.size() > 0) addRdfDescription(p);
        	if (basic.size() > 0) addRdfDescription(basic);
        }
    }
    
    /**
     * @param os
     * @param info
     * @throws IOException
     */
    public XmpWriter(OutputStream os, HashMap info) throws IOException {
        this(os);
        if (info != null) {
        	DublinCoreSchema dc = new DublinCoreSchema();
        	PdfSchema p = new PdfSchema();
        	XmpBasicSchema basic = new XmpBasicSchema();
        	String key;
        	String value;
        	for (Iterator it = info.keySet().iterator(); it.hasNext();) {
        		key = (String)it.next();
        		value = (String)info.get(key);
        		if (value == null)
        			continue;
        		if ("Title".equals(key)) {
        			dc.addTitle(value);
        		}
        		if ("Author".equals(key)) {
        			dc.addAuthor(value);
        		}
        		if ("Subject".equals(key)) {
        			dc.addSubject(value);
        		}
        		if ("Keywords".equals(key)) {
        			p.addKeywords(value);
        		}
        		if ("Creator".equals(key)) {
        			basic.addCreatorTool(value);
        		}
        		if ("Producer".equals(key)) {
        			p.addProducer(value);
        		}
        		if ("CreationDate".equals(key)) {
        			basic.addCreateDate(PdfDate.getW3CDate(value));
        		}
        		if ("ModDate".equals(key)) {
        			basic.addModDate(PdfDate.getW3CDate(value));
        		}
        	}
        	if (dc.size() > 0) addRdfDescription(dc);
        	if (p.size() > 0) addRdfDescription(p);
        	if (basic.size() > 0) addRdfDescription(basic);
        }
    }
}