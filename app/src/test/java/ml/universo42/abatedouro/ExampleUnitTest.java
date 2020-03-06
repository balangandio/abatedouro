package ml.universo42.abatedouro;

import org.junit.Test;

import ml.universo42.abatedouro.model.Model;
import ml.universo42.abatedouro.model.ModelFile;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    /*@Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }*/

    @Test
    public void test() {
        ModelFile mf = new ModelFile(null);

        Model model = mf.fromJson("{\n" +
                "\t\"jornadaDiaria\": 8,\n" +
                "\t\"meses\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"mes\": \"01/2020\",\n" +
                "\t\t\t\"dias\": [\n" +
                "\t\t\t\t[ \"12:12\", \"13:13\", \"14:14\" ]\n" +
                "\t\t\t]\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}");

        String s = mf.toJson(model);

        System.out.println(s);
        assertNotNull(s);
    }

}