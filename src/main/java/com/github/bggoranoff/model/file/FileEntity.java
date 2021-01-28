package com.github.bggoranoff.model.file;

import org.jodconverter.core.office.OfficeException;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;

public interface FileEntity {
    void convert(String dest, String format) throws FileAlreadyExistsException, FileNotFoundException, OfficeException;

    void generatePdf(String dest) throws FileNotFoundException, OfficeException, FileAlreadyExistsException;
}
