package pl.isa.hadoop;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

@Description(value = "_FUNC_ returns 'hello ' + input")
public class HelloWorldUdf extends UDF {
    public String evaluate(String input) {
        return "hello2 " + input;
    }
}
