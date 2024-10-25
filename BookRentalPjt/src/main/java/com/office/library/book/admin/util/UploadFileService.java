package com.office.library.book.admin.util;

import java.io.File;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//파일 업로드 기능구현 
@Service
public class UploadFileService {

	public String upload(MultipartFile file) {
		
		boolean result = false;
		
		//파일 기존 이름 저장
		String fileOriName = file.getOriginalFilename();
		//파일 확장자명을 긇어고위한것
		String fileExtension = fileOriName.substring(fileOriName.lastIndexOf("."), fileOriName.length());
		//파일 저장 위치
		String uploadDir = "C:\\library\\upload\\";
		
		//UUID로 랜덤자연수 추출 
		UUID uuid = UUID.randomUUID();
		// "-","" 을 다 지워주고 그걸 유니크네임에넣어줘
		String uniqueName =uuid.toString().replaceAll("-","");
		
		//파일 네임 저장
		File saveFile = new File(uploadDir + "\\" + uniqueName + fileExtension);
		
		if (!saveFile.exists())
			saveFile.mkdirs();
		
		try {
			file.transferTo(saveFile);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (result) {
			System.out.println("[UploadFileService] FILE UPLOAD SUCCESS!!");
			return uniqueName + fileExtension;
		} else {
			System.out.println("[UploadFileService] FILE UPLOAD FAIL!!");
			
			return null;
		}
	}

}
