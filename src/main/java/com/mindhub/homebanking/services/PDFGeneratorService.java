package com.mindhub.homebanking.services;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class PDFGeneratorService {

    public void export(HttpServletResponse response) throws IOException {
        //Create the document
        Document document = new Document(PageSize.A4);
        //add the document to the response output stream
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA);
        fontTitle.setSize(18);
        Paragraph paragraph = new Paragraph("THIS IS A TITLE",fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);
        document.close();
    }
}
