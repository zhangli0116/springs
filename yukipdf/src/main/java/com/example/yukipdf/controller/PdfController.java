package com.example.yukipdf.controller;

import com.example.yukipdf.service.impl.PdfBoxServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.ObjectUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * Create by Administrator on 2018/9/12
 *
 * @author admin
 */
@Controller
public class PdfController {
    @Autowired
    private PdfBoxServiceImpl pdfBoxService;

    @RequestMapping("/")
    public String hello(){
        return "index";
    }

    @RequestMapping("/index")
    public String template(){
        return "index";
    }
    @RequestMapping("/importExcel")
    public void importExcel(HttpServletRequest request, HttpServletResponse response, MultipartFile multipartFile, @RequestParam(name = "element",required = false,defaultValue = "1") int element){
        try {
            String fileName = multipartFile.getOriginalFilename().split("\\.")[0];
            InputStream inputStream = multipartFile.getInputStream();
            ServletOutputStream servletOut = response.getOutputStream();
            fileName = element==1? fileName+"(奇数页).pdf" : fileName+"(偶数页).pdf";
            response.setHeader("Content-disposition","attachment; filename=" + URLEncoder.encode(fileName,"utf-8"));
            response.setHeader("X-Frame-Options", "SAMEORIGIN");
            response.setContentType("application/pdf");
            pdfBoxService.merge(inputStream,servletOut,element);
            servletOut.flush();
        }catch (Exception e){

        }
    }
}
