package com.github.bggoranoff.model.pdf;

import com.github.bggoranoff.model.file.DocumentTest;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.Assert.assertTrue;

public class PdfDocumentTest {
    private String getResourcePath(String fileName) {
        return Objects.requireNonNull(DocumentTest.class.getClassLoader().getResource("pdf")).getPath() + File.separator + fileName;
    }

    @Test
    public void optimisePdfShouldDecreasePdfFileSize() throws IOException {
        PdfDocument doc = new PdfDocument(this.getResourcePath("sample.pdf"));
        doc.optimise(this.getResourcePath(""));
        File optimised = new File(this.getResourcePath("sample_opt.pdf"));
        assertTrue(optimised.exists());
        assertTrue(Files.size(Paths.get(optimised.getPath())) <= Files.size(Paths.get(doc.getPath())));
        optimised.delete();
    }

    @Test(expected = FileNotFoundException.class)
    public void optimisePdfShouldThrowFileNotFoundException() throws IOException {
        PdfDocument doc = new PdfDocument(this.getResourcePath("dfsjklf.pdf"));
        doc.optimise(this.getResourcePath(""));
    }

    @Test(expected = FileAlreadyExistsException.class)
    public void optimisePdfShouldThrowFileAlreadyExistsException() throws IOException {
        PdfDocument doc = new PdfDocument(this.getResourcePath("sample.pdf"));
        doc.optimise(this.getResourcePath(""));
        File optimised = new File(this.getResourcePath("sample_opt.pdf"));
        try {
            doc.optimise(this.getResourcePath(""));
        } finally {
            optimised.delete();
        }
    }
}
