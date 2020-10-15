package com.example.test.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.readAllBytes;
import static org.springframework.http.ContentDisposition.builder;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM;
import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/file")
public class FileController {

    /**
     * 上传附件（可多个）
     * @param files
     * @param businessId
     * @return
     */
    @PostMapping("/addFiles")
    public JSONObject addFiles(@RequestParam("file") MultipartFile[] files, String businessId)  {
//        if (files == null){
//            return ServerResponse.errorMessage("文件不能为空");
//        }
        String path = "E:/file/"+businessId+"/";
        File filePath = new File(path);
        if(!filePath.exists()){
            filePath.mkdirs();
        }
        int addFilesCount = 0;
        int time = 1;
        for (MultipartFile file:files){
//            SysFileEntity sysFile = new SysFileEntity();
//            sysFile.init(true);
//            sysFile.setBusinessId(businessId);
//            sysFile.setFileName(file.getOriginalFilename());
//            sysFile.setPath(path);
            String timeStr = Long.toString(System.currentTimeMillis() + time++);//时间戳+顺序
            path = path+timeStr+file.getOriginalFilename();//完整路径(目录+时间戳+顺序+原文件名)
            try {
                file.transferTo(new File(path));
            } catch (IOException e) {
//                return ServerResponse.errorMessage("文件"+file.getOriginalFilename()+"上传失败");
            }
//            int insert = iSysFileService.insert(sysFile);
//            addFilesCount += insert;
        }
//        return ServerResponse.success(addFilesCount);
        JSONObject json = new JSONObject();
        json.put("pass",true);
        return json;
    }

    /**
     * 下载附件（单个）
     * @param path
     * @param fileName
     * @return
     */
    @RequestMapping("/download")
    public ResponseEntity<byte[]> download(String path,String fileName) throws IOException {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(fileName).build());
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        byte[] bytes = new byte[0];
//        try {
//            bytes = FileCopyUtils.copyToByteArray(new File(path+fileName));
//        } catch (IOException e) {
//            e.printStackTrace();
////            return ServerResponse.errorMessage("下载附件出错");
//        }
//
//        return new ResponseEntity<>(bytes,headers, HttpStatus.OK);

        String filePath = path+fileName;
        return ok().headers(
                h ->
                        h.setContentDisposition(
                                builder("attachment")
                                        .filename(fileName, UTF_8)
                                        .build()))
                .contentType(APPLICATION_OCTET_STREAM)
                .body(readAllBytes(Paths.get(filePath)));
    }
}
