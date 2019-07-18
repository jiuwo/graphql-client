package org.jiuwo.graphql.client.contract;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Steven Han
 */
@ToString
@Getter
@Setter
public class ExecutionResult {
    /**
     * 返回Data
     */
    private Object data;

    /**
     * 错误信息
     */
    private List<GraphqlError> errors;
}
