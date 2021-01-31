package com.github.bggoranoff.model.file;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.Assert.*;

public class ImageTest {
    private String getResourcePath(String fileName) {
        return Objects.requireNonNull(DocumentTest.class.getClassLoader().getResource("image")).getPath() + File.separator + fileName;
    }

    @Test
    public void convertImageShouldPerformValidImageConversion() throws IOException {
        Image img = new Image(this.getResourcePath("star.jpg"));
        img.convert(this.getResourcePath(""), "png");
        File output = new File(this.getResourcePath("star.png"));
        assertTrue(output.exists());
        output.delete();
    }

    @Test(expected = FileNotFoundException.class)
    public void convertImageShouldThrowFileNotFoundException() throws IOException {
        Image img = new Image(this.getResourcePath("jsdlskj.jpg"));
        img.convert(this.getResourcePath(""), "png");
    }

    @Test(expected = FileAlreadyExistsException.class)
    public void convertImageShouldThrowFileAlreadyExistsException() throws IOException {
        Image img = new Image(this.getResourcePath("star.jpg"));
        img.convert(this.getResourcePath(""), "jpg");
    }

    @Test
    public void generatePdfShouldGeneratePdfDocumentFromImage() throws IOException {
        Image img = new Image(this.getResourcePath("star.jpg"));
        img.generatePdf(this.getResourcePath(""));
        File output = new File(this.getResourcePath("star.pdf"));
        assertTrue(output.exists());
        output.delete();
    }

    @Test(expected = FileNotFoundException.class)
    public void generatePdfShouldThrowFileNotFoundException() throws IOException {
        Image img = new Image(this.getResourcePath("sdfkjlskjfd.jpg"));
        img.generatePdf(this.getResourcePath(""));
    }

    @Test(expected = FileAlreadyExistsException.class)
    public void generatePdfShouldThrowFileAlreadyExistsException() throws IOException {
        Image img = new Image(this.getResourcePath("star.jpg"));
        img.generatePdf(this.getResourcePath(""));
        try {
            img.generatePdf(this.getResourcePath(""));
        } finally {
            new File(this.getResourcePath("star.pdf")).delete();
        }
    }

    @Test
    public void optimiseImageShouldOptimiseImageFileSize() throws IOException {
        Image img = new Image(this.getResourcePath("star.jpg"));
        img.optimise(this.getResourcePath(""), 0.6f);
        File optimised = new File(this.getResourcePath("star_opt.jpg"));
        assertTrue(optimised.exists());
        assertTrue(Files.size(Paths.get(optimised.getPath())) <= Files.size(Paths.get(img.getPath())));
        optimised.delete();
    }

    @Test(expected = FileNotFoundException.class)
    public void optimiseImageShouldThrowFileNotFoundException() throws IOException {
        Image img = new Image(this.getResourcePath("djkslsj.jpg"));
        img.optimise(this.getResourcePath(""), 0.6f);
    }

    @Test
    public void generateGreyscaleShouldCreateGreyscaleImageCopy() throws IOException {
        Image img = new Image(this.getResourcePath("star.jpg"));
        img.generateGreyscale(this.getResourcePath(""));
        File greyscale = new File(this.getResourcePath("star_greyscale.jpg"));
        assertTrue(greyscale.exists());
        greyscale.delete();
    }

    @Test(expected = FileNotFoundException.class)
    public void generateGreyscaleShouldThrowFileNotFoundException() throws IOException {
        Image img = new Image(this.getResourcePath("sdfjkl.jpg"));
        img.generateGreyscale(this.getResourcePath(""));
    }
}
