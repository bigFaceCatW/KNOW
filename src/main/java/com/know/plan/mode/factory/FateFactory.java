package com.know.plan.mode.factory;

/**
 * @Author: Facecat
 * @Date: 2020/9/20 11:05
 */
public class FateFactory implements AnimeFactory {
    @Override
    public Anime create() {
        System.out.println("创建fate类");
        return new Fate();
    }
}
