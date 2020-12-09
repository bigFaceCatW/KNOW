package com.know.mybatis.dto;

import java.io.Serializable;

/**
 * @author Ning
 * @date 2020/8/14 16:42
 */
public class ChannelDto implements Serializable {
    private long channelId;
    private String channerlName;

    /**
     * 父渠道ID
     */
    private long channelPId;

    public int getShardingId() {
        return shardingId;
    }

    public void setShardingId(int shardingId) {
        this.shardingId = shardingId;
    }

    private int shardingId;

    public long getChannelId() {
        return channelId;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    public String getChannerlName() {
        return channerlName;
    }

    public void setChannerlName(String channerlName) {
        this.channerlName = channerlName;
    }

    public long getChannelPId() {
        return channelPId;
    }

    public void setChannelPId(long channelPId) {
        this.channelPId = channelPId;
    }
}
