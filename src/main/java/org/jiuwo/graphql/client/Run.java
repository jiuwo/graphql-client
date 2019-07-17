package org.jiuwo.graphql.client;

import java.util.ArrayList;
import java.util.List;

import org.jiuwo.graphql.client.contract.ExecutionResult;
import org.jiuwo.graphql.client.enums.RequestTypeEnum;

/**
 * @author Steven Han
 */
public class Run {

    public static void main(String[] args) {
        String url = "http://localhost:8080/graphql";

//        ExecutionResult object = GraphqlClient
//                .build()
//                .url(url)
//                .addResultAttributes("id", "name")
//                .setRequestName("findAuthorById")
//                .addParameter("id", 2)
//                .execute();

        ExecutionResult object = GraphqlClient
                .build()
                .url(url)
                .addResultAttributes("id", "name")
                .addResultAttribute("author", "id", "name")
                .setRequestName("findBooks")
                .setRequestType(RequestTypeEnum.QUERY)
                .execute();

//        ExecutionResult object = GraphqlClient
//                .build()
//                .url(url)
//                .addResultAttributes("id", "name")
//                .setRequestName("newBook")
//                .setRequestType(RequestTypeEnum.MUTATION)
//                .addParameter("title", "tttt")
//                .addParameter("isbn", "bn")
//                .addParameter("pageCount", 1)
//                .addParameter("authorId", 2)
//                .execute();


        StringBuffer stringBuilder = new StringBuffer();
        stringBuilder.append("1 ");
        stringBuilder.append("2 ");

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");

        String aaa = String.join(" ", list);

        String aa = "";
    }
}
