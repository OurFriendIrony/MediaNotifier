package uk.co.ourfriendirony.medianotifier.general;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class HelperTest {
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Test
    public void cleanUrlEncodesSpaces() {
        assertEquals(
                "simple+url",
                Helper.cleanUrl("simple url")
        );
    }

    @Test
    public void cleanTitleReversesTheAndA() {
        assertEquals(
                "Simple Test, The",
                Helper.cleanTitle("The Simple Test")
        );
        assertEquals(
                "Simple Test, A",
                Helper.cleanTitle("A Simple Test")
        );
        assertEquals(
                "Simple Test",
                Helper.cleanTitle("Simple Test")
        );
    }

    @Test
    public void canConvertDateToString() {
        Date date = new GregorianCalendar(2001, 0, 1).getTime();
        assertEquals(
                "2001-01-01",
                Helper.dateToString(date)
        );
    }

    @Test
    public void canHandleNullDateToString() {
        assertEquals(
                "",
                Helper.dateToString(null)
        );
    }

    @Test
    public void canHandleStringToDate() {
        Date expectedDate = new GregorianCalendar(2001, 0, 1).getTime();
        String dateString = "2001-01-01";
        assertEquals(
                expectedDate,
                Helper.stringToDate(dateString)
        );
    }

    @Test
    public void willDefaultToNowIfFailsToParseDate() {
        String dateString = "wibble";
        long currentTime = new GregorianCalendar().getTime().getTime();
        long exceptionTime = Helper.stringToDate(dateString).getTime();
        assertTrue(currentTime <= exceptionTime);
    }

    @Test
    public void canPadNumberWithLeadingZeros() {
        assertEquals(
                "0000003",
                Helper.pad(3, 7)
        );
        assertEquals(
                "00000000123",
                Helper.pad(123, 11)
        );
    }

    @Test
    public void canReplaceSingleTokens() {
        String token = "@WIBBLE@";
        String value = "expected";
        String initialString = "this is my @WIBBLE@ string. string values should be @WIBBLE@";

        assertEquals(
                "this is my expected string. string values should be expected",
                Helper.replaceTokens(initialString, token, value)
        );
    }

    @Test
    public void canReplaceAllTokens() {
        String[] tokens = {"@WIBBLE@", "@WOBBLE@"};
        String[] values = {"expected", "string"};
        String initialString = "this is my @WIBBLE@ @WOBBLE@. @WOBBLE@ values should be @WIBBLE@";

        assertEquals(
                "this is my expected string. string values should be expected",
                Helper.replaceTokens(initialString, tokens, values)
        );
    }

    @Test
    public void ignoreReplaceTokensWhenValueAndTokenLengthsMismatch() {
        String[] tokens = {"@WIBBLE@", "@WOBBLE@"};
        String[] values = {"expected"};
        String initialString = "this is my @WIBBLE@ @WOBBLE@. @WOBBLE@ values should be @WIBBLE@";

        assertEquals(
                "this is my @WIBBLE@ @WOBBLE@. @WOBBLE@ values should be @WIBBLE@",
                Helper.replaceTokens(initialString, tokens, values)
        );
    }

    @Test
    public void canGetStringValueOfInteger() {
        assertEquals(
                "1",
                Helper.getNotificationNumber(1)
        );
        assertEquals(
                "7",
                Helper.getNotificationNumber(7)
        );
        assertEquals(
                "9",
                Helper.getNotificationNumber(9)
        );
    }

    @Test
    public void canGetDefinedStringValueIfIntegerGreaterThanTen() {
        assertEquals(
                "9+",
                Helper.getNotificationNumber(10)
        );
        assertEquals(
                "9+",
                Helper.getNotificationNumber(20)
        );
    }
}
