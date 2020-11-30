package com.github.cinema.home.client.server.common.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class FileSizeConverterTest {
    @Test(expected = NumberFormatException.class)
    public void givenNonNumber_assertExceptionThrown() {
        FileSizeConverter.toBytesSize("NonNumber123");
    }

    @Test
    public void givenNoPostFix_assertBitsConvertedToBytes() {
        long bytes = FileSizeConverter.toBytesSize("416");
        Assert.assertEquals(52, bytes);
    }

    @Test
    public void givenNoPostFixAndNotRoundNumber_assertBitsConvertedToBytes() {
        long bytes = FileSizeConverter.toBytesSize("420");
        Assert.assertEquals(53, bytes);
    }

    @Test
    public void givenGBWithoutSpace_assertBytes() {
        long bytes = FileSizeConverter.toBytesSize("1.2GiB");
        Assert.assertEquals(1288490188, bytes);
        bytes = FileSizeConverter.toBytesSize("1.2GB");
        Assert.assertEquals(1200000000, bytes);
    }

    @Test
    public void givenGBWithSpace_assertBytes() {
        long bytes = FileSizeConverter.toBytesSize("1.2 GiB");
        Assert.assertEquals(1288490188, bytes);
        bytes = FileSizeConverter.toBytesSize("1.2 GB");
        Assert.assertEquals(1200000000, bytes);
    }

    @Test
    public void givenMBWithoutSpace_assertBytes() {
        long bytes = FileSizeConverter.toBytesSize("1.58MiB");
        Assert.assertEquals(1656750, bytes);
        bytes = FileSizeConverter.toBytesSize("1.58MB");
        Assert.assertEquals(1580000, bytes);
    }

    @Test
    public void givenMBWithSpace_assertBytes() {
        long bytes = FileSizeConverter.toBytesSize("1.58 MiB");
        Assert.assertEquals(1656750, bytes);
        bytes = FileSizeConverter.toBytesSize("1.58 MB");
        Assert.assertEquals(1580000, bytes);
    }

    @Test
    public void givenKBWithoutSpace_assertBytes() {
        long bytes = FileSizeConverter.toBytesSize("5.0KiB");
        Assert.assertEquals(5120, bytes);
        bytes = FileSizeConverter.toBytesSize("5.0KB");
        Assert.assertEquals(5000, bytes);
    }

    @Test
    public void givenKBWithSpace_assertBytes() {
        long bytes = FileSizeConverter.toBytesSize("5.0 KiB");
        Assert.assertEquals(5120, bytes);
        bytes = FileSizeConverter.toBytesSize("5.0 KB");
        Assert.assertEquals(5000, bytes);
    }

    @Test
    public void givenTBWithSpace_assertBytes() {
        long bytes = FileSizeConverter.toBytesSize("1.67 TiB");
        Assert.assertEquals(1836184418385L, bytes);
        bytes = FileSizeConverter.toBytesSize("1.67 TB");
        Assert.assertEquals(1670000000000L, bytes);
    }

    @Test
    public void givenTBWithoutSpace_assertBytes() {
        long bytes = FileSizeConverter.toBytesSize("1.67TiB");
        Assert.assertEquals(1836184418385L, bytes);
        bytes = FileSizeConverter.toBytesSize("1.67TB");
        Assert.assertEquals(1670000000000L, bytes);
    }

    @Test
    public void givenMBOver1000_assertBytes() {
        long bytes = FileSizeConverter.toBytesSize("1007.59 MiB");
        Assert.assertEquals(1056534691L, bytes);
        bytes = FileSizeConverter.toBytesSize("1007.59 MB");
        Assert.assertEquals(1007590000, bytes);
    }
}
