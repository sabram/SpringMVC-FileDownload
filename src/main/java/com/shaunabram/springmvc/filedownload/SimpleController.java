package com.shaunabram.springmvc.filedownload;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;

@Controller
public class SimpleController {

    @RequestMapping(value = "/")
    public String home() {
        System.out.println("SimpleController: Passing through...");
        //without view resolver
        // return "WEB-INF/views/index.jsp";
        return "index";
    }

    @RequestMapping(value = "/Excel", method = RequestMethod.GET)
    public void handleFileDownload(HttpServletResponse response) {
        try {
            String fileName = "/Test.xls";
            URL url = getClass().getResource(fileName);
            File file = new File(url.toURI());
            System.out.println("Loading file " + fileName + " (" + file.getAbsolutePath() + ")");
            if (file.exists()) {
                response.setContentType("application/xls");
                response.setContentLength(new Long(file.length()).intValue());
                response.setHeader("Content-Disposition","attachment; filename=Test.xls");
                FileCopyUtils.copy(new FileInputStream(file), response.getOutputStream());
            } else {
                System.out.println("File " + fileName + " (" + file.getAbsolutePath() + ") does not exist");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
