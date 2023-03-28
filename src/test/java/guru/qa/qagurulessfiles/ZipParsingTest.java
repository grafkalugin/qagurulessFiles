package guru.qa.qagurulessfiles;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ZipParsingTest {
    private ClassLoader cl = guru.qa.qagurulessfiles.ZipParsingTest.class.getClassLoader();

    @Test
    void zipPdfParseTest() throws Exception {
        try (InputStream is = cl.getResourceAsStream("zip/files.zip");
             ZipInputStream zs = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zs.getNextEntry()) != null) {
                System.out.println(entry.getName());
                if (entry.getName().equals("results.pdf")) {
                    System.out.println("файл найден");
                    PDF pdf = new PDF(zs);
                    assertEquals(1, pdf.numberOfPages);
                    assertTrue(pdf.text.startsWith("ГБУЗ"));
                    assertTrue(pdf.text.contains("РЕЗУЛЬТАТЫ"));
                    assertTrue(pdf.title.equals("Результат лабораторного исследования"));
                    return;
                }
            }
        }
    }

    @Test
    public void zipXlsxParse() throws Exception {
        try (InputStream is = cl.getResourceAsStream("zip/files.zip");
             ZipInputStream zs = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zs.getNextEntry()) != null) {
                System.out.println(entry.getName());
                if (entry.getName().equals("types.xlsx")) {
                    System.out.println("файл найден");
                    XLS xls = new XLS(zs);
                    Assertions.assertTrue(
                            xls.excel.getSheetAt(0).getRow(0).getCell(0).getStringCellValue()
                                    .startsWith("CODE")
                    );
                    return;
                }
            }
        }
    }

    @Test
    public void zipCsvParse() throws Exception {
        try (InputStream is = cl.getResourceAsStream("zip/files.zip");
             ZipInputStream zs = new ZipInputStream(is);
            InputStreamReader isr = new InputStreamReader(zs)){
            ZipEntry entry;
            while ((entry = zs.getNextEntry()) != null) {
                System.out.println(entry.getName());
                if (entry.getName().equals("hello.csv")) {
                    System.out.println("файл найден");
                    CSVReader csvReader = new CSVReader(isr);
                    List<String[]> content = csvReader.readAll();
                    System.out.println(Arrays.toString(content.get(0)));
                    Assertions.assertArrayEquals(new String[] {"label", "expected", "label"}, content.get(0));
                    Assertions.assertEquals(3, content.size());
                    Assertions.assertEquals(3, content.get(1).length);
                    return;
                }
            }
        }
    }
}
