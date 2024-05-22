package com.cu.gdu.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cu.gdu.dto.AffReservationDto;
import com.cu.gdu.dto.AffiliatedOrganDto;
import com.cu.gdu.dto.AttachDto;
import com.cu.gdu.dto.MajorDto;
import com.cu.gdu.dto.PageInfoDto;
import com.cu.gdu.service.AdminService;
import com.cu.gdu.service.AffiliatedOrganService;
import com.cu.gdu.util.FileUtil;
import com.cu.gdu.util.PagingUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/aff")
@RequiredArgsConstructor
@Controller
public class AffiliatedOrganController {
	
	private final AffiliatedOrganService affiliatedOrganService;
	private final PagingUtil pagingUtil;
	private final FileUtil fileUtil;
	private final AdminService adminService;
	
	// * ------------------- 부속기관 목록관련 -------------------
	// 부속기관 리스트 페이지
	@GetMapping("/affiliatedOrgan.page")
	public String affiliatedOrgan() {
		return "/affiliatedOrgan/affiliatedOrganList";
	}
	
	// 부속기관리스트 조회 
	@GetMapping("/affiliatedOrganList.do")
	public ModelAndView aflist(@RequestParam(value="page", defaultValue="1") int currentPage
							 , @RequestParam(value="tab", defaultValue="affList") String tab
							 , String myPage
							 , ModelAndView mv) {
		
		PageInfoDto affPi = pagingUtil.getPageInfoDto(affiliatedOrganService.selectAffiliatedOrganListCount(), currentPage, 5, 8);
		List<AffiliatedOrganDto> aflist = affiliatedOrganService.selectAffiliatedOrganList(affPi);
		
		PageInfoDto resPi = pagingUtil.getPageInfoDto(affiliatedOrganService.selectAffiliatedOrganResListCount(), currentPage, 5, 8);
		List<AffReservationDto> reslist = affiliatedOrganService.selectAffiliatedOrganResList(resPi);
		
		log.debug("resList : " + reslist);
		log.debug("resPi : " + resPi );
		
		//System.out.println(list);
		mv.addObject("affPi", affPi)
		  .addObject("aflist", aflist)
		  .addObject("resPi", resPi)
		  .addObject("reslist", reslist)
		  .addObject("tab", tab)
		  .addObject("myPage", myPage)
		  .setViewName("/affiliatedOrgan/affiliatedOrganList");
		
		return mv;
	}
	
	
	// * ------------------- 부속기관 예약관련 -------------------
	// 부속기관 예약페이지
	@GetMapping("/affiliatedOrganResForm.page")
	public String affiliatedOrganFrom(int no, Model model) {	
		model.addAttribute("affiliatedOrgan",affiliatedOrganService.selectAffiliatedOrgan(no));
		
		return "/affiliatedOrgan/affiliatedOrganRes";
	}
	   
	// 부속기관 예약서비스
	@PostMapping("/affiliatedOrganRes.do")
	public String affiliatedOrganRes(AffReservationDto affres
								   , Model model
								   , RedirectAttributes redirectAttributes) {
		log.debug("{}", affres);
		int result = affiliatedOrganService.insertAffiliatedOrganRes(affres);
		if(result > 0) {
			// 성공메세지
			redirectAttributes.addFlashAttribute("alertMsg", "성공");
		}else{
			// 실패메세지
			redirectAttributes.addFlashAttribute("alertMsg", "실패");
			redirectAttributes.addFlashAttribute("historyBackYN", "Y");
		}
		return "affiliatedOrgan/affiliatedOrganRes";
	}
	
	
	/* 부속기관 예약목록 페이지 조회
	@GetMapping("/affiliatedOrganResList.do")
	public ModelAndView resList(@RequestParam(value="page", defaultValue="1") int currentPage, ModelAndView mv) {
			
		int listCount = affiliatedOrganService.selectAffiliatedOrganResListCount();
		PageInfoDto pi = pagingUtil.getPageInfoDto(listCount, currentPage, 5, 8);
		List<AffReservationDto> reslist = affiliatedOrganService.selectAffiliatedOrganResList(pi);
		System.out.println(reslist);
		mv.addObject("pi", pi)
		  .addObject("reslist", reslist)
		  .setViewName("/affiliatedOrgan/affiliatedOrganResList");
			
			return mv;
		}
*/
	
	// * ------------------- 부속기관 등록관련 -------------------
	// 부속기관 등록페이지 
	@GetMapping("/affiliatedOrganEnrollForm.page")
	public String affiliatedOrganEnrollForm(Model model) {
		List<MajorDto> list = adminService.selectMajorList();
		model.addAttribute("list", list);
		return "/affiliatedOrgan/affiliatedOrganEnrollForm";
	}
	
	// 부속기관 등록서비스
	@PostMapping("/affiliatedOrganEnroll.do")
	public String affiliatedOrganEnroll(AffiliatedOrganDto aff, List<MultipartFile> uploadFiles
									   , HttpSession session, Model model, RedirectAttributes redirectAttributes) {
		
		List<AttachDto> attachList = new ArrayList<>();	
		
		for(MultipartFile uploadFile: uploadFiles) {
			if(uploadFile != null && !uploadFile.isEmpty()) {
				//파일 업로드
				Map<String, String> map = fileUtil.fileUpload(uploadFile, session, "aff");
					
				// insert할 데이터 => AttachDto객체만들기 => attachList쌓기
				attachList.add( AttachDto.builder()
						  .filePath(map.get("filePath"))
						  .filesystemName(map.get("filesystemName"))
						  .originalName(map.get("originalName"))
						  .refType("A")
						  .build());			
			}
		}
		aff.setAttachList(attachList); // 첨부파일이 없었을 경우 텅빈리스트

		int result = affiliatedOrganService.insertAffiliatedOrgan(aff);
				
		if(attachList.isEmpty() && result == 1 || !attachList.isEmpty() && result == attachList.size()) {
			// 성공메세지
			redirectAttributes.addFlashAttribute("alertMsg", "성공");
		}else{
			// 실패메세지
			redirectAttributes.addFlashAttribute("alertMsg", "실패");
			redirectAttributes.addFlashAttribute("historyBackYN", "Y");
		}
		return "redirect:/aff/affiliatedOrganList.do";
	}
	

	// * ------------------- 부속기관 검색관련 -------------------
	// 검색
	@GetMapping("/affiliatedOrganSearch.do")
	public ModelAndView search(@RequestParam(value="page", defaultValue="1") int currentPage,
							   @RequestParam Map<String, String> search,
							   ModelAndView mv) {
		
		log.debug("search :{}", search);
		
		int listCount = affiliatedOrganService.selectAffiliatedOrganSearchListCount(search);
		PageInfoDto pi = pagingUtil.getPageInfoDto(listCount, currentPage, 5, 8);
		List<AffiliatedOrganDto> list = affiliatedOrganService.selectAffiliatedOrganSearchList(search, pi);
		
		mv.addObject("pi", pi)
		  .addObject("list", list)
		  .addObject("search", search)
		  .setViewName("aff/list");
		
		return mv;
	}
	

}
