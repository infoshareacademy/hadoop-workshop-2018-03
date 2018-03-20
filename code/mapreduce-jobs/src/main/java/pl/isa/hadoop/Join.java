package pl.isa.hadoop;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Join {
    public static class TransferMapper extends Mapper<LongWritable, Text, Text, Text> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            // 4522e5f518669dd1ba4f9c6b7114446880efecc8,85b2d6dba46efef9fa7503fdd331b1d8df613f81,516358,2018-03-16 19:39:59
            String[] fields = value.toString().split(",");
            String src = fields[0];
            context.write(new Text(src), value);
        }
    }

    public static class OwnerMapper extends Mapper<LongWritable, Text, Text, Text> {
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            // Lidia,ec3094166bd337b37c1bb7df15bdd53a0d9c65df
            String [] fields = value.toString().split(",");
            String accountId = fields[1];
            context.write(new Text(accountId), new Text(fields[0]));
        }
    }

    public static class JoinReducer extends Reducer<Text, Text, Text, Text> {
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            String hash = key.toString();
            String name = "";
            List<String> transfers = new ArrayList<>();

            for(Text value : values) {
                String val = value.toString();
                if(val.startsWith(hash)) {
                    transfers.add(val);
                } else {
                    name = val;
                }
            }

            for(String transfer : transfers) {
                context.write(key, new Text(transfer.replace(hash, name)));
            }
        }
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        String inputTransfers = args[0];
        String inputOwners = args[1];
        String output = args[2];

        Job job = Job.getInstance();
        job.setReducerClass(JoinReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setJarByClass(Join.class);
        job.setJobName("join task");

        MultipleInputs.addInputPath(job, new Path(inputTransfers), TextInputFormat.class, TransferMapper.class);
        MultipleInputs.addInputPath(job, new Path(inputOwners), TextInputFormat.class, OwnerMapper.class);
        FileOutputFormat.setOutputPath(job, new Path(output));

        job.waitForCompletion(true);
    }
}
