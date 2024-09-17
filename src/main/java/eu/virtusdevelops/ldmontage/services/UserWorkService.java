package eu.virtusdevelops.ldmontage.services;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import eu.virtusdevelops.ldmontage.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserWorkService {

    /**
     * Creates pdf with exported work times for user
     * within specified dates
     * @param user the user for which to get work time
     * @param start from when
     * @param end to when
     * @return path to file
     */
    public String exportUsersWork(User user, Date start, Date end){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Document document = new Document();
        try{
            PdfWriter writer = PdfWriter.getInstance(document, os);
            document = setupTemplate(writer, document, "TemplatePath.pdf");

            // make the pdf
            // get all user's hours by day
            // get all user's breaks by day
            // mark edited hours with different color
            // write table to pdf
            // create temporary file thats valid for 10 days
            // create background service that cleans files older than 10 days
            // create new entity for temporary files



        }catch (Exception e){

        }

        return "";
    }


    /**
     * Reads first page of pdf found at {@param pathToTemplate}
     * imports it as template into pdf {@param document}
     * @param writer writer instance for the {@param document}
     * @param document pdf document where you wish to paste template
     * @param pathToTemplate path to the template pdf
     * @return updated document
     * @throws IOException if template document doesnt exist
     */
    private Document setupTemplate(PdfWriter writer, Document document, String pathToTemplate) throws IOException {
        document.open();
        document.newPage();

        FileInputStream template = new FileInputStream(pathToTemplate);
        PdfReader reader = new PdfReader(template);

        PdfImportedPage page = writer.getImportedPage(reader, 1);
        PdfContentByte cb = writer.getDirectContent();
        cb.addTemplate(page, 0, 0);


        return document;
    }
}
