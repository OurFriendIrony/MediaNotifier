package uk.co.ourfriendirony.medianotifier.general;

import uk.co.ourfriendirony.medianotifier.R;

public class ImageNumber {
    public static int getNumberImage(int num) {
        switch (num) {
            case 0:
                return R.drawable.num_0_mini;
            case 1:
                return R.drawable.num_1_mini;
            case 2:
                return R.drawable.num_2_mini;
            case 3:
                return R.drawable.num_3_mini;
            case 4:
                return R.drawable.num_4_mini;
            case 5:
                return R.drawable.num_5_mini;
            case 6:
                return R.drawable.num_6_mini;
            case 7:
                return R.drawable.num_7_mini;
            case 8:
                return R.drawable.num_8_mini;
            case 9:
                return R.drawable.num_9_mini;
            default:
                return R.drawable.num_plus_mini;
        }
    }
}
