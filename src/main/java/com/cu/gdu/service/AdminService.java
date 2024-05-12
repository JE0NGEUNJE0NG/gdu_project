package com.cu.gdu.service;

import java.util.List;

import com.cu.gdu.dto.CollegeDto;
import com.cu.gdu.dto.MajorDto;
import com.cu.gdu.dto.MemberDto;
import com.cu.gdu.dto.PageInfoDto;

public interface AdminService {
	
	// 조직도페이지에서 단과조회
	List<CollegeDto> selectCollegeList();
	
	// 조직도페이지에서 학과조회
	List<MajorDto> selectMajorList();
	
	// 직원관리페이지에서 직원수 조회 (페이징)
	int selectMemberListCount();
	List<MemberDto> selectMemberList(PageInfoDto pi);
	
}
