package org.oppia.app.topic.info

import android.text.SpannedString
import android.text.style.StyleSpan
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.hamcrest.Matchers.containsString
import org.junit.Test
import org.junit.runner.RunWith
import org.oppia.app.R
import org.oppia.app.topic.TopicActivity
import org.oppia.app.utility.EspressoTestsMatchers.withDrawable
import org.oppia.app.utility.OrientationChangeAction.Companion.orientationLandscape

private const val TEST_TOPIC_ID = "GJ2rLXRKD5hw"
private const val TOPIC_NAME = "Fractions"
private const val TOPIC_DESCRIPTION =
  "You'll often need to talk about part of an object or group. For example, a jar of milk might be half-full, or " +
      "some of the eggs in a box might have broken. In these lessons, you'll learn to use fractions to describe " +
      "situations like these."

// NOTE TO DEVELOPERS: this test must be annotated with @LooperMode(LooperMode.MODE.PAUSED) to pass on Robolectric.
/** Tests for [TopicInfoFragment]. */
@RunWith(AndroidJUnit4::class)
class TopicInfoFragmentTest {
  private val topicThumbnail = R.drawable.lesson_thumbnail_graphic_child_with_fractions_homework
  private val internalProfileId = 0

  @Test
  fun testTopicInfoFragment_loadFragment_checkTopicName_isCorrect() {
    launchTopicActivityIntent(internalProfileId, TEST_TOPIC_ID).use {
      onView(withId(R.id.topic_name_text_view)).check(matches(withText(containsString(TOPIC_NAME))))
    }
  }

  @Test
  fun testTopicInfoFragment_loadFragmentWithTestTopicId1_checkTopicDescription_isCorrect() {
    launchTopicActivityIntent(internalProfileId, TEST_TOPIC_ID).use {
      onView(withId(R.id.topic_description_text_view)).check(matches(withText(containsString(TOPIC_DESCRIPTION))))
    }
  }

  @Test
  fun testTopicInfoFragment_loadFragmentWithTestTopicId1_checkTopicDescription_hasRichText() {
    launchTopicActivityIntent(internalProfileId, TEST_TOPIC_ID).use { scenario ->
      scenario.onActivity { activity ->
        val descriptionTextView: TextView = activity.findViewById(R.id.topic_description_text_view)
        val descriptionText = descriptionTextView.text as SpannedString
        val spans = descriptionText.getSpans(0, descriptionText.length, StyleSpan::class.java)
        assertThat(spans).isNotEmpty()
      }
    }
  }

  @Test
  fun testTopicInfoFragment_loadFragment_configurationChange_checkTopicThumbnail_isCorrect() {
    launchTopicActivityIntent(internalProfileId, TEST_TOPIC_ID).use {
      onView(withId(R.id.topic_thumbnail_image_view)).check(matches(withDrawable(topicThumbnail)))
    }
  }

  @Test
  fun testTopicInfoFragment_loadFragment_configurationChange_checkTopicName_isCorrect() {
    launchTopicActivityIntent(internalProfileId, TEST_TOPIC_ID).use {
      onView(isRoot()).perform(orientationLandscape())
      onView(withId(R.id.topic_name_text_view)).check(matches(withText(containsString(TOPIC_NAME))))
    }
  }

  @Test
  fun testTopicInfoFragment_loadFragment_configurationLandscape_isCorrect() {
    launchTopicActivityIntent(internalProfileId, TEST_TOPIC_ID).use {
      onView(isRoot()).perform(orientationLandscape())
      onView(withId(R.id.topic_tabs_viewpager_container)).check(matches(isDisplayed()))
    }
  }

  @Test
  fun testTopicInfoFragment_loadFragment_configurationLandscape_imageViewNotDisplayed() {
    launchTopicActivityIntent(internalProfileId, TEST_TOPIC_ID).use {
      onView(isRoot()).perform(orientationLandscape())
      onView(withId(R.id.topic_thumbnail_image_view)).check(doesNotExist())
    }
  }

  private fun launchTopicActivityIntent(internalProfileId: Int, topicId: String): ActivityScenario<TopicActivity> {
    val intent =
      TopicActivity.createTopicActivityIntent(ApplicationProvider.getApplicationContext(), internalProfileId, topicId)
    return ActivityScenario.launch(intent)
  }
}
