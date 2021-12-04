package com.hkblog.test;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.junit.Test;

import java.io.*;

/**
 * @author : HK意境
 * @ClassName : Md2HtmlTest
 * @date : 2021/12/4 19:17
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class Md2HtmlTest {

    @Test
    public void md2HtmlTest() throws IOException {

        MutableDataSet options = new MutableDataSet();

        String md = "# HKBlog 博客系统\n" +
                "\n" +
                "\n" +
                "# 文章post:\n" +
                "\n" +
                "- 文章ID\n" +
                "- 修改时间\n" +
                "\n" +
                "\n" +
                "## 评论comment：\n" +
                "\n" +
                "- 评论ID\n" +
                "- 修改时间\n" +
                "\n" +
                "\n" +
                "### 回复表reply：\n" +
                "\n" +
                "- 回复id：\n" +
                "- 回复修改时间\n" +
                "\n" +
                "\n" +
                "#### 用户个人补充信息user_info：\n" +
                "\n" +
                " - 用户id\n" +
                " - gitee\n" +
                "\n" ;

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();


        Node document = parser.parse(md);
        String html = renderer.render(document);


        File file = new File("C:/Users/HK意境/Desktop/test.html");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(html.getBytes());


    }

}
