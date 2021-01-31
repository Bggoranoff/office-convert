package com.github.bggoranoff.model.pdf;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;

public class PdfDocument extends File {
    public PdfDocument(String pathname) {
        super(pathname);
    }

    public void optimise(String dest) throws IOException {
        if(!this.exists()) {
            throw new FileNotFoundException(String.format("Input file %s not found!", this.getPath()));
        }
        PdfReader reader = new PdfReader(new FileInputStream(this.getPath()));
        String compressedFilePath = dest + File.separator + FilenameUtils.removeExtension(this.getName()) + "_opt.pdf";
        if(new File(compressedFilePath).exists()) {
            throw new FileAlreadyExistsException(String.format("Output file %s already exists!", compressedFilePath));
        }
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(compressedFilePath));
        int total = reader.getNumberOfPages() + 1;
        for(int i = 1; i < total; i++) {
            reader.setPageContent(i + 1, reader.getPageContent(i + 1));
        }
        stamper.setFullCompression();
        stamper.close();
    }
}
