package com.github.cinema.home.client.server.common.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Locale;

@RunWith(JUnit4.class)
public class LocaleDetectorTest {
    private LocaleDetector objectUnderTest;

    @Before
    public void before() {
        this.objectUnderTest = new LocaleDetector();
    }

    @Test
    public void givenHunTitle_assertHu() {
        Locale result = this.objectUnderTest.detect("Szerelem vasút");
        Assert.assertEquals("hu", result.getLanguage());
    }

    @Test
    public void givenHunSeasonNo_assertHu() {
        Locale result = this.objectUnderTest.detect("1. évad");
        Assert.assertEquals("hu", result.getLanguage());
    }

    @Test
    public void givenEngSeasonNo_assertEn() {
        Locale result = this.objectUnderTest.detect("Season 1");
        Assert.assertEquals("en", result.getLanguage());
    }

    @Test
    public void givenHunEpisodeNo_assertHu() {
        Locale result = this.objectUnderTest.detect("1. rész");
        Assert.assertEquals("hu", result.getLanguage());
    }
}
