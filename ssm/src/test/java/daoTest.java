import com.guo.ssm.websocket.Content;
import com.guo.ssm.websocket.ContentDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-mybatis.xml")
public class daoTest {

    @Autowired
    ContentDao contentDao;

    @Test
    public void insertContent(){
        Content content=new Content();
        content.setContent("hahha");
        contentDao.insertSelective(content);

    }
}
