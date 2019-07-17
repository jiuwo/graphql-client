package org.jiuwo.graphql.client.contract;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Steven Han
 */
@Getter
@Setter
public class GraphqlError {
    /**
     * 错误信息
     */
    private String message;

    /**
     * Locations
     */
    private List<Locations> locations;
}
