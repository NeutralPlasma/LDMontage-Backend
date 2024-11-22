package eu.virtusdevelops.ldmontage.services;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import eu.virtusdevelops.ldmontage.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
            // create temporary file that's valid for 10 days
            // create background service that cleans files older than 10 days
            // create new entity for temporary files



        }catch (Exception e){

        }

        return "";
    }


    /**
     * Reads the first page of a PDF found at {@param pathToTemplate},
     * imports it as a template into the provided {@param document}.
     *
     * @param writer Writer instance for the {@param document}.
     * @param document PDF document where you wish to paste the template.
     * @param pathToTemplate Path to the template PDF.
     * @return Updated document with the template imported.
     * @throws IOException If the template document doesn't exist or cannot be opened.
     */
    private Document setupTemplate(PdfWriter writer, Document document, String pathToTemplate) throws IOException {
        // Open the document and create a new page
        document.open();
        document.newPage();

        PdfReader reader = null;
        try (FileInputStream templateStream = new FileInputStream(pathToTemplate)) {
            reader = new PdfReader(templateStream);

            // Import the first page of the template PDF
            PdfImportedPage page = writer.getImportedPage(reader, 1);
            PdfContentByte cb = writer.getDirectContent();
            // Add the imported page as the template to the current document
            cb.addTemplate(page, 0, 0);

        } catch (FileNotFoundException e) {
            // Handle the case where the template file is not found
            throw new IOException("Template file not found: " + pathToTemplate, e);
        } finally {
            // Ensure that the resources are closed properly
            if (reader != null) {
                reader.close();
            }
        }

        return document;
    }
}
