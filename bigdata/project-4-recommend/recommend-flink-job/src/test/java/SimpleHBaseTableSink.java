import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.util.Preconditions;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.protobuf.generated.HBaseProtos;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class SimpleHBaseTableSink extends RichSinkFunction<ConsumerRecord<String, String>> {

    private final String tableName;

    private transient TableName tName;
    private transient Connection connection;

    public SimpleHBaseTableSink(String tableName) throws Exception {
        Preconditions.checkNotNull(tableName);

        this.tableName = tableName;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        this.tName = TableName.valueOf(tableName);

        org.apache.hadoop.conf.Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "127.0.0.1");
        conf.set("hbase.zookeeper.property.clientPort", "12181");
        conf.set("bulk.flush.interval.ms", "1000");
        this.connection = ConnectionFactory.createConnection(conf);
    }

    @Override
    public void invoke(ConsumerRecord<String, String> record, Context context) throws Exception {
        String rowKey = String.valueOf(record.offset());
        String value = record.value();

        // 设置缓存1m，当达到1m时数据会自动刷到hbase，替代了hTable.setWriteBufferSize(30 * 1024 * 1024)
        BufferedMutatorParams params = new BufferedMutatorParams(this.tName);
        params.writeBufferSize(1024 * 1024);
        // 创建一个批量异步与hbase通信的对象
        BufferedMutator mutator = connection.getBufferedMutator(params);
        Put put = new Put(Bytes.toBytes(rowKey));
        put.addColumn(Bytes.toBytes("test_family"), Bytes.toBytes("test_col"), Bytes.toBytes(value));
        // 向hbase插入数据,达到缓存会自动提交,这里也可以通过传入List<put>的方式批量插入
        mutator.mutate(put);
        // 不用每次put后就调用flush，最后调用就行，这个方法替代了旧api的hTable.setAutoFlush(false, false)
        mutator.flush();
        mutator.close();
    }

    @Override
    public void close() throws Exception {
        if (this.connection != null) {
            this.connection.close();
            this.connection = null;
        }
    }
}