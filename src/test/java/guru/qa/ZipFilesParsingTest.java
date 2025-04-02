package guru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class ZipFilesParsingTest {

    private final ClassLoader cl = ZipFilesParsingTest.class.getClassLoader();

    @Test
    void parsePdfFromZipTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("test_files.zip")) {
            assert is != null;
            try (ZipInputStream zis = new ZipInputStream(is)) {

                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.getName().endsWith(".pdf")) {
                        PDF pdf = new PDF(zis);
                        assertTrue(pdf.text.contains("Sample PDF Content"));
                        return;
                    }
                }
            }
        }
    }

    @Test
    void parseExcelFromZipTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("test_files.zip")) {
            assert is != null;
            try (ZipInputStream zis = new ZipInputStream(is)) {

                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.getName().endsWith(".xlsx")) {
                        byte[] excelData = zis.readAllBytes();
                        XLS xls = new XLS(excelData);
                        assertEquals("Sample Excel Data",
                                xls.excel.getSheetAt(0).getRow(0).getCell(0).getStringCellValue());
                        return;
                    }
                }
            }
        }
    }

    @Test
    void parseCsvFromZipTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("test_files.zip")) {
            assert is != null;
            try (ZipInputStream zis = new ZipInputStream(is)) {

                ZipEntry entry;
                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.getName().endsWith(".csv")) {
                        CSVReader csvReader = new CSVReader(new InputStreamReader(zis, StandardCharsets.UTF_8));
                        List<String[]> csvData = csvReader.readAll();

                        assertEquals(3, csvData.size());
                        assertArrayEquals(
                                new String[]{"id", "name", "email", "registration_date"},
                                csvData.get(0));

                        assertEquals("1", csvData.get(1)[0]);
                        assertEquals("Иван Иванов", csvData.get(1)[1]);
                        assertEquals("ivan@example.com", csvData.get(1)[2]);

                        assertEquals("2", csvData.get(2)[0]);
                        assertEquals("Петр Петров", csvData.get(2)[1]);
                        assertEquals("peter@example.com", csvData.get(2)[2]);
                        return;
                    }
                }
            }
        }
    }
}