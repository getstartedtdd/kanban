package test.apis.java;

import org.junit.Test;

import java.math.BigInteger;
import java.net.URLEncoder;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by L.x on 15-6-2.
 */
public class StringTest {
    @Test
    public void numberToHexString() throws Exception {
        assertThat(String.format("%X", 0xff), equalTo("FF"));
    }

    @Test
    public void bytesToHexString() throws Exception {
        String text = "中文";
        String encoding = "GBK";
        String expected = URLEncoder.encode(text, encoding).replaceAll("%", "");

        String actual = String.format("%X", new BigInteger(1, text.getBytes(encoding)));

        assertThat(actual, equalTo(expected));
    }

}
