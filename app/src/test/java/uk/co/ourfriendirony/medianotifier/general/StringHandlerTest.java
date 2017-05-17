package uk.co.ourfriendirony.medianotifier.general;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class StringHandlerTest {
    @Test
    public void cleanUrlEncodesSpaces() {
        assertEquals(
                "simple+url",
                StringHandler.cleanUrl("simple url")
        );
    }

    @Test
    public void cleanTitleReversesTheAndA() {
        assertEquals(
                "Simple Test, The",
                StringHandler.cleanTitle("The Simple Test")
        );
        assertEquals(
                "Simple Test, A",
                StringHandler.cleanTitle("A Simple Test")
        );
    }
}
