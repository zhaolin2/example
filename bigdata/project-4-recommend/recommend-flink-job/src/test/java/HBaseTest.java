import org.apache.flink.api.common.functions.OpenContext;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.BufferedMutator;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseTest {
    public static void main(String[] args) throws Exception {
        org.apache.hadoop.conf.Configuration conf = HBaseConfiguration.create();
        System.setProperty("hadoop.home.dir","E:\\tools\\winutils-master\\winutils-master\\hadoop-2.6.0");
        conf.set("hbase.zookeeper.quorum", "127.0.0.1");
        conf.set("hbase.rootdir", "hdfs://127.0.0.1:9000/hbase");
        conf.set("hbase.zookeeper.property.clientPort", "12181");
        conf.set("bulk.flush.interval.ms", "1000");
        Connection connection = ConnectionFactory.createConnection(conf);

// 创建一个批量异步与hbase通信的对象
        BufferedMutator mutator = connection.getBufferedMutator(TableName.valueOf("rating"));
        Put put = new Put(Bytes.toBytes("rowKey"));
        put.addColumn(Bytes.toBytes("p"), Bytes.toBytes("test_col"), Bytes.toBytes("value"));
        // 向hbase插入数据,达到缓存会自动提交,这里也可以通过传入List<put>的方式批量插入
        mutator.mutate(put);
        // 不用每次put后就调用flush，最后调用就行，这个方法替代了旧api的hTable.setAutoFlush(false, false)
        mutator.flush();
        mutator.close();
        connection.close();

        System.out.println("成功");
    }
}
