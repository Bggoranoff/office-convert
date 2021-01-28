package com.github.bggoranoff.model.file;

import org.jodconverter.core.office.OfficeException;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Objects;

import static org.junit.Assert.*;

public class DocumentTest {
    private String getResourcePath(String fileName) {
        return Objects.requireNonNull(DocumentTest.class.getClassLoader().getResource("document")).getPath() + File.separator + fileName;
    }

    @Test
    public void convertShouldConvertDocumentToAnotherOfficeFormat() throws OfficeException, IOException {
        Document wordDocument = new Document(this.getResourcePath("word.docx"));
        if(wordDocument.createNewFile()) {
            try {
                wordDocument.convert(this.getResourcePath(""), "odt");
                Document odtDocument = new Document(this.getResourcePath("word.odt"));
                assertTrue(odtDocument.exists());
            } finally {
                wordDocument.delete();
                File newDoc = new File(this.getResourcePath("word.odt"));
                if(newDoc.exists()) {
                    newDoc.delete();
                }
            }
        }
    }

    @Test(expected = FileAlreadyExistsException.class)
    public void convertShouldThrowFileAlreadyExistsException() throws OfficeException, IOException {
        Document wordDocument = new Document(this.getResourcePath("word.docx"));
        if(wordDocument.createNewFile()) {
            try {
                Document odtDocument = new Document(this.getResourcePath("word.odt"));
                odtDocument.createNewFile();
                wordDocument.convert(this.getResourcePath(""), "odt");
            } finally {
                wordDocument.delete();
                File newDoc = new File(this.getResourcePath("word.odt"));
                if(newDoc.exists()) {
                    newDoc.delete();
                }
            }
        }
    }

    @Test(expected = FileNotFoundException.class)
    public void convertShouldThrowFileNotFoundException() throws IOException, OfficeException {
        Document wordDocument = new Document(this.getResourcePath("word.docx"));
        try {
            Document odtDocument = new Document(this.getResourcePath("word.odt"));
            wordDocument.convert(this.getResourcePath(""), "odt");
            odtDocument.createNewFile();
        } finally {
            if(wordDocument.exists()) {
                wordDocument.delete();
            }
            File newDoc = new File(this.getResourcePath("word.odt"));
            if(newDoc.exists()) {
                newDoc.delete();
            }
        }
    }

    @Test
    public void generatePdfShouldConvertDocumentToPdf() throws IOException, OfficeException {
        Document wordDocument = new Document(this.getResourcePath("word.docx"));
        if(wordDocument.createNewFile()) {
            try {
                wordDocument.generatePdf(this.getResourcePath(""));
                Document odtDocument = new Document(this.getResourcePath("word.pdf"));
                assertTrue(odtDocument.exists());
            } finally {
                wordDocument.delete();
                File newDoc = new File(this.getResourcePath("word.pdf"));
                if(newDoc.exists()) {
                    newDoc.delete();
                }
            }
        }
    }

    @Test(expected = FileAlreadyExistsException.class)
    public void generatePdfShouldThrowFileAlreadyExistsException() throws IOException, OfficeException {
        Document wordDocument = new Document(this.getResourcePath("word.docx"));
        if(wordDocument.createNewFile()) {
            try {
                Document odtDocument = new Document(this.getResourcePath("word.pdf"));
                odtDocument.createNewFile();
                wordDocument.generatePdf(this.getResourcePath(""));
            } finally {
                wordDocument.delete();
                File newDoc = new File(this.getResourcePath("word.pdf"));
                if(newDoc.exists()) {
                    newDoc.delete();
                }
            }
        }
    }

    @Test(expected = FileNotFoundException.class)
    public void generatePdfShouldThrowFileNotFoundException() throws IOException, OfficeException {
        Document wordDocument = new Document(this.getResourcePath("word.docx"));
        try {
            Document odtDocument = new Document(this.getResourcePath("word.pdf"));
            wordDocument.generatePdf(this.getResourcePath(""));
            odtDocument.createNewFile();
        } finally {
            if(wordDocument.exists()) {
                wordDocument.delete();
            }
            File newDoc = new File(this.getResourcePath("word.pdf"));
            if(newDoc.exists()) {
                newDoc.delete();
            }
        }
    }
}
