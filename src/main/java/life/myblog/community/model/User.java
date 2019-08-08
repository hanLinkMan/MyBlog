package life.myblog.community.model;

import lombok.Data;

/**
 * @author hlq
 * @create 2019-07-29 11:49
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private long gmtCreate;
    private long gmtModified;
    private String avatarUrl;
}
