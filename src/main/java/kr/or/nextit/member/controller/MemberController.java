package kr.or.nextit.member.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import kr.or.nextit.member.model.Member;
import kr.or.nextit.member.service.MemberService;


@Controller
@RequestMapping("/member")
//@SessionAttributes(value="member")
public class MemberController {
	
	@Resource(name="memberService")
	private MemberService memberService;
	
	@ModelAttribute("searchTypeMap")
	public Map<String, String> getSearchType() {
		HashMap<String, String> searchTypeMap = new LinkedHashMap<>();
		searchTypeMap.put("id", "아이디");
		searchTypeMap.put("name", "이름");
		return searchTypeMap;
	}
	
	
	@RequestMapping(value="/memberList")
	public String memberList(
			HttpServletRequest request,
			@RequestParam(value="searchType", required=false, defaultValue="") String searchType,
			String searchWord,
			@RequestParam(value="currentPage", defaultValue="1") int currentPage,
			@RequestParam(value="pageSize", defaultValue="10") int pageSize,
			Model model
			) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		System.out.println("currentPage : " + currentPage);
		System.out.println("pageSize : " + pageSize);
		
	/*	String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");*/
		
		Map<String , Object> paramMap = new HashMap<>();
		
		if(StringUtils.isNotBlank(searchWord)) {
			paramMap.put("searchType", searchType);
			paramMap.put("searchWord", searchWord);
		}
		
		// 회원목록 조회
		List<Member> memberList = memberService.getMemberList(paramMap);
		
		model.addAttribute("memberList", memberList);
		
//		request.setAttribute("memberList", memberList);
		
		
		return "member/memberList";
	}
	
	// 회원 단건 조회
	@RequestMapping("/memberView")
	public String memberView(
			@RequestParam(value="mem_id", required=true)String mem_id,
			Model model
			) throws Exception{
		
		Member member = memberService.getMember(mem_id);
		
		model.addAttribute("member",member);
		
		return "member/memberView";
	}
	
	// 회원가입 양식
	@RequestMapping(value = "/memberForm")
	public String memberForm(
			@RequestParam(value="mem_id", required=false) String mem_id,
			HttpSession session,
			Model model
			) throws Exception {

		Member member = new Member();
		
		if(StringUtils.isNotBlank(mem_id)) {
			member = memberService.getMember(mem_id);
		}
		
		if (session.getAttribute("member") != null) {
			member = (Member)session.getAttribute("member");
			System.out.println("memberForm" + member.getMem_name());
		}
		
		model.addAttribute("member", member);
		
		return "member/memberForm";
	}
	
	// 회원 추가
	@RequestMapping(value="/memberInsert", method=RequestMethod.POST)
	public String memberInsert(
			@ModelAttribute("member") Member member,
			Model model,
			SessionStatus sessionStatus
			) throws Exception {
		
		String viewPage = "common/message";
		
		boolean isError = false;
		String message = "정상적으로 회원가입 되었습니다.";
		
		// 유효성 검증.
		if(member.getMem_id() == null || member.getMem_id().isEmpty() ) {
			isError = true;
			message = "아이디를 입력하세요.";
		}
		if(StringUtils.isBlank(member.getMem_name())) {
			isError = true;
			message = "이름을 입력하세요.";
		}

		try{
			if(!isError) {		
				int updCnt = memberService.insertMember(member);
				
				if(updCnt == 0){
					// 에러
					message = "회원등록에 실패하였습니다.";
					isError = true;
				}
			}
		}catch(Exception e){
			// 에러
//			e.printStackTrace();
			message = "회원등록에 실패하였습니다.";
			isError = true;
			throw e;
		}
		
		//response.sendRedirect(request.getContextPath() + "/member/memberList.do");
		model.addAttribute("isError", isError);
		model.addAttribute("message", message);
		model.addAttribute("locationURL", "/member/memberList");
		
		
		return viewPage;
	}
	
	@RequestMapping(value="/memberUpdate", method=RequestMethod.POST)
	public String memberUpdate(
			Member member,
			Model model
			) throws Exception {
		
		String viewPage = "common/message";
		
		boolean isError = false;
		String message = "정상 수정되었습니다.";
		
		if(StringUtils.isBlank(member.getMem_id())) {
			isError = true;
			message = "아이디를 입력해주세요.";
		}
		if(StringUtils.isBlank(member.getMem_name())) {
			isError = true;
			message = "이름을 입력해주세요.";
		}
		
		if(!isError) {
						
			try {
				
				int updCnt = memberService.updateMember(member);
				
				if(updCnt == 0) {
					message = "회원정보 수정 실패하였습니다.";
					isError = true;
				}
			}catch(SQLException e) {
				message = "회원정보 수정 실패하였습니다.";
				isError = true;
			}
		}	
		
		model.addAttribute("isError", isError);
		model.addAttribute("message", message);
		model.addAttribute("locationURL", "/member/memberView.do?mem_id=" + member.getMem_id());
		
		return viewPage;
		
		
	}
	
	@RequestMapping(value="/memberDelete")
	public String memberDelete(
			@RequestParam(value="mem_id", required=true)String mem_id,
			Model model
			) throws Exception {
		
		boolean isError = false;
		String viewPage = "common/message";
		String message = "정상 삭제되었습니다.";
		
		try {
			int updCnt = memberService.deleteMember(mem_id);
			if(updCnt == 0) {
				message = "회원삭제 실패하였습니다.";
				isError = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
			message = "회원삭제 실패하였습니다.";
			isError = true;
			throw e;
		}
		
		model.addAttribute("isError", isError);
		model.addAttribute("message", message);
		model.addAttribute("locationURL", "/member/memberList.do");
		
		return viewPage;
		
		
		
	}
	
	

}






