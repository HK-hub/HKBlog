package com.hkblog.test;

import com.github.houbb.markdown.toc.core.impl.AtxMarkdownToc;
import com.github.houbb.markdown.toc.vo.TocGen;
import org.junit.Test;

/**
 * @author : HK意境
 * @ClassName : MarkdownTocTest
 * @date : 2021/12/4 18:53
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class MarkdownTocTest {


    @Test
    public void tocTest(){

        String path = "E:\\百度网盘\\资料-ja va设计模式（图解+框架源码分析+实战）\\Java设计模式资料day02\\笔记\\HKBlog 博客系统.md";
        TocGen tocGen = AtxMarkdownToc.newInstance().write(false).genTocFile(path);


        for (String tocLine : tocGen.getTocLines()) {
            System.out.println(tocLine);
        }

    }



}
