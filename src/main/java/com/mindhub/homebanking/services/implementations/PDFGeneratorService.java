package com.mindhub.homebanking.services.implementations;

import java.util.List;
import com.lowagie.text.*;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.PdfWriter;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;

@Service
public class PDFGeneratorService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public void export(long id, LocalDateTime start,LocalDateTime end,HttpServletResponse response) throws IOException {
        //Create the document
        Document document = new Document(PageSize.A4);
        //add the document to the response output stream
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        //Optional<Account> account = accountRepository.findById(id);
        List<Transaction> transactionsFiltered = transactionRepository.filterByDatesAndAccountId(start,end,id);
        String htmlContent = "<html><body><table border='1'><tr><th>Amount</th><th>Description</th><th>Date</th></tr>";
       // if(account.isPresent()){
            //Set<Transaction> transactionList = account.get().getTransactions();
            for(Transaction transaction : transactionsFiltered){
                htmlContent += "<tr><td>"+transaction.getAmount()+"</td><td>"+transaction.getDescription()+"</td><td>"+transaction.getDate()+"</td></tr>";
            }
            htmlContent += "</table></body></html>";

        //}
        HTMLWorker htmlWorker = new HTMLWorker(document);
        htmlWorker.parse(new StringReader(htmlContent));
        document.close();
    }
}
