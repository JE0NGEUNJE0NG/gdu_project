package com.cu.gdu.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.cu.gdu.dto.CollegeDto;
import com.cu.gdu.dto.JobDto;
import com.cu.gdu.dto.MajorDto;
import com.cu.gdu.dto.MemberDto;
import com.cu.gdu.dto.PageInfoDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class AdminDao {

	private final SqlSessionTemplate sqlSessionTemplate;
	
	public List<CollegeDto> selectCollegeList(){
		return sqlSessionTemplate.selectList("adminMapper.selectCollegeList");
	}
	
	public List<MajorDto> selectMajorList(){
		return sqlSessionTemplate.selectList("adminMapper.selectMajorList");
	}
	
	public int selectMemberListCount() {
		return sqlSessionTemplate.selectOne("adminMapper.selectMemberListCount");
	}
	
	public List<MemberDto> selectMemberList(PageInfoDto pi){
		int limit = pi.getBoardLimit();
		int offset = (pi.getCurrentPage()-1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		return sqlSessionTemplate.selectList("adminMapper.selectMemberList", pi, rowBounds);
	}
	
	public List<JobDto> selectJobList(){
		return sqlSessionTemplate.selectList("adminMapper.selectJobList");
	}
	
	public int updateOutMember(String[] memNo) {
		return sqlSessionTemplate.update("adminMapper.updateOutMember", memNo);
	}
	
	public int updateMajorMember(Map<String, Object> map) {
		return sqlSessionTemplate.update("adminMapper.updateMajorMember", map);
	}
	
	public int updateJobMember(Map<String, Object> map) {
		return sqlSessionTemplate.update("adminMapper.updateJobMember", map);
	}

	// 학과및직급 필터링으로 ajax통신해 직원수조회 (페이징)
	public int ajaxFilterMemberListCount(MemberDto m) {
		return sqlSessionTemplate.selectOne("adminMapper.ajaxFilterMemberListCount", m);
	}
	
	// 학과및직급 필터링으로 ajax통신해 직원수조회
	public List<MemberDto> ajaxFilterMemberList(Map<String, Object> map){
		PageInfoDto pi = (PageInfoDto)map.get("pi");
		int limit = pi.getBoardLimit();
		int offset = (pi.getCurrentPage()-1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		return sqlSessionTemplate.selectList("adminMapper.ajaxFilterMemberList", map, rowBounds);
	}
	
	// 이름검색으로 ajax통신해 직원수조회 (페이징, 학과직급포함)
	public int ajaxNameFilterMemberListCount(MemberDto m) {
		return sqlSessionTemplate.selectOne("adminMapper.ajaxNameFilterMemberListCount", m);
	}
	
	// 이름검색으로 ajax통신해 직원조회(학과직급포함)
	public List<MemberDto> ajaxNameFilterMemberList(Map<String, Object> map){
		PageInfoDto pi = (PageInfoDto)map.get("pi");
		int limit = pi.getBoardLimit();
		int offset = (pi.getCurrentPage()-1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		return sqlSessionTemplate.selectList("adminMapper.ajaxNameFilterMemberList", map, rowBounds);
	}
	
	public int insertOneMember(MemberDto m) {
		return sqlSessionTemplate.insert("adminMapper.insertOneMember", m);
	}
	
}
