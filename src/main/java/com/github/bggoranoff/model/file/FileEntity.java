package com.github.bggoranoff.model.file;

import org.jodconverter.core.office.OfficeException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;

public interface FileEntity {
    void convert(String dest, String format) throws IOException, OfficeException;

    void generatePdf(String dest) throws IOException, OfficeException;
}
