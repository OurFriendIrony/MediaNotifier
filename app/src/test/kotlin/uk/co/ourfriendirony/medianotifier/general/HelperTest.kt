package uk.co.ourfriendirony.medianotifier.general

import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNull
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class HelperTest {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")

    @Test
    fun cleanUrlEncodesSpaces() {
        assertEquals(
            "simple+url",
            Helper.cleanUrl("simple url")
        )
    }

    @Test
    fun cleanTitleReversesTheAndA() {
        assertEquals(
            "Simple Test, The",
            Helper.cleanTitle("The Simple Test")
        )
        assertEquals(
            "Simple Test, A",
            Helper.cleanTitle("A Simple Test")
        )
        assertEquals(
            "Simple Test",
            Helper.cleanTitle("Simple Test")
        )
    }

    @Test
    fun canConvertDateToString() {
        val date = GregorianCalendar(2001, 0, 1).time
        assertEquals(
            "2001-01-01",
            Helper.dateToString(date)
        )
    }

    @Test
    fun canHandleNullDateToString() {
        assertNull(Helper.dateToString(null))
    }

    @Test
    fun canHandleStringToDate() {
        val expectedDate = GregorianCalendar(2001, 0, 1).time
        val dateString = "2001-01-01"
        assertEquals(
            expectedDate,
            Helper.stringToDate(dateString)
        )
    }

    @Test
    fun canReplaceSingleTokens() {
        val token = "@WIBBLE@"
        val value = "expected"
        val initialString = "this is my @WIBBLE@ string. string values should be @WIBBLE@"

        assertEquals(
            "this is my expected string. string values should be expected",
            Helper.replaceTokens(initialString, token, value)
        )
    }

    @Test
    fun canReplaceAllTokens() {
        val tokens = arrayOf("@WIBBLE@", "@WOBBLE@")
        val values = arrayOf("expected", "string")
        val initialString = "this is my @WIBBLE@ @WOBBLE@. @WOBBLE@ values should be @WIBBLE@"

        assertEquals(
            "this is my expected string. string values should be expected",
            Helper.replaceTokens(initialString, tokens, values)
        )
    }

    @Test
    fun ignoreReplaceTokensWhenValueAndTokenLengthsMismatch() {
        val tokens = arrayOf("@WIBBLE@", "@WOBBLE@")
        val values = arrayOf("expected")
        val initialString = "this is my @WIBBLE@ @WOBBLE@. @WOBBLE@ values should be @WIBBLE@"

        assertEquals(
            "this is my @WIBBLE@ @WOBBLE@. @WOBBLE@ values should be @WIBBLE@",
            Helper.replaceTokens(initialString, tokens, values)
        )
    }

    @Test
    fun canGetStringValueOfInteger() {
        assertEquals(
            "1",
            Helper.getNotificationNumber(1)
        )
        assertEquals(
            "7",
            Helper.getNotificationNumber(7)
        )
        assertEquals(
            "9",
            Helper.getNotificationNumber(9)
        )
    }

    @Test
    fun canGetDefinedStringValueIfIntegerGreaterThanTen() {
        assertEquals(
            "9+",
            Helper.getNotificationNumber(10)
        )
        assertEquals(
            "9+",
            Helper.getNotificationNumber(20)
        )
    }
}
