package life.myblog.community.dto;

import life.myblog.community.model.User;
import lombok.Data;

/**
 * @author hlq
 * @create 2019-08-08 20:11
 */
@Data
public class QuestionDto {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
