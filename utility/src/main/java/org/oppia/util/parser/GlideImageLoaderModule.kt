package org.oppia.util.parser

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/** Provides image loading dependencies. */
@Module
class GlideImageLoaderModule {

  @Provides
  @ImageLoaderAnnotation
  fun providesGlideImageLoader(context: Context): ImageLoader {
    return GlideImageLoader(context)
  }
}