package com.github.bggoranoff.model.file;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.HashMap;

public class Image extends File implements FileEntity {
    private static final HashMap<String, Double> GRAY_NUANCE = new HashMap<String, Double>() {
        {
            put("red", 0.299);
            put("green", 0.587);
            put("blue", 0.114);
        }
    };

    public Image(String pathname) {
        super(pathname);
    }

    @Override
    public void convert(String dest, String format) throws IOException {
        if(!this.exists()) {
            throw new FileNotFoundException(String.format("Input file %s does not exist!", this.getPath()));
        }
        File outputFile = new File(dest + File.separator + FilenameUtils.removeExtension(this.getName()) + "." + format);
        if(outputFile.exists()) {
            throw new FileAlreadyExistsException(String.format("Output file %s already exists!", outputFile.getPath()));
        }
        if(outputFile.createNewFile()) {
            BufferedImage image = ImageIO.read(this);
            BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            result.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
            ImageIO.write(result, format, outputFile);
        }
    }

    @Override
    public void generatePdf(String dest) throws IOException {
        if(!this.exists()) {
            throw new FileNotFoundException(String.format("Input file %s does not exist!", this.getPath()));
        }
        if(new File(dest + File.separator + FilenameUtils.removeExtension(this.getName()) + ".pdf").exists()) {
            throw new FileAlreadyExistsException(String.format("Output file %s already exists!", dest + File.separator + FilenameUtils.removeExtension(this.getName()) + ".pdf"));
        }
        PDDocument doc = new PDDocument();
        PDImageXObject image = PDImageXObject.createFromFile(this.getPath(), doc);
        PDRectangle pageSize = PDRectangle.A4;
        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();
        float pageWidth = pageSize.getWidth();
        float pageHeight = pageSize.getHeight();
        float ratio = Math.min(pageWidth / originalWidth, pageHeight / originalHeight);
        float scaledWidth = originalWidth * ratio;
        float scaledHeight = originalHeight * ratio;
        float x = ( pageWidth - scaledWidth ) / 2;
        float y = ( pageHeight - scaledHeight ) / 2;
        PDPage page = new PDPage(pageSize);
        doc.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);
        contentStream.drawImage(image, x, y, scaledWidth, scaledHeight);
        contentStream.close();
        doc.save(dest + File.separator + FilenameUtils.removeExtension(this.getName()) + ".pdf");
        doc.close();
    }

    public void optimise(String dest, float quality) throws IOException {
        if(!this.exists()) {
            throw new FileNotFoundException(String.format("Input file %s does not exist!", this.getPath()));
        }
        File outputFile = new File(dest + File.separator + FilenameUtils.removeExtension(this.getName()) + "_opt.jpg");
        BufferedImage image = ImageIO.read(this);
        OutputStream outputStream = new FileOutputStream(outputFile);
        ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
        ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
        writer.setOutput(imageOutputStream);
        ImageWriteParam param = writer.getDefaultWriteParam();
        if(param.canWriteCompressed()) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality);
        }
        writer.write(null, new IIOImage(image, null, null), param);
        outputStream.close();
        imageOutputStream.close();
        writer.dispose();
    }

    public void generateGreyscale(String dest) throws IOException {
        if(!this.exists()) {
            throw new FileNotFoundException(String.format("Input file %s does not exist!", this.getPath()));
        }
        BufferedImage img = ImageIO.read(this);
        for(int i = 0; i < img.getHeight(); i++) {
            for(int j = 0; j < img.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i));
                int red = (int) (color.getRed() * GRAY_NUANCE.get("red"));
                int green = (int) (color.getGreen() * GRAY_NUANCE.get("green"));
                int blue = (int) (color.getBlue() * GRAY_NUANCE.get("blue"));
                Color grayScale = new Color(red + green + blue, red + green + blue, red + green + blue);
                img.setRGB(j, i, grayScale.getRGB());
            }
        }
        File file = new File(dest + File.separator + FilenameUtils.removeExtension(this.getName()) + "_greyscale." + FilenameUtils.getExtension(this.getName()));
        ImageIO.write(img, FilenameUtils.getExtension(this.getName()), file);
    }
}
