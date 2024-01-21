package cc.newex.dax.integration.data.msg;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.integration.criteria.msg.MessageExample;
import cc.newex.dax.integration.domain.msg.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MessageRepository
        extends CrudRepository<Message, MessageExample, Long> {

    List<Message> selectOutboxMessages(@Param("offset") int offset,
                                       @Param("length") int length,
                                       @Param("date") Date date);
}
