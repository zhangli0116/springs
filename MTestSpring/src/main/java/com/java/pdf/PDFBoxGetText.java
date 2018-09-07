package com.java.pdf;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Create by Administrator on 2018/9/5
 *
 * @author admin
 */
public class PDFBoxGetText {

    /**
     * pdf装doc
     * @param path
     * @throws Exception
     */
    public static void getText(String path) throws Exception{
        FileOutputStream fileOutputStream =new FileOutputStream(path + ".doc");
        OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream);
        File file = new File(path + ".pdf");
        PDDocument pdDocument = PDDocument.load(file);

        Splitter splitter = new Splitter();
        PDFTextStripper pdfStripper = new PDFTextStripper();
        //拆分成每一页页的提取
        List<PDDocument> pages = splitter.split(pdDocument);
        for(int i =0,len=pages.size();i<len;i++){
            PDDocument document = pages.get(i);
            /*PDPage pdPage = document.getPage(0);
            PDResources pdResources = pdPage.getResources();
           Iterable<COSName> cosNames =  pdResources.getXObjectNames();
           Iterator<COSName> cosNameIter =  cosNames.iterator();
           while(cosNameIter.hasNext()){
               COSName cosName  = cosNameIter.next();
               if(pdResources.isImageXObject(cosName)){
                   PDImageXObject Ipdmage = (PDImageXObject)pdResources.getXObject(cosName);
                   BufferedImage image = Ipdmage.getImage();
                   FileOutputStream out = new FileOutputStream("F:\\work\\项目文档\\temp\\temp\\"+ UUID.randomUUID()+".doc");
                   ImageIO.write(image,"png",fileOutputStream);

                   out.close();
               }
           }*/
            pdfStripper.writeText(document,writer);
        }
        writer.close();
        fileOutputStream.close();
        pdDocument.close();
        System.out.println("转换完成");
    }

    public static void main(String[] args) throws Exception{
        String path = "F:\\work\\项目文档\\杨雄讲刑诉之考前必背精华（上篇）";
        PDFBoxGetText.getText(path);
    }
}
