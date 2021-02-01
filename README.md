# Office Convert
A maven library for conversion, optimisation and merging of documents and images.

## Supported functionality
- Document conversion to different office formats, including pdf
- Image conversion
- Generating greyscale of an image
- Image optimisation in expense of quality
- Pdf document optimisation
- Merging pdfs into a single document
- Merging images into a single pdf document, supporting black and white, and image optimisation

## Installation
Add the following lines to your pom.xml file:
<br />
```xml
<project>
    <dependencies>
        <dependency>
            <groupId>com.github.bggoranoff</groupId>
            <artifactId>office-convert</artifactId>
            <version>1.0</version>
        </dependency>
    </dependencies>
</project>
```

## File conversion specification
- Optimised images have _opt added to their name
- Greyscale images have _greyscale added to their name

## License
Project is open source, using MIT license

## Used libraries
Project uses [JodConverter](https://github.com/sbraconnier/jodconverter) and [Apache PDFBox](https://pdfbox.apache.org/) for implementing basic conversion and merging functionality.