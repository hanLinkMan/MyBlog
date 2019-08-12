package life.myblog.community.service;

import life.myblog.community.dto.PageInfomationDto;
import life.myblog.community.dto.QuestionDto;
import life.myblog.community.mapper.QuestionMapper;
import life.myblog.community.mapper.UserMapper;
import life.myblog.community.model.Question;
import life.myblog.community.model.User;
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
        Integer totalCount = questionMapper.count();
        pageInfomationDto.setPageInfomation(totalCount,page,size);
        //容错处理
        if(page < 1){
            page = 1;
        }
        if(page > pageInfomationDto.getTotalPage()){
            page = pageInfomationDto.getTotalPage();
        }
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDto> questionDtoList = new ArrayList<>();

        for (Question question : questions){
            User user = userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        pageInfomationDto.setQuestions(questionDtoList);
        return pageInfomationDto;
    }
}
