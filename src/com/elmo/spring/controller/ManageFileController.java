package com.elmo.spring.controller;

import com.elmo.spring.constants.ViewNames;
import com.elmo.spring.persistence.daos.AssignmentDao;
import com.elmo.spring.persistence.daos.FileDao;
import com.elmo.spring.persistence.daos.UserDao;
import com.elmo.spring.persistence.dos.Assignment;
import com.elmo.spring.persistence.dos.File;
import com.elmo.spring.persistence.dos.Enrollment;
import com.elmo.spring.persistence.dos.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import framework.Toolbox;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by Nivedita on 12/2/2015.
 */
@Controller
public class ManageFileController  {

    @RequestMapping(value = "/elmo/files", method = RequestMethod.GET)
    public ModelAndView init(HttpServletRequest request,
                             HttpServletResponse response) throws Exception {

        List<File> files = FileDao.loadAllForCSId(Toolbox.getCSIDforActiveEnrollment());

        return new ModelAndView("files", "files", files);

            }


    @RequestMapping(value = "/elmo/upload", method = RequestMethod.POST)
     public ModelAndView uploadFile(@RequestParam("exp") String exp,@RequestParam("pub") String pub, HttpServletRequest request) throws Exception
    {

       /* ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
        List<FileItem> fileItems = null;
        try {
            fileItems = fileUpload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        InputStream in = null;
        try {
            in = fileItems.get(0).getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        File file=new File();

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("file");
        file.setFile_content(multipartFile.getBytes());


        file.setFile_date_created(new Timestamp(new Date().getTime()));
        String str=new String();
        str = request.getParameter("exp");
        str=ServletRequestUtils.getStringParameter(request, "exp");
        //file.setFile_date_expired(Toolbox.formatThisStringToTimestamp(ServletRequestUtils.getStringParameter(request, "exp")));
        file.setFile_date_expired(Toolbox.formatThisStringToTimestamp(exp));
        file.setFile_date_published(Toolbox.formatThisStringToTimestamp(pub));

        file.setUploaded_by_id(Toolbox.getUserObjectInSession().getUser_id());
        file.setCourse_id(Toolbox.getCSIDforActiveEnrollment());
        try {
            FileDao.create(file);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ModelAndView("redirect:files.htm");
    }
    @RequestMapping(value = "/elmo/download", method = RequestMethod.GET)
      public ModelAndView download(@RequestParam Map<String, String> params,HttpServletRequest request,HttpServletResponse response) throws Exception {
        long id = ServletRequestUtils.getRequiredIntParameter(request, "id");

        File file = new File();
        file.setFile_id(id);
        File fileDB= FileDao.searchMatching(file).get(0);


        response.setContentType("pdf");
        response.setHeader("Content-Disposition","attachment; filename=file_\"" + fileDB.getFile_id() +"\"");

        FileCopyUtils.copy(fileDB.getFile_content(), response.getOutputStream());

        return null;

    }

    @RequestMapping(value = "/elmo/delete", method = RequestMethod.GET)
    public ModelAndView delete(HttpServletRequest request,HttpServletResponse response) throws Exception {
        long id = ServletRequestUtils.getRequiredIntParameter(request, "id");
        File file = new File();
        file.setFile_id(id);
        FileDao.delete(file);
        return new ModelAndView("redirect:files.htm");
    }


}
