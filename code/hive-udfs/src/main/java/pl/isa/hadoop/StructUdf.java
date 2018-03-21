package pl.isa.hadoop;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StructUdf extends GenericUDF {
    private StringObjectInspector soi;
    private StringObjectInspector soi2;

    @Override
    public ObjectInspector initialize(ObjectInspector[] objectInspectors) throws UDFArgumentException {
        if (objectInspectors.length != 2) {
            throw new UDFArgumentException("asdf");
        }

        soi = (StringObjectInspector) objectInspectors[0];
        soi2 = (StringObjectInspector) objectInspectors[1];

        List<String> fieldNames = Arrays.asList("field1", "field2");

        List<ObjectInspector> fieldOis = new ArrayList<>();
        fieldOis.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
        fieldOis.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);

        return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOis);
    }

    @Override
    public Object evaluate(DeferredObject[] deferredObjects) throws HiveException {
        String value1 = soi.getPrimitiveJavaObject(deferredObjects[0].get());
        String value2 = soi2.getPrimitiveJavaObject(deferredObjects[1].get());

        return new Object[]{value1, value2};
    }

    @Override
    public String getDisplayString(String[] strings) {
        return null;
    }
}
