package com.project.cx.processcontroljx.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Administrator on 2017/12/26 0026.
 */

public class TelphoneUtil {
    public static void toDial(Context ctx,String phoneNumber){
        Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));//跳转到拨
        ctx.startActivity(dialIntent);
    }
}
