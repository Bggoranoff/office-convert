package com.github.bggoranoff.model.file;

import org.jodconverter.core.office.OfficeException;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;

public class Image extends File implements FileEntity {
    public Image(String pathname) {
        super(pathname);
    }

    @Override
    public void convert(String dest, String format) throws FileAlreadyExistsException, FileNotFoundException, OfficeException {

    }

    @Override
    public void generatePdf(String dest) throws FileNotFoundException, OfficeException, FileAlreadyExistsException {

    }
}
