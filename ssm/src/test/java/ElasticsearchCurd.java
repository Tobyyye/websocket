import com.fasterxml.jackson.databind.ObjectMapper;
import com.guo.ssm.CsdnBlog;
import com.guo.ssm.User;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class ElasticsearchCurd {
    public final static String HOST = "127.0.0.1";
    //http请求的端口是9200，客户端是
    public final static int PORT = 9300;

    TransportClient client;

    @Before
    public void getConect() throws UnknownHostException {
//        Settings esSettings = Settings.builder()
//                .put("cluster.name", "sb") //设置ES实例的名称
//                .put("client.transport.sniff", true) //自动嗅探整个集群的状态，把集群中其他ES节点的ip添加到本地的客户端列表中
//                .build();
//        this.client = new PreBuiltTransportClient(esSettings);//初始化client较老版本发生了变化，此方法有几个重载方法，初始化插件等。
//        //此步骤添加IP，至少一个，其实一个就够了，因为添加了自动嗅探配置
//        client.addTransportAddress(new TransportAddress(InetAddress.getByName(HOST), PORT));
         client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(HOST), PORT));
               // .addTransportAddress(new TransportAddress(InetAddress.getByName("host2"), 9300));
    }

    @After
    public void close(){
        client.close();
    }
    @Test
    public void CreateJSON(){
        String json = "{" +
                "\"user\":\"fendo\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"Hell word\"" +
                "}";
        IndexResponse response = client.prepareIndex("fendo", "fendodate")
                .setSource(json,XContentType.JSON)
                .get();
        System.out.println("status:"+response.toString());
    }

    /**
     * 使用JACKSON序列化
     * @throws Exception
     */
    @Test
    public void CreateJACKSON() throws Exception{
        User user=new User();
     user.setUsername("ssb");
     user.setUserId(111);
     user.setPassword("dddd");
        // instance a json mapper
        ObjectMapper mapper = new ObjectMapper(); // create once, reuse
        // generate json
        byte[] json = mapper.writeValueAsBytes(user);
        IndexResponse response = client.prepareIndex("fendo", "fendodate")
                .setSource(json, XContentType.JSON
                )
                .get();
        System.out.println("result:"+response.toString());
    }

    @Test
    public void CreateJsonBuilder() throws IOException {
        IndexResponse response = client.prepareIndex("twitter", "tweet", "1")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "trying out Elasticsearch")
                        .endObject()
                )
                .execute()
                .actionGet();
        System.out.println("result:"+response.toString());
    }

    @Test
    public void GetAPI(){
        GetResponse response = client.prepareGet("twitter", "tweet", "1")
                .get();
        System.out.println("response:"+response.toString());
    }

    @Test
    public void DeleteAPI(){
        DeleteResponse response = client.prepareDelete("twitter", "tweet", "1").get();
        System.out.println("response:"+response.toString());
    }

    @Test
    public void  UpdateRequest() throws Exception {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("twitter");
        updateRequest.type("tweet");
        updateRequest.id("1");
        updateRequest.doc(jsonBuilder()
                .startObject()
                .field("gender", "male")
                .endObject());
        client.update(updateRequest).get();
        System.out.println("updateRequest"+updateRequest.toString());
    }

    @Test
    public void prepareUpdate() throws IOException {
        client.prepareUpdate("twitter", "tweet", "1")
                .setDoc(jsonBuilder()
                        .startObject()
                        .field("gender", "male")
                        .endObject())
                .get();
    }


}
