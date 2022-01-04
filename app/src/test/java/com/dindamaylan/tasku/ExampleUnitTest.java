package com.dindamaylan.tasku;

import org.junit.Test;

import static org.junit.Assert.*;

import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void getAWeekToGo(){
        Calendar calendar = Calendar.getInstance(new Locale("in", "ID"));
        calendar.setTime(Timestamp.now().toDate());
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy", new Locale("in", "ID"));
        System.out.println("Before : "+format.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_WEEK, 7);
        System.out.println("After : "+format.format(calendar.getTime()));
    }
}