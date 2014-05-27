package org.koroed.lepra.content;

import java.util.Date;

/**
 * Author: Nikita Koroed
 * E-mail: nikita@koroed.org
 * Date: 14.05.14
 * Time: 14:24
 */
public class LepraPost {
    private String userLogin;
    private String userTitle;
    private Date date;
    private String totalCommentCnt;
    private String newCommentCnt;
    private String content;

    public LepraPost(String userLogin, String userTitle, Date date, String totalCommentCnt, String newCommentCnt, String content) {
        this.userLogin = userLogin;
        this.userTitle = userTitle;
        this.date = date;
        this.totalCommentCnt = totalCommentCnt;
        this.newCommentCnt = newCommentCnt;
        this.content = content;
    }

    @Override
    public String toString() {
        return "LepraPost{" +
                "userLogin='" + userLogin + '\'' +
                ", userTitle='" + userTitle + '\'' +
                ", date='" + date + '\'' +
                ", totalCommentCnt=" + totalCommentCnt +
                ", newCommentCnt=" + newCommentCnt +
                ", content='" + (content != null && content.length() > 15 ? content.substring(0, 13): content)  + '\'' +
                '}';
    }
}
