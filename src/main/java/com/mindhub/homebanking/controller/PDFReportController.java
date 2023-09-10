package com.mindhub.homebanking.controller;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class PDFReportController extends HttpServlet {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping ("/generate-report/{id}")
    public void generateReport(@PathVariable long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String dest = "src/main/resources/static/web/output.pdf";
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        Optional<Account> account = accountRepository.findById(id);
        if(account.isPresent()){
            Set<Transaction> transactionList = account.get().getTransactions();
            String htmlContent = "<html><body><table border='1'><tr><th>Amount</th><th>Description</th><th>Date</th></tr>";
            for(Transaction transaction : transactionList){
                htmlContent += "<tr><td>"+transaction.getAmount()+"</td><td>"+transaction.getDescription()+"</td><td>"+transaction.getDate()+"</td></tr>";
            }
            htmlContent += "</table></body></html>";
            try{
                HtmlConverter.convertToPdf(htmlContent, pdf, new ConverterProperties());
                document.close();
            }catch (Exception e){
            }
        }
    }
}
