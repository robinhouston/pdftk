/*
 * $Id: XmpBasicSchema.java,v 1.4 2005/09/08 07:50:15 blowagie Exp $
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

/**
 * An implementation of an XmpSchema.
 */
public class XmpBasicSchema extends XmpSchema {

	/** default namespace identifier*/
	public static final String DEFAULT_XPATH_ID = "xmp";
	/** default namespace uri*/
	public static final String DEFAULT_XPATH_URI = "http://ns.adobe.com/xap/1.0/";
	
	/** An unordered array specifying properties that were edited outside the authoring application. Each item should contain a single namespace and XPath separated by one ASCII space (U+0020). */
	public static final String ADVISORY = "xmp:Advisory";
	/** The base URL for relative URLs in the document content. If this document contains Internet links, and those links are relative, they are relative to this base URL. This property provides a standard way for embedded relative URLs to be interpreted by tools. Web authoring tools should set the value based on their notion of where URLs will be interpreted. */
	public static final String BASEURL = "xmp:BaseURL";
	/** The date and time the resource was originally created. */
	public static final String CREATEDATE = "xmp:CreateDate";
	/** The name of the first known tool used to create the resource. If history is present in the metadata, this value should be equivalent to that of xmpMM:History's softwareAgent property. */
	public static final String CREATORTOOL = "xmp:CreatorTool";
	/** An unordered array of text strings that unambiguously identify the resource within a given context. */
	public static final String IDENTIFIER = "xmp:Identifier";
	/** The date and time that any metadata for this resource was last changed. */
	public static final String METADATADATE = "xmp:MetadataDate";
	/** The date and time the resource was last modified. */
	public static final String MODIFYDATE = "xmp:ModifyDate";
	/** A short informal name for the resource. */
	public static final String NICKNAME = "xmp:Nickname";
	/** An alternative array of thumbnail images for a file, which can differ in characteristics such as size or image encoding. */
	public static final String THUMBNAILS = "xmp:Thumbnails";

	
	/**
	 * @throws IOException
	 */
	public XmpBasicSchema() throws IOException {
		super("xmlns:" + DEFAULT_XPATH_ID + "=\"" + DEFAULT_XPATH_URI + "\"");
	}
	
	/**
	 * Adds the creatortool.
	 * @param creator
	 */
	public void addCreatorTool(String creator) {
		setProperty(CREATORTOOL, creator);
	}
	
	/**
	 * Adds the creation date.
	 * @param date
	 */
	public void addCreateDate(String date) {
		setProperty(CREATEDATE, date);
	}
	
	/**
	 * Adds the modification date.
	 * @param date
	 */
	public void addModDate(String date) {
		setProperty(MODIFYDATE, date);
	}

	/**
	 * Adds the meta data date.
	 * @param date
	 */
	public void addMetaDataDate(String date) {
		setProperty(METADATADATE, date);
	}

	/** Adds the identifier.
	 * @param id
	 */
	public void addIdentifiers(String[] id) {
		XmpArray array = new XmpArray(XmpArray.UNORDERED);
		for (int i = 0; i < id.length; i++) {
			array.add(id[i]);
		}
		setProperty(IDENTIFIER, array);
	}

	/** Adds the nickname.
	 * @param name
	 */
	public void addNickname(String name) {
		setProperty(NICKNAME, name);
	}
}
