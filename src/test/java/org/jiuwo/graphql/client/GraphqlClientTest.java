package org.jiuwo.graphql.client;

import org.jiuwo.graphql.client.enums.RequestTypeEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Steven Han
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RequestTypeEnum.class})
public class GraphqlClientTest {
    GraphqlClient client;

    @Before
    public void setUp() {
        client = new GraphqlClient();
    }

    @Test
    public void executeTest() {

    }
}
