package com.github.bggoranoff.model.file;

public interface FileEntity {
    void convert(String dest, String format);

    void generatePdf(String dest);
}
