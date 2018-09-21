package com.java.pdf;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.util.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Create by Administrator on 2018/9/12
 *
 * @author admin
 */
public class PDFBoxTest2 {
    private static int size = 0;

    /**
     *
     * @param inputStream
     * @param element  ==1 返回奇数页
     * @return
     * @throws Exception
     */
    public static List<InputStream> segement(InputStream inputStream,int element) throws Exception{
        PDDocument pdDocument = PDDocument.load(inputStream);
        Splitter splitter = new Splitter();
        List<PDDocument> pages = splitter.split(pdDocument);
        size = pages.size();
        List<InputStream> inputStreamList = new ArrayList<>();
        for(int i =0,len=pages.size();i<len;i++){
            if((i+1)%2==element) {
                PDDocument pdDocument1 = pages.get(i);
                String fileName = "第" +(i+1) + "页.pdf";
                pdDocument1.save(fileName);
                pdDocument1.close();
                FileInputStream fileInputStream = new FileInputStream(fileName);
                inputStreamList.add(fileInputStream);
            }
        }
        System.out.println("Multiple PDF's created");
        pdDocument.close();
        return  inputStreamList;

    }

    public static OutputStream merge( List<InputStream> inputStreamList) throws Exception{
        if(size > 0){
            PDFMergerUtility pdfMergerUtility = new PDFMergerUtility();
            pdfMergerUtility.setDestinationFileName("合并后.pdf");
            pdfMergerUtility.addSources(inputStreamList);
            pdfMergerUtility.mergeDocuments();
            System.out.println("合并完成");
        }
        //关闭流
        for(int i=0,len=inputStreamList.size();i<len;i++){
            inputStreamList.get(i).close();
        }

        return null;
    }

    public static void deleteTemplateFile() throws Exception{
        //删除文件
        for(int i=0;i<size;i++){
            File file = new File("第"+(i+1)+"页.pdf");
            if(file.isFile() && file.exists()) {
                boolean flag = file.delete();
            }
        }
    }
    public static void main(String[] args) throws Exception{
        String path="F:\\work\\项目文档\\李晗商经.pdf";
        FileInputStream inputStream = new FileInputStream(path);
        List<InputStream> inputStreamList = PDFBoxTest2.segement(inputStream,1);
        PDFBoxTest2.merge(inputStreamList);
        PDFBoxTest2.deleteTemplateFile();

    }
}
