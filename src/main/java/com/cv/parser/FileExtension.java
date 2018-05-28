package com.cv.parser;

public class FileExtension {

    public FileExtension() {
	// default constructor
    }

    public enum Ext {
	PDF, DOC, DOCX, TXT, RTF
    }

    public String get(Ext ext) {
	switch (ext) {
	case PDF:
	    return ".pdf";
	case DOC:
	    return ".doc";
	case DOCX:
	    return ".docx";
	case TXT:
	    return ".txt";
	case RTF:
	    return ".rtf";
	}
	return null;
    }

}
