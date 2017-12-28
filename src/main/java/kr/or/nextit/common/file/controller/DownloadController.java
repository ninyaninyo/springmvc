package kr.or.nextit.common.file.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.nextit.common.file.model.FileItem;
import kr.or.nextit.common.file.service.FileItemService;
import kr.or.nextit.common.file.service.impl.FileItemServiceImpl;
import kr.or.nextit.common.util.ConstantUtil;

@Controller
public class DownloadController{
	
	@Autowired
	FileItemService fileItemService;

	@RequestMapping("/common/download")
	public void process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			response.reset();
			
			String strFileSeqNo = request.getParameter("file_seq_no");
			int fileSeqNo = 0;
			
			if(strFileSeqNo != null) {
				fileSeqNo = Integer.parseInt(strFileSeqNo);
			}
			
			Map<String, Object> paramMap = new HashMap<>();
			
			paramMap.put("file_seq_no", fileSeqNo);
			
			FileItem fileItem = fileItemService.getFileItem(paramMap);
			
			if(fileItem == null) {
				throw new RuntimeException("해당 파일이 존재하지 않습니다.");
			}
			
			// 응답 헤더 정보 변경.
			response.setHeader("Content-Type", "application/octet-stream;");		
			
			// Content-Disposition
			String fileName = URLEncoder.encode(fileItem.getFile_name(), "utf-8");
			
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
			
			// Content-Transfer-Encoding
			response.setHeader("Content-Transfer-Encoding", "binary");
			
			// 컨텐트 사이즈
			response.setContentLength((int)fileItem.getFile_size());
			
			// 캐쉬관련
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Expires", "-1");
			
			// 파일 전송.
			File saveFile = new File(ConstantUtil.UPLOAD_PATH + "/" 
					+ fileItem.getFile_path() + "/" + fileItem.getFile_save_name());
			
			if(!saveFile.exists()) {
				throw new RuntimeException("해당 파일이 존재하지 않습니다.");
			}
			
			// 응답 데이터로 파일 전송.
			FileUtils.copyFile(saveFile, response.getOutputStream());
			response.getOutputStream().close();			
		
		}catch(Exception e) {
			e.printStackTrace();
			response.reset();
			response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 에러
		}
		
	}
}









