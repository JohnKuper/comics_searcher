package com.korobeinikov.comicsviewer.util;

import org.junit.Test;

import static com.korobeinikov.comicsviewer.util.MD5HashHelper.computeMarvelMD5hash;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Dmitriy_Korobeinikov.
 */
public class MD5HashHelperTest {

    @Test
    public void md5HashShouldBeComputedCorrectly() {
        int[] timeStamps = {123456789, 342345234, 34523423};
        String[] correctResults = {"0af01c5a157cbd1424a7e76a100e7cb3", "6405106e2ad203f38fcbd3d782a522b8", "68945ae2c0306b3ee194412a97b1c99e"};
        for (int i = 0; i < timeStamps.length; i++) {
            assertEquals(correctResults[i], computeMarvelMD5hash(timeStamps[i]));
        }
    }
}