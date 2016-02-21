package adalberto.com.phunwaretest;

import junit.framework.TestCase;

import adalberto.com.phunwaretest.model.Venue;

/**
 * Created by Adalberto on 2/20/2016.
 */
public class VenueTest extends TestCase{

    Venue instance;

    public void setUp() throws Exception{
        super.setUp();
        instance = new Venue();
    }

    public void test_set_name(){
        instance.setName("Adalberto");
        assertEquals("Adalberto", instance.getName());
    }
}
