package com.jt.sharemap.common;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/05/06
 *     desc   :
 *     version: 1.0
 * </pre>
 */
@GlideModule
public final class MyAppGlideModule extends AppGlideModule{
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
