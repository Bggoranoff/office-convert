package com.github.bggoranoff;

import org.jodconverter.core.office.OfficeException;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

import static org.mockito.Mockito.*;

public class ConversionServiceTest {
    @Test
    public void convertDocumentShouldAcceptValidInput() throws FileNotFoundException, OfficeException, FileAlreadyExistsException {
        ConversionService mockService = mock(ConversionService.class);
        doNothing().when(mockService).convertDocument("/valid/path.docx", "/valid/dest/", "odt");
        mockService.convertDocument("/valid/path.docx", "/valid/dest/", "odt");
        verify(mockService, times(1)).convertDocument("/valid/path.docx", "/valid/dest/", "odt");
    }

    @Test(expected = FileNotFoundException.class)
    public void convertDocumentShouldThrowFileNotFoundException() throws FileNotFoundException, OfficeException, FileAlreadyExistsException {
        ConversionService mockService = mock(ConversionService.class);
        doThrow(new FileNotFoundException("Input file not found!")).when(mockService).convertDocument("/invalid/path.docx", "/valid/dest/", "odt");
        mockService.convertDocument("/invalid/path.docx", "/valid/dest/", "odt");
    }

    @Test(expected = FileAlreadyExistsException.class)
    public void convertDocumentShouldThrowFileAlreadyExistsException() throws FileNotFoundException, OfficeException, FileAlreadyExistsException {
        ConversionService mockService = mock(ConversionService.class);
        doThrow(new FileAlreadyExistsException("Output file already exists!")).when(mockService).convertDocument("/valid/path.docx", "/existing/dest/", "odt");
        mockService.convertDocument("/valid/path.docx", "/existing/dest/", "odt");
    }

    @Test
    public void convertImageToPdfShouldAcceptValidInput() throws IOException {
        ConversionService mockService = mock(ConversionService.class);
        doNothing().when(mockService).convertImageToPdf(false, "/valid/path.jpg", "/valid/dest/");
        mockService.convertImageToPdf(false, "/valid/path.jpg", "/valid/dest/");
        verify(mockService, times(1)).convertImageToPdf(false, "/valid/path.jpg", "/valid/dest/");
    }

    @Test(expected = FileNotFoundException.class)
    public void convertImageToPdfShouldThrowFileNotFoundException() throws IOException {
        ConversionService mockService = mock(ConversionService.class);
        doThrow(new FileNotFoundException("Input file not found!")).when(mockService).convertImageToPdf(true, "/invalid/path.jpg", "/valid/dest/");
        mockService.convertImageToPdf(true, "/invalid/path.jpg", "/valid/dest/");
    }

    @Test(expected = FileAlreadyExistsException.class)
    public void convertImageToPdfShouldThrowFileAlreadyExistsException() throws IOException {
        ConversionService mockService = mock(ConversionService.class);
        doThrow(new FileAlreadyExistsException("Output file already exists!")).when(mockService).convertImageToPdf(true, "/valid/path.png", "/existing/dest/");
        mockService.convertImageToPdf(true, "/valid/path.png", "/existing/dest/");
    }

    @Test
    public void mergePdfsShouldAcceptValidInput() throws IOException {
        ConversionService mockService = mock(ConversionService.class);
        doNothing().when(mockService).mergePdfs(new String[]{}, "/valid/dest/", "newFile");
        mockService.mergePdfs(new String[]{}, "/valid/dest/", "newFile");
        verify(mockService, times(1)).mergePdfs(new String[]{}, "/valid/dest/", "newFile");
    }

    @Test(expected = FileNotFoundException.class)
    public void mergePdfsShouldThrowFileNotFoundException() throws IOException {
        ConversionService mockService = mock(ConversionService.class);
        doThrow(new FileNotFoundException("Input file not found!")).when(mockService).mergePdfs(new String[]{"/invalid/file"}, "/invalid/dest/", "newFile");
        mockService.mergePdfs(new String[]{"/invalid/file"}, "/invalid/dest/", "newFile");
    }

    @Test
    public void mergeImagesIntoPdfShouldAcceptValidInput() throws IOException {
        ConversionService mockService = mock(ConversionService.class);
        doNothing().when(mockService).mergeImagesIntoPdf(true, new String[]{}, "/valid/dest", "newPdf");
        mockService.mergeImagesIntoPdf(true, new String[]{}, "/valid/dest", "newPdf");
        verify(mockService, times(1)).mergeImagesIntoPdf(true, new String[]{}, "/valid/dest", "newPdf");
    }

    @Test
    public void mergeImagesIntoPdfShouldAcceptOptimisationProperty() throws IOException {
        ConversionService mockService = mock(ConversionService.class);
        doNothing().when(mockService).mergeImagesIntoPdf(true, 0.6f, new String[]{}, "/valid/dest", "newPdf");
        mockService.mergeImagesIntoPdf(true, 0.6f, new String[]{}, "/valid/dest", "newPdf");
        verify(mockService, times(1)).mergeImagesIntoPdf(true, 0.6f, new String[]{}, "/valid/dest", "newPdf");
    }

    @Test(expected = FileNotFoundException.class)
    public void mergeImagesIntoPdfShouldThrowFileNotFoundException() throws IOException {
        ConversionService mockService = mock(ConversionService.class);
        doThrow(new FileNotFoundException("Input file not found!")).when(mockService).mergeImagesIntoPdf(true, new String[]{"/invalid.jpg"}, "/valid/dest", "newPdf");
        mockService.mergeImagesIntoPdf(true, new String[]{"/invalid.jpg"}, "/valid/dest", "newPdf");
    }

    @Test(expected = FileAlreadyExistsException.class)
    public void mergeImagesIntoPdfShouldThrowFileAlreadyExistsException() throws IOException {
        ConversionService mockService = mock(ConversionService.class);
        doThrow(new FileAlreadyExistsException("Output file already exists!")).when(mockService).mergeImagesIntoPdf(true, new String[]{}, "/existing/dest", "existingFile");
        mockService.mergeImagesIntoPdf(true, new String[]{}, "/existing/dest", "existingFile");
    }

    @Test
    public void optimiseImageShouldAcceptValidInput() throws IOException {
        ConversionService mockService = mock(ConversionService.class);
        doNothing().when(mockService).optimiseImage("/valid/file.jpg", "/valid/dest/", 0.6f);
        mockService.optimiseImage("/valid/file.jpg", "/valid/dest/", 0.6f);
        verify(mockService, times(1)).optimiseImage("/valid/file.jpg", "/valid/dest/", 0.6f);
    }

    @Test(expected = FileNotFoundException.class)
    public void optimiseImageShouldThrowFileNotFoundException() throws IOException {
        ConversionService mockService = mock(ConversionService.class);
        doThrow(new FileNotFoundException("Input file not found!")).when(mockService).optimiseImage("/invalid/file.jpg", "/valid/dest/", 0.6f);
        mockService.optimiseImage("/invalid/file.jpg", "/valid/dest/", 0.6f);
    }

    @Test
    public void optimisePdfShouldAcceptValidInput() throws IOException {
        ConversionService mockService = mock(ConversionService.class);
        doNothing().when(mockService).optimisePdf("/valid/file/path.pdf", "/dest/pdf/");
        mockService.optimisePdf("/valid/file/path.pdf", "/dest/pdf/");
        verify(mockService, times(1)).optimisePdf("/valid/file/path.pdf", "/dest/pdf/");
    }

    @Test(expected = FileNotFoundException.class)
    public void optimisePdfShouldThrowFileNotFoundException() throws IOException {
        ConversionService mockService = mock(ConversionService.class);
        doThrow(new FileNotFoundException("Input file not found!")).when(mockService).optimisePdf("/invalid/file/path.pdf", "/dest/pdf/");
        mockService.optimisePdf("/invalid/file/path.pdf", "/dest/pdf/");
    }
}
