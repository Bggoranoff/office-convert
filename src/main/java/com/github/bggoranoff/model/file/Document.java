package com.github.bggoranoff.model.file;

import org.apache.commons.io.FilenameUtils;
import org.jodconverter.core.office.OfficeException;
import org.jodconverter.local.JodConverter;
import org.jodconverter.local.office.LocalOfficeManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;

public class Document extends File implements FileEntity {
    private final LocalOfficeManager OFFICE_MANAGER = LocalOfficeManager.install();

    public Document(String pathname) throws OfficeException {
        super(pathname);
    }

    @Override
    public void convert(String dest, String format) throws FileAlreadyExistsException, FileNotFoundException, OfficeException {
        if(!this.exists()) {
            throw new FileNotFoundException(String.format("Input file %s does not exist!", this.getPath()));
        }
        String currentFileExtension = FilenameUtils.getExtension(this.getName());
        if(currentFileExtension.equals(format)) return;
        File outputFile = new File(dest + File.separator + FilenameUtils.removeExtension(this.getName()) + "." + format);
        if(outputFile.exists()) {
            throw new FileAlreadyExistsException(String.format("Output file %s already exists!", outputFile.getPath()));
        }
        if(!OFFICE_MANAGER.isRunning()) {
            OFFICE_MANAGER.start();
        }
        JodConverter.convert(this)
                .to(outputFile)
                .execute();
        OFFICE_MANAGER.stop();
    }

    @Override
    public void generatePdf(String dest) throws FileNotFoundException, OfficeException, FileAlreadyExistsException {
        this.convert(dest, "pdf");
    }
}
