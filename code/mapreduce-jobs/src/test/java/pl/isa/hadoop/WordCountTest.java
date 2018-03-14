package pl.isa.hadoop;

import com.google.common.collect.ImmutableList;
import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Test;

public class WordCountTest {

    @Test
    public void testMap() throws IOException {
        WordCount.WcMapper mapper = new WordCount.WcMapper();

        MapDriver.newMapDriver(mapper)
                .withInput(new LongWritable(0), new Text("asdf qwerty asdf"))
                .withOutput(new Text("asdf"), new LongWritable(1))
                .withOutput(new Text("qwerty"), new LongWritable(1))
                .withOutput(new Text("asdf"), new LongWritable(1))
                .runTest();
    }

    @Test
    public void testReduce() throws IOException {
        WordCount.WcReducer reducer = new WordCount.WcReducer();

        ReduceDriver.newReduceDriver(reducer)
                .withInput(new Text("asdf"), ImmutableList.of(new LongWritable(1), new LongWritable(1)))
                .withInput(new Text("qwerty"), ImmutableList.of(new LongWritable(1)))
                .withOutput(new Text("asdf"), new LongWritable(2))
                .withOutput(new Text("qwerty"), new LongWritable(1))
                .runTest();
    }

    @Test
    public void testMapReduce() throws IOException {
        WordCount.WcMapper mapper = new WordCount.WcMapper();
        WordCount.WcReducer reducer = new WordCount.WcReducer();

        MapReduceDriver.newMapReduceDriver(mapper, reducer)
                .withInput(new LongWritable(0), new Text("asdf qwerty asdf"))
                .withOutput(new Text("asdf"), new LongWritable(2))
                .withOutput(new Text("qwerty"), new LongWritable(1))
                .runTest();
    }

}