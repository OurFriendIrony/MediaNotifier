package uk.co.ourfriendirony.medianotifier.general

import org.junit.Test
import java.util.*

class HelperTest {
    @Test
    fun cleanUrlEncodesSpaces() {
        kotlin.test.assertEquals(
            "simple+url",
            Helper.cleanUrl("simple url")
        )
    }

    @Test
    fun cleanTitleReversesTheAndA() {
        kotlin.test.assertEquals(
            "Simple Test, The",
            Helper.cleanTitle("The Simple Test")
        )
        kotlin.test.assertEquals(
            "Simple Test, A",
            Helper.cleanTitle("A Simple Test")
        )
        kotlin.test.assertEquals(
            "Simple Test",
            Helper.cleanTitle("Simple Test")
        )
    }

    @Test
    fun canConvertDateToString() {
        val date = GregorianCalendar(2001, 0, 1).time
        kotlin.test.assertEquals(
            "2001-01-01",
            Helper.dateToString(date)
        )
    }

    @Test
    fun canHandleNullDateToString() {
        kotlin.test.assertNull(Helper.dateToString(null))
    }

    @Test
    fun canHandleStringToDate() {
        val expectedDate = GregorianCalendar(2001, 0, 1).time
        val dateString = "2001-01-01"
        kotlin.test.assertEquals(
            expectedDate,
            Helper.stringToDate(dateString)
        )
    }

    @Test
    fun canReplaceSingleTokens() {
        val token = "@WIBBLE@"
        val value = "expected"
        val initialString = "this is my @WIBBLE@ string. string values should be @WIBBLE@"

        kotlin.test.assertEquals(
            "this is my expected string. string values should be expected",
            Helper.replaceTokens(initialString, token, value)
        )
    }

    @Test
    fun canReplaceAllTokens() {
        val tokens = arrayOf("@WIBBLE@", "@WOBBLE@")
        val values = arrayOf("expected", "string")
        val initialString = "this is my @WIBBLE@ @WOBBLE@. @WOBBLE@ values should be @WIBBLE@"

        kotlin.test.assertEquals(
            "this is my expected string. string values should be expected",
            Helper.replaceTokens(initialString, tokens, values)
        )
    }

    @Test
    fun ignoreReplaceTokensWhenValueAndTokenLengthsMismatch() {
        val tokens = arrayOf("@WIBBLE@", "@WOBBLE@")
        val values = arrayOf("expected")
        val initialString = "this is my @WIBBLE@ @WOBBLE@. @WOBBLE@ values should be @WIBBLE@"

        kotlin.test.assertEquals(
            "this is my @WIBBLE@ @WOBBLE@. @WOBBLE@ values should be @WIBBLE@",
            Helper.replaceTokens(initialString, tokens, values)
        )
    }

    @Test
    fun canGetStringValueOfInteger() {
        kotlin.test.assertEquals(
            "1",
            Helper.getNotificationNumber(1)
        )
        kotlin.test.assertEquals(
            "7",
            Helper.getNotificationNumber(7)
        )
        kotlin.test.assertEquals(
            "9",
            Helper.getNotificationNumber(9)
        )
    }

    @Test
    fun canGetDefinedStringValueIfIntegerGreaterThanTen() {
        kotlin.test.assertEquals(
            "9+",
            Helper.getNotificationNumber(10)
        )
        kotlin.test.assertEquals(
            "9+",
            Helper.getNotificationNumber(20)
        )
    }
}
