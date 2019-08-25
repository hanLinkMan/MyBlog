package life.myblog.community.service;

import life.myblog.community.dto.PageInfomationDto;
import life.myblog.community.dto.QuestionDto;
import life.myblog.community.mapper.QuestionMapper;
import life.myblog.community.mapper.UserMapper;
import life.myblog.community.model.Question;
import life.myblog.community.model.QuestionExample;
import life.myblog.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hlq
 * @create 2019-08-08 20:13
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PageInfomationDto list(Integer page, Integer size) {
        PageInfomationDto pageInfomationDto = new PageInfomationDto();
        Integer totalPage;
        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());
        if(totalCount % size == 0){
            totalPage = totalCount/size;
        }else {
            totalPage = totalCount/size + 1;
        }
        //容错处理
        if(page < 1){
            page = 1;
        }
        if(page > totalPage){
            page = totalPage;
        }
        pageInfomationDto.setPageInfomation(totalPage,page);
        Integer offset = size * (page - 1);
//        List<Question> questions = questionMapper.list(offset,size);
        List<Question> questions =questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(offset,size));
        List<QuestionDto> questionDtoList = new ArrayList<>();
        for (Question question : questions){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        pageInfomationDto.setQuestions(questionDtoList);
        return pageInfomationDto;
    }

    public PageInfomationDto list(Integer userId, Integer page, Integer size) {
        PageInfomationDto pageInfomationDto = new PageInfomationDto();
        Integer totalPage;
//        Integer totalCount = questionMapper.countByUserId(userId);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = (int)questionMapper.countByExample(questionExample);

        if(totalCount % size == 0){
            totalPage = totalCount/size;
        }else {
            totalPage = totalCount/size + 1;
        }
        //容错处理
        if(page < 1){
            page = 1;
        }
        if(page > totalPage){
            page = totalPage;
        }
        pageInfomationDto.setPageInfomation(totalPage,page);
        Integer offset = size * (page - 1);
//        List<Question> questions = questionMapper.listByUserId(userId,offset,size);
        QuestionExample example = new QuestionExample();
//        example.setOrderByClause("gmtModified");
        example.createCriteria()
                .andCreatorEqualTo(userId);
        List<Question> questions =questionMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,size));

        List<QuestionDto> questionDtoList = new ArrayList<>();

        for (Question question : questions){
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        pageInfomationDto.setQuestions(questionDtoList);
        return pageInfomationDto;
    }

    public QuestionDto getById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question,questionDto);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDto.setUser(user);
        return questionDto;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }else {
            //更新
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            questionMapper.updateByExampleSelective(updateQuestion,example);
        }
    }
}
