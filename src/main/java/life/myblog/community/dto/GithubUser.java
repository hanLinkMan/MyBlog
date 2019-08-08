package life.myblog.community.dto;

import lombok.Data;

/**
 * @author hlq
 * @create 2019-07-29 0:18
 */
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;
}
