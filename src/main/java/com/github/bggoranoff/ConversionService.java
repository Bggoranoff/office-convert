package com.github.bggoranoff;

import com.github.bggoranoff.model.file.Document;
import com.github.bggoranoff.model.file.Image;
import com.github.bggoranoff.model.pdf.PdfDocument;
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.jodconverter.core.office.OfficeException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

public class ConversionService {
    public void convertDocument(String filePath, String dest, String outputFormat) throws FileNotFoundException, OfficeException, FileAlreadyExistsException {
        Document doc = new Document(filePath);
        doc.convert(dest, outputFormat);
    }

    public void convertImageToPdf(boolean blackAndWhite, String filePath, String dest) throws IOException {
        Image sourceImg = new Image(filePath);
        String workingFilePath = filePath;
        if(blackAndWhite) {
            sourceImg.generateGreyscale(dest);
            workingFilePath = dest + File.separator + FilenameUtils.removeExtension(sourceImg.getName()) + "_greyscale." + FilenameUtils.getExtension(sourceImg.getName());
        }
        Image workingImg = new Image(workingFilePath);
        workingImg.convert(dest, "pdf");
        if(blackAndWhite) {
            workingImg.delete();
        }
    }

    public void mergePdfs(String[] orderedFilePaths, String dest, String fileName) throws IOException {
        PDFMergerUtility merger = new PDFMergerUtility();
        merger.setDestinationFileName(dest + File.separator + fileName + ".pdf");
        for(String path : orderedFilePaths) {
            merger.addSource(new File(path));
        }
        merger.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
    }

    public void mergeImagesIntoPdf(boolean blackAndWhite, String[] orderedFilePaths, String dest, String fileName) throws IOException {
        File[] files = new File[orderedFilePaths.length];
        for(int i = 0; i < orderedFilePaths.length; i++) {
            convertImageToPdf(blackAndWhite, orderedFilePaths[i], dest);
            File currentFile = new File(orderedFilePaths[i]);
            files[i] = new File(dest + File.separator + FilenameUtils.removeExtension(currentFile.getName()) + ".pdf");
        }
        String[] pdfPaths = new String[files.length];
        for(int i = 0; i < files.length; i++) {
            pdfPaths[i] = files[i].getPath();
        }
        mergePdfs(pdfPaths ,dest, fileName);
        for(File file : files) {
            file.delete();
        }
    }

    public void mergeImagesIntoPdf(boolean blackAndWhite, float optimisationQuality, String[] orderedFilePaths, String dest, String fileName) throws IOException {
        String[] optimisedFilePaths = new String[orderedFilePaths.length];
        for(int i = 0; i < orderedFilePaths.length; i++) {
            File currentFile = new File(orderedFilePaths[i]);
            optimiseImage(currentFile.getPath(), dest, optimisationQuality);
            optimisedFilePaths[i] = dest + File.separator + FilenameUtils.removeExtension(currentFile.getName()) + "_opt.jpg";
        }
        mergeImagesIntoPdf(blackAndWhite, optimisedFilePaths, dest, fileName);
        for(String path : optimisedFilePaths) {
            new File(path).delete();
        }
    }

    public void optimiseImage(String filePath, String dest, float quality) throws IOException {
        Image img = new Image(filePath);
        img.optimise(dest, quality);
    }

    public void optimisePdf(String filePath, String dest) throws IOException {
        PdfDocument doc = new PdfDocument(filePath);
        doc.optimise(dest);
    }
}
