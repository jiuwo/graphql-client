# graphql-client

#### 项目介绍

* GraphQL Java客户端

#### 使用说明

* 引用JAR包

    ``` xml
        <dependency>
            <groupId>org.jiuwo</groupId>
            <artifactId>graphql-client</artifactId>
            <version>1.1.0</version>
        </dependency>
    ```   
* 具体使用

    ``` java
        //单个实本查询，返回结果可直接使用不需要反序列化
        ExecutionResult<Book> bookItem = GraphqlClient
                .build()
                .url(url)
                .requestType(RequestTypeEnum.QUERY)
                .addHeader("hash", "haha")
                .addRequest(GraphqlRequest.build("findAuthorById").addResultAttributes("id name").addParameter("id", 1))
                .executeSingle(Book.class);
        
    ```   
    
    ``` java
        //多个查询一起反回
        ExecutionResult<Book> bookItem = GraphqlClient
                .build()
                .url(url)
                .requestType(RequestTypeEnum.QUERY)
                .addHeader("hash", "haha")
                .addRequest(GraphqlRequest.build("findAuthorById").addResultAttributes("id name").addParameter("id", 1))
                .addRequest(GraphqlRequest.build("findAuthors").addResultAttributes("id name"))
                .execute();
        
    ```  
* 同时支持无限级JSON结果