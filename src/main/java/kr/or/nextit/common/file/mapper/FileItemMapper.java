package kr.or.nextit.common.file.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nextit.common.file.model.FileItem;

@Mapper
public interface FileItemMapper {
	
	public List<FileItem> selectFileItemList(Map<String, Object> paramMap) throws Exception;
	
	// 파일 정보 가져오기
	public FileItem selectFileItem(Map<String, Object> paramMap) throws Exception;
	
	// 파일 정보 등록
	public int insertFileItem(FileItem fileItem) throws Exception;
	
	// 파일 정보 삭제
	public int deleteFileItem(Map<String, Object> paramMap) throws Exception;
	
	// 파일 다운로드 카운트 갱신
	public int updateDownloadCnt(Map<String, Object> paramMap) throws Exception;

}
