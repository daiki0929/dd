package slim3.model;

import org.slim3.tester.AppEngineTestCase;

import slim3.model.reserve.ManageUser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class userListTest extends AppEngineTestCase {

    private ManageUser model = new ManageUser();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}
