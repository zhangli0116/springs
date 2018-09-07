package com.java.pdf;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.util.IOUtils;

import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * Create by Administrator on 2018/9/4
 * apache PDFBox 拆分 合并使用
 * @author admin
 */
public class PDFBoxTest {
    private static int size = 0;

    /**
     * 将pdf拆分单页
     * @param path
     * @param outPath
     * @throws Exception
     */
    public static void segement(String path,String outPath) throws Exception{
        File file = new File(path);
        PDDocument pdDocument = PDDocument.load(file);
        Splitter splitter = new Splitter();
        List<PDDocument> pages = splitter.split(pdDocument);
        size = pages.size();
        for(int i =0,len=pages.size();i<len;i++){
            PDDocument pdDocument1 = pages.get(i);
            pdDocument1.save(outPath + "第"+(i+1)+"页.pdf");
        }
        System.out.println("Multiple PDF's created");
        pdDocument.close();

    }

    /**
     *
     * @param path
     * @param outPath
     * @param element  element==0 合并奇数页 ; element==1 合并偶数页
     * @throws Exception
     */
    public static void merge(String path,String outPath,int element) throws Exception{
        if(size > 0) {
            PDFMergerUtility pdfMergerUtility = new PDFMergerUtility();
            //设置合并文件目标目录
            String outPathName = element ==0 ? outPath + "(奇数页).pdf" : outPath + "(偶数页).pdf";
            pdfMergerUtility.setDestinationFileName(outPathName);
            for (int i = 0; i < size;i++){
                //element等于0的时候 跳过偶数
                if((i+1)%2==element) {continue;}
                File file = new File(path + "第" +(i+1) + "页.pdf");
                pdfMergerUtility.addSource(file);
            }
            pdfMergerUtility.mergeDocuments();
            System.out.println("合并完成");
        }


    }

    public static void main(String[] args) throws Exception{
        String path = "F:\\work\\项目文档\\杨雄讲刑诉之考前必背精华（上篇）.pdf";
        String outPath = "F:\\work\\项目文档\\temp\\杨雄讲刑诉之考前必背精华（上篇）";
        PDFBoxTest.segement(path,outPath);
        //合并奇数页
        PDFBoxTest.merge(outPath,outPath,0);
        //合并偶数页
        PDFBoxTest.merge(outPath,outPath,1);

    }
}
