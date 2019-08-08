package life.myblog.community.mapper;

import life.myblog.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hlq
 * @create 2019-07-30 22:15
 */

@Mapper
@Component
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag} )")
    void create(Question question);

    @Select("select * from question")
    List<Question> list();
}