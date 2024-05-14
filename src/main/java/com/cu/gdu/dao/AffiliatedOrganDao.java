package com.cu.gdu.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.cu.gdu.dto.AffiliatedOrganDto;
import com.cu.gdu.dto.MajorDto;
import com.cu.gdu.dto.PageInfoDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class AffiliatedOrganDao {

	private final SqlSessionTemplate sqlSessionTemplate;
	
	public int selectAffiliatedOrganListCount() {
		return sqlSessionTemplate.selectOne("affiliatedOrganMapper.selectAffiliatedOrganListCount");
	}
	
	public List<AffiliatedOrganDto> selectAffiliatedOrganList(PageInfoDto pi){
		
		int limit = pi.getBoardLimit();
		int offset = (pi.getCurrentPage() - 1) * limit;
		
		// RowBounds rowBounds = new RowBounds(몇개의게시글건너뛰고, 몇개조회할건지);
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		return sqlSessionTemplate.selectList("affiliatedOrganMapper.selectAffiliatedOrganList", null, rowBounds);
	}
	
	public int selectAffiliatedOrganSearchListCount(Map<String, String> search) {
		return sqlSessionTemplate.selectOne("affiliatedOrganMapper.selectAffiliatedOrganSearchListCount", search);
	}

	public AffiliatedOrganDto selectAffiliatedOrganRes(int affNo) {
		return sqlSessionTemplate.selectOne("affiliatedOrganMapper.selectAffiliatedOrganRes", affNo);
	}
	
	public AffiliatedOrganDto selectAffiliatedOrganEnroll(int affNo) {
		return null;
	}
	
	public int insertAffiliatedOrgan(AffiliatedOrganDto aff) {
		return sqlSessionTemplate.insert("affiliatedOrganMapper.insertAffiliatedOrgan", aff);
	}

	

	/*
	public List<AffiliatedOrganDto> selectSearchList(Map<String, String> search, PageInfoDto pi){
		RowBounds rowBounds = new RowBounds((pi.getCurrentPage()-1) * pi.getBoardLimit(), pi.getBoardLimit());
		return sqlSessionTemplate.selectList("affiliatedOrganMapper.selectSearchList", search, rowBounds);
	}
	*/
	
	
}
