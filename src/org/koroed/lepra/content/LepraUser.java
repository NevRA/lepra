package org.koroed.lepra.content;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: Nikita Koroed
 * E-mail: nikita@koroed.org
 * Date: 26.05.2014
 * Time: 11:22
 */
public class LepraUser implements Serializable {
    private Integer id;// : 40484,
    private String login;// : 'Hutch',
    private String gender;// : 'male',
    private Integer karma;// : 307,
    private Date created;// : 1258152775,
    private Integer invitedById;// : 18529,

    public LepraUser(Integer id, String login, String gender, Integer karma) {
        this.id = id;
        this.login = login;
        this.gender = gender;
        this.karma = karma;
    }

    public LepraUser(Integer id, String login, String gender, Integer karma, Date created, Integer invitedById) {
        this.id = id;
        this.login = login;
        this.gender = gender;
        this.karma = karma;
        this.created = created;
        this.invitedById = invitedById;
    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getGender() {
        return gender;
    }

    public Integer getKarma() {
        return karma;
    }

    public Date getCreated() {
        return created;
    }

    public Integer getInvitedById() {
        return invitedById;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setInvitedById(Integer invitedById) {
        this.invitedById = invitedById;
    }

    @Override
    public String toString() {
        return "LepraUser{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", gender='" + gender + '\'' +
                ", karma=" + karma +
                ", created=" + created +
                ", invitedById=" + invitedById +
                '}';
    }
}
