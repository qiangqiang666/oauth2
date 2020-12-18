package com.traffic.server.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_wechat_info")
public class WechatInfoEntity extends BaseEntity {

    private Long memberId;

    private String openId;

    private String sessionId;

    private String sessionKey;

    private String name;

    private String avatar;

    public enum COL {
        ID("id"),
        CREATE_AT("create_at"),
        UPDATE_AT("update_at"),
        NAME("name"),
        MEMBER_ID("member_id"),
        OPEN_ID("open_id"),
        SESSION_ID("session_id"),
        SESSION_KEY("session_key"),
        AVATAR("avatar"),

        ;
        private final String name;

        COL(String name) {
            this.name = name;
        }

        public String getColName() {
            return this.name;
        }
    }
}
