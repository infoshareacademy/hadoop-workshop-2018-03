package pl.isa.hadoop;

import org.apache.hadoop.hive.ql.exec.UDF;

public class HelloWorldUdf extends UDF {
    public String evaluate(String val) {
        return "hello " + val;
    }
}
