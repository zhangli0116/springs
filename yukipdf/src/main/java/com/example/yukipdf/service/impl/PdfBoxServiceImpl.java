package com.example.yukipdf.service.impl;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by Administrator on 2018/9/12
 *
 * @author admin
 */
@Service("pdfboxservice")
public class PdfBoxServiceImpl {
    private static int size = 0;

    /**
     * 将文件拆分成单页，并返回内存流的集合，每一个元素对应每一页文档流
     * @param inputStream
     * @param element
     * @return
     * @throws Exception
     */
    public List<InputStream> segement(InputStream inputStream,int element) throws Exception{
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

    /**
     *
     * @param inputStream
     * @param outputStream
     * @param element
     * @throws Exception
     */
    public void merge(InputStream inputStream, OutputStream outputStream,int element) throws Exception{
        //文件拆分
        List<InputStream> inputStreamList = segement(inputStream,element);
        PDFMergerUtility pdfMergerUtility = new PDFMergerUtility();
        //目标流
        pdfMergerUtility.setDestinationStream(outputStream);
        pdfMergerUtility.addSources(inputStreamList);
        //可以设置内存大小
        pdfMergerUtility.mergeDocuments();
        System.out.println("合并完成");
        for(int i=0,len=inputStreamList.size();i<len;i++){
            //关闭流之后才能删除文件
            inputStreamList.get(i).close();
        }
        //删除拆分的临时文件
        deleteTemplateFile();
    }

    /**
     * 删除临时文件
     * @throws Exception
     */
    public void deleteTemplateFile() throws Exception{
        //删除文件
        for(int i=0;i<size;i++){
            File file = new File("第"+(i+1)+"页.pdf");
            if(file.isFile() && file.exists()) {
                boolean flag = file.delete();
            }
        }
    }


}
