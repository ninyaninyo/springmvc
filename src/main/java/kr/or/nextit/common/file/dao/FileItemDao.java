package kr.or.nextit.common.file.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.or.nextit.common.file.model.FileItem;

public class FileItemDao {
	
	private static FileItemDao instance = new FileItemDao();
	
	private FileItemDao() {}
	
	public static FileItemDao getInstance() {
		if(instance == null) {
			instance = new FileItemDao();
		}
		return instance;
	}
	
	// 파일 목록 가져오기
	public List<FileItem> selectFileItemList(Connection conn, Map<String, Object> paramMap) throws Exception{
		
		StringBuffer query = new StringBuffer();
		
		query.append(" SELECT                        ");
		query.append("    file_seq_no,               ");
		query.append("    ref_seq_no,                ");
		query.append("    biz_type,                  ");
		query.append("    file_path,                 ");
		query.append("    file_name,                 ");
		query.append("    file_save_name,            ");
		query.append("    file_content_type,         ");
		query.append("    file_size,                 ");
		query.append("    file_fancy_size,           ");
		query.append("    file_down_cnt,             ");
		query.append("    reg_user,                  ");
		query.append("    reg_date,                  ");
		query.append("    upd_user,                  ");
		query.append("    upd_date                   ");
		query.append(" FROM                          ");
		query.append("    tb_file_item               ");
		query.append(" WHERE ref_seq_no = ?          ");
	    query.append(" AND biz_type = ?              ");
	    
	    PreparedStatement pstmt = conn.prepareStatement(query.toString());
	    
	    pstmt.setObject(1, paramMap.get("ref_seq_no"));
	    pstmt.setString(2, (String)paramMap.get("biz_type"));
	    
	    ResultSet rs = pstmt.executeQuery();
	    
	    List<FileItem> fileItemList = new ArrayList<>();
	    
	    while(rs.next()) {
	    	FileItem fileItem = new FileItem();
	    	
	    	fileItem.setFile_seq_no(rs.getInt("file_seq_no"));
	    	fileItem.setRef_seq_no(rs.getString("ref_seq_no"));
	    	fileItem.setBiz_type(rs.getString("biz_type"));
	    	fileItem.setFile_path(rs.getString("file_path"));
	    	fileItem.setFile_name(rs.getString("file_name"));
	    	fileItem.setFile_save_name(rs.getString("file_save_name"));
	    	fileItem.setFile_content_type(rs.getString("file_content_type"));
	    	fileItem.setFile_size(rs.getInt("file_size"));
	    	fileItem.setFile_fancy_size(rs.getString("file_fancy_size"));
	    	fileItem.setFile_down_cnt(rs.getInt("file_down_cnt"));
	    	
	    	fileItemList.add(fileItem);
	    }
	    
		return fileItemList;
	}
	
	// 파일 정보 가져오기
	public FileItem selectFileItem(Connection conn, Map<String, Object> paramMap) throws Exception{
		
		StringBuffer query = new StringBuffer();
		
		query.append(" SELECT                        ");
		query.append("    file_seq_no,               ");
		query.append("    ref_seq_no,                ");
		query.append("    biz_type,                  ");
		query.append("    file_path,                 ");
		query.append("    file_name,                 ");
		query.append("    file_save_name,            ");
		query.append("    file_content_type,         ");
		query.append("    file_size,                 ");
		query.append("    file_fancy_size,           ");
		query.append("    file_down_cnt,             ");
		query.append("    reg_user,                  ");
		query.append("    reg_date,                  ");
		query.append("    upd_user,                  ");
		query.append("    upd_date                   ");
		query.append(" FROM                          ");
		query.append("    tb_file_item               ");
		query.append(" WHERE file_seq_no = ?          ");
	    
	    PreparedStatement pstmt = conn.prepareStatement(query.toString());
	    
	    pstmt.setInt(1, (Integer)paramMap.get("file_seq_no"));
	    
	    ResultSet rs = pstmt.executeQuery();
	    
	    FileItem fileItem = null;
	    
	    if(rs.next()) {
	    	fileItem = new FileItem();
	    	
	    	fileItem.setFile_seq_no(rs.getInt("file_seq_no"));
	    	fileItem.setRef_seq_no(rs.getString("ref_seq_no"));
	    	fileItem.setBiz_type(rs.getString("biz_type"));
	    	fileItem.setFile_path(rs.getString("file_path"));
	    	fileItem.setFile_name(rs.getString("file_name"));
	    	fileItem.setFile_save_name(rs.getString("file_save_name"));
	    	fileItem.setFile_content_type(rs.getString("file_content_type"));
	    	fileItem.setFile_size(rs.getInt("file_size"));
	    	fileItem.setFile_fancy_size(rs.getString("file_fancy_size"));
	    	fileItem.setFile_down_cnt(rs.getInt("file_down_cnt"));
	    }
		
		return fileItem;
	}
	
	// 파일 정보 등록
	public int insertFileItem(Connection conn, FileItem fileItem) throws Exception{
		
		StringBuffer query = new StringBuffer();
		
		query.append(" INSERT INTO tb_file_item (  ");
		query.append("     file_seq_no,            ");
		query.append("     ref_seq_no,             ");
		query.append("     biz_type,               ");
		query.append("     file_path,              ");
		query.append("     file_name,              ");
		query.append("     file_save_name,         ");
		query.append("     file_content_type,      ");
		query.append("     file_size,              ");
		query.append("     file_fancy_size,        ");
		query.append("     file_down_cnt,          ");
		query.append("     reg_user,               ");
		query.append("     reg_date,               ");
		query.append("     upd_user,               ");
		query.append("     upd_date                ");
		query.append(" ) VALUES (                  ");
		query.append("     seq_file_seq_no.nextval,");
		query.append("     ?,                      ");
		query.append("     ?,                      ");
		query.append("     ?,                      ");
		query.append("     ?,                      ");
		query.append("     ?,                      ");
		query.append("     ?,                      ");
		query.append("     ?,                      ");
		query.append("     ?,                      ");
		query.append("     0,                      ");
		query.append("     ?,                      ");
		query.append("     sysdate,                ");
		query.append("     ?,                      ");
		query.append("     sysdate                 ");
		query.append(" )                           ");
		
		PreparedStatement pstmt = conn.prepareStatement(query.toString());
		
		int i = 1;
		pstmt.setString(i++, fileItem.getRef_seq_no());
		pstmt.setString(i++, fileItem.getBiz_type());
		pstmt.setString(i++, fileItem.getFile_path());
		pstmt.setString(i++, fileItem.getFile_name());
		pstmt.setString(i++, fileItem.getFile_save_name());
		pstmt.setString(i++, fileItem.getFile_content_type());
		pstmt.setLong(i++, fileItem.getFile_size());
		pstmt.setString(i++, fileItem.getFile_fancy_size());
		pstmt.setString(i++, fileItem.getReg_user());
		pstmt.setString(i++, fileItem.getUpd_user());
		
		int updCnt = pstmt.executeUpdate();
		
		return updCnt;
	}	
	
	
	// 파일 정보 삭제
	public int deleteFileItem(Connection conn, Map<String, Object> paramMap) throws Exception{
		
		StringBuffer query = new StringBuffer();
		
		query.append(" DELETE FROM tb_file_item WHERE file_seq_no IN ( ");
		
		String[] delFileSeqNo = (String[])paramMap.get("delFileSeqNo");
		
		for(int i = 0; i < delFileSeqNo.length; i++) {			
			// 11, 12, 13
			if(i == 0) {
				query.append(delFileSeqNo[i]);
			}else {			
				query.append(", " + delFileSeqNo[i]);
			}
		}
		
		query.append(" ) ");
		
		PreparedStatement pstmt = conn.prepareStatement(query.toString());
		
		int updCnt = pstmt.executeUpdate();		
		
		return updCnt;
	}
	
	// 파일 다운로드 카운트 갱신
	public int updateDownloadCnt(Connection conn, Map<String, Object> paramMap) throws Exception{
		
		StringBuffer query = new StringBuffer();
		
		query.append(" UPDATE tb_file_item SET file_down_cnt = file_down_cnt + 1  WHERE file_seq_no = ? ");
		
		PreparedStatement pstmt = conn.prepareStatement(query.toString());
		
		pstmt.setInt(1, (Integer)paramMap.get("file_seq_no"));
		
		int updCnt = pstmt.executeUpdate();		
		
		return updCnt;
	}
	

}
