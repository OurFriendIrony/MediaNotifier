package uk.co.ourfriendirony.medianotifier.general;

import uk.co.ourfriendirony.medianotifier.R;

public class ImageNumber {
    public static int getNumberImage(int num) {
        switch (num) {
            case 0:
                return R.drawable.img_num0;
            case 1:
                return R.drawable.img_num1;
            case 2:
                return R.drawable.img_num2;
            case 3:
                return R.drawable.img_num3;
            case 4:
                return R.drawable.img_num4;
            case 5:
                return R.drawable.img_num5;
            case 6:
                return R.drawable.img_num6;
            case 7:
                return R.drawable.img_num7;
            case 8:
                return R.drawable.img_num8;
            case 9:
                return R.drawable.img_num9;
            default:
                return R.drawable.img_num_plus;
        }
    }
}
