package com.mindhub.homebanking.controller;


import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.implementations.PDFGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class PDFReportController extends HttpServlet {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PDFGeneratorService pdfGeneratorService;


    @GetMapping("/generate-report")
    public void generatePDF(@RequestParam long id, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end, HttpServletResponse response){
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormatter.format(new Date());
        //Tells the browsers what kind of file is returning
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_"+ currentDateTime + ".pdf";
        response.setHeader(headerKey,headerValue);
        try {
            pdfGeneratorService.export(id,start,end,response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
