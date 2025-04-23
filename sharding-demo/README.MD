# 集成shardingsphere-jdbc:5.5.0版本
> 5.5.0之前的版本
```xml
<dependency>
    <groupId>org.apache.shardingsphere</groupId>
    <artifactId>shardingsphere-jdbc-core</artifactId>
    <version>${shardingsphere.version}</version>
</dependency>
```
> 5.5.0版本
```xml
<dependency>
    <groupId>org.apache.shardingsphere</groupId>
    <artifactId>shardingsphere-jdbc</artifactId>
    <version>5.5.0</version>
    <exclusions>
        <exclusion>
            <groupId>org.apache.shardingsphere</groupId>
            <artifactId>shardingsphere-test-util</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

> [参考文档](https://shardingsphere.apache.org/document/5.5.0/cn/user-manual/shardingsphere-jdbc/yaml-config/)