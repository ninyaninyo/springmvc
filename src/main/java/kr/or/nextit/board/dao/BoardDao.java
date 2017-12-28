package kr.or.nextit.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.or.nextit.board.model.Board;

public class BoardDao {
	
	private static BoardDao instance = new BoardDao();
	
	private BoardDao() {}
	
	public static BoardDao getInstance() {
		if(instance == null) {
			instance = new BoardDao();
		}
		return instance;
	}
	
	// 총 게시글 수
	public int selectBoardCount(Connection conn, Map<String, Object> paramMap) throws Exception {
		
		StringBuffer query = new StringBuffer();
		
		query.append(" SELECT                     " ); 
		query.append(" 	  count(*) as count             " );
		query.append(" FROM tb_board a, tb_member b    " ); 
		
		query.append(" WHERE a.bo_writer = b.mem_id    " ); 
		query.append(" AND a.bo_del_yn = 'N'      " ); 
		
		if(paramMap != null && !paramMap.isEmpty()) {
			if(paramMap.containsKey("searchWord")) {
				if("01".equals(paramMap.get("searchType"))) {  // 제목
					query.append(" AND a.bo_title LIKE '%' || ? || '%' ");
					
				}else if("02".equals(paramMap.get("searchType"))) {  // 내용
					query.append(" AND a.bo_content LIKE '%' || ? || '%' ");
					
				}else if("03".equals(paramMap.get("searchType"))) {  // 제목 + 내용
					query.append(" AND (a.bo_title LIKE '%' || ? || '%' OR a.bo_content LIKE '%' || ? || '%') ");
					
				}else if("04".equals(paramMap.get("searchType"))) {  // 작성자
					query.append(" AND b.mem_name = ? ");					
				}
			}
		}
		
		query.append(" ORDER BY a.bo_seq_no desc    " ); 
		
		PreparedStatement pstmt = conn.prepareStatement(query.toString());
		
		// 데이터 바인딩.
		int i = 1;
		if(paramMap != null && !paramMap.isEmpty()) {
			if(paramMap.containsKey("searchWord")) {
				if("01".equals(paramMap.get("searchType"))) {  // 제목
					pstmt.setString(i++, (String)paramMap.get("searchWord"));
					
				}else if("02".equals(paramMap.get("searchType"))) {  // 내용
					pstmt.setString(i++, (String)paramMap.get("searchWord"));
					
				}else if("03".equals(paramMap.get("searchType"))) {  // 제목 + 내용
					pstmt.setString(i++, (String)paramMap.get("searchWord"));
					pstmt.setString(i++, (String)paramMap.get("searchWord"));
					
				}else if("04".equals(paramMap.get("searchType"))) {  // 작성자
					pstmt.setString(i++, (String)paramMap.get("searchWord"));					
				}
			}
		}
		
		ResultSet rs = pstmt.executeQuery();
		
		int totalCnt = 0;
		
		if(rs.next()) {
			totalCnt = rs.getInt("count");
		}
		
		return totalCnt;
	}
	
	// 게시글 목록조회
	public List<Board> selectBoardList(Connection conn, Map<String, Object> paramMap) throws Exception{
		
		StringBuffer query = new StringBuffer();
		
		query.append(" SELECT                      ");
		query.append("     *                       ");
		query.append(" FROM                        ");
		query.append(" (                           ");
		query.append("     SELECT ");
		query.append("         ROWNUM AS RNUM      ");
		query.append("       , TB.*                ");
		query.append("     FROM                    ");
		query.append("     (                       ");		
		
		query.append(" SELECT                     " ); 
		query.append(" 	  a.bo_seq_no             " ); 
		query.append(" 	, a.bo_type               " ); 
		query.append(" 	, a.bo_title              " ); 
		query.append(" 	, a.bo_content            " ); 
		query.append(" 	, a.bo_writer             " ); 
		query.append(" 	, b.mem_name as bo_writer_name    " ); 
		query.append(" 	, a.bo_hit_cnt            " ); 
		query.append(" 	, a.bo_open_yn            " ); 
		query.append(" 	, a.bo_del_yn             " ); 
		query.append(" 	, a.reg_user              " ); 
		query.append(" 	, TO_CHAR(a.reg_date, 'YYYY-MM-DD HH24:MI:SS') as reg_date  " ); 
		query.append(" 	, a.upd_user              " ); 
		query.append(" 	, TO_CHAR(a.upd_date, 'YYYY-MM-DD HH24:MI:SS') as upd_date  " ); 
		query.append(" FROM tb_board a, tb_member b    " ); 
		
		query.append(" WHERE a.bo_writer = b.mem_id    " ); 
		query.append(" AND a.bo_del_yn = 'N'      " ); 
		
		if(paramMap != null && !paramMap.isEmpty()) {
			if(paramMap.containsKey("searchWord")) {
				if("01".equals(paramMap.get("searchType"))) {  // 제목
					query.append(" AND a.bo_title LIKE '%' || ? || '%' ");
					
				}else if("02".equals(paramMap.get("searchType"))) {  // 내용
					query.append(" AND a.bo_content LIKE '%' || ? || '%' ");
					
				}else if("03".equals(paramMap.get("searchType"))) {  // 제목 + 내용
					query.append(" AND (a.bo_title LIKE '%' || ? || '%' OR a.bo_content LIKE '%' || ? || '%') ");
					
				}else if("04".equals(paramMap.get("searchType"))) {  // 작성자
					query.append(" AND b.mem_name = ? ");					
				}
			}
		}
		
		query.append(" ORDER BY a.bo_seq_no desc    " ); 
		
		query.append("     ) TB  " );
		query.append(" ) WHERE RNUM BETWEEN ? AND ?  " );		
		
		PreparedStatement pstmt = conn.prepareStatement(query.toString());
		
		// 데이터 바인딩.
		int i = 1;
		if(paramMap != null && !paramMap.isEmpty()) {
			if(paramMap.containsKey("searchWord")) {
				if("01".equals(paramMap.get("searchType"))) {  // 제목
					pstmt.setString(i++, (String)paramMap.get("searchWord"));
					
				}else if("02".equals(paramMap.get("searchType"))) {  // 내용
					pstmt.setString(i++, (String)paramMap.get("searchWord"));
					
				}else if("03".equals(paramMap.get("searchType"))) {  // 제목 + 내용
					pstmt.setString(i++, (String)paramMap.get("searchWord"));
					pstmt.setString(i++, (String)paramMap.get("searchWord"));
					
				}else if("04".equals(paramMap.get("searchType"))) {  // 작성자
					pstmt.setString(i++, (String)paramMap.get("searchWord"));					
				}
			}
		}
		
		pstmt.setInt(i++, (Integer)paramMap.get("startRow"));
		pstmt.setInt(i++, (Integer)paramMap.get("endRow"));
		
		ResultSet rs = pstmt.executeQuery();
		
		List<Board> boardList = new ArrayList<Board>();
		
		while(rs.next()) {
			Board board = new Board();
			
			board.setBo_seq_no(rs.getInt("bo_seq_no"));
			board.setBo_type(rs.getString("bo_type"));
			board.setBo_title(rs.getString("bo_title"));
			board.setBo_content(rs.getString("bo_content"));
			board.setBo_writer(rs.getString("bo_writer"));
			
			board.setBo_writer_name(rs.getString("bo_writer_name"));
			
			board.setBo_hit_cnt(rs.getInt("bo_hit_cnt"));
			board.setBo_open_yn(rs.getString("bo_open_yn"));
			board.setBo_del_yn(rs.getString("bo_del_yn"));
			board.setReg_user(rs.getString("reg_user"));
			board.setReg_date(rs.getString("reg_date"));
			board.setUpd_user(rs.getString("upd_user"));
			board.setUpd_date(rs.getString("upd_date"));
			
			boardList.add(board);
		}
		
		return boardList;
	}
	
	// 게시글 조회
	public Board selectBoard(Connection conn, int bo_seq_no) throws Exception{
		
		StringBuffer query = new StringBuffer();
		
		query.append(" SELECT                     " ); 
		query.append(" 	  a.bo_seq_no             " ); 
		query.append(" 	, a.bo_type               " ); 
		query.append(" 	, a.bo_title              " ); 
		query.append(" 	, a.bo_content            " ); 
		query.append(" 	, a.bo_writer             " ); 
		
		query.append(" 	, (SELECT mem_name FROM tb_member b WHERE b.mem_id = a.bo_writer) as bo_writer_name        " );
		
		query.append(" 	, a.bo_hit_cnt            " ); 
		query.append(" 	, a.bo_open_yn            " ); 
		query.append(" 	, a.bo_del_yn             " ); 
		query.append(" 	, a.reg_user              " ); 
		query.append(" 	, TO_CHAR(a.reg_date, 'YYYY-MM-DD HH24:MI:SS') as reg_date  " ); 
		query.append(" 	, a.upd_user              " ); 
		query.append(" 	, TO_CHAR(a.upd_date, 'YYYY-MM-DD HH24:MI:SS') as upd_date  " ); 
		query.append(" FROM tb_board a            " ); 
		query.append(" WHERE a.bo_del_yn = 'N'      " ); 
		query.append(" AND a.bo_seq_no = ?    " ); 
		
		PreparedStatement pstmt = conn.prepareStatement(query.toString());
		
		pstmt.setInt(1, bo_seq_no);
		
		ResultSet rs = pstmt.executeQuery();
		
		Board board = null;
		
		if(rs.next()) {
			board = new Board();
			
			board.setBo_seq_no(rs.getInt("bo_seq_no"));
			board.setBo_type(rs.getString("bo_type"));
			board.setBo_title(rs.getString("bo_title"));
			board.setBo_content(rs.getString("bo_content"));
			board.setBo_writer(rs.getString("bo_writer"));
			
			board.setBo_writer_name(rs.getString("bo_writer_name"));
			
			board.setBo_hit_cnt(rs.getInt("bo_hit_cnt"));
			board.setBo_open_yn(rs.getString("bo_open_yn"));
			board.setBo_del_yn(rs.getString("bo_del_yn"));
			board.setReg_user(rs.getString("reg_user"));
			board.setReg_date(rs.getString("reg_date"));
			board.setUpd_user(rs.getString("upd_user"));
			board.setUpd_date(rs.getString("upd_date"));
		}
		
		return board;
	}	
	
	// 게시글 등록
	public int insertBoard(Connection conn, Board board) throws Exception {
		
		StringBuffer query = new StringBuffer();
		
		query.append(" SELECT SEQ_BO_SEQ_NO.NEXTVAL FROM DUAL ");
		
		PreparedStatement pstmt = conn.prepareStatement(query.toString());
		
		ResultSet rs = pstmt.executeQuery();
		
		if(rs.next()) {
			board.setBo_seq_no(rs.getInt(1));
		}
		
		query = new StringBuffer();
		query.append(" INSERT INTO tb_board(         ");
		query.append(" 	  bo_seq_no                  ");
		query.append(" 	, bo_type                    ");
		query.append(" 	, bo_title                   ");
		query.append(" 	, bo_content                 ");
		query.append(" 	, bo_writer                  ");
		query.append(" 	, bo_hit_cnt                 ");
		query.append(" 	, bo_open_yn                 ");
		query.append(" 	, bo_del_yn                  ");
		query.append(" 	, reg_user                   ");
		query.append(" 	, reg_date                   ");
		query.append(" 	, upd_user                   ");
		query.append(" 	, upd_date                   ");
		query.append(" ) VALUES (                    ");
		query.append("    ?                          ");
		query.append(" 	, ?                          ");
		query.append(" 	, ?                          ");
		query.append(" 	, ?                          ");
		query.append(" 	, ?                          ");
		query.append(" 	, 0                          ");
		query.append(" 	, NVL(?, 'N')                ");
		query.append(" 	, 'N'                        ");
		query.append(" 	, ?                          ");
		query.append(" 	, SYSDATE                    ");
		query.append(" 	, ?                          ");
		query.append(" 	, SYSDATE                    ");
		query.append(" )                             ");
		
		pstmt = conn.prepareStatement(query.toString());
		
		int i = 1;
		pstmt.setInt(i++, board.getBo_seq_no());
		pstmt.setString(i++, board.getBo_type());
		pstmt.setString(i++, board.getBo_title());
		pstmt.setString(i++, board.getBo_content());
		pstmt.setString(i++, board.getBo_writer());
		pstmt.setString(i++, board.getBo_open_yn());
		pstmt.setString(i++, board.getBo_writer());
		pstmt.setString(i++, board.getBo_writer());
		
		int updCnt = pstmt.executeUpdate();
				
		return updCnt;
	}
		
	// 게시글 수정
	public int updateBoard(Connection conn, Board board) throws Exception {
		
		StringBuffer query = new StringBuffer();
		
		query.append(" UPDATE tb_board SET      ");
		query.append(" 	  bo_type = ?           ");
		query.append(" 	, bo_title = ?          ");
		query.append(" 	, bo_content = ?        ");
		query.append(" 	, bo_open_yn = ?        ");
		query.append(" 	, upd_user = ?          ");
		query.append(" 	, upd_date = SYSDATE    ");
		query.append(" WHERE bo_seq_no = ?      ");
				
		PreparedStatement pstmt = conn.prepareStatement(query.toString());
		
		int i = 1;
		pstmt.setString(i++, board.getBo_type());
		pstmt.setString(i++, board.getBo_title());
		pstmt.setString(i++, board.getBo_content());		
		pstmt.setString(i++, board.getBo_open_yn());
		pstmt.setString(i++, board.getUpd_user());
		pstmt.setInt(i++, board.getBo_seq_no());
		
		int updCnt = pstmt.executeUpdate();
				
		return updCnt;
	}
		
	// 게시글 삭제
	public int deleteBoard(Connection conn, Map<String, Object> paramMap) throws Exception {
		
		StringBuffer query = new StringBuffer();
		
		query.append(" UPDATE tb_board SET      ");
		query.append(" 	  bo_del_yn = 'Y'        ");
		query.append(" 	, upd_user = ?          ");
		query.append(" 	, upd_date = SYSDATE    ");
		query.append(" WHERE bo_seq_no = ?      ");
				
		PreparedStatement pstmt = conn.prepareStatement(query.toString());
		
		int i = 1;
		pstmt.setString(i++, (String)paramMap.get("upd_user"));
		pstmt.setInt(i++, (Integer)paramMap.get("bo_seq_no"));
		
		int updCnt = pstmt.executeUpdate();
				
		return updCnt;
	}
	
	// 조회수 업데이트
	public int updateHitCnt(Connection conn, int bo_seq_no) throws Exception{
		
		StringBuffer query = new StringBuffer();
		
		query.append(" UPDATE tb_board SET bo_hit_cnt = bo_hit_cnt + 1 WHERE bo_seq_no = ? ");
		
		PreparedStatement pstmt = conn.prepareStatement(query.toString());
		
		pstmt.setInt(1, bo_seq_no);
		
		int updCnt = pstmt.executeUpdate();
		
		return updCnt;
	}
}




