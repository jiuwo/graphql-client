package org.jiuwo.graphql.client.contract;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Steven Han
 */
@Getter
@Setter
public class Locations {
    /**
     * 行号
     */
    private int line;

    /**
     * 列号
     */
    private int column;

    /**
     * 来源名称
     */
    private String sourceName;
}

