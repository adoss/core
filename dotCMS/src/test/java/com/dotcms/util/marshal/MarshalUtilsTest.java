package com.dotcms.util.marshal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.junit.Test;

import com.dotcms.TestBase;
import com.dotcms.auth.providers.jwt.beans.DotCMSSubjectBean;
import com.dotmarketing.util.json.JSONException;
import com.dotmarketing.util.json.JSONObject;

/**
 * MarshalUtils
 * Test
 * @author jsanca
 * @version 3.7
 */

public class MarshalUtilsTest extends TestBase {
	
    /**
     * Testing the marshall
     */
    @Test
    public void marshalTest() throws ParseException, JSONException {


        final MarshalFactory marshalFactory =
                MarshalFactory.getInstance();

        assertNotNull(marshalFactory);

        final MarshalUtils marshalUtils =
                marshalFactory.getMarshalUtils();

        assertNotNull(marshalUtils);

        final SimpleDateFormat dateFormat =
                new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-8:00"));
        dateFormat.setLenient(true);

        final DotCMSSubjectBean subjectBean =
                new DotCMSSubjectBean(dateFormat.parse("04/10/1981"), "jsanca", "myCompany");

        final String json = marshalUtils.marshal(subjectBean);

        assertNotNull(json);
        System.out.println(json);

        assertTrue(
                new JSONObject("{\"userId\":\"jsanca\",\"lastModified\":371030400000, \"companyId\":\"myCompany\"}").toString().equals
                        (new JSONObject(json).toString())
        );


        final DotCMSSubjectBean dotCMSSubjectBean2 =
                marshalUtils.unmarshal(json, DotCMSSubjectBean.class);

        assertNotNull(dotCMSSubjectBean2);
        assertEquals(subjectBean, dotCMSSubjectBean2);

        final DotCMSSubjectBean dotCMSSubjectBean3 =
                marshalUtils.unmarshal(new StringReader(json), DotCMSSubjectBean.class);

        assertNotNull(dotCMSSubjectBean3);
        assertEquals(subjectBean, dotCMSSubjectBean3);

        final DotCMSSubjectBean dotCMSSubjectBean4 =
                marshalUtils.unmarshal(new ByteArrayInputStream(json.getBytes()), DotCMSSubjectBean.class);

        assertNotNull(dotCMSSubjectBean4);
        assertEquals(subjectBean, dotCMSSubjectBean4);
    }

    @Test
    public void marshalSQLDateTest() throws ParseException, JSONException {


        final MarshalFactory marshalFactory =
                MarshalFactory.getInstance();

        assertNotNull(marshalFactory);

        final MarshalUtils marshalUtils =
                marshalFactory.getMarshalUtils();

        assertNotNull(marshalUtils);

        final SimpleDateFormat dateFormat =
                new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-8:00"));
        dateFormat.setLenient(true);

        final DotCMSSubjectBean subjectBean =
                new DotCMSSubjectBean(new java.sql.Date(dateFormat.parse("04/10/1981").getTime()), "jsanca", "myCompany");

        final String json = marshalUtils.marshal(subjectBean);

        assertNotNull(json);
        System.out.println(json);

        assertTrue(
                new JSONObject("{\"userId\":\"jsanca\",\"lastModified\":371030400000, \"companyId\":\"myCompany\"}").toString().equals
                        (new JSONObject(json).toString())
        );


        final DotCMSSubjectBean dotCMSSubjectBean2 =
                marshalUtils.unmarshal(json, DotCMSSubjectBean.class);

        assertNotNull(dotCMSSubjectBean2);
        assertEquals(subjectBean, dotCMSSubjectBean2);

        final DotCMSSubjectBean dotCMSSubjectBean3 =
                marshalUtils.unmarshal(new StringReader(json), DotCMSSubjectBean.class);

        assertNotNull(dotCMSSubjectBean3);
        assertEquals(subjectBean, dotCMSSubjectBean3);

        final DotCMSSubjectBean dotCMSSubjectBean4 =
                marshalUtils.unmarshal(new ByteArrayInputStream(json.getBytes()), DotCMSSubjectBean.class);

        assertNotNull(dotCMSSubjectBean4);
        assertEquals(subjectBean, dotCMSSubjectBean4);
    }

    @Test
    public void marshalSqlTimeTest() throws ParseException, JSONException {


        final MarshalFactory marshalFactory =
                MarshalFactory.getInstance();

        assertNotNull(marshalFactory);

        final MarshalUtils marshalUtils =
                marshalFactory.getMarshalUtils();

        assertNotNull(marshalUtils);

        final SimpleDateFormat dateFormat =
                new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-8:00"));
        dateFormat.setLenient(true);

        final DotCMSSubjectBean subjectBean =
                new DotCMSSubjectBean(new java.sql.Time(dateFormat.parse("04/10/1981").getTime()), "jsanca", "myCompany");

        final String json = marshalUtils.marshal(subjectBean);

        assertNotNull(json);
        System.out.println(json);

        assertTrue(
                new JSONObject("{\"userId\":\"jsanca\",\"lastModified\":371030400000, \"companyId\":\"myCompany\"}").toString().equals
                        (new JSONObject(json).toString())
        );


        final DotCMSSubjectBean dotCMSSubjectBean2 =
                marshalUtils.unmarshal(json, DotCMSSubjectBean.class);

        assertNotNull(dotCMSSubjectBean2);
        assertEquals(subjectBean, dotCMSSubjectBean2);

        final DotCMSSubjectBean dotCMSSubjectBean3 =
                marshalUtils.unmarshal(new StringReader(json), DotCMSSubjectBean.class);

        assertNotNull(dotCMSSubjectBean3);
        assertEquals(subjectBean, dotCMSSubjectBean3);

        final DotCMSSubjectBean dotCMSSubjectBean4 =
                marshalUtils.unmarshal(new ByteArrayInputStream(json.getBytes()), DotCMSSubjectBean.class);

        assertNotNull(dotCMSSubjectBean4);
        assertEquals(subjectBean, dotCMSSubjectBean4);
    }


    @Test
    public void marshalTimeStampTest() throws ParseException, JSONException {


        final MarshalFactory marshalFactory =
                MarshalFactory.getInstance();

        assertNotNull(marshalFactory);

        final MarshalUtils marshalUtils =
                marshalFactory.getMarshalUtils();

        assertNotNull(marshalUtils);

        final SimpleDateFormat dateFormat =
                new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-8:00"));
        dateFormat.setLenient(true);

        final DotCMSSubjectBean subjectBean =
                new DotCMSSubjectBean(new java.sql.Timestamp(dateFormat.parse("04/10/1981").getTime()), "jsanca", "myCompany");

        final String json = marshalUtils.marshal(subjectBean);

        assertNotNull(json);
        System.out.println(json);

        assertTrue(
                new JSONObject("{\"userId\":\"jsanca\",\"lastModified\":371030400000, \"companyId\":\"myCompany\"}").toString().equals
                        (new JSONObject(json).toString())
        );


        final DotCMSSubjectBean dotCMSSubjectBean2 =
                marshalUtils.unmarshal(json, DotCMSSubjectBean.class);

        assertNotNull(dotCMSSubjectBean2);
        assertEquals(subjectBean, dotCMSSubjectBean2);

        final DotCMSSubjectBean dotCMSSubjectBean3 =
                marshalUtils.unmarshal(new StringReader(json), DotCMSSubjectBean.class);

        assertNotNull(dotCMSSubjectBean3);
        assertEquals(subjectBean, dotCMSSubjectBean3);

        final DotCMSSubjectBean dotCMSSubjectBean4 =
                marshalUtils.unmarshal(new ByteArrayInputStream(json.getBytes()), DotCMSSubjectBean.class);

        assertNotNull(dotCMSSubjectBean4);
        assertEquals(subjectBean, dotCMSSubjectBean4);
    }
}
