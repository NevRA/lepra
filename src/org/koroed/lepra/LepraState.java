package org.koroed.lepra;

/**
 * Author: Nikita Koroed
 * E-mail: nikita@koroed.org
 * Date: 14.05.14
 * Time: 14:19
 */
public class LepraState {
    private boolean isAuthenticated;

    private int newPostsCnt;
    private int newCommentInPostsCnt;
    private int newInboxCnt;
    private int newCommentInInboxCnt;

    protected LepraState(boolean authenticated, int newPostsCnt, int newCommentInPostsCnt, int newInboxCnt, int newCommentInInboxCnt) {
        isAuthenticated = authenticated;
        this.newPostsCnt = newPostsCnt;
        this.newCommentInPostsCnt = newCommentInPostsCnt;
        this.newInboxCnt = newInboxCnt;
        this.newCommentInInboxCnt = newCommentInInboxCnt;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public int getNewPostsCnt() {
        return newPostsCnt;
    }

    public int getNewCommentInPostsCnt() {
        return newCommentInPostsCnt;
    }

    public int getNewInboxCnt() {
        return newInboxCnt;
    }

    public int getNewCommentInInboxCnt() {
        return newCommentInInboxCnt;
    }
}
