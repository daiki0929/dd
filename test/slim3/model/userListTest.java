package slim3.model;

import org.slim3.tester.AppEngineTestCase;

import slim3.model.customerManage.Customer;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class userListTest extends AppEngineTestCase {

    private Customer model = new Customer();

    @Test
    public void test() throws Exception {
        assertThat(model, is(notNullValue()));
    }
}
