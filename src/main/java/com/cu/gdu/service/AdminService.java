package com.cu.gdu.service;

import java.util.List;
import java.util.Map;

import com.cu.gdu.dto.CollegeDto;
import com.cu.gdu.dto.JobDto;
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
	
	// 직원관리페이지에서 직급조회
	List<JobDto> selectJobList();
	
	// 직원관리페이지에서 직원퇴직처리
	int updateOutMember(String[] memNo);
	
	// 직원관리페이지에서 직원학과수정
	int updateMajorMember(Map<String, Object> map);
	
	// 직원관리페이지에서 직원직급수정
	int updateJobMember(Map<String, Object> map);
	
	// 학과 및 직급필터링으로 ajax통신해 직원조회 및 페이징
	int ajaxFilterMemberListCount(MemberDto m);
	List<MemberDto> ajaxFilterMemberList(Map<String, Object> map);
	
	// 이름 검색으로 ajax 통신해 직원조회 및 페이징 (학과직급 검사 포함)
	int ajaxNameFilterMemberListCount(MemberDto m);
	List<MemberDto> ajaxNameFilterMemberList(Map<String, Object> map);
	
	// 직원개별등록
	int insertOneMember(MemberDto m);
	
}
