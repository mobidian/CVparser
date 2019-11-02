package com.cv.parser.extract;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;

import com.cv.parser.UnsupportedFileExtension;

import java.util.List;

public class ExtractFiles {
    private final static Logger LOG = LogManager.getLogger();

    private List<String> superList;
    private Table tableExtractedContent;
    private Button btnSaveInJSON;
    private Button btnSaveInCSV;
    private MenuItem mntmExtractPublicDirectory;

    private Shell mainShell;
    private Label status;

    private ParserFactory parserFactory = new ParserFactory();

    public ExtractFiles(Shell shell, MenuItem mntmExtractPublicDirectory, List<String> superList,
	    Table tableExtractedContent, Button btnSaveInJSON, Button btnSaveInCSV) {
	this.mainShell = shell;
	this.mntmExtractPublicDirectory = mntmExtractPublicDirectory;
	this.superList = superList;
	this.tableExtractedContent = tableExtractedContent;
	this.btnSaveInJSON = btnSaveInJSON;
	this.btnSaveInCSV = btnSaveInCSV;
    }

    public void handleMenuItemClick() {
	mntmExtractPublicDirectory.addListener(SWT.Selection, arg0 -> {

	    Shell doneShell = new Shell(mainShell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	    RowLayout rowLayout = new RowLayout();
	    rowLayout.pack = false;
	    rowLayout.wrap = true;
	    rowLayout.marginLeft = 15;
	    rowLayout.marginTop = 15;
	    rowLayout.marginRight = 55;
	    rowLayout.marginBottom = 15;
	    doneShell.setLayout(rowLayout);

	    status = new Label(doneShell, SWT.NONE);

	    parsePDF(parserFactory);
	    parseDOC(parserFactory);
	    parseDOCX(parserFactory);
	    parseTXT(parserFactory);

	    displayDocumentsInTable();
	    status.setText("Finished!");

	    btnSaveInJSON.setEnabled(true);
	    btnSaveInCSV.setEnabled(true);

	    doneShell.pack();
	    doneShell.open();

	    // Move the dialog to the center of the top level shell.
	    Rectangle shellBounds = mainShell.getBounds();
	    Point dialogSize = doneShell.getSize();

	    doneShell.setLocation(shellBounds.x + (shellBounds.width - dialogSize.x) / 2,
		    shellBounds.y + (shellBounds.height - dialogSize.y) / 2);
	});
    }

    // display extracted documents in table
    private void displayDocumentsInTable() {
	for (String s : superList) {
	    TableItem item = new TableItem(tableExtractedContent, SWT.NONE);
	    item.setText(new String[] { s });
	}
    }

    private void doParse(ParserInterface parserInterface) {
	// based on the given file type
	// get all the files of that type
	// then save it to a temporary list
	parserInterface.setFiles();
	parserInterface.extractFiles();

	// superList is a super List that contain all
	superList.addAll(parserInterface.getContents());
    }

    private void parsePDF(ParserFactory factory) {
	try {
	    doParse(factory.getContent("pdf"));
	} catch (UnsupportedFileExtension e) {
	    LOG.error(e.getMessage());
	}
    }

    private void parseDOC(ParserFactory factory) {
	try {
	    doParse(factory.getContent("doc"));
	} catch (UnsupportedFileExtension e) {
	    LOG.error(e.getMessage());
	}
    }

    private void parseDOCX(ParserFactory factory) {
	try {
	    doParse(factory.getContent("docx"));
	} catch (UnsupportedFileExtension e) {
	    LOG.error(e.getMessage());
	}
    }

    private void parseTXT(ParserFactory factory) {
	try {
	    doParse(factory.getContent("txt"));
	} catch (UnsupportedFileExtension e) {
	    LOG.error(e.getMessage());
	}
    }
}
